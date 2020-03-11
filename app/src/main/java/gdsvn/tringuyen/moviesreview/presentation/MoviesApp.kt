package gdsvn.tringuyen.moviesreview.presentation

import android.app.Application
import gdsvn.tringuyen.moviesreview.BuildConfig
import gdsvn.tringuyen.moviesreview.presentation.di.mNetworkModules
import gdsvn.tringuyen.moviesreview.presentation.di.mRepositoryModules
import gdsvn.tringuyen.moviesreview.presentation.di.mUseCaseModules
import gdsvn.tringuyen.moviesreview.presentation.di.mViewModels

import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        loadKoin()
    }

    private fun loadKoin() {
        startKoin(this,
                listOf( mNetworkModules,
                        mViewModels,
                        mRepositoryModules,
                        mUseCaseModules)

        )

    }
}