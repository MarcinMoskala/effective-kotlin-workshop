package code

import cheap.mutableLazy
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertNull

class MutableLazyTests {

    @Test
    fun `Do not initialize if initialized`() {
        val time = measureTimeMillis {
            var game: Game? by mutableLazy { readGameFromSave() }
            game = Game()
            print(game)
        }
        assert(time in 0..100)
    }

    @Test
    fun `Initializes if not initialized`() {
        val time = measureTimeMillis {
            val game: Game? by mutableLazy { readGameFromSave() }
            print(game)
        }
        assert(time in 450..550)
    }

    @Test
    fun `Do not initialize again if already initialized`() {
        val time = measureTimeMillis {
            val game: Game? by mutableLazy { readGameFromSave() }
            print(game)
            print(game)
            print(game)
        }
        assert(time in 450..550)
    }

    @Test
    fun `MutableLazy should accept nullable values`() {
        val lazy by mutableLazy<String?> { null }
        assertNull(lazy)

        var lazy2 by mutableLazy<String?> { "A" }
        lazy2 = null
        assertNull(lazy2)
    }

    private class Game()

    private fun readGameFromSave(): Game? {
        Thread.sleep(500)
        return Game()
    }
}