package code

class DeckConnector(val deckName: String) {
    var state: ConnectionState = Initial()

    // ...

    sealed class ConnectionState
    class Initial : ConnectionState()
    class Connected : ConnectionState()
    class Disconnected : ConnectionState()
}