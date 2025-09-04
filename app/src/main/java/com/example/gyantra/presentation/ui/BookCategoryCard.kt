package com.example.gyantra.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.example.gyantra.presentation.Effects.imageani
import com.example.gyantra.presentation.navigation.Routes

@Composable
fun BookCategoryCard(
    imageUrl: String,
    category: String,
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Responsive dimensions based on screen size
    val cardSize = when {
        screenWidth <= 360.dp -> 140.dp  // Very small screens
        screenWidth <= 480.dp -> 160.dp  // Small screens
        screenWidth <= 600.dp -> 180.dp  // Medium screens
        screenWidth <= 840.dp -> 200.dp  // Large screens
        else -> 220.dp                   // Extra large screens
    }

    val cardPadding = when {
        screenWidth <= 360.dp -> 4.dp
        screenWidth <= 480.dp -> 5.dp
        else -> 6.dp
    }

    val innerPadding = when {
        screenWidth <= 360.dp -> 8.dp
        screenWidth <= 480.dp -> 10.dp
        else -> 12.dp
    }

    val cornerRadius = when {
        screenWidth <= 360.dp -> 16.dp
        screenWidth <= 480.dp -> 18.dp
        else -> 20.dp
    }

    val imageCornerRadius = when {
        screenWidth <= 360.dp -> 12.dp
        screenWidth <= 480.dp -> 14.dp
        else -> 16.dp
    }

    val fontSize = when {
        screenWidth <= 360.dp -> 12.sp
        screenWidth <= 480.dp -> 13.sp
        screenWidth <= 600.dp -> 14.sp
        else -> 15.sp
    }

    val iconSize = when {
        screenWidth <= 360.dp -> 24.dp
        screenWidth <= 480.dp -> 28.dp
        else -> 32.dp
    }

    val textPadding = when {
        screenWidth <= 360.dp -> 8.dp
        screenWidth <= 480.dp -> 10.dp
        else -> 12.dp
    }

    val verticalTextPadding = when {
        screenWidth <= 360.dp -> 10.dp
        screenWidth <= 480.dp -> 12.dp
        else -> 14.dp
    }

    Card(
        modifier = Modifier
            .padding(cardPadding)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            .clickable {
                navController.navigate(Routes.BooksByCategory(category))
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 12.dp
        )
    ) {

        Column(
            modifier = Modifier
                .size(cardSize)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        ),
                        startY = 0f,
                        endY = 800f
                    )
                )
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Enhanced image container with overlay gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(imageCornerRadius))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = category,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(imageCornerRadius)),

                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            imageani()
                        }
                    },
                    error = {
                        // Beautiful error state for image
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f),
                                            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BrokenImage,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f),
                                    modifier = Modifier.size(iconSize)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Image Error",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                                    fontSize = (fontSize.value - 2).sp
                                )
                            }
                        }
                    }
                )

                // Subtle overlay gradient for better text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
                                ),
                                startY = 0f,
                                endY = 300f
                            ),
                            shape = RoundedCornerShape(imageCornerRadius)
                        )
                )
            }

            // Premium category name with glass morphism effect
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(imageCornerRadius - 2.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f)
                            )
                        )
                    )
            ) {
                // Glass morphism overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(imageCornerRadius - 2.dp)
                        )
                ) {
                    Text(
                        text = category,
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize,
                        maxLines = if (screenWidth <= 360.dp) 1 else 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        lineHeight = (fontSize.value + 5).sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = textPadding, vertical = verticalTextPadding)
                    )
                }
            }
        }
    }
}