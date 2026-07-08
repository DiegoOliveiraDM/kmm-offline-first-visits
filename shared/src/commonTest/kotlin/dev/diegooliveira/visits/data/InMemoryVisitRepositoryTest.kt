package dev.diegooliveira.visits.data

import dev.diegooliveira.visits.model.Visit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class InMemoryVisitRepositoryTest {
    @Test
    fun savesLocallyBeforeSyncing() = runTest {
        val repository = InMemoryVisitRepository(remoteSync = {})
        val visit = sampleVisit()

        repository.saveLocally(visit)

        assertEquals(listOf(visit), repository.observeVisits().first())
    }

    @Test
    fun reportsFailureAndKeepsDataWhenRemoteSyncFails() = runTest {
        val repository = InMemoryVisitRepository {
            error("Network unavailable")
        }
        val visit = sampleVisit()
        repository.saveLocally(visit)

        repository.syncPending()

        assertEquals(listOf(visit), repository.observeVisits().first())
        assertEquals(
            SyncState.Failed("Network unavailable"),
            repository.observeSyncState().first(),
        )
    }

    @Test
    fun returnsToIdleAfterSuccessfulSync() = runTest {
        var syncedVisits = emptyList<Visit>()
        val repository = InMemoryVisitRepository { syncedVisits = it }
        val visit = sampleVisit()
        repository.saveLocally(visit)

        repository.syncPending()

        assertEquals(listOf(visit), syncedVisits)
        assertIs<SyncState.Idle>(repository.observeSyncState().first())
    }

    private fun sampleVisit() = Visit(
        id = "visit-1",
        customerId = "customer-1",
        scheduledAtEpochMillis = 1_800_000_000_000,
    )
}
