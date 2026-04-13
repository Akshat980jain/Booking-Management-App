package com.bms.app.ui.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.Review
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderDetailScreen(
    providerUserId: String,
    onBack: () -> Unit,
    onBook: (String) -> Unit,
    viewModel: ProviderDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(providerUserId) {
        viewModel.loadProviderDetail(providerUserId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = SurfaceContainerLowest.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, null, tint = OnSurface)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Toggle Favorite */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = SurfaceContainerLowest.copy(alpha = 0.8f)
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Outlined.FavoriteBorder, null, tint = OnSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            if (uiState is ProviderDetailUiState.Success) {
                val state = uiState as ProviderDetailUiState.Success
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 8.dp,
                    shadowElevation = 16.dp,
                    color = SurfaceContainerLowest
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .navigationBarsPadding(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Consultation Fee", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                            Text(
                                "₹${state.profile.consultationFee.toInt()}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                        BmsButton(
                            text = "Book Session",
                            onClick = { onBook(providerUserId) },
                            modifier = Modifier.width(180.dp)
                        )
                    }
                }
            }
        },
        containerColor = Background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is ProviderDetailUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Primary)
                    }
                }
                is ProviderDetailUiState.Error -> {
                    ErrorMessage(message = state.message, onRetry = { viewModel.loadProviderDetail(providerUserId) })
                }
                is ProviderDetailUiState.Success -> {
                    ProviderDetailContent(
                        padding = padding,
                        provider = state.provider,
                        profile = state.profile,
                        reviews = state.reviews,
                        reviewerProfiles = state.reviewerProfiles,
                        patientCount = state.patientCount
                    )
                }
            }
        }
    }
}

@Composable
private fun ProviderDetailContent(
    padding: PaddingValues,
    provider: UserProfile,
    profile: ProviderProfile,
    reviews: List<Review>,
    reviewerProfiles: Map<String, UserProfile>,
    patientCount: Int
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // ── Header / Hero Section ─────────────────────────────────────────────
        Box(modifier = Modifier.height(280.dp)) {
            // Gradient Background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(PrimaryContainer, Background)
                        )
                    )
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    border = BorderStroke(4.dp, Color.White),
                    shadowElevation = 8.dp
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = NameUtils.getInitials(provider.fullName),
                            style = MaterialTheme.typography.headlineLarge,
                            color = OnPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Dr. ${provider.fullName}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = profile.profession,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Rating Badge
                Surface(
                    color = Gold.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Gold.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Star, null, tint = Gold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (reviews.isNotEmpty()) {
                                val avgRating = if (reviews.isNotEmpty()) reviews.map { it.rating }.average() else 0.0
                                "${String.format("%.1f", avgRating)} (${reviews.size} Reviews)"
                            } else "New Provider",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Gold
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            // ── Stats Row ─────────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(label = "Experience", value = "${profile.yearsOfExperience} yrs", icon = Icons.Outlined.History)
                StatItem(label = "Patients", value = if (patientCount > 1000) "${String.format("%.1f", patientCount / 1000.0)}k+" else patientCount.toString(), icon = Icons.Outlined.People)
                StatItem(label = "Reviews", value = reviews.size.toString(), icon = Icons.Outlined.ChatBubbleOutline)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Bio ───────────────────────────────────────────────────────────
            Text(
                "About",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = profile.bio ?: "No bio provided yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariant,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ── Specialties ───────────────────────────────────────────────────
            if (profile.specialty != null) {
                Text(
                    "Specialties",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    profile.specialty.split(",").forEach { spec ->
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = spec.trim(),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = OnSurface
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // ── Reviews Section ───────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Patient Reviews",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                if (reviews.isNotEmpty()) {
                    Text(
                        "See All",
                        style = MaterialTheme.typography.labelMedium,
                        color = Primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (reviews.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No reviews yet. Be the first to share your experience!", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant, textAlign = TextAlign.Center)
                }
            } else {
                reviews.take(3).forEach { review ->
                    ReviewItem(review, reviewerProfiles[review.userId])
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // Padding for bottom bar
        }
    }
}

@Composable
private fun StatItem(label: String, value: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(12.dp),
            color = SurfaceContainerLow
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = Primary, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = OnSurface)
        Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
    }
}

@Composable
private fun ReviewItem(review: Review, userProfile: UserProfile?) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Reviewer Avatar
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(SecondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = NameUtils.getInitials(userProfile?.fullName ?: "U"),
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSecondaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        userProfile?.fullName ?: "Anonymous Patient",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { index ->
                            Icon(
                                if (index < review.rating) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                                null,
                                tint = Gold,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            review.createdAt.take(10), // Simple date format
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
            
            if (!review.reviewText.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = review.reviewText,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurface,
                    lineHeight = 18.sp
                )
            }
            
            if (!review.providerResponse.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Primary.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Response from Provider", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.ExtraBold, color = Primary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(review.providerResponse, style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}
