package com.bms.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bms.app.data.local.SessionManager
import com.bms.app.ui.admin.ProvidersScreen
import com.bms.app.ui.admin.UserDetailScreen
import com.bms.app.ui.admin.AdminBookingScreen
import com.bms.app.ui.admin.BookServiceScreen
import com.bms.app.ui.auth.AccessLevel
import com.bms.app.ui.auth.AuthScreen
import com.bms.app.ui.auth.ProviderRegistrationScreen
import com.bms.app.ui.chat.ChatScreen
import com.bms.app.ui.chat.SupportScreen
import com.bms.app.ui.dashboard.AdminDashboardScreen
import com.bms.app.ui.dashboard.ProviderDashboardScreen
import com.bms.app.ui.dashboard.UserDashboardScreen
import com.bms.app.ui.schedule.AdminScheduleScreen
import com.bms.app.ui.schedule.ManageAvailabilityScreen
import com.bms.app.ui.settings.*
import com.bms.app.ui.user.BrowseProvidersScreen
import com.bms.app.ui.user.MyBookingsScreen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object ProviderRegistration : Screen("provider_registration")
    object ProviderDashboard : Screen("provider_dashboard")
    object AdminDashboard : Screen("admin_dashboard")
    object Settings : Screen("settings")
    object PersonalInfo : Screen("personal_info")
    object ProfessionalInfo : Screen("professional_info")
    object VisibilitySettings : Screen("visibility_settings")
    object NotificationPreferences : Screen("notification_preferences")
    object ManageAvailability : Screen("manage_availability")
    object ProviderSchedule : Screen("provider_schedule")
    object AdminSchedule : Screen("admin_schedule")
    object UserDetail : Screen("user_detail/{userId}")
    object Chat : Screen("chat/{userId}")
    object Providers : Screen("providers_screen")
    object AdminBooking : Screen("admin_booking")
    object BookService : Screen("book_service/{providerId}")
    object Support : Screen("support")
    object Inbox : Screen("inbox")
    // ── User Screens ──────────────────────────────────────
    object UserDashboard : Screen("user_dashboard")
    object MyBookings : Screen("my_bookings")
    object BrowseProviders : Screen("browse_providers")
}

@Composable
fun BmsNavigation(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    // NOTE: Read role dynamically so it always reflects the latest session state
    // A static val would get stale if user role changes without recomposition

    val startDestination = remember {
        when (sessionManager.getUserRole()) {
            "ADMIN"    -> Screen.AdminDashboard.route
            "PROVIDER" -> Screen.ProviderDashboard.route
            "USER"     -> Screen.UserDashboard.route
            else       -> Screen.Auth.route
        }
    }

    // ── Centralized bottom nav handler ────────────────────────────────────────
    val handleBottomNav: (String) -> Unit = { route ->
        when (route) {
            "home" -> {
                // Always read the role fresh — never use a stale captured value
                val currentRole = sessionManager.getUserRole()
                val homeRoute = when (currentRole) {
                    "ADMIN"    -> Screen.AdminDashboard.route
                    "PROVIDER" -> Screen.ProviderDashboard.route
                    "USER"     -> Screen.UserDashboard.route
                    else       -> Screen.Auth.route
                }
                navController.navigate(homeRoute) {
                    popUpTo(homeRoute) { inclusive = false }
                    launchSingleTop = true
                }
            }
            "admin_booking" -> {
                navController.navigate(Screen.AdminBooking.route) {
                    launchSingleTop = true
                }
            }
            // ── User nav actions ─────────────────────────────────────────
            "my_bookings" -> {
                navController.navigate(Screen.MyBookings.route) {
                    launchSingleTop = true
                }
            }
            "browse_providers" -> {
                navController.navigate(Screen.BrowseProviders.route) {
                    launchSingleTop = true
                }
            }
            "messages" -> {
                navController.navigate(Screen.Inbox.route) {
                    launchSingleTop = true
                }
            }
            // ── Shared nav actions ───────────────────────────────────────
            "schedule" -> {
                val currentRole = sessionManager.getUserRole()
                val scheduleRoute = if (currentRole == "ADMIN") Screen.AdminSchedule.route else "provider_schedule"
                navController.navigate(scheduleRoute) {
                    launchSingleTop = true
                }
            }
            "providers" -> {
                if (sessionManager.getUserRole() == "ADMIN") {
                    navController.navigate(Screen.Providers.route) {
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate(Screen.ProviderDashboard.route) {
                        popUpTo(Screen.ProviderDashboard.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
            "settings" -> {
                navController.navigate(Screen.Settings.route) {
                    launchSingleTop = true
                }
            }
            else -> {
                navController.navigate(route)
            }
        }
    }

    // Avatar button → navigate to Settings
    val handleAvatarClick: () -> Unit = {
        navController.navigate(Screen.Settings.route) {
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ── Auth ──────────────────────────────────
        composable(Screen.Auth.route) {
            AuthScreen(
                onLoginSuccess = { role ->
                    sessionManager.saveUserRole(role.name)
                    val destination = when (role) {
                        AccessLevel.ADMIN    -> Screen.AdminDashboard.route
                        AccessLevel.PROVIDER -> Screen.ProviderDashboard.route
                        AccessLevel.USER     -> Screen.UserDashboard.route
                    }
                    navController.navigate(destination) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                },
                onJoinAsProvider = {
                    navController.navigate(Screen.ProviderRegistration.route)
                }
            )
        }

        // ── Provider Registration ─────────────────
        composable(Screen.ProviderRegistration.route) {
            ProviderRegistrationScreen(
                onBack = { navController.popBackStack() },
                onCreateAccount = {
                    sessionManager.saveUserRole("PROVIDER")
                    navController.navigate(Screen.ProviderDashboard.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Provider Dashboard ────────────────────
        composable(Screen.ProviderDashboard.route) {
            ProviderDashboardScreen(
                onManageAvailability = {
                    navController.navigate(Screen.ManageAvailability.route)
                },
                onNavigate = handleBottomNav,
                onMessagePatient = { userId ->
                    navController.navigate("chat/$userId")
                },
                onContactSupport = {
                    navController.navigate(Screen.Support.route)
                },
                onInboxClick = {
                    navController.navigate(Screen.Inbox.route)
                }
            )
        }

        // ── Admin Dashboard ───────────────────────
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onNavigate = handleBottomNav,
                onAvatarClick = handleAvatarClick,
                onDeepNavigate = { route -> navController.navigate(route) },
                onInboxClick = { navController.navigate(Screen.Inbox.route) }
            )
        }

        // ── Admin Booking ─────────────────────────
        composable(Screen.AdminBooking.route) {
            AdminBookingScreen(
                onNavigate = handleBottomNav,
                onAvatarClick = handleAvatarClick
            )
        }

        // ── Providers (Admin) ─────────────────────
        composable(Screen.Providers.route) {
            ProvidersScreen(
                onNavigate = handleBottomNav,
                onAvatarClick = handleAvatarClick
            )
        }

        // ── Settings ──────────────────────────────
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToPersonal = {
                    navController.navigate(Screen.PersonalInfo.route)
                },
                onNavigateToProfessional = {
                    navController.navigate(Screen.ProfessionalInfo.route)
                },
                onNavigateToVisibility = {
                    navController.navigate(Screen.VisibilitySettings.route)
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.NotificationPreferences.route)
                },
                onNavigateToAvailability = {
                    navController.navigate(Screen.ManageAvailability.route)
                },
                onLogout = {
                    sessionManager.clearSession()
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onBottomNav = handleBottomNav
            )
        }

        // ── Personal Info ─────────────────────────
        composable(Screen.PersonalInfo.route) {
            PersonalInfoScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Professional Info ─────────────────────
        composable(Screen.ProfessionalInfo.route) {
            ProfessionalInfoScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Visibility Settings ───────────────────
        composable(Screen.VisibilitySettings.route) {
            VisibilitySettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Notification Preferences ──────────────
        composable(Screen.NotificationPreferences.route) {
            NotificationPreferencesScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Admin Schedule ────────────────────────
        composable(Screen.AdminSchedule.route) {
            AdminScheduleScreen(
                onNavigate = handleBottomNav
            )
        }

        // ── Provider Schedule Hub ─────────────────
        composable(Screen.ProviderSchedule.route) {
            com.bms.app.ui.schedule.ProviderScheduleScreen(
                onNavigate = handleBottomNav
            )
        }

        // ── Manage Availability (Settings) ──
        composable(Screen.ManageAvailability.route) {
            ManageAvailabilityScreen(
                onNavigate = handleBottomNav
            )
        }

        // ── User Detail ───────────────────────────
        composable(Screen.UserDetail.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserDetailScreen(
                userId = userId,
                onBack = { navController.popBackStack() },
                onNavigate = handleBottomNav
            )
        }

        // ── Chat Screen ──────────────────────
        composable(Screen.Chat.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ChatScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }

        // ── Book Service ──────────────────────
        composable(Screen.BookService.route) { backStackEntry ->
            val providerId = backStackEntry.arguments?.getString("providerId") ?: ""
            val role = sessionManager.getUserRole()
            BookServiceScreen(
                providerId = providerId,
                onBack = { navController.popBackStack() },
                onBookingSuccess = {
                    if (role == "USER") {
                        // Take user to My Bookings so they can see the confirmed appointment
                        navController.navigate(Screen.MyBookings.route) {
                            popUpTo(Screen.BrowseProviders.route) { inclusive = true }
                        }
                    } else {
                        // Admin/Provider flow — go to schedule
                        navController.navigate(Screen.AdminSchedule.route) {
                            popUpTo(Screen.AdminBooking.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        // ── Support (Provider → Admin Chat gateway) ──────────
        composable(Screen.Support.route) {
            SupportScreen(
                onBack = { navController.popBackStack() },
                onOpenChat = { adminUserId ->
                    // Replace support screen with actual chat so Back returns to dashboard
                    navController.navigate("chat/$adminUserId") {
                        popUpTo(Screen.Support.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Inbox (Messages Hub) ──────────────────
        composable(Screen.Inbox.route) {
            com.bms.app.ui.chat.InboxScreen(
                onBack = { navController.popBackStack() },
                onOpenChat = { userId ->
                    navController.navigate("chat/$userId")
                }
            )
        }

        // ── User Dashboard ────────────────────────
        composable(Screen.UserDashboard.route) {
            UserDashboardScreen(
                onNavigate = handleBottomNav,
                onMessageProvider = { userId ->
                    // Standardized: Always pass auth UUID (userId) to chat route
                    navController.navigate("chat/$userId")
                },
                onBrowseProviders = {
                    navController.navigate(Screen.BrowseProviders.route) {
                        launchSingleTop = true
                    }
                },
                onViewAllBookings = {
                    navController.navigate(Screen.MyBookings.route) {
                        launchSingleTop = true
                    }
                },
                onInboxClick = { navController.navigate(Screen.Inbox.route) }
            )
        }

        // ── My Bookings ───────────────────────────
        composable(Screen.MyBookings.route) {
            MyBookingsScreen(
                onNavigate = handleBottomNav,
                onBack = { navController.popBackStack() },
                onMessageProvider = { userId ->
                    navController.navigate("chat/$userId")
                }
            )
        }

        // ── Browse Providers ──────────────────────
        composable(Screen.BrowseProviders.route) {
            BrowseProvidersScreen(
                onNavigate = handleBottomNav,
                onBack = { navController.popBackStack() },
                onBookProvider = { providerId ->
                    navController.navigate("book_service/$providerId")
                }
            )
        }
    }
}
