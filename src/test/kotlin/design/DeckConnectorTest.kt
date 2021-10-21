package design

import creation.DeckConnector
import org.junit.jupiter.api.Test
import kotlin.test.*

class DeckConnectorTest {
    val deck1 = DeckConnector("AAA")
    val deck1copy = DeckConnector("AAA")
    val deck2 = DeckConnector("BBB")
    val deckConnected1 = DeckConnector("AAA").apply {
        state = DeckConnector.Connected()
    }
    val deckConnected1copy = DeckConnector("AAA").apply {
        state = DeckConnector.Connected()
    }
    val deckConnected2 = DeckConnector("BBB").apply {
        state = DeckConnector.Connected()
    }

    @Test
    fun equalityTest() {
        assertTrue(deck1 == deck1copy)
        assertTrue(deck1 != deck2)
        assertTrue(deckConnected1 == deckConnected1copy)
        assertTrue(deckConnected1 != deckConnected2)
    }

    @Test
    fun hashCodeTest() {
        assertTrue(deck1.hashCode() == deck1copy.hashCode())
        assertTrue(deck1.hashCode() != deck2.hashCode())
        assertTrue(deckConnected1.hashCode() == deckConnected1copy.hashCode())
        assertTrue(deckConnected1.hashCode() != deckConnected2.hashCode())

        assertEquals(123, deck1.hashCode())
        assertEquals(123, deck1.hashCode())
        assertEquals(123, deck1.hashCode())
        assertEquals(123, deck1.hashCode())
    }

//    @Test
//    fun comparisionTest() {
//        val decks = listOf(deck2, deckConnected2, deck1, deckConnected1)
//        val decksInOrder = listOf(deck1, deckConnected1, deck2, deckConnected2)
//        assertEquals(decksInOrder, decks.sorted())
//    }
}