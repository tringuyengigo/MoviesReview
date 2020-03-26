package gdsvn.tringuyen.moviesreview.presentation.di

import gdsvn.tringuyen.moviesreview.data.remote.api.MoviesApi
import gdsvn.tringuyen.moviesreview.data.responsitory.MoviesRemoteRepositoryImpl
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMovieDetailUseCase
import gdsvn.tringuyen.moviesreview.domain.usecase.GetMoviesPopularUseCase
import gdsvn.tringuyen.moviesreview.presentation.common.base.AsyncFlowableTransformer
import gdsvn.tringuyen.moviesreview.presentation.common.utils.BarcodeFactory
import gdsvn.tringuyen.moviesreview.presentation.vm.moviedetail.MovieDetailViewModel
import gdsvn.tringuyen.moviesreview.presentation.vm.popular.MoviesPopularViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit

val mRepositoryModules = module {
    single(name = REMOTE) { MoviesRemoteRepositoryImpl(apiService = get(API))}
}

val mUseCaseModules = module {
    factory(name = MOVIES_POPULAR) { GetMoviesPopularUseCase(transformer = AsyncFlowableTransformer(), repositories = get(REMOTE)) }
    factory(name = MOVIE_DETAIL) { GetMovieDetailUseCase(transformer = AsyncFlowableTransformer(), repositories = get(REMOTE)) }
}


val mNetworkModules = module {
    single(name = RETROFIT_INSTANCE) { createNetworkClient(BASE_URL) }
    single(name = API) { (get(RETROFIT_INSTANCE) as Retrofit).create(MoviesApi::class.java) }
}

val mViewModels = module {
    viewModel {
        MoviesPopularViewModel(
            getMoviesPopularUseCase = get(MOVIES_POPULAR),
            barcodeObject = BarcodeFactory.getInstance()!!
        )
    }
    viewModel {
        MovieDetailViewModel(getMovieDetailUseCase = get(MOVIE_DETAIL))
    }

}


private const val BASE_URL = "https://api.themoviedb.org/"
private const val RETROFIT_INSTANCE = "Retrofit"
private const val API = "Api"
private const val REMOTE = "Remote"
private const val MOVIES_POPULAR = "GetMoviesPopularUseCase"
private const val MOVIE_DETAIL = "GetMovieDetailUseCase"


