package jp.thinkdifferent.devsurface.domain.usecase

import jp.thinkdifferent.devsurface.domain.repository.ScoresRepository
import javax.inject.Inject

class SaveBestScoresUseCase @Inject constructor(private val scoresRepository: ScoresRepository) {
    operator fun invoke(scores: Int){
        scoresRepository.saveScores(scores)
    }
}