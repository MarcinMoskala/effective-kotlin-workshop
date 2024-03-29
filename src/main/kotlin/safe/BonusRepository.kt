package safe

import safe.BonusRepository.User

// This repo is incomplete and full of errors. Improve it
class BonusRepository(
    private val bonusesService: BonusesService = PrintingBonusesService
) {
    private val users: MutableSet<User> = mutableSetOf()
    private val bonuses: MutableMap<User, MutableList<String>> = mutableMapOf()

    fun addUser(user: User) {
        users += user
    }

    operator fun contains(user: User) = user in users

    fun changeUserSurname(userId: Int, newSurname: String?) {
        users.first { it.id == userId }.surname = newSurname
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

    data class User(
        var id: Int,
        var surname: String?,
        var name: String?
    )
}

interface BonusesService {
    fun update(bonuses: Map<User, List<String>>)
}

object PrintingBonusesService : BonusesService {
    override fun update(bonuses: Map<User, List<String>>) {
        print("Bonuses changed to $bonuses")
    }
}
