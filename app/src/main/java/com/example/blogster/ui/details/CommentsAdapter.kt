package com.example.blogster.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.blogster.R
import com.example.blogster.data.remote.models.Comment
import com.example.blogster.databinding.ItemCommentBinding

class CommentsAdapter() :
    ListAdapter<Comment, CommentsAdapter.CommentViewHolder>(
        object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    ) {
    inner class CommentViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.commentTv.text=comment.body
            binding.nameTv.text=comment.author.username
            binding.profileIv.load(comment.author.image){
                placeholder(R.drawable.ic_profile_default)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}