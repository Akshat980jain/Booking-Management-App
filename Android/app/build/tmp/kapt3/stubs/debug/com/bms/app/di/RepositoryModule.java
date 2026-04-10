package com.bms.app.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\'\u00a8\u0006\u0017"}, d2 = {"Lcom/bms/app/di/RepositoryModule;", "", "()V", "bindAppointmentRepository", "Lcom/bms/app/domain/repository/AppointmentRepository;", "appointmentRepositoryImpl", "Lcom/bms/app/data/repository/AppointmentRepositoryImpl;", "bindAuthRepository", "Lcom/bms/app/domain/repository/AuthRepository;", "authRepositoryImpl", "Lcom/bms/app/data/repository/AuthRepositoryImpl;", "bindAvailabilityRepository", "Lcom/bms/app/domain/repository/AvailabilityRepository;", "availabilityRepositoryImpl", "Lcom/bms/app/data/repository/AvailabilityRepositoryImpl;", "bindChatRepository", "Lcom/bms/app/domain/repository/ChatRepository;", "chatRepositoryImpl", "Lcom/bms/app/data/repository/ChatRepositoryImpl;", "bindProfileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "profileRepositoryImpl", "Lcom/bms/app/data/repository/ProfileRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.bms.app.domain.repository.AuthRepository bindAuthRepository(@org.jetbrains.annotations.NotNull()
    com.bms.app.data.repository.AuthRepositoryImpl authRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.bms.app.domain.repository.ProfileRepository bindProfileRepository(@org.jetbrains.annotations.NotNull()
    com.bms.app.data.repository.ProfileRepositoryImpl profileRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.bms.app.domain.repository.AppointmentRepository bindAppointmentRepository(@org.jetbrains.annotations.NotNull()
    com.bms.app.data.repository.AppointmentRepositoryImpl appointmentRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.bms.app.domain.repository.AvailabilityRepository bindAvailabilityRepository(@org.jetbrains.annotations.NotNull()
    com.bms.app.data.repository.AvailabilityRepositoryImpl availabilityRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.bms.app.domain.repository.ChatRepository bindChatRepository(@org.jetbrains.annotations.NotNull()
    com.bms.app.data.repository.ChatRepositoryImpl chatRepositoryImpl);
}