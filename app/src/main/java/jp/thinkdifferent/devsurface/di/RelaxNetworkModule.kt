package jp.thinkdifferent.devsurface.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.thinkdifferent.devsurface.data.networkrepo.AppsRepositoryImpl
import jp.thinkdifferent.devsurface.data.networkrepo.FacebookRepositoryImpl
import jp.thinkdifferent.devsurface.data.storage.GameStorage
import jp.thinkdifferent.devsurface.domain.repository.AppsRepository
import jp.thinkdifferent.devsurface.domain.repository.FacebookRepository
import jp.thinkdifferent.devsurface.presentation.viewmodels.ViewModelRelax
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RelaxNetworkModule {

    @Provides
    @Singleton
    fun provideappsRepo() : AppsRepository {
        return AppsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providefacebookRepo(@ApplicationContext appContext: Context) : FacebookRepository {
        return FacebookRepositoryImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideViewModel(
        gameStorage: GameStorage,
        appsRepository: AppsRepository,
        facebookRepository: FacebookRepository
    ) : ViewModelRelax {
        return ViewModelRelax(
            gameStorage = gameStorage,
            appsRepo = appsRepository,
            facebookRepo = facebookRepository
        )
    }
}