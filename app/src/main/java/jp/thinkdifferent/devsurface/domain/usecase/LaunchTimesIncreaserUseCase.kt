package jp.thinkdifferent.devsurface.domain.usecase

import jp.thinkdifferent.devsurface.domain.repository.LaunchTimesRepository
import javax.inject.Inject

class LaunchTimesIncreaserUseCase @Inject constructor(private val launchTimesRepository: LaunchTimesRepository) {

    operator fun invoke(){
        val currentLaunchTimes = launchTimesRepository.readLaunchTimes()
        launchTimesRepository.saveLaunchTimes(currentLaunchTimes+1)
    }
}