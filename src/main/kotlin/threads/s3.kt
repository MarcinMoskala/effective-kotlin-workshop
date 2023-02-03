package examples.n1

import examples.massiveRun
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

private val counter = AtomicInteger()

fun main() = runBlocking {
    massiveRun {
        counter.incrementAndGet()
    }
    println("Counter = ${counter.get()}")
}
