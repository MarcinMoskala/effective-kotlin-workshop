package cheap

private val LOCK = Any()

class EventListenerRepository {
    private var listeners: List<EventListener> = emptyList()

    fun addEventListener(event: Event, handler: () -> Unit): EventListener = synchronized(LOCK) {
        val listener = EventListener(event, handler)
        listeners = listeners + listener
        listener
    }

    fun invokeListeners(event: Event): Unit = synchronized(LOCK) {
        val activeListeners = listeners.filter { it.event == event && it.isActive }
        activeListeners.onEach { it.handler() }
    }

    enum class Event { A, B, C }
}

class EventListener(
    val event: EventListenerRepository.Event,
    val handler: () -> Unit,
    isActive: Boolean = true
) {
    var isActive: Boolean = isActive
        private set

    fun cancel(): Unit = synchronized(LOCK) {
        isActive = false
    }
}
