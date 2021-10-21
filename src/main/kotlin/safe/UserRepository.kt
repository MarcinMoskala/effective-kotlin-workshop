package safe

class InMemoryUserRepository {
    private val users = mutableSetOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUsers() = users

    fun hasUser(user: User): Boolean = user in users

    fun changeSurname(userId: Int, newSurname: String) {
        users.find { it.id == userId }?.surname = newSurname
    }

    fun changeAllSurnames(newSurname: String) {
        users.forEach { it.surname = newSurname }
    }

    data class User(val id: Int, val name: String, var surname: String)
}