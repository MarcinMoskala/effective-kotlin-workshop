package examples.n3

import examples.massiveRun
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

private val semaphore = Semaphore(permits = 10)
//val counterContext = Executors.newSingleThreadExecutor()
//    .asCoroutineDispatcher()
private var counter = 0

//var cache = listOf<User>()
//
//suspend fun fetchUser() = mutex.withLock {
//    val user = fetchUser()
//    cache += user
//    user
//}

fun main() = runBlocking {
    massiveRun {
        semaphore.withPermit {
            delay(1000)
            counter++
            println(counter)
        }
    }
    println("Counter = $counter")
}
