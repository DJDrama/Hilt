package com.hilt.www

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /** Field Injection **/
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, someClass.doAThing1())
        Log.d(TAG, someClass.doAThing2())
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

@AndroidEntryPoint
class MyFragment : Fragment() {
    /** Field Injection **/
    @Inject
    lateinit var someClass: SomeClass
}


//@Singleton
//@ActivityScoped
//@FragmentScoped
class SomeClass
@Inject
/** Constructor Injection **/
constructor(
    @Impl1 private val someInterfaceImpl1: SomeInterface,
    @Impl2 private val someInterfaceImpl2: SomeInterface,
    private val gson: Gson
) {
    fun doAThing1(): String {
        return "Do a thing: ${someInterfaceImpl1.getAThing()}"
    }
    fun doAThing2(): String {
        return "Do a thing: ${someInterfaceImpl2.getAThing()}"
    }
}


class SomeInterfaceImpl
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing"
    }
}

class SomeInterfaceImpl1
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A Thing2"
    }
}

interface SomeInterface {
    fun getAThing(): String
}


///** using binds **/
//@Module
//@InstallIn(ApplicationComponent::class) //alive as long as application is alive
////@InstallIn(ActivityComponent::class) //alive as long as application is alive
//abstract class MyModule {
//
//    @Singleton // <-> @InstallIn(ApplicationComponent::class)
//    //@ActivityScoped // <-> @InstallIn(ActivityComponent::class)
//    @Binds
//    abstract fun bindSomeDependency(
//        someImpl: SomeInterfaceImpl
//    ): SomeInterface
//
//    @Singleton
//    @Binds
//    abstract fun bindGson(
//        gson: Gson
//    ): Gson
//}

/** using provides **/
@Module
@InstallIn(ActivityComponent::class)
class AnotherModule {

    @ActivityScoped
    @Provides
    @Impl1
    fun provideSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl1()
    }

    @ActivityScoped
    @Provides
    @Impl2
    fun provideSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }

    @ActivityScoped
    @Provides
    fun provideGson(): Gson{
        return Gson()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2