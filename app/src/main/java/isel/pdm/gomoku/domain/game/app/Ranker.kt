package isel.pdm.gomoku.domain.game.app

data class Ranker (val username: String, val score: Int,val totalGames:Int){
    init {
        require(username.isNotBlank()) { "The username must not be blank" }
    }
}