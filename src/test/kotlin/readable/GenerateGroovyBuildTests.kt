package readable

import org.junit.jupiter.api.Test
import readable.ClassModel
import readable.ParameterModel
import kotlin.test.assertEquals

internal class GenerateGroovyBuildTests : DtoGeneratorTests() {

    @Test
    fun `should generate for simple class`() {
        val model = ClassModel("Name", listOf(ParameterModel(false, "name", "String")))
        val expected = """
            |@Builder(builderStrategy = SimpleStrategy, prefix = "with")
            |class NameBuild {
            |   String name
            |   
            |   static NameBuild aNameBuild() {
            |       return new NameBuild()
            |   }
            |   
            |   Name build() {
            |       return new Name(name)
            |   }
            |}
        """.trimMargin()
        assertEquals(expected, service.generateGroovyBuilder(model))
    }

    @Test
    fun `should generate for complex class`() {
        val model = ClassModel(
            "User",
            listOf(
                ParameterModel(true, "name", "String"),
                ParameterModel(true, "surname", "String"),
                ParameterModel(true, "age", "Int"),
                ParameterModel(true, "ids", "List<Int>")
                )
        )
        val expected = """
            |@Builder(builderStrategy = SimpleStrategy, prefix = "with")
            |class UserBuild {
            |   String name
            |   String surname
            |   int age
            |   List<Integer> ids
            |   
            |   static UserBuild aUserBuild() {
            |       return new UserBuild()
            |   }
            |   
            |   User build() {
            |       return new User(name, surname, age, ids)
            |   }
            |}
        """.trimMargin()
        assertEquals(expected, service.generateGroovyBuilder(model))
    }
}
