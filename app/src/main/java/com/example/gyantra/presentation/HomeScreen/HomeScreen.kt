package com.example.gyantra.presentation.HomeScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gyantra.R
import com.example.gyantra.presentation.TabScreen.TabScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val urlHandler = LocalUriHandler.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Get screen configuration for responsive design
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val isCompact = screenWidth < 600.dp
    val isMedium = screenWidth >= 600.dp && screenWidth < 840.dp
    val isExpanded = screenWidth >= 840.dp

    // Responsive dimensions
    val drawerWidth = when {
        isCompact -> minOf(screenWidth * 0.85f, 300.dp)
        isMedium -> 320.dp
        else -> 360.dp
    }

    val appIconSize = when {
        isCompact -> if (screenWidth < 360.dp) 60.dp else 80.dp
        isMedium -> 90.dp
        else -> 100.dp
    }

    val topBarIconSize = when {
        isCompact -> if (screenWidth < 360.dp) 32.dp else 40.dp
        else -> 44.dp
    }

    val menuIconSize = when {
        isCompact -> if (screenWidth < 360.dp) 20.dp else 24.dp
        else -> 24.dp
    }

    val titleFontSize = when {
        isCompact -> if (screenWidth < 360.dp) 18.sp else 22.sp
        isMedium -> 24.sp
        else -> 26.sp
    }

    val subtitleFontSize = when {
        isCompact -> if (screenWidth < 360.dp) 12.sp else 14.sp
        else -> 14.sp
    }

    val horizontalPadding = when {
        isCompact -> if (screenWidth < 360.dp) 12.dp else 16.dp
        isMedium -> 20.dp
        else -> 24.dp
    }

    val verticalPadding = when {
        isCompact -> if (screenHeight < 700.dp) 12.dp else 20.dp
        else -> 20.dp
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(drawerWidth)
                    .shadow(8.dp),
                drawerShape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                drawerContentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontalPadding)
                ) {

                    // Enhanced app header section with gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = if (isCompact && screenHeight < 700.dp) 16.dp else 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(appIconSize)
                                    .shadow(8.dp, CircleShape)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary,
                                                MaterialTheme.colorScheme.tertiary
                                            )
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "📚",
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 24.sp else 36.sp
                                        isMedium -> 40.sp
                                        else -> 44.sp
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(if (isCompact) 8.dp else 12.dp))
                            Text(
                                text = "Gyantra",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 20.sp else 24.sp
                                        isMedium -> 28.sp
                                        else -> 32.sp
                                    }
                                ),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Digital Library",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 12.sp else 14.sp
                                        else -> 16.sp
                                    }
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Elegant divider
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Spacer(modifier = Modifier.height(if (isCompact) 16.dp else 20.dp))

                    // Enhanced navigation items
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Home",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 14.sp else 16.sp
                                        else -> 16.sp
                                    }
                                ),
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(if (isCompact && screenWidth < 360.dp) 18.dp else 22.dp)
                            )
                        },
                        selected = true,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(if (isCompact) 12.dp else 16.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = if (isCompact) 2.dp else 4.dp)
                            .fillMaxWidth()
                    )

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Version 1.0",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 14.sp else 16.sp
                                        else -> 16.sp
                                    }
                                ),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(if (isCompact && screenWidth < 360.dp) 18.dp else 22.dp)
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            Toast.makeText(context, "Version 1.0", Toast.LENGTH_SHORT).show()
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(if (isCompact) 12.dp else 16.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = if (isCompact) 2.dp else 4.dp)
                            .fillMaxWidth()
                    )

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Contact Me",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 14.sp else 16.sp
                                        else -> 16.sp
                                    }
                                ),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.linkedin),
                                contentDescription = null,
                                modifier = Modifier.size(if (isCompact && screenWidth < 360.dp) 18.dp else 22.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        onClick = {
                            urlHandler.openUri("https://www.linkedin.com/in/aman-jha-007273317/")
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(if (isCompact) 12.dp else 16.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = if (isCompact) 2.dp else 4.dp)
                            .fillMaxWidth()
                    )

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Source Code",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 14.sp else 16.sp
                                        else -> 16.sp
                                    }
                                ),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.github),
                                contentDescription = null,
                                modifier = Modifier.size(if (isCompact && screenWidth < 360.dp) 18.dp else 22.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        onClick = {
                            urlHandler.openUri("https://github.com/developeraj732/Gyantra")
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(if (isCompact) 12.dp else 16.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = if (isCompact) 2.dp else 4.dp)
                            .fillMaxWidth()
                    )

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Bug Report",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = when {
                                        isCompact -> if (screenWidth < 360.dp) 14.sp else 16.sp
                                        else -> 16.sp
                                    }
                                ),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.bug),
                                contentDescription = null,
                                modifier = Modifier.size(if (isCompact && screenWidth < 360.dp) 18.dp else 22.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            Toast.makeText(context, "Bug has been reported successfully !!", Toast.LENGTH_SHORT).show()
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(if (isCompact) 12.dp else 16.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = if (isCompact) 2.dp else 4.dp)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Beautiful footer section with subtle gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(if (isCompact) 12.dp else 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Made with ❤️",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = when {
                                    isCompact -> if (screenWidth < 360.dp) 12.sp else 14.sp
                                    else -> 14.sp
                                }
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Surface(
                    shadowElevation = 6.dp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                ) {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = if (isCompact) 4.dp else 8.dp)
                            ) {
                                // Enhanced app icon with gradient background
                                Box(
                                    modifier = Modifier
                                        .size(topBarIconSize)
                                        .shadow(4.dp, CircleShape)
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    MaterialTheme.colorScheme.primary,
                                                    MaterialTheme.colorScheme.secondary
                                                )
                                            ),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "📚",
                                        fontSize = when {
                                            isCompact -> if (screenWidth < 360.dp) 16.sp else 20.sp
                                            else -> 20.sp
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.width(if (isCompact && screenWidth < 360.dp) 8.dp else 16.dp))
                                Column {
                                    Text(
                                        "Gyantra",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = titleFontSize
                                    )
                                    if (!isCompact || screenWidth >= 360.dp) {
                                        Text(
                                            "Your Digital Library",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = subtitleFontSize
                                        )
                                    }
                                }
                            }
                        },

                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                },
                                modifier = Modifier
                                    .padding(if (isCompact && screenWidth < 360.dp) 4.dp else 8.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .size(topBarIconSize)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(menuIconSize)
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f)
                        ),
                        modifier = Modifier.padding(
                            horizontal = if (isCompact && screenWidth < 360.dp) 4.dp else 8.dp,
                            vertical = 4.dp
                        )
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerpadding ->

            // Enhanced main content area with subtle gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                            ),
                            startY = 0f,
                            endY = 1000f
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(innerpadding)
                        .fillMaxSize()
                ) {
                    // Beautiful spacer with subtle gradient line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (isCompact) 16.dp else 20.dp)
                            .padding(
                                horizontal = horizontalPadding,
                                vertical = if (isCompact) 6.dp else 8.dp
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                                            Color.Transparent
                                        )
                                    ),
                                    shape = RoundedCornerShape(1.dp)
                                )
                                .align(Alignment.Center)
                        )
                    }

                    // Enhanced TabScreen container
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = if (isCompact && screenWidth < 360.dp) 4.dp else 8.dp),
                        color = Color.Transparent,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    ) {
                        TabScreen(navController = navController)
                    }
                }
            }
        }
    }
}