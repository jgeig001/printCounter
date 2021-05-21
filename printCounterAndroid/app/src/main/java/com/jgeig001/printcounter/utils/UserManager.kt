package com.jgeig001.printcounter.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.jgeig001.printcounter.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles the unique user ID.
 */
class UserManager @Inject constructor(
    private val context: Context,
    private val userRepository: UserRepository
) {

    private val USER_KEY = "THIS_USER"

    /**
     * Do I exist ?
     */
    fun userExists(): Boolean {
        val userID = getUserID()
        return userID != SharedPreferencesManager.DEFAULT_INT
    }

    /**
     * Create me !
     */
    fun saveNewUserID(id: Int) {
        SharedPreferencesManager.writeInt(context, USER_KEY, id)
    }

    /**
     * Delete me !
     */
    fun deleteUser(activity: Activity) {
        val userID = getUserID()
        GlobalScope.launch(Dispatchers.IO) {
            userRepository.deleteUser(userID).body()?.let { wasDeleted ->
                if (wasDeleted) {
                    quitApp(activity)
                } else {
                    Toast.makeText(activity, "Fehlgeschlagen", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(activity, "Fehlgeschlagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Who am I ?
     */
    fun getUserID(): Int {
        return SharedPreferencesManager.getInt(context, USER_KEY)
    }

    private fun quitApp(activity: Activity) {
        SharedPreferencesManager.writeInt(
            context,
            USER_KEY,
            SharedPreferencesManager.DEFAULT_INT
        )
        activity.finishAndRemoveTask()
    }

}