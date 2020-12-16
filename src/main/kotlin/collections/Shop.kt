package collections

data class Shop(val name: String, val customers: List<Customer>)
data class Customer(val name: String, val city: City, val orders: List<Order>)
data class Order(val products: List<Product>, val isDelivered: Boolean)
data class Product(val name: String, val price: Double)
data class City(val name: String)

// Ger customers who paid `amount`
fun Shop.getCustomersWhoPaidAtLeast(amount: Double): List<Customer> = this
	.customers
	.filter { it.orders.sumByDouble { it.products.sumByDouble { it.price } } > amount }

// Get customers with undelivered products
fun Shop.getCustomersWithUndeliveredProducts(): List<Customer> = this
	.customers
	.filter { it.orders.any { !it.isDelivered } }

// Return the number of times the given product was ordered.
// Note: a customer may order the same product for several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int = this
	.customers
    .sumBy { it.orders.sumBy { it.products.count { it == product } } }