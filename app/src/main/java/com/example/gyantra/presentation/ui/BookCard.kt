package com.example.gyantra.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.example.gyantra.presentation.Effects.imageani
import com.example.gyantra.presentation.navigation.Routes

@Composable
fun BookCard(
    imageUrl: String,
    title: String,
    author: String = null.toString(),
    description: String,
    bookUrl: String,
    navController: NavController
) {

    // Get screen configuration for responsiveness
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Responsive values based on screen size
    val cardHeight = when {
        screenHeight < 600.dp -> 140.dp  // Increased for better author text visibility
        screenHeight < 800.dp -> 160.dp
        screenWidth < 400.dp -> 150.dp
        screenWidth < 600.dp -> 170.dp
        else -> 180.dp
    }

    val imageSize = when {
        screenWidth < 400.dp -> 90.dp
        screenWidth < 600.dp -> 105.dp
        screenWidth < 900.dp -> 115.dp
        else -> 125.dp
    }

    val cardPadding = when {
        screenWidth < 400.dp -> 12.dp
        screenWidth < 600.dp -> 16.dp
        else -> 20.dp
    }

    val spacerWidth = when {
        screenWidth < 400.dp -> 12.dp
        screenWidth < 600.dp -> 16.dp
        else -> 20.dp
    }

    val cardRadius = when {
        screenWidth < 400.dp -> 12.dp
        screenWidth < 600.dp -> 16.dp
        else -> 18.dp
    }

    val imageRadius = when {
        screenWidth < 400.dp -> 8.dp
        screenWidth < 600.dp -> 12.dp
        else -> 14.dp
    }

    val titleFontSize = when {
        screenWidth < 400.dp -> 14.sp
        screenWidth < 600.dp -> 16.sp
        screenWidth < 900.dp -> 18.sp
        else -> 20.sp
    }

    val descriptionFontSize = when {
        screenWidth < 400.dp -> 12.sp
        screenWidth < 600.dp -> 13.sp
        else -> 14.sp
    }

    val authorFontSize = when {
        screenWidth < 400.dp -> 10.sp
        screenWidth < 600.dp -> 11.sp
        else -> 12.sp
    }

    // Calculate available width for content
    val availableContentWidth = screenWidth - (cardPadding * 2) - imageSize - spacerWidth - 8.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .shadow(
                elevation = when {
                    screenWidth < 400.dp -> 2.dp
                    screenWidth < 600.dp -> 3.dp
                    else -> 4.dp
                },
                shape = RoundedCornerShape(cardRadius),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            .clickable {
                navController.navigate(Routes.ShowPdfScreen(url = bookUrl))
            },
        shape = RoundedCornerShape(cardRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = when {
                screenWidth < 400.dp -> 1.dp
                screenWidth < 600.dp -> 2.dp
                else -> 3.dp
            },
            pressedElevation = when {
                screenWidth < 400.dp -> 4.dp
                screenWidth < 600.dp -> 6.dp
                else -> 8.dp
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .padding(cardPadding)
        ) {

            // Enhanced image container with gradient overlay
            Box(
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(imageRadius))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(imageRadius))
                        .clickable {
                            navController.navigate(Routes.ShowPdfScreen(url = bookUrl))
                        },
                    loading = {
                        Box(
                            modifier = Modifier
                                .size(imageSize)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            imageani()
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .size(imageSize)
                                .background(
                                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
                                    RoundedCornerShape(imageRadius)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "📖",
                                    fontSize = when {
                                        screenWidth < 400.dp -> 18.sp
                                        screenWidth < 600.dp -> 22.sp
                                        else -> 24.sp
                                    }
                                )
                                Text(
                                    text = "Image Error",
                                    fontSize = when {
                                        screenWidth < 400.dp -> 8.sp
                                        screenWidth < 600.dp -> 9.sp
                                        else -> 10.sp
                                    },
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.width(spacerWidth))

            // Enhanced content section with better spacing
            Column(
                modifier = Modifier
                    .weight(1f) // Use weight to fill available space
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Title with gradient text effect
                Text(
                    text = title,
                    style = when {
                        screenWidth < 400.dp -> MaterialTheme.typography.titleSmall
                        screenWidth < 600.dp -> MaterialTheme.typography.titleMedium
                        else -> MaterialTheme.typography.titleLarge
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = titleFontSize,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = when {
                        screenHeight < 600.dp -> 2
                        screenWidth < 400.dp -> 2
                        else -> 2
                    },
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = when {
                        screenWidth < 400.dp -> 4.dp
                        else -> 6.dp
                    })
                )

                // Description with better typography - flexible height
                Text(
                    text = description,
                    style = when {
                        screenWidth < 400.dp -> MaterialTheme.typography.bodySmall
                        else -> MaterialTheme.typography.bodyMedium
                    },
                    fontSize = descriptionFontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = when {
                        screenHeight < 600.dp -> 2
                        screenWidth < 400.dp -> 3
                        else -> 3
                    },
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = when {
                        screenWidth < 400.dp -> 16.sp
                        screenWidth < 600.dp -> 18.sp
                        else -> 20.sp
                    },
                    modifier = Modifier
                        .padding(bottom = when {
                            screenWidth < 400.dp -> 6.dp
                            else -> 8.dp
                        })
                        .weight(1f, fill = false) // Allow description to take available space
                )

                // Author with accent styling - Fixed width and wrapping
                if (author != "null" && author.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // Take full available width
                            .background(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                RoundedCornerShape(when {
                                    screenWidth < 400.dp -> 6.dp
                                    else -> 8.dp
                                })
                            )
                            .padding(
                                horizontal = when {
                                    screenWidth < 400.dp -> 8.dp
                                    else -> 10.dp
                                },
                                vertical = when {
                                    screenWidth < 400.dp -> 4.dp
                                    else -> 6.dp
                                }
                            )
                    ) {
                        Text(
                            text = "by $author",
                            style = when {
                                screenWidth < 400.dp -> MaterialTheme.typography.labelSmall
                                else -> MaterialTheme.typography.labelMedium
                            },
                            fontWeight = FontWeight.Medium,
                            fontSize = authorFontSize,
                            color = MaterialTheme.colorScheme.primary,
                            fontStyle = FontStyle.Italic,
                            maxLines = when {
                                screenWidth < 400.dp -> 2  // Allow wrapping on small screens
                                else -> 2
                            },
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = (authorFontSize.value + 2).sp // Better line spacing
                        )
                    }
                }
            }
        }
    }
}