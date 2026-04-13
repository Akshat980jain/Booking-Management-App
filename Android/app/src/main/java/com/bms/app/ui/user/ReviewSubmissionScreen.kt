package com.bms.app.ui.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.Review
import com.bms.app.ui.components.BmsButton
import com.bms.app.ui.components.BmsTopBar
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewSubmissionScreen(
    appointmentId: String,
    providerId: String,
    providerName: String,
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    var rating by remember { mutableStateOf(5) }
    var reviewText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is ReviewUiState.Success) {
            onSuccess()
        }
    }

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "Rate your experience",
                showBackButton = true,
                onNavigationClick = onBack
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "How was your session with",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceVariant
            )
            Text(
                providerName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Rating Stars
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 1..5) {
                    IconButton(onClick = { rating = i }) {
                        Icon(
                            imageVector = if (i <= rating) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                            contentDescription = "$i Stars",
                            tint = Gold,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when(rating) {
                    1 -> "Poor"
                    2 -> "Fair"
                    3 -> "Good"
                    4 -> "Very Good"
                    5 -> "Excellent"
                    else -> ""
                },
                style = MaterialTheme.typography.labelLarge,
                color = Gold,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))
            
            OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                label = { Text("Write a review (optional)") },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = OutlineVariant
                )
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            if (uiState is ReviewUiState.Error) {
                Text(
                    (uiState as ReviewUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            BmsButton(
                text = "Submit Review",
                onClick = {
                    viewModel.submitReview(
                        providerId = providerId,
                        appointmentId = appointmentId,
                        rating = rating,
                        text = reviewText
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState is ReviewUiState.Loading
            )
        }
    }
}
