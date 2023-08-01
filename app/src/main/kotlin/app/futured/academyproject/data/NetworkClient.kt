package app.futured.academyproject.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkClient {

    fun send(): Flow<Int> {
        return flow {
            delay(300)
            emit(0)
        }
    }

    fun sendAndReturnError(): Flow<Any> {
        //TODO("edit function to wait 300 ms and throw exception")
        return flow {
            delay(300)
            emit(Exception())
        }
    }
}
