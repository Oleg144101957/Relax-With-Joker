package jp.thinkdifferent.devsurface.data.repository

import jp.thinkdifferent.devsurface.data.storage.GameStorage
import jp.thinkdifferent.devsurface.domain.repository.LaunchTimesRepository
import javax.inject.Inject

class LaunchTimesRepositoryImpl @Inject constructor(private val gameStorage: GameStorage) :
    LaunchTimesRepository {

    override fun readLaunchTimes(): Int {
        return gameStorage.readLaunchTimes()
    }

    override fun saveLaunchTimes(times: Int) {
        gameStorage.saveLaunchTimes(times)
    }

}