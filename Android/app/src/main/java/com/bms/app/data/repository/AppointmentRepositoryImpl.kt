package com.bms.app.data.repository

import com.bms.app.domain.model.Appointment
import com.bms.app.domain.repository.AppointmentRepository
import io.github.jan.supabase.postgrest.Postgrest
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class AppointmentStatusUpdate(val status: String)

@Serializable
data class AppointmentRejectUpdate(
    val status: String,
    @SerialName("cancellation_reason") val cancellationReason: String?
)

@Serializable
data class RescheduleUpdate(
    val status: String,
    @SerialName("reschedule_requested_date") val requestedDate: String,
    @SerialName("reschedule_requested_start_time") val requestedStartTime: String,
    @SerialName("reschedule_requested_end_time") val requestedEndTime: String,
    @SerialName("cancellation_reason") val cancellationReason: String
)

@Serializable
data class AcceptRescheduleUpdate(
    val status: String,
    @SerialName("appointment_date") val appointmentDate: String?,
    @SerialName("start_time") val startTime: String?,
    @SerialName("end_time") val endTime: String?,
    @SerialName("reschedule_requested_date") val requestedDate: String? = null,
    @SerialName("reschedule_requested_start_time") val requestedStartTime: String? = null,
    @SerialName("reschedule_requested_end_time") val requestedEndTime: String? = null,
    @SerialName("cancellation_reason") val cancellationReason: String? = null
)

@Singleton
class AppointmentRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : AppointmentRepository {

    override suspend fun getAppointmentsForProvider(providerProfileId: String): Result<List<Appointment>> {
        return try {
            val appointments = postgrest["appointments"]
                .select {
                    filter { eq("provider_id", providerProfileId) }
                }
                .decodeList<Appointment>()

            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAppointmentsForUser(userId: String): Result<List<Appointment>> {
        return try {
            val appointments = postgrest["appointments"]
                .select {
                    filter { eq("user_id", userId) }
                }
                .decodeList<Appointment>()

            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllAppointments(): Result<List<Appointment>> {
        return try {
            val appointments = postgrest["appointments"]
                .select()
                .decodeList<Appointment>()

            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTodayAppointments(providerProfileId: String): Result<List<Appointment>> {
        return try {
            val today = LocalDate.now().toString()
            val appointments = postgrest["appointments"]
                .select {
                    filter {
                        eq("provider_id", providerProfileId)
                        eq("appointment_date", today)
                    }
                }
                .decodeList<Appointment>()

            Result.success(appointments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createAppointment(appointment: Appointment): Result<Unit> {
        return try {
            postgrest["appointments"].insert(appointment)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun confirmAppointment(appointmentId: String): Result<Unit> {
        return try {
            postgrest["appointments"].update(
                AppointmentStatusUpdate(status = "approved")
            ) {
                filter { eq("id", appointmentId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectAppointment(appointmentId: String, reason: String?): Result<Unit> {
        return try {
            postgrest["appointments"].update(
                AppointmentRejectUpdate(status = "rejected", cancellationReason = reason)
            ) {
                filter { eq("id", appointmentId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelAppointment(appointmentId: String, reason: String?): Result<Unit> {
        return try {
            postgrest["appointments"].update(
                AppointmentRejectUpdate(status = "cancelled", cancellationReason = reason)
            ) {
                filter { eq("id", appointmentId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun completeAppointment(appointmentId: String): Result<Unit> {
        return try {
            postgrest["appointments"].update(
                AppointmentStatusUpdate(status = "completed")
            ) {
                filter { eq("id", appointmentId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rescheduleAppointment(
        appointmentId: String,
        newDate: String,
        newStartTime: String,
        newEndTime: String,
        reason: String
    ): Result<Unit> {
        return try {
            postgrest["appointments"].update(
                RescheduleUpdate(
                    status = "rescheduling",
                    requestedDate = newDate,
                    requestedStartTime = newStartTime,
                    requestedEndTime = newEndTime,
                    cancellationReason = reasoning(reason)
                )
            ) {
                filter { eq("id", appointmentId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun reasoning(text: String): String {
        return if (text.startsWith("reschedule:")) text else "reschedule: $text"
    }

    override suspend fun acceptReschedule(appointmentId: String): Result<Unit> {
        return try {
            // First fetch the appointment to get the requested values
            val appt = postgrest["appointments"].select {
                filter { eq("id", appointmentId) }
            }.decodeSingle<Appointment>()

            postgrest["appointments"].update(
                AcceptRescheduleUpdate(
                    status = "approved",
                    appointmentDate = appt.rescheduleRequestedDate,
                    startTime = appt.rescheduleRequestedStartTime,
                    endTime = appt.rescheduleRequestedEndTime,
                    requestedDate = null,
                    requestedStartTime = null,
                    requestedEndTime = null,
                    cancellationReason = null
                )
            ) {
                filter { eq("id", appointmentId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun declineReschedule(appointmentId: String): Result<Unit> {
        return try {
            postgrest["appointments"].update(
                AcceptRescheduleUpdate(
                    status = "approved", // Stay approved as it was before reschedule request
                    appointmentDate = null, // Don't change main date
                    startTime = null,
                    endTime = null,
                    requestedDate = null,
                    requestedStartTime = null,
                    requestedEndTime = null,
                    cancellationReason = null
                )
            ) {
                filter { eq("id", appointmentId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
