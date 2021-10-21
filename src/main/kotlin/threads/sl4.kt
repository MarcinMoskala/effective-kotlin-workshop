package examples.sl4

import examples.Dog
import examples.massiveRun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

val dogs = mutableListOf<Dog>()
private val LOCK = Any()

fun main() = runBlocking {
    massiveRun {
        synchronized(LOCK) {
            dogs += Dog("Reks")
        }
    }
    println("Counter = ${dogs.size}")
}