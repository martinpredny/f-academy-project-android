package app.futured.academyproject

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class Task3Test {

    @Test
    fun santaAndGoblins() = runTest {
        val myFlow = flow {
            for (i in 1..10) {
                val randomDwarfTaskTime = Random.nextLong(100, 1001)
                delay(randomDwarfTaskTime)
                emit("Dwarf $i finished his work")
            }
        }
        this.launch {
            myFlow.collect { result ->
                println(result)
            }
            println("Now santa can finish his work")
        }
    }
}
