package readable

import org.junit.jupiter.api.Test
import readable.ClassModel
import readable.ParameterModel
import kotlin.test.assertEquals

internal class GenerateGroovyAssertionTests : DtoGeneratorTests() {

    @Test
    fun `should generate for simple class`() {
        val model = ClassModel("Name", listOf(ParameterModel(false, "name", "String")))
        val expected = """
            |class NameAssertion {
            |   private Name name
            |   
            |   NameAssertion(Name name) {
            |       this.name = name
            |   }
            |   
            |   static NameAssertion assertThat(Name name) {
            |       return NameAssertion(name)
            |   }
            |   
            |   NameAssertion hasName(String name) {
            |       assert name.name == name
            |       return this
            |   }
            |}
        """.trimMargin()
        assertEquals(expected, service.generateGroovyObjectAssertion(model))
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
            |class UserAssertion {
            |   private User user
            |   
            |   UserAssertion(User user) {
            |       this.user = user
            |   }
            |   
            |   static UserAssertion assertThat(User user) {
            |       return UserAssertion(user)
            |   }
            |   
            |   UserAssertion hasName(String name) {
            |       assert user.name == name
            |       return this
            |   }
            |   
            |   UserAssertion hasSurname(String surname) {
            |       assert user.surname == surname
            |       return this
            |   }
            |   
            |   UserAssertion hasAge(int age) {
            |       assert user.age == age
            |       return this
            |   }
            |   
            |   UserAssertion hasIds(List<Integer> ids) {
            |       assert user.ids == ids
            |       return this
            |   }
            |}
        """.trimMargin()
        assertEquals(expected, service.generateGroovyObjectAssertion(model))
    }
}
