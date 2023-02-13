package reflection

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SerializerTest {

    @Test
    fun `should serialize numbers`() {
        class ExampleClass(val a: Int, val b: Int, val c: Int)
        assertEquals(
            "{a: 1, b: 2, c: 3}",
            ExampleClass(1, 2, 3).toJson()
        )
    }

    @Test
    fun `should serialize string`() {
        class ExampleClass(val s1: String, val s2: String)
        assertEquals(
            "{s1: \"ABC\", s2: \"DEF\"}",
            ExampleClass("ABC", "DEF").toJson()
        )
    }

    @Test
    fun `should serialize list`() {
        class ExampleClass(val names: List<String>, val grades: List<Int>)
        assertEquals(
            "{grades: [3, 4, 3], names: [\"A\", \"B\", \"C\"]}",
            ExampleClass(listOf("A", "B", "C"), listOf(3, 4, 3)).toJson()
        )
    }

    @Test
    fun `should serialize map`() {
        class ExampleClass(val grades: Map<String, Int>)
        assertEquals(
            "{grades: {Alex: 5, Beatrice: 1}}",
            ExampleClass(mapOf("Alex" to 5, "Beatrice" to 1)).toJson()
        )
    }

    @Test
    fun `should serialize nested objects`() {
        class Name(val value: String)
        class Box(val name: Name)
        assertEquals(
            "{name: {value: \"ABC\"}}",
            Box(Name("ABC")).toJson()
        )
    }

    @Test
    fun `should serialize complex object`() {
        class Creature(
            val name: String,
            val attack: Int,
            val defence: Int,
            val traits: List<Trait>,
            val cost: Map<Element, Int>
        )

        val creature = Creature(
            name = "Cockatrice",
            attack = 2,
            defence = 4,
            traits = listOf(Trait.FLYING),
            cost = mapOf(
                Element.ANY to 3,
                Element.FOREST to 2
            )
        )
        assertEquals(
            "{attack: 2, cost: {ANY: 3, FOREST: 2}, defence: 4, name: \"Cockatrice\", traits: [FLYING]}",
            creature.toJson()
        )
    }

    @Test
    fun `should ignore properties`() {
        class Creature(
            @JsonIgnore
            val name: String,
            val attack: Int,
            val defence: Int,
            val traits: List<Trait>,
            val cost: Map<Element, Int>
        )

        val creature = Creature(
            name = "Cockatrice",
            attack = 2,
            defence = 4,
            traits = listOf(Trait.FLYING),
            cost = mapOf(
                Element.ANY to 3,
                Element.FOREST to 2
            )
        )
        assertEquals(
            "{attack: 2, cost: {ANY: 3, FOREST: 2}, defence: 4, traits: [FLYING]}",
            creature.toJson()
        )
    }

    @Test
    fun `should use different property names`() {
        class Creature(
            val name: String,
            @JsonName("att") val attack: Int,
            @JsonName("def") val defence: Int,
            val traits: List<Trait>,
            val cost: Map<Element, Int>
        )

        val creature = Creature(
            name = "Cockatrice",
            attack = 2,
            defence = 4,
            traits = listOf(Trait.FLYING),
            cost = mapOf(
                Element.ANY to 3,
                Element.FOREST to 2
            )
        )
        assertEquals(
            "{att: 2, cost: {ANY: 3, FOREST: 2}, def: 4, name: \"Cockatrice\", traits: [FLYING]}",
            creature.toJson()
        )
    }

    enum class Element {
        FOREST, ANY,
    }
    enum class Trait {
        FLYING
    }
}