package dev.diegooliveira.visits.domain

sealed interface ValidationResult {
    data object Valid : ValidationResult

    data class Invalid(val reason: String) : ValidationResult
}
