package _1_safety

import java.lang.Error

fun Notifier.notifyUser(user: User?) {
    TODO()
}

class IdIsRequired: Error()

class Notifier(initialized: Boolean = false) {
    var initialized = initialized
        private set
    var usersNotified = setOf<Int>()

    fun initialize() {
        initialized = true
    }

    fun notifyPerson(id: Int) {
        usersNotified = usersNotified + id
    }
}