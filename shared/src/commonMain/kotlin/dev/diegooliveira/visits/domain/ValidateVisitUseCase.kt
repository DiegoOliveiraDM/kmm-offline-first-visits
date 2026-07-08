package dev.diegooliveira.visits.domain

import dev.diegooliveira.visits.model.Visit

class ValidateVisitUseCase {
    operator fun invoke(visit: Visit): ValidationResult =
        when {
            visit.id.isBlank() -> ValidationResult.Invalid("Visit id is required")
            visit.customerId.isBlank() -> ValidationResult.Invalid("Customer is required")
            visit.scheduledAtEpochMillis == null ->
                ValidationResult.Invalid("Scheduled date is required")
            else -> ValidationResult.Valid
        }
}
