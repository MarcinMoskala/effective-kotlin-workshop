package readable

import org.junit.jupiter.api.Test
import readable.ClassModel
import readable.GenerateDtoOptions
import readable.GenerateRequest
import readable.ParameterModel
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class GenerateDtoTests : DtoGeneratorTests() {

    @Test
    fun `should make default suffix`() {
        val model = ClassModel("Name", emptyList())
        val expected = """
            |class NameJson()
            |
            |fun Name.toNameJson() = NameJson()
            |
            |fun NameJson.toName() = Name()
        """.trimMargin()
        assertEquals(expected, service.generateDto(model, GenerateDtoOptions()))
    }

    @Test
    fun `should respect new name`() {
        val model = ClassModel("Name", emptyList())
        val expected = """
            |class NewName()
            |
            |fun Name.toNewName() = NewName()
            |
            |fun NewName.toName() = Name()
        """.trimMargin()
        assertEquals(expected, service.generateDto(model, GenerateDtoOptions(newName = "NewName")))
    }

    @Test
    fun `should respect suffix`() {
        val model = ClassModel("Name", emptyList())
        val expected = """
            |class NameEntity()
            |
            |fun Name.toNameEntity() = NameEntity()
            |
            |fun NameEntity.toName() = Name()
        """.trimMargin()
        assertEquals(expected, service.generateDto(model, GenerateDtoOptions(suffix = "Entity")))
    }

    @Test
    fun `should generate for simple class`() {
        val model = ClassModel("Name", listOf(ParameterModel(false, "name", "String")))
        val expected = """
            |class NameEntity(
            |   var name: String
            |)
            |
            |fun Name.toNameEntity() = NameEntity(
            |   name = name
            |)
            |
            |fun NameEntity.toName() = Name(
            |   name = name
            |)
        """.trimMargin()
        assertEquals(expected, service.generateDto(model, GenerateDtoOptions(suffix = "Entity")))
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
            |class NewName(
            |   val name: String,
            |   val surname: String,
            |   val age: Int,
            |   val `something strange`: String,
            |   val ids: List<Int>
            |)
            |
            |fun User.toNewName() = NewName(
            |   name = name,
            |   surname = surname,
            |   age = age,
            |   `something strange` = `something strange`,
            |   ids = ids
            |)
            |
            |fun NewName.toUser() = User(
            |   name = name,
            |   surname = surname,
            |   age = age,
            |   `something strange` = `something strange`,
            |   ids = ids
            |)
        """.trimMargin()
        assertEquals(expected, service.generateDto(model, GenerateDtoOptions(newName = "NewName")))
    }
}
