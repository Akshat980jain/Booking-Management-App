package com.bms.app.domain.repository

import com.bms.app.domain.model.Appointment

interface AppointmentRepository {
    suspend fun getAppointmentsForProvider(providerProfileId: String): Result<List<Appointment>>
    suspend fun getAppointmentsForUser(userId: String): Result<List<Appointment>>
    suspend fun getAllAppointments(): Result<List<Appointment>>
    suspend fun getTodayAppointments(providerProfileId: String): Result<List<Appointment>>
    suspend fun createAppointment(appointment: Appointment): Result<Unit>
    suspend fun confirmAppointment(appointmentId: String): Result<Unit>
    suspend fun rejectAppointment(appointmentId: String, reason: String? = null): Result<Unit>
    suspend fun cancelAppointment(appointmentId: String, reason: String?): Result<Unit>
    suspend fun completeAppointment(appointmentId: String): Result<Unit>
    suspend fun rescheduleAppointment(appointmentId: String, newDate: String, newStartTime: String, newEndTime: String, reason: String): Result<Unit>
    suspend fun acceptReschedule(appointmentId: String): Result<Unit>
    suspend fun declineReschedule(appointmentId: String): Result<Unit>
}
