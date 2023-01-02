package com.sorto.pokesortoball

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sorto.pokesortoball.databinding.ActivityLoginBinding
import com.sorto.pokesortoball.utils.SavedPreference
import java.util.*


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    private lateinit var firebaseAuth: FirebaseAuth
    var callbackManager: CallbackManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signInInputsArray = arrayOf(binding.txtEmail, binding.txtPassword)
        FirebaseApp.initializeApp(this)
        callbackManager = CallbackManager.Factory.create()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.btnGoogle.setOnClickListener{ view: View ->
            //Toast.makeText(this,"Logging In",Toast.LENGTH_SHORT).show()
            val snack = Snackbar.make(view,"Logging In",Snackbar.LENGTH_LONG)
            snack.show()
            signInGoogle()
        }

        binding.btnFacebook.setOnClickListener { view: View ->
            val snack = Snackbar.make(view,"Logging In",Snackbar.LENGTH_LONG)
            snack.show()
            signInFacebook()
        }

        binding.imgLogin.setOnClickListener { view: View ->
            signInUserEmail()
        }
        binding.lblRegister.setOnClickListener { view: View ->
            val snack = Snackbar.make(view,"Register",Snackbar.LENGTH_LONG)
            snack.show()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInGoogle(){
        val signInIntent: Intent =mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)
    }
    private fun signInFacebook(){
       // Toast.makeText(this,"prueba", Toast.LENGTH_SHORT).show()
        LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("email"))
        LoginManager.getInstance().registerCallback(callbackManager,object: FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAccessToken(result!!.accessToken)

            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                Log.e("fbError", error?.message.toString())
            }

        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken?){
        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnFailureListener {e ->
              Toast.makeText(this,"Fb" +e.message,Toast.LENGTH_LONG).show()
                Log.e("ERROR_FB",e.message.toString())
            }
            .addOnSuccessListener { result ->
                SavedPreference.setEmail(this, result.user?.email.toString())
                SavedPreference.setUsername(this, result.user?.displayName.toString())
                Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //updateUIFB()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager!!.onActivityResult(requestCode,resultCode,data)

        if(requestCode==Req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
//            firebaseAuthWithGoogle(account!!)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException){
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                SavedPreference.setEmail(this,account.email.toString())
                SavedPreference.setUsername(this,account.displayName.toString())
                SavedPreference.setPhoto(this,account.photoUrl.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun updateUIFB(user: FirebaseUser){

    }
    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUserEmail(){
        signInEmail = binding.txtEmail.text.toString().trim()
        signInPassword = binding.txtPassword.text.toString().trim()

        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        SavedPreference.setEmail(this,signIn.result!!.user!!.email.toString())
                        SavedPreference.setUsername(this,"Ash Ketchum")
                        SavedPreference.setPhoto(this,"https://i.pinimg.com/736x/39/d0/02/39d0020fa964577a1c69fdcc3ab24eab.jpg")
                        finish()
                    } else {
                        //
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }

    }
    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}