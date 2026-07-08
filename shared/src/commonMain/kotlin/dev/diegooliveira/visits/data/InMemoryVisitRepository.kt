package dev.diegooliveira.visits.data

import dev.diegooliveira.visits.model.Visit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryVisitRepository(
    private val remoteSync: suspend (List<Visit>) -> Unit,
) : VisitRepository {
    private val visits = MutableStateFlow<List<Visit>>(emptyList())
    private val pendingIds = mutableSetOf<String>()
    private val syncState = MutableStateFlow<SyncState>(SyncState.Idle)

    override fun observeVisits(): Flow<List<Visit>> = visits.asStateFlow()

    override fun observeSyncState(): Flow<SyncState> = syncState.asStateFlow()

    override suspend fun saveLocally(visit: Visit) {
        visits.update { current ->
            current.filterNot { it.id == visit.id } + visit
        }
        pendingIds += visit.id
    }

    override suspend fun syncPending() {
        val pending = visits.value.filter { it.id in pendingIds }
        if (pending.isEmpty()) return

        syncState.value = SyncState.Syncing
        runCatching { remoteSync(pending) }
            .onSuccess {
                pendingIds.removeAll(pending.map(Visit::id).toSet())
                syncState.value = SyncState.Idle
            }
            .onFailure { error ->
                syncState.value = SyncState.Failed(error.message ?: "Unknown sync error")
            }
    }
}
