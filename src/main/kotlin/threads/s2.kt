package examples.n1

import examples.massiveRun
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

private var counter = 0
private val LOCK = object {}

fun main() = runBlocking {
    massiveRun {
        synchronized(LOCK) {
            counter++
        }
    }
    println("Counter = ${counter}")
}
