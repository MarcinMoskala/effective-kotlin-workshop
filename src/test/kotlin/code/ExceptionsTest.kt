package code

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.typeOf
import kotlin.test.*

class ExceptionsTest {

    @Test
    fun `changeUserSurname when user cannot be found, proper error is displayed`() {
        val repo = UserRepository()
        val exception = assertThrows<IllegalAccessException> { repo.changeUserSurname(123, "XXX") }
        assertEquals("No such user in the repository", exception.message, "Function has correct message")
    }

    @ExperimentalStdlibApi
    @Test
    fun `getById has correct signature`() {
        val repoClass = UserRepository::class
        val method = repoClass.memberFunctions.singleOrNull { it.name == "getById" }
        assertNotNull(method, "Method getById needs to be implemented")

        // Check return type
        assertFalse(method.returnType.isMarkedNullable, "Return type should not be nullable")
        assertEquals(typeOf<User>(), method.returnType, "Function should return User")
        // Check parameter
        assertTrue(method.parameters.size == 2, "There is only a single expected argument (+ dispatch receiver)")
        // The first parameter is dispatch receiver - reference to the class
        assertEquals(typeOf<UserRepository>(), method.parameters[0].type, "Parameter type should be Int")
        assertEquals(typeOf<Int>(), method.parameters[1].type, "Parameter type should be Int")
        assertTrue(method.typeParameters.isEmpty(), "There should be no type parameters")
    }

    @Test
    fun `getById works correctly`() {
        val repo = UserRepository()
        val repoClass = repo::class
        val method = repoClass.memberFunctions.singleOrNull { it.name == "getById" }
        assertNotNull(method, "Method getById needs to be implemented")

        // Works for correct user
        val user = User(10, "A", "B")
        repo.addUser(user)
        assertEquals(user, method.call(repo, user.id))

        // Throws a correct error for lack of user with given id
        val exception = assertThrows<InvocationTargetException> { // All errors should be wrapped into this type by reflection
            method.call(repo, 0)
        }.targetException // We unpack to get actual exception throw by this function
        assertTrue(exception is NoSuchElementException, "The type of error should be NoSuchElementException")
        assertEquals("No user with id 0", exception.message, "Check for concrete error message")
    }

    @ExperimentalStdlibApi
    @Test
    fun `getByIdOrNull has correct signature`() {
        val repoClass = UserRepository::class
        val method = repoClass.memberFunctions.singleOrNull { it.name == "getByIdOrNull" }
        assertNotNull(method, "Method getByIdOrNull needs to be implemented")

        // Check return type
        assertTrue(method.returnType.isMarkedNullable, "Return type should be nullable")
        assertEquals(typeOf<User?>(), method.returnType, "Function should return User")
        // Check parameter
        assertTrue(method.parameters.size == 2, "There is only a single expected argument (+ dispatch receiver)")
        // The first parameter is dispatch receiver - reference to the class
        assertEquals(typeOf<UserRepository>(), method.parameters[0].type, "Parameter type should be Int")
        assertEquals(typeOf<Int>(), method.parameters[1].type, "Parameter type should be Int")
        assertTrue(method.typeParameters.isEmpty(), "There should be no type parameters")
    }

    @Test
    fun `getByIdOrNull works correctly`() {
        val repo = UserRepository()
        val repoClass = repo::class
        val method = repoClass.memberFunctions.singleOrNull { it.name == "getByIdOrNull" }
        assertNotNull(method, "Method getByIdOrNull needs to be implemented")

        // Works for correct user
        val user = User(10, "A", "B")
        repo.addUser(user)
        assertEquals(user, method.call(repo, user.id))

        // Returns a null lack of user with given id
        val result = method.call(repo, 0)
        assertNull(result, "Function should return null when no user with given id")
    }

    @ExperimentalStdlibApi
    @Test
    fun `getByIdOrDefault has correct signature`() {
        val repoClass = UserRepository::class
        val method = repoClass.memberFunctions.singleOrNull { it.name == "getByIdOrDefault" }
        assertNotNull(method, "Method getByIdOrDefault needs to be implemented")

        // Check return type
        assertFalse(method.returnType.isMarkedNullable, "Return type should not be nullable")
        assertEquals(typeOf<User>(), method.returnType, "Function should return User")
        // Check parameter
        assertTrue(method.parameters.size == 3, "There are two parameters in this function (+ dispatch receiver)")
        val (dispatchReceiver, param1, param2) = method.parameters
        assertEquals(typeOf<UserRepository>(), dispatchReceiver.type)
        assertEquals(typeOf<Int>(), param1.type, "The type of the first parameter should be Int")
        assertEquals(typeOf<User>(), param2.type, "The type of the second parameter should be User")
        assertTrue(method.typeParameters.isEmpty(), "There should be no type parameters")
    }

    @Test
    fun `getByIdOrDefault works correctly`() {
        val repo = UserRepository()
        val repoClass = repo::class
        val method = repoClass.memberFunctions.singleOrNull { it.name == "getByIdOrDefault" }
        assertNotNull(method, "Method getByIdOrDefault needs to be implemented")
        val default = User(100, "C", "D")

        // Works for correct user
        val user = User(10, "A", "B")
        repo.addUser(user)
        assertEquals(user, method.call(repo, user.id, default))

        // Returns a null lack of user with given id
        val result = method.call(repo, 0, default)
        assertEquals(default, result, "Function should return null when no user with given id")
    }
}