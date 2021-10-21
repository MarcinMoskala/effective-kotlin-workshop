package examples

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Dog(val name: String)

var dogs = listOf<Dog>()

fun main() = runBlocking {
    massiveRun {
        dogs += Dog("Reks")
    }
    println("Counter = ${dogs.size}")
}