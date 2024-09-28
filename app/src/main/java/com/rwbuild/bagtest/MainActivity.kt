package com.rwbuild.bagtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rwbuild.bagtest.ui.theme.BagTestTheme


// Data model for user
data class User(
    val id: Int,
    val name: String,
    val position: String,
    val photoResId: Int // Resource ID for user photo
)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BagTestTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("My App") },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            actions = {
                                IconButton(onClick = { /* Search action */ }) {
                                    Icon(
                                        Icons.Filled.Search,
                                        contentDescription = "Search",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = navController.currentDestination?.route == "home",
                                onClick = { navController.navigate("home") },
                                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                                label = { Text("Home") }
                            )
                            NavigationBarItem(
                                selected = navController.currentDestination?.route == "settings",
                                onClick = { navController.navigate("settings") },
                                icon = {
                                    Icon(
                                        Icons.Filled.Settings,
                                        contentDescription = "Settings"
                                    )
                                },
                                label = { Text("Settings") }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Home Screen with user list
                        composable("home") {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                UserListScreen(onUserClick = { userId ->
                                    // Navigate to the user details screen when a user is clicked
                                    navController.navigate("userDetails/$userId")
                                })
                            }
                        }

                        // Settings Screen
                        composable("settings") {
                            Text(text = "Settings Screen", modifier = Modifier.fillMaxSize())
                        }

                        // User Details Screen (navigate with user ID)
                        composable(
                            route = "userDetails/{userId}",
                            arguments = listOf(navArgument("userId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
                            UserDetailsScreen(navController, userId)
                        }
                    }
                }
            }
        }
    }
}

// Sample user data
fun getSampleUsers(): List<User> {
    return listOf(
        User(1, "Vianney R.", "Software Engineer", R.drawable.profile_1),
        User(2, "Jane Smith", "Product Manager", R.drawable.profile_3),
        User(3, "Alice Johnson", "Designer", R.drawable.profile_4),
        User(4, "Bob Williams", "Data Scientist", R.drawable.profile_6),
        User(5, "Vianney R.", "Software Engineer", R.drawable.profile_1),
        User(6, "Jane Smith", "Product Manager", R.drawable.profile_3),
        User(7, "Alice Johnson", "Designer", R.drawable.profile_4),
        User(8, "Bob Williams", "Data Scientist", R.drawable.profile_6),
        User(9, "Vianney R.", "Software Engineer", R.drawable.profile_1),
        User(10, "Jane Smith", "Product Manager", R.drawable.profile_3),
        User(11, "Alice Johnson", "Designer", R.drawable.profile_4),
        User(12, "Bob Williams", "Data Scientist", R.drawable.profile_6),
        User(13, "Vianney R.", "Software Engineer", R.drawable.profile_1),
        User(14, "Jane Smith", "Product Manager", R.drawable.profile_3),
        User(15, "Alice Johnson", "Designer", R.drawable.profile_4),
        User(16, "Bob Williams", "Data Scientist", R.drawable.profile_6)
    )
}
// Composable function to display list of users
@Composable
fun UserListScreen(onUserClick: (Int) -> Unit) {
    val users = getSampleUsers()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp)
    ) {
        items(users) { user ->
            UserRow(user = user, onClick = { onUserClick(user.id) })
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}
// Composable function to display each user's info (photo, name, and position) with action button
@Composable
fun UserRow(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 5.dp, 0.dp, 0.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .shadow(2.dp, RoundedCornerShape(2.dp))
            .clickable { onClick() } // Trigger the onClick when the row is clicked
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Photo as a Circle
            Image(
                painter = painterResource(id = user.photoResId),
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape) // Make the image circular
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // User Info (Name and Position)
            Column {
                Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = user.position, style = MaterialTheme.typography.bodyMedium)
            }
        }

        // Action button (e.g., a more icon)
        IconButton(onClick = { /* Perform action here */ }) {
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = "More options",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(navController: NavController, userId: Int) {
    val user = getSampleUsers().find { it.id == userId }

    Scaffold(
    ) { innerPadding ->
        if (user != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User Photo as a Circle
                Image(
                    painter = painterResource(id = user.photoResId),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // User Info (Name and Position)
                Text(text = user.name, style = MaterialTheme.typography.headlineLarge)
                Text(text = user.position, style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            Text("User not found", modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    BagTestTheme {
        UserListScreen(onUserClick = { userId ->
            // Navigate to the user details screen when a user is clicked
        })
    }
}