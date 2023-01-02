package com.sorto.pokesortoball

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sorto.pokesortoball.databinding.ActivityLoginBinding
import com.sorto.pokesortoball.databinding.ActivitySignupBinding
import com.sorto.pokesortoball.utils.SavedPreference

class RegisterActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    private lateinit var binding: ActivitySignupBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createAccountInputsArray = arrayOf(binding.txtEmail, binding.txtPassword, binding.txtPassword)

        FirebaseApp.initializeApp(this)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.imgRegister.setOnClickListener { view: View ->
            signInUserEmail()
        }
    }
    private fun notEmpty(): Boolean = binding.txtEmail.text.toString().trim().isNotEmpty() &&
            binding.txtPassword.text.toString().trim().isNotEmpty() &&
            binding.txtPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            binding.txtPassword.text.toString().trim() == binding.txtPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            //no son correctas
        }
        return identical
    }
    private fun signInUserEmail() {
        if (identicalPassword()) {
            userEmail = binding.txtEmail.text.toString().trim()
            userPassword = binding.txtPassword.text.toString().trim()


            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification()
                        firebaseUser = firebaseAuth.currentUser!!;
                        startActivity(Intent(this, MainActivity::class.java))
                        SavedPreference.setEmail(this,task.result!!.user!!.email.toString())
                        SavedPreference.setUsername(this,"Ash Ketchum")
                        SavedPreference.setPhoto(this,"https://i.pinimg.com/736x/39/d0/02/39d0020fa964577a1c69fdcc3ab24eab.jpg")
                        finish()
                    } else {
                        //fallo mostrar mensaje
                    }
                }
        }
    }
    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //
                }
            }
        }
    }
    /*
    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))

        }
    }*/
}