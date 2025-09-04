package com.example.gyantra.presentation.CategoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gyantra.presentation.Effects.categoryShimmer
import com.example.gyantra.presentation.ViewModel
import com.example.gyantra.presentation.ui.BookCategoryCard

@Composable
fun CategoryScreen(
    viewModel: ViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) {
        viewModel.getAllCategory()
    }

    // Get screen configuration for responsiveness
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Responsive values based on screen size
    val horizontalPadding = when {
        screenWidth < 400.dp -> 12.dp
        screenWidth < 600.dp -> 16.dp
        screenWidth < 900.dp -> 24.dp
        else -> 32.dp
    }

    val verticalPadding = when {
        screenHeight < 600.dp -> 8.dp
        screenHeight < 800.dp -> 12.dp
        else -> 16.dp
    }

    val gridColumns = when {
        screenWidth < 400.dp -> 2
        screenWidth < 600.dp -> 2
        screenWidth < 900.dp -> 3
        screenWidth < 1200.dp -> 4
        else -> 5
    }

    val cardSpacing = when {
        screenWidth < 400.dp -> 8.dp
        screenWidth < 600.dp -> 12.dp
        else -> 16.dp
    }

    val iconSize = when {
        screenWidth < 400.dp -> 20.dp
        screenWidth < 600.dp -> 24.dp
        else -> 28.dp
    }

    val headerIconSize = when {
        screenWidth < 400.dp -> 28.dp
        screenWidth < 600.dp -> 32.dp
        else -> 36.dp
    }

    // Main container with elegant background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.05f)
                    ),
                    startY = 0f,
                    endY = 1200f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalPadding)
                .widthIn(max = 1200.dp) // Maximum width for very large screens
        ) {

            val res = viewModel.state.value

            when {

                res.isLoading -> {
                    // Enhanced loading state with better spacing
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Beautiful header for loading state
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = verticalPadding),
                            color = Color.Transparent
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LibraryBooks,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(iconSize)
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                Text(
                                    text = "Loading Categories...",
                                    style = when {
                                        screenWidth < 400.dp -> MaterialTheme.typography.titleSmall
                                        screenWidth < 600.dp -> MaterialTheme.typography.titleMedium
                                        else -> MaterialTheme.typography.titleLarge
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(gridColumns),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(cardSpacing),
                            verticalArrangement = Arrangement.spacedBy(cardSpacing)
                        ) {
                            items(10) {
                                categoryShimmer()
                            }
                        }
                    }
                }

                res.error.isNotEmpty() -> {
                    // Beautiful error state
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(when {
                                    screenWidth < 400.dp -> 16.dp
                                    screenWidth < 600.dp -> 24.dp
                                    else -> 32.dp
                                }),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(when {
                                screenWidth < 400.dp -> 12.dp
                                else -> 16.dp
                            }),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(when {
                                        screenWidth < 400.dp -> 16.dp
                                        screenWidth < 600.dp -> 20.dp
                                        else -> 24.dp
                                    }),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(when {
                                        screenWidth < 400.dp -> 40.dp
                                        else -> 48.dp
                                    })
                                )
                                Spacer(modifier = Modifier.height(when {
                                    screenWidth < 400.dp -> 12.dp
                                    else -> 16.dp
                                }))
                                Text(
                                    text = "Oops! Something went wrong",
                                    style = when {
                                        screenWidth < 400.dp -> MaterialTheme.typography.titleSmall
                                        else -> MaterialTheme.typography.titleMedium
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = res.error,
                                    style = when {
                                        screenWidth < 400.dp -> MaterialTheme.typography.bodySmall
                                        else -> MaterialTheme.typography.bodyMedium
                                    },
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                res.category.isNotEmpty() -> {
                    // Enhanced success state with beautiful header
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Elegant header section
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = verticalPadding),
                            color = Color.Transparent
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(headerIconSize)
                                        .background(
                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LibraryBooks,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(iconSize)
                                    )
                                }
                                Spacer(modifier = Modifier.size(12.dp))
                                Column {
                                    Text(
                                        text = "Book Categories",
                                        style = when {
                                            screenWidth < 400.dp -> MaterialTheme.typography.titleMedium
                                            screenWidth < 600.dp -> MaterialTheme.typography.titleLarge
                                            else -> MaterialTheme.typography.headlineSmall
                                        },
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = "${res.category.size} categories available",
                                        style = when {
                                            screenWidth < 400.dp -> MaterialTheme.typography.bodySmall
                                            else -> MaterialTheme.typography.bodyMedium
                                        },
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Enhanced grid with better spacing
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(gridColumns),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                start = when {
                                    screenWidth < 400.dp -> 4.dp
                                    else -> 8.dp
                                },
                                end = when {
                                    screenWidth < 400.dp -> 4.dp
                                    else -> 8.dp
                                },
                                bottom = 16.dp,
                                top = 8.dp
                            ),
                            horizontalArrangement = Arrangement.spacedBy(cardSpacing),
                            verticalArrangement = Arrangement.spacedBy(when {
                                screenWidth < 400.dp -> 12.dp
                                else -> 16.dp
                            })
                        ) {
                            items(res.category) { categoryItem ->
                                // Enhanced card container
                                BookCategoryCard(
                                    imageUrl = categoryItem.categoryImageUrl,
                                    category = categoryItem.categoryName,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}