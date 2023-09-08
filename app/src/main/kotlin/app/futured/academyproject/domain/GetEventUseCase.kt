package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.data.store.EventsStore
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    private val eventsStore: EventsStore,
) : UseCase<GetEventUseCase.Args, Event>() {

    override suspend fun build(args: Args): Event =
        eventsStore.getEvent(args.eventId) ?: throw IllegalArgumentException("Event with id ${args.eventId} not found")

    data class Args(val eventId: Int)
}
