package id.ac.ui.cs.mobileprogramming.jeffrey.remindme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val glView =  MyGLSurfaceView(this)
        setContentView(glView)
    }
}