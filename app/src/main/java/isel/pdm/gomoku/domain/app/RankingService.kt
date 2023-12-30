package isel.pdm.gomoku.domain.app

import android.util.Log
import isel.pdm.gomoku.domain.game.app.Ranker
import kotlinx.coroutines.delay

/**
 * Contract of the service that provides jokes
 */
interface RankingService {
    /**
     * Fetches a joke from the service
     */
    suspend fun getRankers(limit: Int): List<Ranker>
}

/**
 * Fake implementation of the JokesService. It will replaced by a real implementation
 * in a future lecture.
 */
class FakeRankingService : RankingService {

    private val rankings = listOf(
        Ranker(
            username = "Chuck Norris",
            score = 100,
            400,
        ),
        Ranker(
            username = "Bruce Lee",
            score = 90,
            300,
        ),
        Ranker(
            username = "Jackie Chan",
            score = 80,
            200,
        ),
        Ranker(
            username = "Jet Li",
            score = 70,
            100,
        ),
    )

    override suspend fun getRankers(limit: Int): List<Ranker> {
        delay(3000)
        return rankings
    }
}

object NoOpRankService : RankingService {
    override suspend fun getRankers(limit: Int): List<Ranker> = listOf(
        Ranker(
            username = "Chuck Norris",
            score = 100,
            400,
        ),
        Ranker(
            username = "Bruce Lee",
            score = 90,
            300,
        ),
        Ranker(
            username = "Jackie Chan",
            score = 80,
            200,
        ),
        Ranker(
            username = "Jet Li",
            score = 70,
            100,
        ),
    )
}