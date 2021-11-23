package creation

import java.math.BigDecimal

enum class Currency { EUR, PLN, GBP }

data class Money(val amount: BigDecimal, val currency: Currency) {
}

fun main() {
//    val m1 = Money(BigDecimal.TEN, Currency.EUR)
//    val m2 = Money(BigDecimal.ONE, Currency.EUR)
//    val m3 = m1 + m2
//    val m4 = m1 - m2
//    val m5 = m1 * BigDecimal.valueOf(2)

//    val money1 = 10.pln()
//    val money2 = 10.eur
//    val money3 = 10.GBP
//    val money4 = PLN(10)
//    val money5 = Money.eur(10)
}
