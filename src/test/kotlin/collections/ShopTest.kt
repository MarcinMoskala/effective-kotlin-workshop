package collections

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ShopTests {
    @Test
    fun testCustomersWhoPaidAtLeastFilter() {
        val customersWhoBoughtAtLeast10 = shop.customers - customersMap[cooper]
        assertEquals(customersWhoBoughtAtLeast10, shop.getCustomersWhoPaidAtLeast(10.0))
        val customersWhoBoughtAtLeast150 = customersWhoBoughtAtLeast10 - customersMap[nathan] - customersMap[bajram]
        assertEquals(customersWhoBoughtAtLeast150, shop.getCustomersWhoPaidAtLeast(150.0))
        val customersWhoBoughtAtLeast300 = listOf(customersMap[lucas], customersMap[reka])
        assertEquals(customersWhoBoughtAtLeast300, shop.getCustomersWhoPaidAtLeast(300.0))
    }

    @Test
    fun testCustomersWithUndeliveredProductsilter() {
        assertEquals(listOf(), Shop("", listOf()).getCustomersWithUndeliveredProducts())
        assertEquals(listOf(customersMap[reka]), shop.getCustomersWithUndeliveredProducts())

        val fakeProducts = listOf(Product("", 0.0))
        val emptyCity = City("")
        val c1 = Customer("C1", emptyCity, listOf(Order(fakeProducts, isDelivered = false)))
        val c2 = Customer("C2", emptyCity, listOf(Order(fakeProducts, isDelivered = false)))
        val cDelivered = Customer("CDelivered", emptyCity, listOf(Order(fakeProducts, isDelivered = true)))
        assertEquals(listOf(c1), Shop("", listOf(c1)).getCustomersWithUndeliveredProducts())
        assertEquals(listOf(c2), Shop("", listOf(c2)).getCustomersWithUndeliveredProducts())
        assertEquals(listOf(), Shop("", listOf(cDelivered)).getCustomersWithUndeliveredProducts())
        assertEquals(listOf(c1, c2), Shop("", listOf(c1, c2)).getCustomersWithUndeliveredProducts())
        assertEquals(listOf(c1, c2), Shop("", listOf(c1, cDelivered, c2)).getCustomersWithUndeliveredProducts())
    }

    @Test
    fun testNumberOfTimesEachProductWasOrdered() {
        assertEquals(4, shop.getNumberOfTimesProductWasOrdered(idea))
    }

    @Test
    fun testNumberOfTimesEachProductWasOrderedForRepeatedProduct() {
        assertEquals(3, shop.getNumberOfTimesProductWasOrdered(reSharper), "A customer may order a product for several times")
    }

    @Test
    fun testNumberOfTimesEachProductWasOrderedForRepeatedInOrderProduct() {
        assertEquals(3, shop.getNumberOfTimesProductWasOrdered(phpStorm), "An order may contain a particular product more than once")
    }
}

val idea = Product("IntelliJ IDEA Ultimate", 199.0)
val reSharper = Product("ReSharper", 149.0)
val dotTrace = Product("DotTrace", 159.0)
val dotMemory = Product("DotMemory", 129.0)
val phpStorm = Product("PhpStorm", 99.0)
val rubyMine = Product("RubyMine", 99.0)
val webStorm = Product("WebStorm", 49.0)

//customers
const val lucas = "Lucas"
const val cooper = "Cooper"
const val nathan = "Nathan"
const val reka = "Reka"
const val bajram = "Bajram"
const val asuka = "Asuka"
const val riku = "Riku"

//cities
val Canberra = City("Canberra")
val Vancouver = City("Vancouver")
val Budapest = City("Budapest")
val Ankara = City("Ankara")
val Tokyo = City("Tokyo")

fun customer(name: String, city: City, vararg orders: Order) = Customer(name, city, orders.toList())
fun order(vararg products: Product, isDelivered: Boolean = true) = Order(products.toList(), isDelivered)
fun shop(name: String, vararg customers: Customer) = Shop(name, customers.toList())

val shop = shop("jb test shop",
    customer(lucas, Canberra,
        order(reSharper),
        order(reSharper, dotMemory, dotTrace)
    ),
    customer(cooper, Canberra),
    customer(nathan, Vancouver,
        order(rubyMine, webStorm)
    ),
    customer(reka, Budapest,
        order(idea, isDelivered = false),
        order(idea, isDelivered = false),
        order(idea)
    ),
    customer(bajram, Ankara,
        order(reSharper)
    ),
    customer(asuka, Tokyo,
        order(idea)
    ),
    customer(riku, Tokyo,
        order(phpStorm, phpStorm),
        order(phpStorm)
    )
)

val customersMap = shop.customers.associateBy { it.name }