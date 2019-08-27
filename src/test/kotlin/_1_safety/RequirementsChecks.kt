package _1_safety

import _1_safety.RequirementsChecks.FakeNotifier.Companion.INCORRECT_ID
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RequirementsChecks {
    @Test
    fun `Function sends message for correct arguments and state`() {
        val notifier = FakeNotifier(initialized = true)
        val id = 123
        notifier.notifyUser(User(id, "Ben", "Barack"))
        assertEquals(setOf(id), notifier.usersNotified)
    }

    @Test
    fun `Function does nothing when user is null`() {
        val notifier = FakeNotifier(initialized = true)
        notifier.notifyUser(null)
        assertEquals(setOf(), notifier.usersNotified)
    }

    @Test
    fun `Function throws IllegalArgumentException when name is null`() {
        val notifier = FakeNotifier(initialized = true)
        val id = 123
        assertThrows<IllegalArgumentException> { notifier.notifyUser(User(id, "Barack", null)) }
    }

    @Test
    fun `Function throws IllegalArgumentException when surname is null`() {
        val notifier = FakeNotifier(initialized = true)
        val id = 123
        assertThrows<IllegalArgumentException> { notifier.notifyUser(User(id, "Mike", null)) }
    }

    @Test
    fun `Function throws IllegalStateException when notifier is not initialized`() {
        val notifier = FakeNotifier(initialized = false)
        val id = 123
        assertThrows<IllegalStateException> { notifier.notifyUser(User(id, "Mike", "Bull")) }
    }

    @Test
    fun `Function throws IncorrectId when id is incorrect`() {
        val notifier = FakeNotifier(initialized = true)
        assertThrows<IncorrectId> { notifier.notifyUser(User(INCORRECT_ID, "Mike", "Bull")) }
    }

    class FakeNotifier(initialized: Boolean = false): Notifier {
        override var initialized = initialized
            private set
        var usersNotified = setOf<Int>()

        override fun initialize() {
            initialized = true
        }

        override fun checkId(id: Int): Boolean = id != INCORRECT_ID

        override fun notifyPerson(id: Int) {
            usersNotified = usersNotified + id
        }
        
        companion object {
            const val INCORRECT_ID = -100
        }
    }
}