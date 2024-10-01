package com.rwbuild.bagtest.Repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.rwbuild.bagtest.Models.UserModel
import com.rwbuild.bagtest.Services.RetrofitClient
import com.rwbuild.bagtest.Utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    private val apiService = RetrofitClient.api
    private val _userModelLiveData = MutableLiveData<Resource<UserModel>>()

    fun getUserModelLiveData(): MutableLiveData<Resource<UserModel>> {
        return _userModelLiveData
    }

    /**
     * Fetches the list of users from the API.
     */
    suspend fun fetchUsers() {
        _userModelLiveData.postValue(Resource.loading(null))

        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getUsers()
            }


            if (response.isSuccessful && response.body() != null) {
                if (response.body().toString().trim().startsWith("{")) {
                    try {
                        val parsedObject = Gson().fromJson(response.body()?.toString(), UserModel::class.java)
                        _userModelLiveData.postValue(Resource.success(parsedObject))
                    } catch (e: Exception) {
                        Log.e("parsing_error", "JSON Parsing error: ${e.message}")
                        _userModelLiveData.postValue(Resource.error("JSON Parsing error", null))
                    }
                } else {
                    _userModelLiveData.postValue(Resource.success(response.body()))
                }
            } else {
                _userModelLiveData.postValue(Resource.error("Error: ${response.message()}", null))
            }

        } catch (e: Exception) {
            Log.e("network_error", "Exception: ${e.message}")
            _userModelLiveData.postValue(Resource.error("Exception: ${e.message}", null))
        }
    }
}