package com.bms.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bms.app.ui.theme.*

@Composable
fun BmsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector = Icons.Outlined.Person,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityToggle: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = OnSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = Outline.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (isFocused) Primary else Outline,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = if (isPassword && onPasswordVisibilityToggle != null) {
                {
                    IconButton(onClick = onPasswordVisibilityToggle) {
                        // Normally you'd use Visibility / VisibilityOff from Icons.Filled, 
                        // but let's use a standard char or icon if available.
                        // I will use an outlined icon if you have material-icons-extended 
                        // If not, a simple Text or fallback icon.
                        val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        Icon(icon, contentDescription = "Toggle password visibility", tint = Outline)
                    }
                }
            } else null,
            singleLine = singleLine,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            shape = InputShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceContainerLowest,
                focusedContainerColor = SurfaceContainerLowest,
                unfocusedBorderColor = GhostBorder,
                focusedBorderColor = Primary,
                cursorColor = Primary,
                focusedTextColor = OnSurface,
                unfocusedTextColor = OnSurface
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
        )
    }
}

@Composable
fun BmsTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    minLines: Int = 4
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = OnSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = Outline.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            minLines = minLines,
            shape = InputShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceContainerLowest,
                focusedContainerColor = SurfaceContainerLowest,
                unfocusedBorderColor = GhostBorder,
                focusedBorderColor = Primary,
                cursorColor = Primary
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
