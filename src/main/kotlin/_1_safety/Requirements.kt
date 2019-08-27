package _1_safety

import java.lang.Error

class UserDto(val name: String?, val surname: String?, val id: String?)

fun Notifier.notifyUser(user: UserDto?) {
    TODO()
}

class IdIsRequired: Error()

class Notifier(initialized: Boolean = false) {
    var initialized = initialized
        private set
    var usersNotified = setOf<String>()

    fun initialize() {
        initialized = true
    }

    fun notifyPerson(id: String) {
        usersNotified = usersNotified + id
    }
}