package com.example.userlists.usersList

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.userlists.api.RestRepository
import com.example.userlists.api.servicegenerator.RetrofitService.getPostsService
import com.example.userlists.commondialogs.TimeOutDialog
import com.example.userlists.userdetails.model.UserDetails
import com.example.userlists.settings.SettingsActivity
import com.example.userlists.utils.ViewModelFactory
import com.example.userlists.utils.observe
import com.example.userlists.R
import com.example.userlists.databinding.ActivityPostListBinding
import com.example.userlists.userdetails.DetailsActivity
import com.example.userlists.userdetails.EXTRA_USER_DETAILS
import com.example.userlists.utils.Event


class UsersListActivity : AppCompatActivity(), UsersAdapter.Listener {

    private lateinit var binding: ActivityPostListBinding
    private lateinit var usersListViewModel: UsersListViewModel
    private lateinit var refreshUsers: SwipeRefreshLayout
    private lateinit var noInternet: LinearLayout
    private lateinit var userList: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var timeOutDialog: TimeOutDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        noInternet = binding.noInternet
        userList = binding.userList
        refreshUsers = binding.refreshUsers

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_divider)!!)
        userList.addItemDecoration(itemDecorator)
        usersAdapter = UsersAdapter(this)
        userList.adapter = usersAdapter

        usersListViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this, RestRepository(getPostsService()))
        ).get(UsersListViewModel::class.java)
        observeViewModel()

        timeOutDialog = TimeOutDialog(this) {
            usersListViewModel.fetchUsers()
        }
        onNoInternetRetryClick()
        refreshUsers.setOnRefreshListener {
            usersListViewModel.fetchUsers()
        }
    }

    private fun observeViewModel() {
        observe(usersListViewModel.userDetailsList, ::handlePostList)
        observe(usersListViewModel.progress, ::handelProgress)
        observe(usersListViewModel.notInternet, ::handelNoInternet)
        observe(usersListViewModel.timeOutDialog, ::handelTimeOut)
        observe(usersListViewModel.toastMessage, ::handelToastError)
    }

    private fun handlePostList(userDetails: List<UserDetails>) {
        if (userDetails.isNotEmpty()) {
            binding.noContent.visibility = View.GONE
            usersAdapter.data = userDetails
        } else {
            binding.noContent.visibility = View.VISIBLE
        }
    }

    private fun handelNoInternet(noInternet: Boolean) {
        this.noInternet.isVisible = noInternet
        userList.isVisible = !noInternet
    }

    private fun handelTimeOut(message: Int) {
        timeOutDialog.show(message)
    }

    private fun handelProgress(showProgress: Boolean) {
        refreshUsers.isRefreshing = showProgress
    }

    private fun handelToastError(message: Event<String>) {
        message.getContentIfNotHandled()?.let {
            Toast.makeText(this, message.peekContent(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBodyClick(userDetails: UserDetails) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_USER_DETAILS, userDetails)
        startActivity(intent)
    }

    private fun onNoInternetRetryClick() {
        binding.retry.setOnClickListener {
            usersListViewModel.fetchUsers()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        } else super.onOptionsItemSelected(item)
    }
}