package id.ac.ui.cs.mobileprogramming.jeffrey.remindme

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui.TodoListActivity

class SplashActivity : Activity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashActivity, TodoListActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}