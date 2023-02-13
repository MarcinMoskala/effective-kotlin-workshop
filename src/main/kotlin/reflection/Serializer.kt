package reflection

import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

@Target(AnnotationTarget.PROPERTY)
annotation class JsonName(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class JsonIgnore

// Serialization function definition
fun Any.toJson(): String = TODO()

// Example use
class Creature(
    val name: String,
    val attack: Int,
    val defence: Int,
    val traits: List<Trait>,
    val cost: Map<Element, Int>
)
enum class Element {
    FOREST, ANY,
}
enum class Trait {
    FLYING
}

fun main() {
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
    println(creature.toJson())
    // {"attack": 2, "cost": {"ANY": 3, "FOREST": 2}, "defence": 4,
    // "name": "Cockatrice", "traits": ["FLYING"]}
}