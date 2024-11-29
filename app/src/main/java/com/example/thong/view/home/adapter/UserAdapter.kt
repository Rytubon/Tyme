package com.example.thong.view.home.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thong.databinding.UserItemRowBinding
import com.example.thong.models.User


class UserAdapter(private val onItemClick: ((User) -> Unit)) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<User>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private var binding: UserItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                tvName.text = user.login
                tvHtmlUrl.text = user.htmlUrl
                tvHtmlUrl.paintFlags =
                    tvHtmlUrl.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                Glide.with(binding.root)
                    .load(user.avatarUrl)
                    .into(binding.imgAvatar)
                root.setOnClickListener {
                    onItemClick.invoke(user)
                }
            }
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}