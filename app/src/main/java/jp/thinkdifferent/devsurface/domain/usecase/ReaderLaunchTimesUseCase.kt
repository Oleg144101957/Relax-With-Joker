package jp.thinkdifferent.devsurface.domain.usecase

import jp.thinkdifferent.devsurface.domain.repository.LaunchTimesRepository
import javax.inject.Inject

class ReaderLaunchTimesUseCase @Inject constructor(launchTimesRepository: LaunchTimesRepository) {

    private val launchTimes = launchTimesRepository.readLaunchTimes()

    operator fun invoke(): Int{
        return launchTimes
    }
}