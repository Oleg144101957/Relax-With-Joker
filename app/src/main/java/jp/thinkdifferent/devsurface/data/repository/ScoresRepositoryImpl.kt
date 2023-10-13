package jp.thinkdifferent.devsurface.data.repository

import jp.thinkdifferent.devsurface.data.storage.GameStorage
import jp.thinkdifferent.devsurface.domain.repository.ScoresRepository
import javax.inject.Inject

class ScoresRepositoryImpl @Inject constructor(private val gameStorage: GameStorage) :
    ScoresRepository {
    override fun readScores(): Int {
        return gameStorage.readScores()
    }

    override fun saveScores(newScores: Int) {

        val lastScores = gameStorage.readScores()

        if (lastScores<newScores){
            gameStorage.saveScores(newScores)
        }
    }
}