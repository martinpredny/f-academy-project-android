package app.futured.academyproject.domain

import android.os.Build
import androidx.annotation.RequiresApi
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.data.model.mappers.toEvent
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<Event>>() {

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun build(args: Unit): List<Event> {
        val events = apiManager.getEvents()
        return events.features.map { it.toEvent() }
    }
}
