@file:Suppress("DEPRECATION")

package gdsvn.tringuyen.moviesreview.presentation.ui.activity.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.facebook.*
import com.facebook.FacebookSdk.sdkInitialize
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.main.HomeMovieActivity
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //Global variables
    private var email: String? = null
    private var password: String? = null

    //UI elements
    private var tvForgotPassword: TextView? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Firebase references
    private var mAuth: FirebaseAuth? = null
    private var mCallbackManager: CallbackManager? = null
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val rootView: View = window.decorView.findViewById(android.R.id.content)
        rootView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        //Set icon
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        sdkInitialize(this.applicationContext);
        setContentView(R.layout.activity_login)
        initialise()
        processEvents()
    }



    private fun processEvents() {
        bt_back.setOnClickListener {
            finish()
        }

        sign_up.setOnClickListener { startActivity(Intent(this, RegistrationActivity::class.java)) }

        tvForgotPassword!!.setOnClickListener { startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java)) }

        btnLogin!!.setOnClickListener { loginUser() }


        loginWithFaceBook()

        loginWithGoogle()

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = mAuth?.currentUser
        if (currentUser != null) {
            Timber.tag(TAG).i("onStart: Someone logged in <3")
            updateUI()
        } else {
            Timber.tag(TAG).i("onStart: No one logged in :/")
        }
    }

    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgressBar!!.setMessage("Registering User...")
            mProgressBar!!.show()

            Log.d(TAG, "Logging in user.")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->

                    mProgressBar!!.hide()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginWithGoogle() {
        google_button.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }




    private fun loginWithFaceBook() {
        //Setting the permission that we need to read
        btn_login_fb.setReadPermissions("public_profile", "email", "user_birthday")
        //Registering callback!
        btn_login_fb.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) { //Sign in completed
                Timber.tag(TAG).i("onSuccess: logged in successfully")
                //handling the token for Firebase Auth
                handleFacebookAccessToken(loginResult.accessToken)
                //Getting the user information
                val request = GraphRequest.newMeRequest(
                    loginResult.accessToken
                ) { `object`, response ->
                    // Application code
                    Timber.tag(TAG).i("onCompleted: response: $response")
                    try {
                        val email = `object`.getString("email")
                        val birthday = `object`.getString("birthday")
                        Timber.tag(TAG).i("onCompleted: Email: $email")
                        Timber.tag(TAG).i("onCompleted: Birthday: $birthday")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Timber.tag(TAG).i(
                            "onCompleted: JSON exception"
                        )
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender,birthday")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                Timber.tag(TAG).i("facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Timber.tag(TAG).i("facebook:onError $error")
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Timber.tag(TAG).i("handleFacebookAccessToken:$token")
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(
                this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        Timber.tag(TAG).i("signInWithCredential:success")
                        val user: FirebaseUser? = mAuth!!.currentUser
                        Timber.tag(TAG).i("onComplete: login completed with user:  ${user?.displayName}")
                        updateUI()
                    } else { // If sign in fails, display a message to the user.
                        Timber.tag(TAG).i("signInWithCredential:failure ${task.exception}")
                        Toast.makeText(
                            this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }


    private fun initialise() {
        tvForgotPassword = findViewById<View>(R.id.tv_forgot_password) as TextView
        etEmail = findViewById<View>(R.id.et_email_address) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnLogin = findViewById<View>(R.id.btn_login) as Button
        mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create()
        configureGoogleSignIn()
    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, HomeMovieActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_LONG).show()
            }
        }
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth?.signInWithCredential(credential)?.addOnCompleteListener {
            if (it.isSuccessful) {
                updateUI()
            } else {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_LONG).show()
            }
        }
    }


}