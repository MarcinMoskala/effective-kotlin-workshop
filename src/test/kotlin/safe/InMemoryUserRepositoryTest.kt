package safe

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import safe.InMemoryUserRepository.User
import kotlin.reflect.typeOf
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InMemoryUserRepositoryTest {

    lateinit var repo: InMemoryUserRepository

    @BeforeEach
    fun setup() {
        repo = InMemoryUserRepository()
        List(1000) { User(it * 2, "Name$it", "Surname$it") }
            .forEach { repo.addUser(it) }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `should not expose mutation point`() {
        assertEquals(typeOf<List<User>>(), InMemoryUserRepository::getUsers.returnType)
    }

    @Test
    fun `should not have user lost after surname change`() {
        val users = repo.getUsers()
        val newUsers = mutableListOf<User>()
        for (user in users) {
            val newSurname = "NewSurname${user.id}"
            repo.changeSurname(user.id, newSurname)
            newUsers.add(user.copy(surname = newSurname))
        }

        for (user in newUsers) {
            assertTrue(repo.hasUser(user))
        }
    }

    @Test
    fun parallelAddAndRead(): Unit = runBlocking {
        launch {
            repeat(1000) {
                repo.addUser(User(it * 2 + 1, "NewUserName$it", "NewUserSurname$it"))
            }
        }
        launch {
            repeat(1000) {
                val users = repo.getUsers()
                assertTrue(users.count() > 1000)
                assertTrue(users.sumOf { it.id } > 500_000)
            }
        }
    }

    @Test
    fun `When we set new surnames, they should all be the same`(): Unit = runBlocking {
        repo.changeAllSurnames("NewSurname")
        launch {
            repeat(1000) {
                repo.changeAllSurnames("NewSurname$it")
            }
        }
        launch {
            repeat(1000) {
                assertEquals(1, repo.getUsers().distinct().size)
            }
        }
    }
}