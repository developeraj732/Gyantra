package com.example.gyantra

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.postDelayed
import androidx.navigation.compose.rememberNavController
import com.example.gyantra.presentation.navigation.NavGraph
import com.example.gyantra.ui.theme.GyantraTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GyantraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val showSplash = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                showSplash.value = false
            }, 3000
        )
    }

    if (showSplash.value) {
        SplashScreen()
    } else {
        NavGraph(navController = navController)
    }

}

@Composable
fun SplashScreen() {
    val isDarkMode = isSystemInDarkTheme()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Responsive dimensions based on screen size
    val containerPadding = when {
        screenWidth <= 360.dp -> 16.dp  // Very small screens - reduced padding
        screenWidth <= 480.dp -> 24.dp  // Small screens
        screenWidth <= 600.dp -> 32.dp  // Medium screens
        else -> 40.dp                   // Large screens
    }

    val cardCornerRadius = when {
        screenWidth <= 360.dp -> 16.dp
        screenWidth <= 480.dp -> 20.dp
        screenWidth <= 600.dp -> 24.dp
        else -> 28.dp
    }

    val cardPadding = when {
        screenWidth <= 360.dp -> 20.dp  // Reduced padding for small screens
        screenWidth <= 480.dp -> 32.dp
        screenWidth <= 600.dp -> 40.dp
        else -> 48.dp
    }

    val logoSize = when {
        screenWidth <= 360.dp -> 80.dp  // Smaller logo for very small screens
        screenWidth <= 480.dp -> 100.dp
        screenWidth <= 600.dp -> 120.dp
        else -> 140.dp
    }

    val logoImageSize = when {
        screenWidth <= 360.dp -> 160.dp  // Proportionally smaller
        screenWidth <= 480.dp -> 200.dp
        screenWidth <= 600.dp -> 240.dp
        else -> 280.dp
    }

    val logoCornerRadius = when {
        screenWidth <= 360.dp -> 12.dp
        screenWidth <= 480.dp -> 16.dp
        screenWidth <= 600.dp -> 20.dp
        else -> 24.dp
    }

    val spacerHeight = when {
        screenWidth <= 360.dp -> 16.dp  // Reduced spacing
        screenWidth <= 480.dp -> 20.dp
        screenWidth <= 600.dp -> 24.dp
        else -> 28.dp
    }

    // More aggressive text size reduction for small screens
    val titleFontSize = when {
        screenWidth <= 360.dp -> 16.sp  // Much smaller for very small screens
        screenWidth <= 400.dp -> 17.sp  // Added breakpoint for slightly larger small screens
        screenWidth <= 480.dp -> 18.sp
        screenWidth <= 600.dp -> 20.sp
        else -> 22.sp
    }

    val subtitleFontSize = when {
        screenWidth <= 360.dp -> 11.sp  // Smaller subtitle
        screenWidth <= 400.dp -> 11.5.sp
        screenWidth <= 480.dp -> 12.sp
        screenWidth <= 600.dp -> 13.sp
        else -> 14.sp
    }

    val textPadding = when {
        screenWidth <= 360.dp -> 8.dp   // Less horizontal padding
        screenWidth <= 480.dp -> 12.dp
        screenWidth <= 600.dp -> 14.dp
        else -> 16.dp
    }

    val subtitlePadding = when {
        screenWidth <= 360.dp -> 12.dp  // Less padding
        screenWidth <= 480.dp -> 16.dp
        screenWidth <= 600.dp -> 18.dp
        else -> 20.dp
    }

    // Responsive decorative elements
    val topDecorationSize = when {
        screenWidth <= 360.dp -> 32.dp  // Smaller decorations
        screenWidth <= 480.dp -> 40.dp
        screenWidth <= 600.dp -> 50.dp
        else -> 60.dp
    }

    val bottomDecorationSize = when {
        screenWidth <= 360.dp -> 24.dp
        screenWidth <= 480.dp -> 28.dp
        screenWidth <= 600.dp -> 32.dp
        else -> 40.dp
    }

    val topDecorationMargin = when {
        screenWidth <= 360.dp -> 16.dp
        screenWidth <= 480.dp -> 24.dp
        screenWidth <= 600.dp -> 32.dp
        else -> 40.dp
    }

    val bottomDecorationMargin = when {
        screenWidth <= 360.dp -> 24.dp
        screenWidth <= 480.dp -> 32.dp
        screenWidth <= 600.dp -> 40.dp
        else -> 60.dp
    }

    // Responsive positioning based on screen height
    val topDecorationTopMargin = when {
        screenHeight <= 600.dp -> 40.dp  // Reduced for small heights
        screenHeight <= 800.dp -> 60.dp
        else -> 80.dp
    }

    val bottomDecorationBottomMargin = when {
        screenHeight <= 600.dp -> 60.dp
        screenHeight <= 800.dp -> 80.dp
        else -> 100.dp
    }

    // Animation states
    var startAnimation by remember { mutableStateOf(false) }

    // Animated values
    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(800),
        label = "logoScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1000),
        label = "logoAlpha"
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1200, delayMillis = 300),
        label = "textAlpha"
    )

    // Start animation when screen loads
    LaunchedEffect(Unit) {
        delay(200)
        startAnimation = true
    }

    // Beautiful gradient background based on theme
    val backgroundGradient = if (isDarkMode) {
        Brush.radialGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surface.copy(alpha = 1f),
                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
            )
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                MaterialTheme.colorScheme.background
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {

        // Main content card for better visual hierarchy
        Surface(
            modifier = Modifier
                .padding(containerPadding)
                .clip(RoundedCornerShape(cardCornerRadius)),
            color = MaterialTheme.colorScheme.surface.copy(
                alpha = if (isDarkMode) 0.8f else 0.9f
            ),
            shadowElevation = 8.dp,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(cardPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Logo with elegant container
                Box(
                    modifier = Modifier
                        .size(logoSize)
                        .clip(RoundedCornerShape(logoCornerRadius))
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.gyantra),
                        contentDescription = "Gyantra Logo",
                        modifier = Modifier
                            .size(logoImageSize)
                            .scale(logoScale)
                            .alpha(logoAlpha),
                        colorFilter = if (isDarkMode) {
                            ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f))
                        } else null
                    )
                }

                Spacer(modifier = Modifier.height(spacerHeight))

                // Welcome text with beautiful typography - more compact for small screens
                Text(
                    text = if (screenWidth <= 360.dp) "Gyantra ❤️" else "Welcome to Gyantra ❤️",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = titleFontSize,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = if (screenWidth <= 360.dp) 0.2.sp else 0.5.sp
                    ),
                    modifier = Modifier
                        .alpha(textAlpha)
                        .padding(horizontal = textPadding),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(if (screenWidth <= 360.dp) 4.dp else 8.dp))

                // Subtitle for better hierarchy - shorter for small screens
                Text(
                    text = if (screenWidth <= 360.dp) "Unlock Wisdom" else "Discover Knowledge, Unlock Wisdom",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = subtitleFontSize,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    ),
                    modifier = Modifier
                        .alpha(textAlpha)
                        .padding(horizontal = subtitlePadding),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Subtle decorative elements - only show on larger screens to avoid clutter
        if (startAnimation && screenWidth > 400.dp) {  // Increased threshold
            // Top decoration
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = topDecorationTopMargin, end = topDecorationMargin)
                    .size(topDecorationSize)
                    .clip(RoundedCornerShape(topDecorationSize / 2))
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                    .alpha(0.6f)
            )

            // Bottom decoration
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = bottomDecorationBottomMargin, start = bottomDecorationMargin)
                    .size(bottomDecorationSize)
                    .clip(RoundedCornerShape(bottomDecorationSize / 2))
                    .background(
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                    )
                    .alpha(0.4f)
            )
        }
    }
}