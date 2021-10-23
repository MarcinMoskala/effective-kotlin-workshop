package safe

import org.junit.jupiter.api.Test
import safe.BonusRepository.User
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BonusRepositoryTest {
    private val user1 = User(0, "Moskała", "Marcin")
    private val user1withSurnameChanged = User(0, "Aaron", "Marcin")
    private val bonus1 = "Bonus1"
    private val bonus2 = "Bonus2"

    @Test
    fun `When user is added, it can be found with contains`() {
        val user1 = user1.copy()
        val repo = BonusRepository()
        assert(user1 !in repo)
        repo.addUser(user1)
        assert(user1 in repo)
    }

    @Test
    fun `User exists after surname change`() {
        val user1 = user1.copy() // We need it because User is mutable
        val user1withSurnameChanged = user1withSurnameChanged.copy() // We need it because User is mutable
        val repo = BonusRepository()
        repo.addUser(user1.copy())
        repo.addUser(User(0, "BBB", "AAA"))
        repo.addUser(User(1, "CCC", "DDD"))
        assert(user1.copy() in repo)
        assertNotEquals(user1, user1withSurnameChanged, "Values should be different, actual $user1 and $user1withSurnameChanged")
        assertNotEquals(user1.hashCode(), user1withSurnameChanged.hashCode(), "Values should be different, actual $user1 and $user1withSurnameChanged")

        repo.changeUserSurname(user1.id, user1withSurnameChanged.surname)
        assert(user1withSurnameChanged in repo) { "No user $user1withSurnameChanged in the repository $repo" }
    }

    @Test
    fun `Bonus add, remove and update test`() {
        val user1 = user1.copy() // We need it because User is mutable
        val repo = BonusRepository()
        repo.addUser(user1)

        repo.addBonus(user1, bonus1)
        assert(bonus1 in repo.bonusesOf(user1))
    }

    @Test
    fun `Repo is not modified when we change bonuses`() {
        val user1 = user1.copy() // We need it because User is mutable
        val repo = BonusRepository()
        repo.addUser(user1)

        repo.addBonus(user1, bonus1)
        assert(bonus1 in repo.bonusesOf(user1))

        repo.updateBonus(user1, bonus1, bonus2)
        assert(bonus1 !in repo.bonusesOf(user1))
        assert(bonus2 in repo.bonusesOf(user1))

        repo.removeBonus(user1, bonus1)
        assert(bonus1 !in repo.bonusesOf(user1))
    }

    @Test
    fun `Whenever bonuses are modified, BonusesService is notified`() {
        val user1 = user1.copy() // We need it because User is mutable
        var lastUpdatedBonuses = mapOf<User, List<String>>()
        val service = object : BonusesService {
            override fun update(bonuses: Map<User, List<String>>) {
                lastUpdatedBonuses = bonuses
            }
        }

        val repo = BonusRepository(bonusesService = service)
        repo.addUser(user1)
        repo.addBonus(user1, bonus1)
        assertEquals(mapOf(user1 to listOf(bonus1)), lastUpdatedBonuses)
        repo.updateBonus(user1, bonus1, bonus2)
        assertEquals(mapOf(user1 to listOf(bonus2)), lastUpdatedBonuses)
        repo.removeBonus(user1, bonus2)
        assertEquals(mapOf(user1 to listOf<String>()), lastUpdatedBonuses)
    }

    @Test
    fun `Bonuses are preserved after user surname change`() {
        val user1 = user1.copy() // We need it because user is mutable
        val user1withSurnameChanged = user1withSurnameChanged.copy() // We need it because user is mutable
        val repo = BonusRepository()
        repo.addUser(user1)
        repo.addBonus(user1, bonus1)
        repo.changeUserSurname(user1.id, user1withSurnameChanged.surname)
        assert(user1withSurnameChanged in repo) { "Name change does not work correctly" }
        assert(bonus1 in repo.bonusesOf(user1withSurnameChanged)) {
            "Bonus is not preserved through surname change"
        }
    }

    @Test
    fun `User class should be read-only`() {
        val members = User::class.members
        members.filterIsInstance<KProperty<*>>()
            .forEach {
                assert(it !is KMutableProperty<*>) { "Property ${it.name} is not final" }
            }
    }
}
