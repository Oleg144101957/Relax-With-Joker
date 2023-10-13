package jp.thinkdifferent.devsurface.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.thinkdifferent.devsurface.data.repository.LaunchTimesRepositoryImpl
import jp.thinkdifferent.devsurface.data.repository.ScoresRepositoryImpl
import jp.thinkdifferent.devsurface.data.storage.GameStorage
import jp.thinkdifferent.devsurface.data.storage.GameStoragePaperImpl
import jp.thinkdifferent.devsurface.domain.repository.LaunchTimesRepository
import jp.thinkdifferent.devsurface.domain.repository.ScoresRepository
import jp.thinkdifferent.devsurface.domain.usecase.GetScoresUseCase
import jp.thinkdifferent.devsurface.domain.usecase.LaunchTimesIncreaserUseCase
import jp.thinkdifferent.devsurface.domain.usecase.ReaderLaunchTimesUseCase
import jp.thinkdifferent.devsurface.domain.usecase.SaveBestScoresUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RelaxDataModule {

    @Provides
    @Singleton
    fun provideGameStorage() : GameStorage {
        return GameStoragePaperImpl()
    }

    @Provides
    @Singleton
    fun provideLaunchTimesRepository() : LaunchTimesRepository {
        return LaunchTimesRepositoryImpl(provideGameStorage())
    }

    @Provides
    @Singleton
    fun provideReaderLaunchTimes() : ReaderLaunchTimesUseCase {
        return ReaderLaunchTimesUseCase(provideLaunchTimesRepository())
    }

    @Provides
    fun provideLauncherUseCase() : LaunchTimesIncreaserUseCase {
        return LaunchTimesIncreaserUseCase(provideLaunchTimesRepository())
    }

    @Provides
    fun provideReaderLaunchTimesUseCase() : ReaderLaunchTimesUseCase {
        return ReaderLaunchTimesUseCase(provideLaunchTimesRepository())
    }

    @Provides
    @Singleton
    fun provideScoresRepository() : ScoresRepository {
        return ScoresRepositoryImpl(provideGameStorage())
    }

    @Provides
    fun provideGetScoresUseCase(): GetScoresUseCase {
        return GetScoresUseCase(provideScoresRepository())
    }

    @Singleton
    @Provides
    fun provideSaveBestScoresUseCase() : SaveBestScoresUseCase {
        return SaveBestScoresUseCase(provideScoresRepository())
    }

}