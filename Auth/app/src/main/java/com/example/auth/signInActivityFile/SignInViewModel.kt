package com.example.auth.signInActivityFile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository) : ViewModel() {
    private val _signInStatus = MutableLiveData<Boolean>()
    val signInStatus: LiveData<Boolean> get() = _signInStatus

    private val _userType = MutableLiveData<String?>()
    val userType: LiveData<String?> get() = _userType

    private val _userName = MutableLiveData<String?>()
    val userName :LiveData<String?> get() = _userName

    private val _userEmail = MutableLiveData<String?>()
    val userEmail :LiveData<String?> get() = _userEmail

    //This function is to sign in the user through User Repository
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            repository.authentication(email, password) { isSuccess, uid ->
                _signInStatus.value = isSuccess

                if(isSuccess){
                    repository.getUserType(uid){userType,userName, userEmail ->
                        _userType.value = userType
                        _userName.value = userName
                        _userEmail.value = userEmail
//                        Log.i("MyTag", "Sign In View Model the email is $userEmail")
                    }
                }
            }
        }
    }

    //This function is to check the user whether the user already sign in or not
    fun checkUserAlreadySignedIn() {
        repository.getCurrentUser(){ isSuccess, uid, email->
            _signInStatus.value = isSuccess
            _userEmail.value = email
            if(isSuccess) {
                _userEmail.value = email
                repository.getUserType(uid) { userType, userName, userEmail ->
                    _userType.value = userType
                    _userName.value = userName
                    _userEmail.value = userEmail
//                    Log.i("MyTag", "Sign In View Model the email is $userEmail")
                }
            }
        }
    }

    //This function is to get the user details from repository and save in live data
    fun checkUserDetails() {
        repository.getCurrentUser(){ isSuccess, uid, email->
            _signInStatus.value = isSuccess
            _userEmail.value = email
            if(isSuccess) {
                _userEmail.value = email
                repository.getUserType(uid) { userType, userName, userEmail ->
                    _userType.value = userType
                    _userName.value = userName
                    _userEmail.value = userEmail
//                    Log.i("MyTag", "Sign In View Model the email is $userEmail")
                }
            }
        }
    }

}