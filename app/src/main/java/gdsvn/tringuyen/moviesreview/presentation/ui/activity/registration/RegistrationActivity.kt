package gdsvn.tringuyen.moviesreview.presentation.ui.activity.registration

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.login.LoginActivity
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.main.HomeMovieActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.bt_back
import kotlinx.android.synthetic.main.activity_registration.*
import timber.log.Timber

class RegistrationActivity : AppCompatActivity() {

    private val TAG = "RegistrationActivity"

    //UI elements
    private var etFullName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //global variables
    private var fullName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        //Set icon
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initialise()
        processEvents()

    }

    private fun processEvents() {
        bt_back.setOnClickListener {
            finish()
        }

        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnCreateAccount!!.setOnClickListener { createNewAccount() }

    }

    private fun initialise() {
        etFullName = findViewById<View>(R.id.et_full_name) as EditText
        etEmail = findViewById<View>(R.id.et_email_address) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnCreateAccount = findViewById<View>(R.id.btn_signup) as Button
        mProgressBar = ProgressDialog(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

    }

    private fun createNewAccount() {
        fullName = et_full_name?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgressBar!!.setMessage("Registering User...")
            mProgressBar!!.show()

            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    mProgressBar!!.hide()

                    Log.e(TAG, "createUserWithEmail:task ----> ${task.result.toString()}")


                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        val userId = mAuth!!.currentUser!!.uid

                        //Verify Email
                        verifyEmail()

                        //update user profile information
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("fullName").setValue(fullName)

                        updateUserInfoAndUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@RegistrationActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@RegistrationActivity, HomeMovieActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Toast.makeText(this@RegistrationActivity,
                        "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@RegistrationActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}