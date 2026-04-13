package com.bms.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmsTopBar(
    title: String = "BookEase24X7",
    showBackButton: Boolean = false,
    showAvatar: Boolean = true,
    isLoading: Boolean = false,
    onNavigationClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onMessagesClick: () -> Unit = {},
    avatarInitials: String = "AJ",
    unreadCount: Int = 0,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        fontFamily = ManropeFamily
                    ),
                    color = OnSurface,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                // Subtle underline indicator as seen in sketch
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(3.dp)
                        .background(Primary, RoundedCornerShape(2.dp))
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = OnSurface
                    )
                }
            }
        },
        actions = {
            if (showAvatar) {
                IconButton(onClick = onMessagesClick) {
                    Box {
                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Messages",
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                        if (unreadCount > 0) {
                            Surface(
                                color = MaterialTheme.colorScheme.error,
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = 2.dp, y = (-2).dp),
                                border = androidx.compose.foundation.BorderStroke(1.5.dp, Background)
                            ) {}
                        }
                    }
                }
                
                Spacer(modifier = Modifier.width(8.dp))

                if (isLoading) {
                    // Shimmer skeleton circle during loading
                    SkeletonCircle(size = 36.dp)
                } else {
                    BmsAvatar(
                        name = avatarInitials,
                        size = AvatarSize.SMALL,
                        modifier = Modifier.clickable { onAvatarClick() }
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Background.copy(alpha = 0.9f),
            titleContentColor = OnSurface
        ),
        modifier = modifier
    )
}
