package jp.thinkdifferent.devsurface.data.storage

interface GameStorage {

    fun readLaunchTimes() : Int
    fun saveLaunchTimes(times: Int)

    fun readScores() : Int
    fun saveScores(scores: Int)

    fun readDestination(): String
    fun writeDestination(dest: String)

}