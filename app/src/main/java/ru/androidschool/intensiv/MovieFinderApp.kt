package ru.androidschool.intensiv

import android.app.Application
import ru.androidschool.intensiv.di.core.CoreComponent
import ru.androidschool.intensiv.di.core.DaggerCoreComponent
import ru.androidschool.intensiv.di.core.DatabaseModule
import ru.androidschool.intensiv.di.core.NetworkModule
import timber.log.Timber

class MovieFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDebugTools()
        coreDaggerComponent = DaggerCoreComponent.builder()
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule(this))
            .build()
    }

    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var instance: MovieFinderApp
            private set

        lateinit var coreDaggerComponent: CoreComponent
            private set
    }
}
