package com.bms.app.di

import com.bms.app.data.repository.AuthRepositoryImpl
import com.bms.app.data.repository.ProfileRepositoryImpl
import com.bms.app.data.repository.AppointmentRepositoryImpl
import com.bms.app.data.repository.AvailabilityRepositoryImpl
import com.bms.app.data.repository.ChatRepositoryImpl
import com.bms.app.domain.repository.AuthRepository
import com.bms.app.domain.repository.ProfileRepository
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.AvailabilityRepository
import com.bms.app.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindAppointmentRepository(
        appointmentRepositoryImpl: AppointmentRepositoryImpl
    ): AppointmentRepository

    @Binds
    @Singleton
    abstract fun bindAvailabilityRepository(
        availabilityRepositoryImpl: AvailabilityRepositoryImpl
    ): AvailabilityRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository
}
