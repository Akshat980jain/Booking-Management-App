package com.bms.app.ui.chat

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.ChatConversation
import com.bms.app.ui.components.BmsTextField
import com.bms.app.ui.components.SkeletonBox
import com.bms.app.ui.components.RoleBadge
import com.bms.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    onBack: () -> Unit,
    onOpenChat: (String) -> Unit,
    viewModel: InboxViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                BmsTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = "Search",
                    placeholder = "Search conversations...",
                    leadingIcon = Icons.Default.Search,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = uiState) {
                is InboxUiState.Loading -> {
                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        repeat(6) {
                            Box(modifier = Modifier.padding(vertical = 8.dp)) {
                                SkeletonBox(height = 80.dp, shape = RoundedCornerShape(16.dp))
                            }
                        }
                    }
                }
                is InboxUiState.Success -> {
                    val filtered = state.conversations.filter {
                        it.otherParticipantName.contains(searchQuery, ignoreCase = true) ||
                        it.lastMessage.contains(searchQuery, ignoreCase = true)
                    }

                    if (filtered.isEmpty()) {
                        EmptyInboxState()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                        ) {
                            items(filtered, key = { it.conversationId }) { conversation ->
                                ConversationItem(
                                    conversation = conversation,
                                    onClick = { onOpenChat(conversation.otherParticipantId) }
                                )
                            }
                        }
                    }
                }
                is InboxUiState.Error -> {
                    InboxErrorState(
                        message = state.message,
                        onRetry = { viewModel.loadConversations() }
                    )
                }
            }
        }
    }
}

@Composable
fun InboxErrorState(
    message: String,
    onRetry: () -> Unit
) {
    val isNetworkError = message.contains("resolve host", ignoreCase = true) || 
                         message.contains("network", ignoreCase = true) ||
                         message.contains("connection", ignoreCase = true)

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Error.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isNetworkError) Icons.Default.Search else Icons.Outlined.ChatBubbleOutline,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Error
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = if (isNetworkError) "Connection Lost" else "Something went wrong",
            style = MaterialTheme.typography.titleMedium,
            color = OnSurface,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = if (isNetworkError) "Please check your internet connection and try again." else message,
            style = MaterialTheme.typography.bodySmall,
            color = OnSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.height(48.dp).fillMaxWidth(0.6f)
        ) {
            Text("Retry", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ConversationItem(
    conversation: ChatConversation,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick),
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with initials
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(PrimaryContainer, PrimaryContainer.copy(alpha = 0.6f))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = conversation.otherParticipantName.take(2).uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = conversation.otherParticipantName,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = OnSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        conversation.otherParticipantRole?.let { role ->
                            RoleBadge(role = role)
                        }
                    }
                    
                    Text(
                        text = formatChatTime(conversation.lastMessageTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = conversation.lastMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (conversation.unreadCount > 0) OnSurface else OnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = if (conversation.unreadCount > 0) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun EmptyInboxState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(SurfaceContainerLowest, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.ChatBubbleOutline,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Primary.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "No conversations yet",
            style = MaterialTheme.typography.titleMedium,
            color = OnSurfaceVariant
        )
        Text(
            "Messages with patients and support will appear here.",
            style = MaterialTheme.typography.bodySmall,
            color = OnSurfaceVariant.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 48.dp)
        )
    }
}

private fun formatChatTime(dateStr: String): String {
    if (dateStr.isBlank()) return ""
    return try {
        // Handle ISO-8601 from Supabase
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse(dateStr) ?: return ""
        
        val now = Calendar.getInstance()
        val msgTime = Calendar.getInstance().apply { time = date }
        
        if (now.get(Calendar.DATE) == msgTime.get(Calendar.DATE)) {
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
        } else {
            SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
        }
    } catch (e: Exception) {
        ""
    }
}
