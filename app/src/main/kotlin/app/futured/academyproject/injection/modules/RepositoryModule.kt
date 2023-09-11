package app.futured.academyproject.injection.modules

import android.content.Context
import app.futured.academyproject.data.persistence.db.AppDatabase
import app.futured.academyproject.data.persistence.db.culture.CulturalPlaceDao
import app.futured.academyproject.data.persistence.db.culture.CulturalPlacesRepository
import app.futured.academyproject.data.persistence.db.culture.CulturalPlacesRepositoryImpl
import app.futured.academyproject.data.persistence.db.event.EventDao
import app.futured.academyproject.data.persistence.db.event.EventsRepository
import app.futured.academyproject.data.persistence.db.event.EventsRepositoryImpl
import app.futured.academyproject.data.persistence.db.tourism.TouristPlaceDao
import app.futured.academyproject.data.persistence.db.tourism.TouristPlacesRepository
import app.futured.academyproject.data.persistence.db.tourism.TouristPlacesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideCulturalPlacesRepository(dao: CulturalPlaceDao): CulturalPlacesRepository {
        return CulturalPlacesRepositoryImpl(dao)
    }
    @Provides
    fun provideTouristPlacesRepository(dao: TouristPlaceDao): TouristPlacesRepository {
        return TouristPlacesRepositoryImpl(dao)
    }
    @Provides
    fun provideEventRepository(dao: EventDao): EventsRepository {
        return EventsRepositoryImpl(dao)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    @Provides
    fun provideCulturalPlaceDao(appDatabase: AppDatabase): CulturalPlaceDao {
        return appDatabase.culturalPlaceDao()
    }
    @Provides
    fun provideTouristPlaceDao(appDatabase: AppDatabase): TouristPlaceDao {
        return appDatabase.touristPlaceDao()
    }
    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }
}