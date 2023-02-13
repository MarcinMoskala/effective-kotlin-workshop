package reflection

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FunctionCallerTest {
    var value: Any? = null
    fun callStr(str: String) { value = "callStr $str" }
    fun callInt(int: Int) { value = "callInt $int" }
    fun callStringInt(str: String, int: Int) { value = "callStringInt $str $int" }
    data class User(val id: Int, val name: String?, var surname: String?)
    fun callUser(user: User) { value = user }
    fun callWithDefault(c: Char, i: Int = 999, s: String = "XXX", l: Long) { value = "callWithDefault $c $i $s $l" }

    @AfterEach
    fun cleanUp() {
        value = null
    }

    @Test
    fun testWithString() {
        val caller = FunctionCaller()
        caller.setConstant("ABC")
        caller.call(::callStr)
        assertEquals("callStr ABC", value)
    }

    @Test
    fun testWithInt() {
        val caller = FunctionCaller()
        caller.setConstant(123)
        caller.call(::callInt)
        assertEquals("callInt 123", value)
    }

    @Test
    fun testWithStringInt() {
        val caller = FunctionCaller()
        caller.setConstant("DEF")
        caller.setConstant(456)
        caller.call(::callStringInt)
        assertEquals("callStringInt DEF 456", value)
    }

    @Test
    fun testWithUser() {
        val caller = FunctionCaller()
        val user = User(123, "DEF", "GHI")
        caller.setConstant(user)
        caller.call(::callUser)
        assertEquals(user, value)
    }

    @Test
    fun testIgnoredOptional() {
        val caller = FunctionCaller()
        caller.setConstant('Z')
        caller.setConstant(123L)
        caller.call(::callWithDefault)
        assertEquals("callWithDefault Z 999 XXX 123", value)
    }
}