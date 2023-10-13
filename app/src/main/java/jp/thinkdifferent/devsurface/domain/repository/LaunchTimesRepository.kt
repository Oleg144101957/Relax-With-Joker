package jp.thinkdifferent.devsurface.domain.repository

interface LaunchTimesRepository {

    fun readLaunchTimes() : Int

    fun saveLaunchTimes(times: Int)

}