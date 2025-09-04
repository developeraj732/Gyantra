package com.example.gyantra.presentation.AllBooksByCategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksByCategoryScreen(
    viewModel: ViewModel = hiltViewModel(),
    category: String,
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Responsive dimensions based on screen size
    val horizontalPadding = when {
        screenWidth <= 360.dp -> 12.dp  // Very small screens
        screenWidth <= 480.dp -> 16.dp  // Small screens
        screenWidth <= 600.dp -> 20.dp  // Medium screens
        screenWidth <= 840.dp -> 24.dp  // Large screens
        else -> 32.dp                   // Extra large screens & tablets
    }

    val verticalPadding = when {
        screenWidth <= 360.dp -> 12.dp
        screenWidth <= 480.dp -> 14.dp
        else -> 16.dp
    }

    val cardSpacing = when {
        screenWidth <= 360.dp -> 12.dp
        screenWidth <= 480.dp -> 15.dp
        else -> 18.dp
    }

    val cornerRadius = when {
        screenWidth <= 360.dp -> 12.dp
        screenWidth <= 480.dp -> 14.dp
        else -> 16.dp
    }

    val cardElevation = when {
        screenWidth <= 360.dp -> 2.dp
        screenWidth <= 480.dp -> 2.5.dp
        else -> 3.dp
    }

    val errorCardPadding = when {
        screenWidth <= 360.dp -> 16.dp
        screenWidth <= 480.dp -> 20.dp
        else -> 24.dp
    }

    val errorCardInnerPadding = when {
        screenWidth <= 360.dp -> 24.dp
        screenWidth <= 480.dp -> 28.dp
        else -> 32.dp
    }

    val emptyStateSize = when {
        screenWidth <= 360.dp -> 64.dp
        screenWidth <= 480.dp -> 72.dp
        else -> 80.dp
    }

    val emptyStatePadding = when {
        screenWidth <= 360.dp -> 28.dp
        screenWidth <= 480.dp -> 34.dp
        else -> 40.dp
    }

    val emojiSize = when {
        screenWidth <= 360.dp -> 36.sp
        screenWidth <= 480.dp -> 42.sp
        else -> 48.sp
    }

    val bookEmojiSize = when {
        screenWidth <= 360.dp -> 28.sp
        screenWidth <= 480.dp -> 32.sp
        else -> 36.sp
    }

    val shimmerItems = when {
        screenHeight <= 600.dp -> 6   // Small screens
        screenHeight <= 800.dp -> 7   // Medium screens
        else -> 8                     // Large screens
    }

    LaunchedEffect(Unit) {
        viewModel.getAllBooksByCategory(category)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = category,
                        style = when {
                            screenWidth <= 360.dp -> MaterialTheme.typography.titleLarge
                            screenWidth <= 480.dp -> MaterialTheme.typography.headlineSmall
                            else -> MaterialTheme.typography.headlineSmall
                        },
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = if (screenWidth <= 360.dp) 1 else 2
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                scrollBehavior = scrollBehavior
            )
        }

    ) { innerPadding ->

        val res = viewModel.state.value

        // Beautiful gradient background
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                            items(shimmerItems) {
                                Card(
                                    modifier = Modifier.clip(RoundedCornerShape(cornerRadius)),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = cardElevation
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
                                .padding(errorCardPadding)
                                .clip(RoundedCornerShape(cornerRadius + 4.dp)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(errorCardInnerPadding),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "⚠️",
                                    fontSize = emojiSize,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                                Text(
                                    text = "Something went wrong",
                                    style = when {
                                        screenWidth <= 360.dp -> MaterialTheme.typography.titleLarge
                                        screenWidth <= 480.dp -> MaterialTheme.typography.headlineSmall
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
                                        screenWidth <= 360.dp -> MaterialTheme.typography.bodySmall
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
                    Column(modifier = Modifier.fillMaxSize()) {
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
                                    modifier = Modifier.clip(RoundedCornerShape(cornerRadius)),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = cardElevation,
                                        hoveredElevation = cardElevation + 3.dp
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
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(errorCardPadding)
                                .clip(RoundedCornerShape(cornerRadius + 8.dp)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(emptyStatePadding),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(emptyStateSize)
                                        .background(
                                            MaterialTheme.colorScheme.primaryContainer,
                                            RoundedCornerShape(emptyStateSize / 2)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "📚",
                                        fontSize = bookEmojiSize
                                    )
                                }
                                Text(
                                    text = "No Books in $category",
                                    style = when {
                                        screenWidth <= 360.dp -> MaterialTheme.typography.titleLarge
                                        screenWidth <= 480.dp -> MaterialTheme.typography.headlineSmall
                                        else -> MaterialTheme.typography.headlineSmall
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
                                )
                                Text(
                                    text = "New books will be added soon!",
                                    style = when {
                                        screenWidth <= 360.dp -> MaterialTheme.typography.bodySmall
                                        screenWidth <= 480.dp -> MaterialTheme.typography.bodyMedium
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