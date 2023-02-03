package creation

import java.util.Objects

class DeckConnector(val deckName: String): Comparable<DeckConnector> {
    var state: ConnectionState = ConnectionState.Initial

    override fun equals(other: Any?): Boolean =
        this === other ||
        other is DeckConnector &&
        deckName == other.deckName &&
        state == other.state

    override fun hashCode(): Int =
        Objects.hash(deckName, state)

    override fun compareTo(other: DeckConnector): Int =
        compareValuesBy(this, other, { it.deckName }, { it.state })

    enum class ConnectionState { Initial, Connected, Disconnected }
}