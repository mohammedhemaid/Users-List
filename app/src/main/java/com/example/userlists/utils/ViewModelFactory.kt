package com.example.userlists.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userlists.api.RestRepository

class ViewModelFactory(
        private val context: Context,
        private val restRepository: RestRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Context::class.java, RestRepository::class.java).newInstance(context, restRepository)
    }
}