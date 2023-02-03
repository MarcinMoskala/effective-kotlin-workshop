package safe

import safe.BonusRepository.User
import kotlin.properties.Delegates

// This repo is incomplete and full of errors. Improve it
class BonusRepository(
    private val bonusesService: BonusesService = PrintingBonusesService
) {
    private var users: Map<Int, User> = mapOf()
    private var bonuses: Map<Int, List<String>> by Delegates.observable(mapOf()) { _, _, new ->
        bonusesService.update(bonuses
            .mapKeys { (id, _) -> users[id] }
            .filter { (user, _) -> user != null } as Map<User, List<String>>
        )
    }

    fun addUser(user: User) {
        users += user.id to user
    }

    operator fun contains(user: User) = user.id in users

    fun changeUserSurname(userId: Int, newSurname: String?) {
        val user: User = users[userId] ?: return
        users = users - userId + (userId to user.copy(surname = newSurname))
    }

    fun addBonus(user: User, bonus: String) {
        val prevBonuses = bonuses[user.id].orEmpty()
        bonuses += user.id to (prevBonuses + bonus)
    }

    fun removeBonus(user: User, bonus: String) {
        val prevBonuses = bonuses[user.id].orEmpty()
        bonuses += user.id to (prevBonuses - bonus)
    }

    fun updateBonus(user: User, oldBonus: String, newBonus: String) {
        val prevBonuses = bonuses[user.id].orEmpty()
        bonuses += user.id to (prevBonuses - oldBonus + newBonus)
    }

    fun bonusesOf(user: User) = bonuses[user.id].orEmpty()

    data class User(
        val id: Int,
        val surname: String?,
        val name: String?
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
