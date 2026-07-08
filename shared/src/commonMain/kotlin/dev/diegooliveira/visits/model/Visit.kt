package dev.diegooliveira.visits.model

data class Visit(
    val id: String,
    val customerId: String,
    val scheduledAtEpochMillis: Long?,
    val notes: String = "",
    val status: VisitStatus = VisitStatus.SCHEDULED,
)

enum class VisitStatus {
    SCHEDULED,
    COMPLETED,
    CANCELED,
}
