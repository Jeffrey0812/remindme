package id.ac.ui.cs.mobileprogramming.jeffrey.remindme

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: MyGLRenderer

    init {
        setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
        renderer = MyGLRenderer()
        setRenderer(renderer)
    }
}