package com.bms.app.ui.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.ui.components.BmsTextField
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*

enum class AuthTab { LOGIN, SIGNUP }
enum class AccessLevel { ADMIN, PROVIDER, USER }

@Composable
fun AuthScreen(
    onLoginSuccess: (AccessLevel) -> Unit,
    onJoinAsProvider: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, snackbarHostState) {
        viewModel.events.collect { event ->
            when (event) {
                is AuthEvent.Success -> onLoginSuccess(event.role)
                is AuthEvent.Error -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Background)
                .systemBarsPadding() // prevent device boundaries cut-off
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // ── BookEase24X7 Logo ──────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "BookEase24X7",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = ManropeFamily
                        ),
                        color = OnSurface
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ── Hero Header ──────────────────────────
                Text(
                    text = if (uiState.selectedTab == AuthTab.LOGIN) "Welcome back" else "Create your account",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (uiState.selectedTab == AuthTab.LOGIN) "Sign in to manage your appointments" else "Join the Appointment Management System",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // ── Auth Tabs (Pill Toggle) ──────────────
                Surface(
                    color = SurfaceContainerLow,
                    shape = PillShape,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        AuthTab.entries.forEach { tab ->
                            val isSelected = uiState.selectedTab == tab
                            Surface(
                                onClick = { viewModel.updateTab(tab) },
                                color = if (isSelected) SurfaceContainerLowest else Color.Transparent,
                                shape = PillShape,
                                shadowElevation = if (isSelected) 1.dp else 0.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (tab == AuthTab.LOGIN) "Log In" else "Sign Up",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = if (isSelected) Primary else OnSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ── Role Selection ───────────────────────
                if (uiState.selectedTab == AuthTab.SIGNUP) {
                    Text(
                        text = "SELECT ACCESS LEVEL",
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = OnSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, bottom = 16.dp)
                    )

                    RoleCard(
                        title = "Admin Access",
                        subtitle = "Full organizational controls",
                        icon = Icons.Outlined.AdminPanelSettings,
                        isSelected = uiState.selectedRole == AccessLevel.ADMIN,
                        selectedBg = Primary,
                        selectedFg = OnPrimary,
                        iconBg = Color.White.copy(alpha = 0.2f),
                        onClick = { viewModel.updateRole(AccessLevel.ADMIN) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    RoleCard(
                        title = "Offer Services",
                        subtitle = "Setup provider profile",
                        icon = Icons.Outlined.Storefront,
                        isSelected = uiState.selectedRole == AccessLevel.PROVIDER,
                        iconBg = SecondaryContainer,
                        iconTint = OnSecondaryContainer,
                        onClick = { viewModel.updateRole(AccessLevel.PROVIDER) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    RoleCard(
                        title = "Book Appointments",
                        subtitle = "Schedule for yourself",
                        icon = Icons.Outlined.Person,
                        isSelected = uiState.selectedRole == AccessLevel.USER,
                        iconBg = TertiaryContainer,
                        iconTint = OnTertiaryContainer,
                        onClick = { viewModel.updateRole(AccessLevel.USER) }
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }

                // ── Form Fields ──────────────────────────
                if (uiState.selectedTab == AuthTab.SIGNUP) {
                    BmsTextField(
                        value = uiState.fullName,
                        onValueChange = { viewModel.updateFullName(it) },
                        label = "Full Name",
                        placeholder = "Enter your full name",
                        leadingIcon = Icons.Outlined.Person
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                BmsTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = "Email Address",
                    placeholder = "name@company.com",
                    leadingIcon = Icons.Outlined.Email
                )
                Spacer(modifier = Modifier.height(16.dp))

                BmsTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    label = "Password",
                    placeholder = "••••••••",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    passwordVisible = uiState.passwordVisible,
                    onPasswordVisibilityToggle = { viewModel.togglePasswordVisibility() }
                )
                
                if (uiState.selectedTab == AuthTab.LOGIN) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Forgot Password?",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = Primary,
                            modifier = Modifier.clickable { onForgotPassword() }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // ── CTA Button ──────────────────────────
                if (uiState.isLoading) {
                    SkeletonBox(height = 52.dp, shape = PillShape)
                } else {
                    BmsButton(
                        text = if (uiState.selectedTab == AuthTab.LOGIN) "Log In" else "Continue",
                        onClick = { viewModel.authenticate() },
                        trailingIcon = Icons.Outlined.ArrowForward
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ── Footer Links ─────────────────────────
                Row(
                    horizontalArrangement = Arrangement.Center, // dynamically adjust string
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (uiState.selectedTab == AuthTab.LOGIN) "Don't have an account? " else "Already have an account? ",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = if (uiState.selectedTab == AuthTab.LOGIN) "Sign up" else "Log in",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = Primary,
                        modifier = Modifier.clickable {
                            val newTab = if (uiState.selectedTab == AuthTab.LOGIN) AuthTab.SIGNUP else AuthTab.LOGIN
                            viewModel.updateTab(newTab)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ── OR divider ───────────────────────────
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = OutlineVariant.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "OR",
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 2.sp
                        ),
                        color = OutlineVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = OutlineVariant.copy(alpha = 0.2f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ── Join as provider link ────────────────
                OutlinedButton(
                    onClick = onJoinAsProvider,
                    shape = PillShape,
                    border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = OnSurfaceVariant
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Work,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Join as a provider",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isSelected: Boolean,
    selectedBg: Color = Primary,
    selectedFg: Color = OnPrimary,
    iconBg: Color = SurfaceContainerLow,
    iconTint: Color = OnSurfaceVariant,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        if (isSelected) selectedBg else SurfaceContainerLowest,
        label = "roleBg"
    )
    val fgColor by animateColorAsState(
        if (isSelected) selectedFg else OnSurface,
        label = "roleFg"
    )

    Surface(
        onClick = onClick,
        color = bgColor,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = if (!isSelected) 2.dp else 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!isSelected) Modifier.border(
                    2.dp,
                    Color.Transparent,
                    RoundedCornerShape(16.dp)
                ) else Modifier
            )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) Color.White.copy(alpha = 0.2f) else iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) selectedFg else iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = fgColor
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = fgColor.copy(alpha = 0.8f)
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Selected",
                    tint = selectedFg,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
