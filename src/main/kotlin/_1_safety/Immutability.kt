package _1_safety

// This repo is incomplete and full of errors. Improve it
data class UserRepository(
    private val users: MutableSet<User> = mutableSetOf(),
    private val bonuses: MutableMap<User, MutableList<String>> = mutableMapOf(),
    private val bonusesService: BonusesService = PrintingBonusesService
) {

    fun addUser(user: User) {
        users += user
    }

    operator fun contains(user: User) = user in users

    fun changeUserSurname(user: User, newSurname: String?) {
        users.first { it.name == user.name && it.surname == user.surname }.surname = newSurname
    }

    fun addBonus(user: User, bonus: String) {
        // TODO
        bonusesService.update(bonuses)
    }

    fun removeBonus(user: User, bonus: String) {
        // TODO
        bonusesService.update(bonuses)
    }

    fun updateBonus(user: User, oldBonus: String, newBonus: String) {
        // TODO
        bonusesService.update(bonuses)
    }

    fun bonusesOf(user: User) = bonuses[user]!!
}

data class User(
    var id: Int?,
    var surname: String?,
    var name: String?
)

interface BonusesService {
    fun update(bonuses: Map<User, List<String>>)
}

object PrintingBonusesService: BonusesService {
    override fun update(bonuses: Map<User, List<String>>) {
        print("Bonuses changed to $bonuses")
    }
}