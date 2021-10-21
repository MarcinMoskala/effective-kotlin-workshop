package examples.sl2

import examples.Dog
import examples.massiveRun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

val dogs = mutableListOf<Dog>()

fun main() = runBlocking {
    massiveRun {
        dogs += Dog("Reks")
    }
    println("Counter = ${dogs.size}")
}