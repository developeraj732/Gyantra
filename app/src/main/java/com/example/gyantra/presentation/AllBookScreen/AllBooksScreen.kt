package com.example.gyantra.presentation.AllBookScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gyantra.presentation.Effects.AnimatedShimmer
import com.example.gyantra.presentation.ViewModel
import com.example.gyantra.presentation.ui.BookCard

@Composable
fun AllBooksScreen(
    viewModel: ViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        viewModel.getAllBooks()
    }

    val res = viewModel.state.value

    // Get screen configuration for responsiveness
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Responsive values based on screen size
    val horizontalPadding = when {
        screenWidth < 400.dp -> 12.dp
        screenWidth < 600.dp -> 20.dp
        screenWidth < 900.dp -> 24.dp
        else -> 32.dp
    }

    val verticalPadding = when {
        screenHeight < 600.dp -> 12.dp
        screenHeight < 800.dp -> 16.dp
        else -> 20.dp
    }

    val cardSpacing = when {
        screenWidth < 400.dp -> 12.dp
        screenWidth < 600.dp -> 16.dp
        screenWidth < 900.dp -> 18.dp
        else -> 20.dp
    }

    val cardPadding = when {
        screenWidth < 400.dp -> 16.dp
        screenWidth < 600.dp -> 20.dp
        screenWidth < 900.dp -> 24.dp
        else -> 32.dp
    }

    val cardRadius = when {
        screenWidth < 400.dp -> 12.dp
        screenWidth < 600.dp -> 16.dp
        else -> 20.dp
    }

    val errorIconSize = when {
        screenWidth < 400.dp -> 60.dp
        screenWidth < 600.dp -> 80.dp
        else -> 100.dp
    }

    // Beautiful gradient background
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    )
                )
            ),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 1000.dp), // Maximum width for very large screens
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                res.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = horizontalPadding,
                                vertical = verticalPadding
                            ),
                            verticalArrangement = Arrangement.spacedBy(cardSpacing)
                        ) {
                            items(6) {
                                Card(
                                    modifier = Modifier.clip(RoundedCornerShape(cardRadius)),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = when {
                                            screenWidth < 400.dp -> 1.dp
                                            screenWidth < 600.dp -> 2.dp
                                            else -> 3.dp
                                        }
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    AnimatedShimmer()
                                }
                            }
                        }
                    }
                }

                res.error.isNotEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(cardPadding)
                                .clip(RoundedCornerShape(cardRadius)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = when {
                                    screenWidth < 400.dp -> 2.dp
                                    screenWidth < 600.dp -> 4.dp
                                    else -> 6.dp
                                }
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(when {
                                    screenWidth < 400.dp -> 20.dp
                                    screenWidth < 600.dp -> 28.dp
                                    else -> 32.dp
                                }),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "⚠️",
                                    fontSize = when {
                                        screenWidth < 400.dp -> 36.sp
                                        screenWidth < 600.dp -> 42.sp
                                        else -> 48.sp
                                    },
                                    modifier = Modifier.padding(bottom = when {
                                        screenWidth < 400.dp -> 12.dp
                                        else -> 16.dp
                                    })
                                )
                                Text(
                                    text = "Something went wrong",
                                    style = when {
                                        screenWidth < 400.dp -> MaterialTheme.typography.titleMedium
                                        screenWidth < 600.dp -> MaterialTheme.typography.titleLarge
                                        else -> MaterialTheme.typography.headlineSmall
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = res.error,
                                    style = when {
                                        screenWidth < 400.dp -> MaterialTheme.typography.bodySmall
                                        else -> MaterialTheme.typography.bodyMedium
                                    },
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }

                res.items.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            horizontal = horizontalPadding,
                            vertical = verticalPadding
                        ),
                        verticalArrangement = Arrangement.spacedBy(cardSpacing)
                    ) {
                        items(res.items) { book ->
                            Card(
                                modifier = Modifier.clip(RoundedCornerShape(cardRadius)),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = when {
                                        screenWidth < 400.dp -> 2.dp
                                        screenWidth < 600.dp -> 3.dp
                                        else -> 4.dp
                                    },
                                    hoveredElevation = when {
                                        screenWidth < 400.dp -> 4.dp
                                        screenWidth < 600.dp -> 6.dp
                                        else -> 8.dp
                                    }
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                BookCard(
                                    imageUrl = book.bookImage,
                                    title = book.bookName,
                                    description = book.bookDescription,
                                    navController = navController,
                                    author = book.bookAuthor,
                                    bookUrl = book.bookUrl
                                )
                            }
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(cardPadding)
                                .clip(RoundedCornerShape(cardRadius)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = when {
                                    screenWidth < 400.dp -> 3.dp
                                    screenWidth < 600.dp -> 4.dp
                                    else -> 6.dp
                                }
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(when {
                                    screenWidth < 400.dp -> 24.dp
                                    screenWidth < 600.dp -> 32.dp
                                    else -> 40.dp
                                }),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(errorIconSize)
                                        .background(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            RoundedCornerShape(when {
                                                screenWidth < 400.dp -> 30.dp
                                                screenWidth < 600.dp -> 35.dp
                                                else -> 40.dp
                                            })
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "📚",
                                        fontSize = when {
                                            screenWidth < 400.dp -> 28.sp
                                            screenWidth < 600.dp -> 32.sp
                                            else -> 36.sp
                                        }
                                    )
                                }
                                Text(
                                    text = "No Books Available",
                                    style = when {
                                        screenWidth < 400.dp -> MaterialTheme.typography.titleMedium
                                        screenWidth < 600.dp -> MaterialTheme.typography.titleLarge
                                        else -> MaterialTheme.typography.headlineSmall
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(
                                        top = when {
                                            screenWidth < 400.dp -> 16.dp
                                            else -> 20.dp
                                        },
                                        bottom = 8.dp
                                    )
                                )
                                Text(
                                    text = "New books will be added soon!",
                                    style = when {
                                        screenWidth < 400.dp -> MaterialTheme.typography.bodyMedium
                                        else -> MaterialTheme.typography.bodyLarge
                                    },
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}