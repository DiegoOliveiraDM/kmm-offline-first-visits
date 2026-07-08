package dev.diegooliveira.visits.data

import dev.diegooliveira.visits.model.Visit
import kotlinx.coroutines.flow.Flow

interface VisitRepository {
    fun observeVisits(): Flow<List<Visit>>

    fun observeSyncState(): Flow<SyncState>

    suspend fun saveLocally(visit: Visit)

    suspend fun syncPending()
}
