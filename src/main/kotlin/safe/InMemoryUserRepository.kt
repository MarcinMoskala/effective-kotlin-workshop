package safe

class InMemoryUserRepository {
    private var users = setOf<User>()
    private val LOCK = Any()

    fun addUser(user: User) = synchronized(LOCK) {
        users += user
    }

    fun getUsers(): Set<User> = users

    fun hasUser(user: User): Boolean = user in users

    fun changeSurname(userId: Int, newSurname: String) = synchronized(LOCK) {
        val user = users.find { it.id == userId } ?: return@synchronized
        val newUser = user.copy(surname = newSurname)
        users = users - user + newUser
    }

    fun changeAllSurnames(newSurname: String) = synchronized(LOCK) {
        users = users.map { it.copy(surname = newSurname) }.toSet()
    }

    data class User(val id: Int, val name: String, val surname: String)
}