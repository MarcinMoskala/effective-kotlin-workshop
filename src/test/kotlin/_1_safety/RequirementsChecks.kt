package _1_safety

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RequirementsChecks {
    @Test
    fun `Function sends message for correct arguments and state`() {
        val notifier = Notifier(initialized = true)
        val id = "123"
        notifier.notifyUser(UserDto("Ben", "Barack", id))
        assertEquals(setOf(id), notifier.usersNotified)
    }

    @Test
    fun `Function does nothing when user is null`() {
        val notifier = Notifier(initialized = true)
        val id = "123"
        notifier.notifyUser(null)
        assertEquals(setOf(), notifier.usersNotified)
    }

    @Test
    fun `Function throws IllegalArgumentException when name is null`() {
        val notifier = Notifier(initialized = true)
        val id = "123"
        assertThrows<IllegalArgumentException> { notifier.notifyUser(UserDto(null, "Barack", id)) }
    }

    @Test
    fun `Function throws IllegalArgumentException when surname is null`() {
        val notifier = Notifier(initialized = true)
        val id = "123"
        assertThrows<IllegalArgumentException> { notifier.notifyUser(UserDto("Mike", null, id)) }
    }

    @Test
    fun `Function throws IllegalStateException when notifier is not initialized`() {
        val notifier = Notifier(initialized = false)
        val id = "123"
        assertThrows<IllegalStateException> { notifier.notifyUser(UserDto("Mike", "Bull", id)) }
    }

    @Test
    fun `Function throws IdIdRequired when is is null`() {
        val notifier = Notifier()
        val id = null
        assertThrows<IdIsRequired> { notifier.notifyUser(UserDto("Mike", "Bull", id)) }
    }

    inline fun <reified T> assertThrows(function: ()->Unit) {
        val error = try {
            function()
            null
        } catch (anything: Throwable) {
            anything
        }
        val expectedErrorName = T::class.simpleName
        assertNotNull(error, "There should be an error of type $expectedErrorName")
        assertTrue(error is T, "Error is of type ${error::class.simpleName} when it should be $expectedErrorName")
    }
}