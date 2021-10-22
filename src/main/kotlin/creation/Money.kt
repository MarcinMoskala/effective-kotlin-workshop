package creation

import java.math.BigDecimal

enum class Currency { EUR, PLN, GBP }

data class Money(val amount: BigDecimal, val currency: Currency) {
}

fun main() {
//    val money1 = 10.pln()
//    val money2 = 10.eur
//    val money3 = 10.GBP
//    val money4 = PLN(10)
//    val money5 = Money.eur(10)
}
