package creation

data class Pizza(
    val cheese: Int = 0,
    val pineapple: Int = 0,
    val ham: Int = 0,
    val egg: Int = 0,
    val spinach: Int = 0
) {
    companion object {
        fun hawaiian() = Pizza(cheese = 1, pineapple = 1, ham = 1)
    }
}

object PizzaFactory {
    fun hawaiian() = Pizza(cheese = 1, pineapple = 1, ham = 1)
}

fun hawaiianPizza() = Pizza(cheese = 1, pineapple = 1, ham = 1)

fun main() {
    val pizza1 = Pizza.hawaiian()
    val pizza2 = PizzaFactory.hawaiian()
//    assert(pizza1 == Pizza(cheese = 1, pineapple = 1, ham = 1))
//    assert(pizza2 == Pizza(cheese = 1, pineapple = 1, ham = 1))
}
