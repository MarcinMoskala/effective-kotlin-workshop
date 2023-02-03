package creation

import java.lang.Error

data class User(val id: Int, val name: String?, var surname: String?)

fun Notifier.notifyUser(user: User?) {
    user ?: return
    if (!checkId(user.id)) throw IncorrectId()
    requireNotNull(user.name) { "User name cannot be null" }
    requireNotNull(user.surname) { "User surname cannot be null" }
    check(this.initialized) { "Notifier must be initialized" }
    val success = notifyPerson(user.id)
    assert(success)
}

class IncorrectId : Error()

interface Notifier {

    /**
     * Indicate instance readiness to notify users
     */
    val initialized: Boolean

    /**
     * Notifies person
     * @param id Is an id of user we want to notify
     * @return Was operation successful
     */
    fun notifyPerson(id: Int): Boolean

    /**
     * Checks if we can send message to the following `id`
     */
    fun checkId(id: Int): Boolean
}