package examples.sl3

import examples.Dog
import examples.massiveRun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList

fun main() = runBlocking {
    val dogs = ConcurrentLinkedQueue<Dog>()
    massiveRun {
        dogs += Dog("Reks")
    }
    println("Counter = ${dogs.size}")
}