package com.rwbuild.bagtest.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rwbuild.bagtest.Models.UserModel
import com.rwbuild.bagtest.Repositories.UserRepository
import com.rwbuild.bagtest.Utils.Resource
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val _userModelLiveData: MutableLiveData<Resource<UserModel>> = userRepository.getUserModelLiveData()
    val userModelLiveData: LiveData<Resource<UserModel>> get() = _userModelLiveData

    fun fetchUsers() {
        viewModelScope.launch {
            userRepository.fetchUsers()
        }
    }
}