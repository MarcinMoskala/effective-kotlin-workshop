package safe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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
    fun `getUsers should not expose mutation point`() {
        assertEquals(typeOf<Set<User>>(), InMemoryUserRepository::getUsers.returnType)
    }

    @Test
    fun `should allow concurrent user addition`(): Unit = runBlocking(Dispatchers.IO) {
        val newUsers = List(1000) { User(it * 2, "NewName$it", "NewSurname$it") }

        coroutineScope {
            for (newUser in newUsers) {
                launch {
                    repo.addUser(newUser)
                }
            }
        }

        assertEquals(2000, repo.getUsers().size)
    }

    @Test
    fun `should allow concurrent username change`(): Unit = runBlocking(Dispatchers.IO) {
        val users = repo.getUsers()

        coroutineScope {
            for (user in users) {
                launch {
                    repo.changeSurname(user.id, "NewSurname")
                }
            }
        }

        assertEquals(1000, repo.getUsers().size)
        assertEquals(List(1000) { "NewSurname" }, repo.getUsers().map { it.surname })
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
    fun `should allow parallel write and read`(): Unit = runBlocking(Dispatchers.IO) {
        repeat(10) {
            launch {
                repeat(1000) {
                    repo.addUser(User(it * 2 + 1, "NewUserName$it", "NewUserSurname$it"))
                }
            }
            launch {
                repeat(1000) {
                    val users = repo.getUsers()
                    // The expected problem here is ConcurrentModificationException from inside count or sumOf
                    assertTrue(users.count() >= 1000, "Problem with $users, size ${users.size}")
                    assertTrue(users.sumOf { it.id } > 500_000)
                }
            }
        }
    }

    @Test
    fun `When we set new surnames, they should all be the same`(): Unit = runBlocking(Dispatchers.IO) {
        repo.changeAllSurnames("NewSurname")
        launch {
            repeat(1000) {
                repo.changeAllSurnames("NewSurname$it")
            }
        }
        launch {
            repeat(1000) {
                assertEquals(1, repo.getUsers().distinctBy { it.surname }.size)
            }
        }
    }
}