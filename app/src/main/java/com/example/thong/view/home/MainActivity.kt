package com.example.thong.view.home

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.bundle.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thong.R
import com.example.thong.databinding.ActivityMainBinding
import com.example.thong.ext.viewBinding
import com.example.thong.utils.BaseActivity
import com.example.thong.utils.EndlessRecyclerOnScrollListener
import com.example.thong.view.detail.UserDetailActivity
import com.example.thong.view.home.adapter.UserAdapter
import com.example.thong.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(R.layout.activity_main) {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private lateinit var adapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun bindView() = with(binding) {

        //init action bar
        setSupportActionBar(materialToolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //init recyclerview
        adapter = UserAdapter { user ->
            val bundle = bundleOf(KEY_ID to user.login)
            val myIntent: Intent = Intent(
                this@MainActivity,
                UserDetailActivity::class.java
            ).putExtras(bundle)
            this@MainActivity.startActivity(myIntent)
        }
        layoutManager = LinearLayoutManager(this@MainActivity)
        rvUser.layoutManager = layoutManager
        rvUser.adapter = adapter
        rvUser.setHasFixedSize(true)
        rvUser.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                viewModel.getUsers(false)
            }
        })
    }

    override fun observer() {
        viewModel.apply {
            onUser.observe(this@MainActivity) {
                adapter.submitList(it.map { user ->
                    user.copy()
                })
            }
            onErrorResponse.observe(this@MainActivity) {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
            onShowLoading.observe(this@MainActivity) {
                binding.progressCircular.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val KEY_ID = "id"
    }
}
