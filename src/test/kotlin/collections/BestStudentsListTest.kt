package collections

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

@Suppress("FunctionName")
class BestStudentsListTest {

    @Test
    fun `Single student that matches criteria gets biggest internship`() {
        val text = listOf(internshipStudent).makeBestStudentsList()
        val expected = "Marc Smith, \$5000"
        assertEquals(expected, text)
    }

    @Test
    fun `Single student with too low result doesn't get internship`() {
        val text = listOf(studentWithTooLowResultToInternship).makeBestStudentsList()
        assertEquals("", text)
    }

    @Test
    fun `Result 80 is acceptable`() {
        val student = Student("Noely", "Peterson", 80.0, 32)
        val text = listOf(student).makeBestStudentsList()
        assertEquals("Noely Peterson, \$5000", text)
    }

    @Test
    fun `30 points is acceptable`() {
        val student = Student("Noely", "Peterson", 81.0, 30)
        val text = listOf(student).makeBestStudentsList()
        assertEquals("Noely Peterson, \$5000", text)
    }

    @Test
    fun `Single student with not enough doesn't get internship`() {
        val text = listOf(studentWithNotEnoughPointsForInternship).makeBestStudentsList()
        assertEquals("", text)
    }

    @Test
    fun `Complex test`() {
        val text = allStudents.makeBestStudentsList()
        val expected = """
            Ester Adams, ${'$'}1000
            Dior Angel, ${'$'}3000
            Oregon Dart, ${'$'}1000
            Jack Johnson, ${'$'}1000
            James Johnson, ${'$'}1000
            Jon Johnson, ${'$'}1000
            Naja Marcson, ${'$'}5000
            Alex Nolan, ${'$'}1000
            Ron Peters, ${'$'}3000
            Marc Smith, ${'$'}3000
        """.trimIndent()
        assertEquals(expected, text)
    }
}