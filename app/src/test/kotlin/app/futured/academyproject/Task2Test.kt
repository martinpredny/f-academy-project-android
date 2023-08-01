package app.futured.academyproject

import app.futured.academyproject.data.NetworkClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class Task2Test {

    private lateinit var networkClient: NetworkClient

    @Before
    fun setup() {
        networkClient = NetworkClient()
    }

    @Test
    fun sendReturnsZero() = runTest {
        launch {
            networkClient.send().collect { result ->
                assert(result == 0)
            }
        }
    }

    @Test
    fun sendAndReturnErrorThrowsError() = runTest {
        launch {
            networkClient.sendAndReturnError().collect { result ->
                assert(result is Exception)
            }
        }
    }
}
