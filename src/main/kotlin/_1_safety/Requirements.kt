package _1_safety

import java.lang.Error

fun Notifier.notifyUser(user: User?) {
    TODO()
}

class IncorrectId: Error()

interface Notifier {
    val initialized: Boolean
    fun initialize()
    fun notifyPerson(id: Int)
    fun checkId(id: Int): Boolean
}