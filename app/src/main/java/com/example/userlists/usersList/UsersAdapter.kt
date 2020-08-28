package com.example.userlists.usersList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userlists.databinding.PostItemBinding
import com.example.userlists.userdetails.model.UserDetails

class UsersAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<UsersAdapter.PostViewHolder>() {

    interface Listener {
        fun onBodyClick(userDetails: UserDetails)
    }

    var data: List<UserDetails> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.loadPosts(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class PostViewHolder(itemBinding: PostItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private val firstName = itemBinding.firstName
        private lateinit var userDetails: UserDetails

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun loadPosts(userDetails: UserDetails) {
            this.userDetails = userDetails
            firstName.text = userDetails.firstName
        }

        override fun onClick(view: View?) {
            listener.onBodyClick(userDetails)
        }
    }
}