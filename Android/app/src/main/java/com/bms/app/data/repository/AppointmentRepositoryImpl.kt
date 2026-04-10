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
}
