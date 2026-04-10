package com.bms.app.ui.dashboard

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.*
import androidx.compose.ui.graphics.Color
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigate: (String) -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onDeepNavigate: (String) -> Unit = {},
    onInboxClick: () -> Unit = {},
    viewModel: AdminViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("home") }
    var searchQuery by remember { mutableStateOf("") }
    
    val segments = listOf(
        "Analytics", "Approvals", "Providers", "Appts", "Payments", 
        "Revenue", "Users", "Emails", "Delivery", "Disputes", "Export"
    )
    var selectedAdminTab by remember { mutableStateOf(0) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var showAddUserDialog by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    val isLoading = uiState is AdminUiState.Loading

    // Dynamic initials from state
    val adminInitials = if (uiState is AdminUiState.Success) {
        (uiState as AdminUiState.Success).adminInitials
    } else ""

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "BookEase24X7",
                avatarInitials = adminInitials,
                onAvatarClick = onAvatarClick,
                onMessagesClick = onInboxClick,
                isLoading = isLoading
            )
        },
        bottomBar = {
            BmsBottomNavBar(
                items = AdminNavItems,
                selectedRoute = selectedNav,
                onItemSelected = { route ->
                    selectedNav = route
                    onNavigate(route)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddUserDialog = true },
                containerColor = Primary,
                contentColor = OnPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Outlined.PersonAdd, "Add User")
            }
        }
    ) { padding ->
        if (showAddUserDialog) {
            QuickAddUserDialog(
                onDismiss = { showAddUserDialog = false },
                onConfirm = { name, email, role ->
                    viewModel.addUser(name, email, role)
                    showAddUserDialog = false
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {
            when (val state = uiState) {
                is AdminUiState.Loading -> {
                    // Full skeleton — also scrollable so it matches real content height
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        AdminDashboardSkeleton()
                    }
                }
                is AdminUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(if (state.isNetwork) Primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.errorContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                if (state.isNetwork) Icons.Outlined.WifiOff else Icons.Outlined.ErrorOutline,
                                null,
                                modifier = Modifier.size(40.dp),
                                tint = if (state.isNetwork) Primary else MaterialTheme.colorScheme.error
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            if (state.isNetwork) "No Internet Connection" else "Something went wrong",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        Button(
                            onClick = { viewModel.loadAdminDashboard() },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary)
                        ) {
                            Icon(Icons.Outlined.Refresh, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Retry")
                        }
                    }
                }
                is AdminUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // ── Header ────────────────────────────────
                        Text(
                            text = "Admin Dashboard",
                            style = MaterialTheme.typography.headlineLarge,
                            color = OnSurface
                        )
                        Text(
                            text = "System overview and management",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // ── Stat Cards ────────────────────────────
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                StatCard(
                                    title = "Total Appointments",
                                    value = state.totalAppointments.toString(),
                                    icon = Icons.Outlined.CalendarToday,
                                    modifier = Modifier.weight(1f)
                                )
                                StatCard(
                                    title = "Pending Providers",
                                    value = state.pendingProviders.toString(),
                                    icon = Icons.Outlined.PendingActions,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                StatCard(
                                    title = "Completed Sessions",
                                    value = state.completedSessions.toString(),
                                    icon = Icons.Outlined.CheckCircleOutline,
                                    modifier = Modifier.weight(1f)
                                )
                                StatCard(
                                    title = "Pending Bookings",
                                    value = state.pendingBookings.toString(),
                                    icon = Icons.Outlined.HourglassEmpty,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // ── Tab Navigation ───────────────────────
                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = segments[selectedAdminTab],
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = SurfaceContainerLowest,
                                    focusedContainerColor = SurfaceContainerLowest,
                                    unfocusedBorderColor = GhostBorder,
                                    focusedBorderColor = Primary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false }
                            ) {
                                segments.forEachIndexed { index, title ->
                                    DropdownMenuItem(
                                        text = { Text(title) },
                                        onClick = {
                                            selectedAdminTab = index
                                            dropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // ── Tab Content ──────────────────────────────
                        Box {
                            when (segments[selectedAdminTab]) {
                                "Analytics" -> {
                                    Column {
                                        // Booking Trends
                                        Surface(color = SurfaceContainerLowest, shape = RoundedCornerShape(12.dp)) {
                                            Column(modifier = Modifier.padding(16.dp)) {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                                    Text("Booking Trends (14 Days)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                                    Badge(containerColor = Primary.copy(alpha = 0.1f), contentColor = Primary) { Text("~ 0.0% WoW") }
                                                }
                                                Spacer(modifier = Modifier.height(16.dp))
                                                BmsLineChart(
                                                    dataPoints = listOf(5f, 12f, 8f, 15f, 10f, 20f, 18f),
                                                    modifier = Modifier.fillMaxWidth().height(120.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                        
                                        // Status Distribution
                                        Surface(color = SurfaceContainerLowest, shape = RoundedCornerShape(12.dp)) {
                                            Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text("Appointment Status Distribution", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))
                                                Spacer(modifier = Modifier.height(16.dp))
                                                DonutChart(
                                                    completed = state.completedSessions,
                                                    cancelled = state.totalAppointments - state.completedSessions,
                                                    modifier = Modifier.size(160.dp)
                                                )
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Box(modifier = Modifier.size(12.dp).background(Color(0xFF333E50)))
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Text("Completed", fontSize = 12.sp, color = OnSurfaceVariant)
                                                    }
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Box(modifier = Modifier.size(12.dp).background(MaterialTheme.colorScheme.error))
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Text("Cancelled", fontSize = 12.sp, color = OnSurfaceVariant)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                "Users" -> {
                                    Column {
                                        // ── Search Bar ────────────────────────────
                                        OutlinedTextField(
                                            value = searchQuery,
                                            onValueChange = { searchQuery = it },
                                            placeholder = { Text("Search users...", color = Outline.copy(alpha = 0.5f)) },
                                            leadingIcon = { Icon(Icons.Outlined.Search, null, tint = Outline, modifier = Modifier.size(20.dp)) },
                                            shape = InputShape,
                                            colors = OutlinedTextFieldDefaults.colors(
                                                unfocusedContainerColor = SurfaceContainerLowest,
                                                focusedContainerColor = SurfaceContainerLowest,
                                                unfocusedBorderColor = GhostBorder,
                                                focusedBorderColor = Primary,
                                                cursorColor = Primary
                                            ),
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        // ── User List ─────────────────────────────
                                        val filteredUsers = state.users.filter {
                                            it.fullName.contains(searchQuery, ignoreCase = true) ||
                                            it.email.contains(searchQuery, ignoreCase = true)
                                        }

                                        filteredUsers.forEach { user ->
                                            UserListItem(
                                                user = user,
                                                onDeepNavigate = onDeepNavigate,
                                                onSuspend = { viewModel.suspendUser(it) },
                                                onBan = { viewModel.banUser(it) },
                                                onChangeRole = { id, role -> viewModel.changeUserRole(id, role) }
                                            )
                                            if (user != filteredUsers.last()) {
                                                Spacer(modifier = Modifier.height(4.dp))
                                            }
                                        }
                                    }
                                }
                                "Approvals" -> {
                                    Column {
                                        val pendingProviders = state.users.filter { it.role == "provider" && it.status?.lowercase() == "pending" }
                                        if (pendingProviders.isEmpty()) {
                                            Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                                                Text("All caught up! No pending approvals.", color = OnSurfaceVariant)
                                            }
                                        } else {
                                            pendingProviders.forEach { provider ->
                                                UserListItem(
                                                    user = provider,
                                                    onDeepNavigate = onDeepNavigate,
                                                    onSuspend = { viewModel.suspendUser(it) },
                                                    onBan = { viewModel.banUser(it) },
                                                    onChangeRole = { id, role -> viewModel.changeUserRole(id, role) }
                                                )
                                                if (provider != pendingProviders.last()) {
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                                "Providers" -> {
                                    Column {
                                        val providers = state.users.filter { it.role == "provider" }
                                        providers.forEach { provider ->
                                            UserListItem(
                                                user = provider,
                                                onDeepNavigate = onDeepNavigate,
                                                onSuspend = { viewModel.suspendUser(it) },
                                                onBan = { viewModel.banUser(it) },
                                                onChangeRole = { id, role -> viewModel.changeUserRole(id, role) }
                                            )
                                            if (provider != providers.last()) {
                                                Spacer(modifier = Modifier.height(4.dp))
                                            }
                                        }
                                        if (providers.isEmpty()) {
                                            Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                                                Text("No providers found.", color = OnSurfaceVariant)
                                            }
                                        }
                                    }
                                }
                                "Appts" -> {
                                    Column {
                                        state.appointments.forEach { appointment ->
                                            AppointmentCard(appointment = appointment, users = state.users)
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                        if (state.appointments.isEmpty()) {
                                            Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                                                Text("No appointments found.", color = OnSurfaceVariant)
                                            }
                                        }
                                    }
                                }
                                "Payments" -> {
                                    Column {
                                        Text("Recent Transactions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = OnSurface)
                                        Spacer(modifier = Modifier.height(16.dp))
                                        
                                        if (state.transactions.isEmpty()) {
                                            Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                                                Text("No transactions record yet.", color = OnSurfaceVariant)
                                            }
                                        } else {
                                            state.transactions.forEach { trans ->
                                                TransactionListItem(
                                                    title = trans.title,
                                                    amount = if (trans.isIncoming) "+$${String.format("%.2f", trans.amount)}" else "-$${String.format("%.2f", trans.amount)}",
                                                    date = trans.date,
                                                    isCredit = trans.isIncoming
                                                )
                                                Spacer(modifier = Modifier.height(8.dp))
                                            }
                                        }
                                    }
                                }
                                "Revenue" -> {
                                    RevenueManagementModule(state = state)
                                }
                                "Emails" -> {
                                    EmailManagementModule()
                                }
                                "Export" -> {
                                    DataExportModule()
                                }
                                else -> {
                                    Surface(color = SurfaceContainerLowest, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.padding(32.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Icon(Icons.Outlined.CheckCircle, null, modifier = Modifier.size(48.dp), tint = Primary.copy(alpha=0.5f))
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Text("${segments[selectedAdminTab]} List", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text("No recent ${segments[selectedAdminTab].lowercase()} activity recorded in the system yet.", color = OnSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                        }
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(80.dp)) // FAB clearance
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserListItem(
    user: UserProfile,
    onDeepNavigate: (String) -> Unit = {},
    onSuspend: (String) -> Unit = {},
    onBan: (String) -> Unit = {},
    onChangeRole: (String, String) -> Unit = { _, _ -> }
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showChangeRoleDialog by remember { mutableStateOf(false) }
    var showSuspendConfirm by remember { mutableStateOf(false) }
    var showBanConfirm by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Change Role Dialog
    if (showChangeRoleDialog) {
        val roles = listOf(
            Triple("user", Icons.Outlined.Person, Color(0xFF3B82F6)),
            Triple("provider", Icons.Outlined.MedicalServices, Color(0xFF10B981)),
            Triple("admin", Icons.Outlined.AdminPanelSettings, Color(0xFF8B5CF6))
        )
        var selectedRole by remember { mutableStateOf(user.role?.lowercase() ?: "user") }

        AlertDialog(
            onDismissRequest = { showChangeRoleDialog = false },
            containerColor = Surface,
            shape = RoundedCornerShape(24.dp),
            title = null,
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Header avatar
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.ManageAccounts, null, tint = Primary, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Change Role", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = OnSurface)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Assign a new role for ${user.fullName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    roles.forEach { (role, icon, tint) ->
                        val isSelected = selectedRole == role
                        Surface(
                            onClick = { selectedRole = role },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) tint.copy(alpha = 0.12f) else SurfaceContainerLowest,
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, tint) else null,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Box(
                                    modifier = Modifier.size(36.dp).clip(CircleShape).background(tint.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(icon, null, tint = tint, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    role.replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (isSelected) tint else OnSurface,
                                    modifier = Modifier.weight(1f)
                                )
                                if (isSelected) {
                                    Icon(Icons.Outlined.CheckCircle, null, tint = tint, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showChangeRoleDialog = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Cancel") }
                    Button(
                        onClick = { onChangeRole(user.userId, selectedRole); showChangeRoleDialog = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) { Text("Apply") }
                }
            },
            dismissButton = null
        )
    }

    // Suspend Confirmation Dialog
    if (showSuspendConfirm) {
        val suspendColor = Color(0xFFE67E22)
        AlertDialog(
            onDismissRequest = { showSuspendConfirm = false },
            containerColor = Surface,
            shape = RoundedCornerShape(24.dp),
            title = null,
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(suspendColor.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.NotInterested, null, tint = suspendColor, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Suspend User", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = suspendColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(color = SurfaceContainerLowest, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            BmsAvatar(
                                name = user.fullName,
                                size = AvatarSize.MEDIUM
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(user.fullName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = OnSurface)
                                Text(user.email, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "This user will be temporarily blocked from accessing the platform. You can reinstate them at any time.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(onClick = { showSuspendConfirm = false }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) { Text("Cancel") }
                    Button(
                        onClick = { onSuspend(user.userId); showSuspendConfirm = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = suspendColor)
                    ) { Text("Suspend") }
                }
            },
            dismissButton = null
        )
    }

    // Ban Confirmation Dialog
    if (showBanConfirm) {
        AlertDialog(
            onDismissRequest = { showBanConfirm = false },
            containerColor = Surface,
            shape = RoundedCornerShape(24.dp),
            title = null,
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.errorContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Block, null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Ban User", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(color = SurfaceContainerLowest, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            BmsAvatar(
                                name = user.fullName,
                                size = AvatarSize.MEDIUM
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(user.fullName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = OnSurface)
                                Text(user.email, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Warning, null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("This is a permanent action. The user will be completely blocked from the platform.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(onClick = { showBanConfirm = false }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) { Text("Cancel") }
                    Button(
                        onClick = { onBan(user.userId); showBanConfirm = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) { Text("Ban User") }
                }
            },
            dismissButton = null
        )
    }

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDeepNavigate("user_detail/${user.userId}") }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with initials
            val initials = NameUtils.getInitials(user.fullName)

            BmsAvatar(
                name = user.fullName,
                size = AvatarSize.MEDIUM
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Name, role badge, email
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                    RoleBadge(role = user.role ?: "USER")
                    
                    if (user.status == "inactive") {
                        StatusBadge("Inactive", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.error)
                    }
                }
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }

            // ── Working 3-dot Dropdown Menu ───────────────
            Box {
                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Outlined.MoreVert,
                        "More options",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Change Role") },
                        leadingIcon = {
                            Icon(Icons.Outlined.ManageAccounts, null, modifier = Modifier.size(18.dp))
                        },
                        onClick = {
                            menuExpanded = false
                            showChangeRoleDialog = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Send Message") },
                        leadingIcon = {
                            Icon(Icons.Outlined.ChatBubbleOutline, null, modifier = Modifier.size(18.dp))
                        },
                        onClick = {
                            menuExpanded = false
                            onDeepNavigate("chat/${user.userId}")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Send Email") },
                        leadingIcon = {
                            Icon(Icons.Outlined.MailOutline, null, modifier = Modifier.size(18.dp))
                        },
                        onClick = {
                            menuExpanded = false
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:${user.email}")
                                putExtra(Intent.EXTRA_SUBJECT, "Message from BookEase Admin")
                            }
                            context.startActivity(Intent.createChooser(intent, "Send Email"))
                        }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Suspend User", color = Color(0xFFE67E22)) },
                        leadingIcon = {
                            Icon(Icons.Outlined.NotInterested, null, tint = Color(0xFFE67E22), modifier = Modifier.size(18.dp))
                        },
                        onClick = {
                            menuExpanded = false
                            showSuspendConfirm = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Ban User", color = MaterialTheme.colorScheme.error) },
                        leadingIcon = {
                            Icon(Icons.Outlined.Block, null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                        },
                        onClick = {
                            menuExpanded = false
                            showBanConfirm = true
                        }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("View Activity") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Timeline, null, modifier = Modifier.size(18.dp))
                        },
                        onClick = {
                            menuExpanded = false
                            onDeepNavigate("user_detail/${user.userId}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AppointmentCard(
    appointment: com.bms.app.domain.model.Appointment,
    users: List<UserProfile>
) {
    val patient = users.find { it.userId == appointment.userId }
    val provider = users.find { it.userId == appointment.providerId }

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${appointment.appointmentDate} | ${appointment.startTime} - ${appointment.endTime}",
                    style = MaterialTheme.typography.labelMedium,
                    color = OnSurfaceVariant
                )
                StatusBadge(
                    text = appointment.status.replaceFirstChar { it.uppercase() },
                    backgroundColor = if (appointment.status == "completed") MaterialTheme.colorScheme.primaryContainer else SurfaceContainerLow,
                    textColor = if (appointment.status == "completed") MaterialTheme.colorScheme.primary else OnSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Person, null, modifier = Modifier.size(16.dp), tint = Outline)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Patient: ${patient?.fullName ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurface
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.MedicalServices, null, modifier = Modifier.size(16.dp), tint = Outline)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Provider: ${provider?.fullName ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurface
                )
            }
        }
    }
}

@Composable
private fun TransactionListItem(title: String, amount: String, date: String, isCredit: Boolean) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(if (isCredit) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isCredit) Icons.Outlined.ArrowDownward else Icons.Outlined.ArrowUpward,
                        null,
                        tint = if (isCredit) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = OnSurface)
                    Text(date, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                }
            }
            Text(amount, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = if (isCredit) MaterialTheme.colorScheme.primary else OnSurface)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuickAddUserDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("user") }
    var roleDropdownExpanded by remember { mutableStateOf(false) }
    val roles = listOf("user", "provider", "admin")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Surface,
        shape = RoundedCornerShape(24.dp),
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.PersonAdd, null, tint = Primary, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Add New User", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    placeholder = { Text("e.g. John Doe") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    placeholder = { Text("john@example.com") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                ExposedDropdownMenuBox(
                    expanded = roleDropdownExpanded,
                    onExpandedChange = { roleDropdownExpanded = !roleDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedRole.replaceFirstChar { it.uppercase() },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Assign Role") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = roleDropdownExpanded) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = roleDropdownExpanded,
                        onDismissRequest = { roleDropdownExpanded = false }
                    ) {
                        roles.forEach { role ->
                            DropdownMenuItem(
                                text = { Text(role.replaceFirstChar { it.uppercase() }) },
                                onClick = {
                                    selectedRole = role
                                    roleDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { if (name.isNotBlank() && email.isNotBlank()) onConfirm(name, email, selectedRole) },
                enabled = name.isNotBlank() && email.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Create Account")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }
        }
    )
}
