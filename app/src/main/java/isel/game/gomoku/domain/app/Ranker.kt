package isel.game.gomoku.domain.app

data class Ranker (val username: String, val score: Int,val totalGames:Int){
    init {
        require(username.isNotBlank()) { "The username must not be blank" }
    }
}