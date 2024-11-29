package com.example.tyme.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tyme.R
import com.example.tyme.databinding.ActivityUserDetailBinding
import com.example.tyme.ext.displayData
import com.example.tyme.ext.viewBinding
import com.example.tyme.utils.BaseActivity
import com.example.tyme.view.home.MainActivity.Companion.KEY_ID
import com.example.tyme.viewmodels.UserDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailActivity : BaseActivity(R.layout.activity_user_detail) {

    private val viewModel: UserDetailViewModel by viewModel()
    private val binding by viewBinding(ActivityUserDetailBinding::bind)

    override fun bindView() = with(binding) {
        setSupportActionBar(materialToolbar)
        materialToolbar.title = "User Details"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun observer() {
        viewModel.apply {
            onUserDetail.observe(this@UserDetailActivity) { user ->
                with(binding) {
                    tvName.text = user.login ?: ""
                    tvLocation.text = user.location ?: ""
                    Glide.with(root).load(user.avatarUrl).into(imgAvatar)
                    tvBlog.text = user.blog ?: ""
                    tvCountFollowers.text = user.followers.displayData()
                    tvCountFollowing.text = user.following.displayData()
                }
            }
            onErrorResponse.observe(this@UserDetailActivity) {
                Toast.makeText(this@UserDetailActivity, it, Toast.LENGTH_SHORT).show()
            }
            onShowLoading.observe(this@UserDetailActivity) {
                binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userName: String = intent.getStringExtra(KEY_ID).toString()
        if (userName.isNotEmpty()) viewModel.getUserDetail(userName)
    }
}