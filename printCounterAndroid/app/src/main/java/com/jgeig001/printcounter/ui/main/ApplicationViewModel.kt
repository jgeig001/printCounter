package com.jgeig001.printcounter.ui.main

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeig001.printcounter.model.domain.User
import com.jgeig001.printcounter.model.repository.UserRepository
import com.jgeig001.printcounter.utils.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val userManager: UserManager,
    private val userRepository: UserRepository,
) : ViewModel() {

    var messageToUserLiveData: MutableLiveData<String> = MutableLiveData()
    var isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var userLiveData: MutableLiveData<User> = MutableLiveData()

    fun fetchUser() {
        viewModelScope.launch {
            coroutineScope {
                isLoadingLiveData.postValue(true)
                try {
                    val userID = userManager.getUserID()
                    val response = userRepository.getUser(userID)
                    if (response.isSuccessful) {
                        val user = response.body()
                        userLiveData.postValue(user)
                    }

                } catch (e: SocketTimeoutException) {
                    messageToUserLiveData.postValue("Es konnte keine Verbindung zum Server hergestellt werden. Prüfe deine Internetverbindung und versuche es später erneut.")
                } catch (e: Exception) {
                    throw NetworkErrorException("Probleme mit der Internetverbindung")
                } finally {
                    isLoadingLiveData.postValue(false)
                }
            }
        }
    }

    fun createNewUser(userName: String) {
        viewModelScope.launch {
            coroutineScope {
                isLoadingLiveData.postValue(true)
                try {
                    val response = userRepository.newUser(userName)
                    if (response.isSuccessful) {
                        response.body()?.let { user ->
                            userLiveData.postValue(user)
                            userManager.saveNewUserID(user.id)
                        } ?: run {
                            throw NetworkErrorException("not possible to create new user")
                        }
                    }
                } finally {
                    isLoadingLiveData.postValue(false)
                }
            }
        }
    }

}