package dev.diegooliveira.visits.domain

import dev.diegooliveira.visits.model.Visit
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateVisitUseCaseTest {
    private val validate = ValidateVisitUseCase()

    @Test
    fun rejectsVisitWithoutCustomer() {
        val result = validate(
            Visit(
                id = "visit-1",
                customerId = "",
                scheduledAtEpochMillis = 1_800_000_000_000,
            ),
        )

        assertEquals(
            ValidationResult.Invalid("Customer is required"),
            result,
        )
    }

    @Test
    fun acceptsCompleteVisit() {
        val result = validate(
            Visit(
                id = "visit-1",
                customerId = "customer-1",
                scheduledAtEpochMillis = 1_800_000_000_000,
            ),
        )

        assertEquals(ValidationResult.Valid, result)
    }
}
