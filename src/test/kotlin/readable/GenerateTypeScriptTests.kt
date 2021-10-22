package readable

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GenerateTypeScriptTests : DtoGeneratorTests() {

    @Test
    fun `should generate for empty`() {
        val model = ClassModel("Name", emptyList())
        val expected = """
            |type Name = {}
        """.trimMargin()
        assertEquals(expected, service.generateTypeScriptObject(model))
    }

    @Test
    fun `should generate for simple class`() {
        val model = ClassModel("Name", listOf(ParameterModel(false, "name", "String")))
        val expected = """
            |type Name = {
            |   name: string
            |}
        """.trimMargin()
        assertEquals(expected, service.generateTypeScriptObject(model))
    }

    @Test
    fun `should generate for complex class`() {
        val model = ClassModel(
            "User",
            listOf(
                ParameterModel(true, "name", "String"),
                ParameterModel(true, "surname", "String"),
                ParameterModel(true, "age", "Int"),
                ParameterModel(true, "`something strange`", "String"),
                ParameterModel(true, "ids", "List<Int>")
            )
        )
        val expected = """
            |type User = {
            |   name: string,
            |   surname: string,
            |   age: number,
            |   `something strange`: string,
            |   ids: number[]
            |}
        """.trimMargin()
        assertEquals(expected, service.generateTypeScriptObject(model))
    }
}
