package com.bms.app.domain.repository

import com.bms.app.domain.model.Appointment

interface AppointmentRepository {
    suspend fun getAppointmentsForProvider(providerProfileId: String): Result<List<Appointment>>
    suspend fun getAppointmentsForUser(userId: String): Result<List<Appointment>>
    suspend fun getAllAppointments(): Result<List<Appointment>>
    suspend fun getTodayAppointments(providerProfileId: String): Result<List<Appointment>>
    suspend fun createAppointment(appointment: Appointment): Result<Unit>
    suspend fun cancelAppointment(appointmentId: String, reason: String?): Result<Unit>
    suspend fun completeAppointment(appointmentId: String): Result<Unit>
}
