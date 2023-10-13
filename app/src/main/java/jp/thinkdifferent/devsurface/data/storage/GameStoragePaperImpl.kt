package jp.thinkdifferent.devsurface.data.storage

import io.paperdb.Paper


private const val KEY_LAUNCH_TIMES = "KEY_LAUNCH_TIMES"
private const val DEFAULT_LAUNCH_TIMES = 0

private const val KEY_SCORES = "KEY_SCORES"
private const val DEFAULT_SCORES = 0

private const val KEY_DEST = "KEY_DEST"
private const val EMPTY_DEST = "NO_DATA_IN_STORAGE"


class GameStoragePaperImpl : GameStorage {

    override fun readLaunchTimes(): Int {
        return Paper.book().read<Int>(KEY_LAUNCH_TIMES) ?: DEFAULT_LAUNCH_TIMES
    }

    override fun saveLaunchTimes(times: Int) {
        Paper.book().write(KEY_LAUNCH_TIMES, times)
    }

    override fun readScores(): Int {
        return Paper.book().read<Int>(KEY_SCORES) ?: DEFAULT_SCORES
    }

    override fun saveScores(scores: Int) {
        Paper.book().write(KEY_SCORES, scores)
    }

    override fun readDestination(): String {
        return Paper.book().read<String>(KEY_DEST) ?: EMPTY_DEST
    }

    override fun writeDestination(dest: String) {
        Paper.book().write(KEY_DEST, dest)
    }
}