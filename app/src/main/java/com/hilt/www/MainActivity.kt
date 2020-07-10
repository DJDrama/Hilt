package com.hilt.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /** Field Injection **/
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, someClass.doAThing())
        Log.d(TAG, someClass.doOtherThing())
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

class SomeClass
@Inject  /** Constructor Injection **/
constructor(
        private val otherClass: OtherClass
) {

    fun doAThing(): String {
        return "Just do it!"
    }

    fun doOtherThing(): String{
        return otherClass.doOtherThing()
    }
}

class OtherClass
@Inject
constructor() {
    fun doOtherThing(): String {
        return "Do other thing!"
    }
}