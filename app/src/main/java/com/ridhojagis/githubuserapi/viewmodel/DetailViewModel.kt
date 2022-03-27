package com.ridhojagis.githubuserapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ridhojagis.githubuserapi.config.ApiConfig
import com.ridhojagis.githubuserapi.config.Event
import com.ridhojagis.githubuserapi.data.model.UserDetailResponse
import com.ridhojagis.githubuserapi.data.model.UserListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    private val _followers = MutableLiveData<List<UserListItem>>()
    val followers: LiveData<List<UserListItem>> = _followers

    private val _following = MutableLiveData<List<UserListItem>>()
    val following: LiveData<List<UserListItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun getFollowers(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<UserListItem>> {
            override fun onResponse(
                call: Call<List<UserListItem>>,
                response: Response<List<UserListItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                    _toastText.value = Event("Success")
                } else {
                    _toastText.value = Event("Tidak ada data yang ditampilkan!")
                }
            }
            override fun onFailure(call: Call<List<UserListItem>>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event("onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UserListItem>> {
            override fun onResponse(
                call: Call<List<UserListItem>>,
                response: Response<List<UserListItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: gagal")
                }
            }
            override fun onFailure(call: Call<List<UserListItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: gagal")
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}