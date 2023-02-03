package creation

import java.math.BigDecimal

enum class Currency { EUR, PLN, GBP }

data class Money(val amount: BigDecimal, val currency: Currency) {

    operator fun plus(another: Money): Money {
        if (currency != another.currency) throw IllegalArgumentException("You cannot add $currency to ${another.currency}")
        return Money(this.amount + another.amount, currency)
    }

    operator fun minus(another: Money): Money {
        if (currency != another.currency) throw IllegalArgumentException("You cannot subtract ${another.currency} from $currency")
        return Money(this.amount - another.amount, currency)
    }

    operator fun times(multiplier: BigDecimal) = copy(amount = amount * multiplier)

    companion object
}

fun main() {
    val m1 = Money(BigDecimal.TEN, Currency.EUR)
    val m2 = Money(BigDecimal.ONE, Currency.EUR)
    val m3 = m1 + m2
    val m4 = m1 - m2
    val m5 = m1 * BigDecimal.valueOf(2)

    val money0 = 10.toMoney(Currency.PLN)
    val money1 = 10.pln()
    val money2 = 10.eur
    val money3 = 10.GBP
    val money4 = PLN(10)
    val money5 = Money.eur(10)
}

fun Int.toMoney(currency: Currency) = Money(this.toBigDecimal(), currency)

fun Int.pln() = Money(this.toBigDecimal(), Currency.PLN)

val Int.eur get() = Money(this.toBigDecimal(), Currency.EUR)

val Int.GBP get() = Money(this.toBigDecimal(), Currency.GBP)

fun PLN(value: Int) = Money(value.toBigDecimal(), Currency.PLN)

fun Money.Companion.eur(value: Int) = Money(value.toBigDecimal(), Currency.PLN)
