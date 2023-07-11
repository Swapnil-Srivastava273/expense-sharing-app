package com.swaps273.expense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.swaps273.expense.ui.login.LoginActivity
import java.util.logging.Handler

class SplashScreen : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        mAuth= FirebaseAuth.getInstance()
        var user=mAuth.currentUser

            if(user!=null){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this,SignInActivity::class.java))
                finish()
            }

    }
}