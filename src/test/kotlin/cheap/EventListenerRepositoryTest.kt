package cheap

import cheap.EventListenerRepository.Event.A
import cheap.EventListenerRepository.Event.B
import cheap.EventListenerRepository.Event.C
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EventListenerRepositoryTest {

    @Test
    fun `should invoke proper handlers`() {
        val eventListenerRepository = EventListenerRepository()
        var a = 0
        var b = 0
        var c = 0

        eventListenerRepository.addEventListener(A, { a++ })
        eventListenerRepository.addEventListener(B, { b++ })
        eventListenerRepository.addEventListener(C, { c++ })

        assertEquals(0, a)
        assertEquals(0, b)
        assertEquals(0, c)

        eventListenerRepository.invokeListeners(A)

        assertEquals(1, a)
        assertEquals(0, b)
        assertEquals(0, c)

        eventListenerRepository.invokeListeners(B)
        eventListenerRepository.invokeListeners(B)

        assertEquals(1, a)
        assertEquals(2, b)
        assertEquals(0, c)

        eventListenerRepository.invokeListeners(C)
        eventListenerRepository.invokeListeners(C)
        eventListenerRepository.invokeListeners(C)

        assertEquals(1, a)
        assertEquals(2, b)
        assertEquals(3, c)
    }

    @Test
    fun `should allow setting more than one handler for an event`() {
        val eventListenerRepository = EventListenerRepository()
        var a = 0
        var b = 0
        var c = 0

        eventListenerRepository.addEventListener(A, { a++ })
        eventListenerRepository.addEventListener(A, { b++ })
        eventListenerRepository.addEventListener(A, { c++ })

        eventListenerRepository.invokeListeners(A)

        assertEquals(1, a)
        assertEquals(1, b)
        assertEquals(1, c)
    }

    @Test
    fun `should allow listener cancelation`() {
        val eventListenerRepository = EventListenerRepository()
        var a = 0

        val listener = eventListenerRepository.addEventListener(A, { a++ })
        listener.cancel()

        eventListenerRepository.invokeListeners(A)

        assertEquals(0, a)
    }
}
