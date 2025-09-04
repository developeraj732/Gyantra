package com.example.gyantra.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfViewerScreen(
    url: String,
    title: String = "PDF Viewer"
) {
    // System dark mode detection
    val isSystemDarkMode = isSystemInDarkTheme()
    var isDarkMode by remember { mutableStateOf(isSystemDarkMode) }

    // Screen configuration for responsive design
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val isTablet = screenWidth > 600.dp

    // Zoom and pan state
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    // PDF state with loading handling
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(url),
        isZoomEnable = true
    )

    // Page tracking state
    var currentPage by remember { mutableStateOf(1) }
    var totalPages by remember { mutableStateOf(0) }

    // Update total pages when PDF is loaded
    LaunchedEffect(pdfState.isLoaded, pdfState.pdfPageCount) {
        if (pdfState.isLoaded) {
            totalPages = pdfState.pdfPageCount ?: 0
        }
    }

    // Track current page based on scroll position
    LaunchedEffect(pdfState.currentPage) {
        currentPage = pdfState.currentPage
    }

    // Loading animation
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Color schemes
    val backgroundColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFF5F5F5)
    val surfaceColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val primaryColor = if (isDarkMode) Color(0xFF4FC3F7) else Color(0xFF2196F3)
    val onSurfaceColor = if (isDarkMode) Color(0xFFE0E0E0) else Color(0xFF424242)
    val pageCounterBgColor = if (isDarkMode) Color(0xFF2C2C2C) else Color(0xFFFFFFFF)
    val pageCounterTextColor = if (isDarkMode) Color(0xFFE0E0E0) else Color(0xFF424242)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar with dark mode toggle
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = onSurfaceColor,
                        fontSize = if (isTablet) 20.sp else 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                actions = {
                    // Dark mode toggle button
                    IconButton(
                        onClick = { isDarkMode = !isDarkMode },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                if (isDarkMode) Color(0xFF333333) else Color(0xFFF0F0F0)
                            )
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = primaryColor,
                            modifier = Modifier.size(if (isTablet) 26.dp else 24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = surfaceColor
                )
            )

            // PDF Viewer Container
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isDarkMode) Color(0xFF1A1A1A) else Color(0xFFF8F8F8)
                    )
            ) {
                // PDF Reader with zoom gestures
                VerticalPDFReader(
                    state = pdfState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (isDarkMode) Color(0xFF2A2A2A) else Color.White
                        )
                        .onSizeChanged { size = it }
                        .pointerInput(Unit) {
                            detectTransformGestures { centroid, pan, zoom, rotation ->
                                val newScale = (scale * zoom).coerceIn(0.5f, 5f)
                                scale = newScale

                                // Calculate bounds for panning based on scale
                                val maxX = (size.width * (scale - 1)) / 2
                                val maxY = (size.height * (scale - 1)) / 2

                                offsetX = (offsetX + pan.x).coerceIn(-maxX, maxX)
                                offsetY = (offsetY + pan.y).coerceIn(-maxY, maxY)
                            }
                        }
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offsetX,
                            translationY = offsetY
                        )
                )

                // Loading Overlay
                if (pdfState.isLoaded.not()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor.copy(alpha = 0.9f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .size(
                                    width = if (isTablet) 240.dp else 200.dp,
                                    height = if (isTablet) 180.dp else 150.dp
                                )
                                .graphicsLayer(alpha = pulseAlpha),
                            shape = RoundedCornerShape(if (isTablet) 24.dp else 20.dp),
                            colors = CardDefaults.cardColors(containerColor = surfaceColor),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = if (isTablet) 10.dp else 8.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                // Animated Loading Icon
                                Box(
                                    modifier = Modifier.size(if (isTablet) 72.dp else 60.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Gradient ring
                                    Box(
                                        modifier = Modifier
                                            .size(if (isTablet) 60.dp else 50.dp)
                                            .rotate(rotationAngle)
                                            .background(
                                                brush = Brush.sweepGradient(
                                                    colors = listOf(
                                                        primaryColor.copy(alpha = 0.1f),
                                                        primaryColor,
                                                        primaryColor.copy(alpha = 0.1f)
                                                    )
                                                ),
                                                shape = CircleShape
                                            )
                                    )

                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Loading",
                                        tint = primaryColor,
                                        modifier = Modifier
                                            .size(if (isTablet) 28.dp else 24.dp)
                                            .rotate(-rotationAngle * 0.5f)
                                    )
                                }

                                Spacer(modifier = Modifier.height(if (isTablet) 20.dp else 16.dp))

                                Text(
                                    text = "Loading PDF...",
                                    color = onSurfaceColor,
                                    fontSize = if (isTablet) 18.sp else 16.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(modifier = Modifier.height(if (isTablet) 12.dp else 8.dp))

                                // Progress bar
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .width(if (isTablet) 140.dp else 120.dp)
                                        .height(if (isTablet) 4.dp else 3.dp)
                                        .clip(RoundedCornerShape(if (isTablet) 3.dp else 2.dp)),
                                    color = primaryColor,
                                    trackColor = primaryColor.copy(alpha = 0.2f)
                                )
                            }
                        }
                    }
                }

                // Error state (if PDF fails to load)
                if (pdfState.error != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor.copy(alpha = 0.95f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(if (isTablet) 40.dp else 32.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(if (isTablet) 20.dp else 16.dp),
                            colors = CardDefaults.cardColors(containerColor = surfaceColor),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = if (isTablet) 10.dp else 8.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(if (isTablet) 32.dp else 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "⚠️",
                                    fontSize = if (isTablet) 56.sp else 48.sp
                                )

                                Spacer(modifier = Modifier.height(if (isTablet) 20.dp else 16.dp))

                                Text(
                                    text = "Failed to Load PDF",
                                    color = onSurfaceColor,
                                    fontSize = if (isTablet) 20.sp else 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(if (isTablet) 12.dp else 8.dp))

                                Text(
                                    text = "Please check your internet connection and try again.",
                                    color = onSurfaceColor.copy(alpha = 0.7f),
                                    fontSize = if (isTablet) 16.sp else 14.sp
                                )

                                Spacer(modifier = Modifier.height(if (isTablet) 20.dp else 16.dp))

                                Button(
                                    onClick = {
                                        // Reset zoom and pan on retry
                                        scale = 1f
                                        offsetX = 0f
                                        offsetY = 0f
                                        // Retry logic can be added here
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = primaryColor
                                    ),
                                    modifier = Modifier.height(if (isTablet) 52.dp else 48.dp)
                                ) {
                                    Text(
                                        "Retry",
                                        fontSize = if (isTablet) 16.sp else 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Page Counter - positioned at bottom right (outside Column scope)
        if (pdfState.isLoaded && totalPages > 0) {
            Card(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(
                        end = if (isTablet) 24.dp else 16.dp,
                        bottom = if (isTablet) 24.dp else 16.dp
                    ),
                shape = RoundedCornerShape(if (isTablet) 20.dp else 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = pageCounterBgColor.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isTablet) 6.dp else 4.dp
                )
            ) {
                Text(
                    text = "$currentPage / $totalPages",
                    color = pageCounterTextColor,
                    fontSize = if (isTablet) 16.sp else 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(
                        horizontal = if (isTablet) 16.dp else 12.dp,
                        vertical = if (isTablet) 10.dp else 8.dp
                    )
                )
            }
        }

        // Zoom level indicator (shows temporarily when zooming)
        if (scale != 1f) {
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = if (isTablet) 80.dp else 72.dp), // Adjusted for TopAppBar
                shape = RoundedCornerShape(if (isTablet) 16.dp else 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = pageCounterBgColor.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isTablet) 6.dp else 4.dp
                )
            ) {
                Text(
                    text = "${(scale * 100).toInt()}%",
                    color = pageCounterTextColor,
                    fontSize = if (isTablet) 14.sp else 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(
                        horizontal = if (isTablet) 12.dp else 10.dp,
                        vertical = if (isTablet) 8.dp else 6.dp
                    )
                )
            }
        }
    }
}