package gdsvn.tringuyen.moviesreview.presentation.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import gdsvn.tringuyen.moviesreview.R
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.login.LoginActivity
import gdsvn.tringuyen.moviesreview.presentation.ui.activity.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        processEvents()
    }

    private fun processEvents() {
        btn_login.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        btn_get_started.setOnClickListener { startActivity(Intent(this, RegistrationActivity::class.java)) }
    }

    /**
     * Set full-screen Splash Screen
     */
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
}