package com.example.userdirectory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    val userList: MutableLiveData<MutableList<User>> by lazy { MutableLiveData<MutableList<User>>() }

    fun addUser(user: User) {
        val currentList = userList.value ?: mutableListOf()
        currentList.add(user)
        userList.value = currentList
    }

    fun removeUser(position: Int) {
        val currentList = userList.value ?: mutableListOf()
        currentList.removeAt(position)
        userList.value = currentList
    }
}