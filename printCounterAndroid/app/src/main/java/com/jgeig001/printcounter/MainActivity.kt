package com.jgeig001.printcounter

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jgeig001.printcounter.model.domain.printTypes.ColorType
import com.jgeig001.printcounter.model.domain.printTypes.PrintType
import com.jgeig001.printcounter.model.repository.UserRepository
import com.jgeig001.printcounter.ui.main.ApplicationViewModel
import com.jgeig001.printcounter.utils.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val applicationViewModel: ApplicationViewModel by viewModels()

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        observeLiveData()

        if (userManager.userExists()) {
            applicationViewModel.fetchUser()
        } else {
            // create new account: open dialog
            showDialog()
        }
    }

    private fun showDialog() {
        val editTextView = EditText(this)
        editTextView.inputType = InputType.TYPE_CLASS_TEXT
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.new_usre_dialog))
            .setView(editTextView)
            .setNeutralButton(getString(R.string.confirm)) { _, _ ->
                val userName = editTextView.text.toString()
                applicationViewModel.createNewUser(userName)
            }
        dialog.show()
    }

    private fun observeLiveData() {
        applicationViewModel.messageToUserLiveData.observe(this, { str ->
            if (str != "") {
                Toast.makeText(this, str, Toast.LENGTH_LONG).show()
                applicationViewModel.messageToUserLiveData.postValue("")
            }
        })
        applicationViewModel.isLoadingLiveData.observe(this, { isLoading ->
            toggleGlobalLoadingAnimation(isLoading)
        })
        applicationViewModel.userLiveData.observe(this, { user ->
        })
    }

    private fun toggleGlobalLoadingAnimation(on: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.global_loading_bar)
        val background = findViewById<FrameLayout>(R.id.disabled_skin)
        when (on) {
            true -> {
                progressBar.visibility = View.VISIBLE
                background.visibility = View.VISIBLE
            }
            false -> {
                progressBar.visibility = View.INVISIBLE
                background.visibility = View.INVISIBLE
            }
        }
    }

}