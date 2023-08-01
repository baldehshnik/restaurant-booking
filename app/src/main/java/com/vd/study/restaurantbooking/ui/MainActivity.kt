package com.vd.study.restaurantbooking.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vd.study.restaurantbooking.R
import com.vd.study.restaurantbooking.ui.model.BottomNavigationScreens
import com.vd.study.restaurantbooking.ui.model.Route
import com.vd.study.restaurantbooking.ui.model.appComponent
import com.vd.study.restaurantbooking.ui.screens.AboutScreen
import com.vd.study.restaurantbooking.ui.screens.BookingScreen
import com.vd.study.restaurantbooking.ui.screens.HubScreen
import com.vd.study.restaurantbooking.ui.screens.MapScreen
import com.vd.study.restaurantbooking.ui.screens.wigets.DropDownListItem
import com.vd.study.restaurantbooking.ui.theme.RestaurantBookingTheme
import com.vd.study.restaurantbooking.ui.viewmodel.daggerViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationContext.appComponent.inject(this)

        val bottomNavigationScreens =
            listOf(BottomNavigationScreens.BookingScreen, BottomNavigationScreens.HubScreen)
        setContent {
            RestaurantBookingTheme {
                val navController = rememberNavController()
                var isMainMenuVisible by remember { mutableStateOf(false) }

                var isBottomBarVisible by rememberSaveable { mutableStateOf(true) }
                var isBackArrowVisible by rememberSaveable { mutableStateOf(false) }

                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        BookingTopAppBar(
                            isMainMenuVisible = isMainMenuVisible,
                            onMenuClick = { isMainMenuVisible = true },
                            onMenuItemClick = {
                                isMainMenuVisible = false
                                navController.navigate(Route.INFORMATION.route) {
                                    launchSingleTop = true
                                }
                            },
                            onDismiss = { isMainMenuVisible = false },
                            onLocationClick = {
                                navController.navigate(Route.MAP.route) {
                                    launchSingleTop = true
                                }
                            },
                            behavior = scrollBehavior,
                            isBackArrowVisible = isBackArrowVisible,
                            onBackArrowClick = {
                                if (isBackArrowVisible) {
                                    isBackArrowVisible = false
                                    navController.popBackStack()
                                }
                            }
                        )
                    },
                    bottomBar = {
                        val backStackEntry by navController.currentBackStackEntryAsState()
                        AnimatedVisibility(visible = isBottomBarVisible) {
                            BookingBottomNavigationBar(
                                bottomNavigationScreens = bottomNavigationScreens,
                                backStackEntry = backStackEntry,
                                onItemClick = { screen ->
                                    navController.navigate(screen.route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavigationScreens.BookingScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavigationScreens.BookingScreen.route) {
                            isBottomBarVisible = true
                            isBackArrowVisible = false
                            val viewModel = daggerViewModel {
                                appComponent.createBookScreenSubcomponent().build().getViewModel()
                            }
                            BookingScreen(viewModel = viewModel)
                        }
                        composable(BottomNavigationScreens.HubScreen.route) {
                            isBottomBarVisible = true
                            isBackArrowVisible = false
                            val viewModel = daggerViewModel {
                                appComponent.createHubScreenSubcomponent().build().getViewModel()
                            }
                            HubScreen(viewModel = viewModel, snackbarHostState = snackbarHostState)
                        }
                        composable(Route.INFORMATION.route) {
                            isBottomBarVisible = false
                            isBackArrowVisible = true
                            AboutScreen()
                        }
                        composable(Route.MAP.route) {
                            isBottomBarVisible = false
                            isBackArrowVisible = true
                            MapScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingTopAppBar(
    isMainMenuVisible: Boolean,
    onLocationClick: () -> Unit,
    onMenuClick: () -> Unit,
    onMenuItemClick: () -> Unit,
    onDismiss: () -> Unit,
    behavior: TopAppBarScrollBehavior,
    isBackArrowVisible: Boolean,
    onBackArrowClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        scrollBehavior = behavior,
        navigationIcon = {
            AnimatedVisibility(visible = isBackArrowVisible) {
                IconButton(onClick = onBackArrowClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_arrow_back),
                        contentDescription = stringResource(id = R.string.close_current_screen_description)
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.booking),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        actions = {
            AnimatedVisibility(visible = !isBackArrowVisible) {
                IconButton(
                    onClick = onLocationClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_location_on),
                        contentDescription = stringResource(id = R.string.show_location_description)
                    )
                }
            }

            AnimatedVisibility(visible = !isBackArrowVisible) {
                IconButton(
                    onClick = onMenuClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_more_vert),
                        contentDescription = stringResource(id = R.string.show_main_menu_items_description)

                    )
                }
            }

            DropdownMenu(
                expanded = isMainMenuVisible,
                onDismissRequest = onDismiss
            ) {
                DropdownMenuItem(
                    text = {
                        DropDownListItem(
                            stringId = R.string.about,
                            drawableId = R.drawable.round_error
                        )
                    },
                    onClick = onMenuItemClick
                )
            }
        }
    )
}

@Composable
fun BookingBottomNavigationBar(
    bottomNavigationScreens: List<BottomNavigationScreens>,
    backStackEntry: NavBackStackEntry?,
    onItemClick: (screen: BottomNavigationScreens) -> Unit,
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        content = {
            bottomNavigationScreens.forEach { screen ->
                val selected = backStackEntry?.destination?.route == screen.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(screen) },
                    icon = {
                        Row {
                            Icon(
                                painter = painterResource(id = screen.drawableId),
                                contentDescription = stringResource(
                                    id = R.string.bottom_navigation_item_description,
                                    screen.route
                                )
                            )

                            AnimatedVisibility(visible = selected) {
                                Text(
                                    text = stringResource(id = screen.resourceId),
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                        }
                    }
                )
            }
        }
    )
}