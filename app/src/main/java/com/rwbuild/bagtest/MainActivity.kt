package com.rwbuild.bagtest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.LaunchedEffect
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
import com.rwbuild.bagtest.Utils.Resource
import com.rwbuild.bagtest.Utils.Status
import com.rwbuild.bagtest.ViewModels.UserViewModel
import com.rwbuild.bagtest.ui.theme.BagTestTheme
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.rememberImagePainter
import com.rwbuild.bagtest.Models.User
import com.rwbuild.bagtest.Models.UserModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
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
                        composable("home") {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                val userList = userViewModel.userModelLiveData.observeAsState(Resource.loading(null))
                                LaunchedEffect(Unit) {
                                    userViewModel.fetchUsers()
                                }
                                UserListScreen(
                                    userList = userList.value,
                                    onUserClick = { userId ->
                                        navController.navigate("userDetails/$userId")
                                    }
                                )
                            }
                        }

                        composable("settings") {
                            Text(text = "Settings Screen", modifier = Modifier.fillMaxSize())
                        }

                        composable(
                            route = "userDetails/{userId}",
                            arguments = listOf(navArgument("userId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
                            UserDetailsScreen(navController, userId, userViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserListScreen(userList: Resource<UserModel>, onUserClick: (Int) -> Unit) {
    when (userList.status) {
        Status.SUCCESS -> {
            val users = userList.data?.results ?: emptyList()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 0.dp)
            ) {
                items(users) { user ->
                    UserRow(user = user, onClick = { onUserClick(user.location.street.number) })
                    Spacer(modifier = Modifier.height(0.dp))
                }
            }
        }
        Status.ERROR -> {
            Text(text = "Error: ${userList.message}", color = MaterialTheme.colorScheme.error)
        }
        Status.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {
            Text(text = "Unexpected status", color = MaterialTheme.colorScheme.error)
        }
    }
}


@Composable
fun UserRow(user: User, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .shadow(2.dp, RoundedCornerShape(2.dp))
            .clickable { onClick(user.location.street.number) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageUrl = user.picture.large
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        placeholder(R.drawable.profile_default)
                        error(R.drawable.profile_default)
                    }
                ),
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = user.name.first,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
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
fun UserDetailsScreen(navController: NavController, userId: Int, userViewModel: UserViewModel) {
    val userListState = userViewModel.userModelLiveData.observeAsState()

    val user = userListState.value?.data?.results?.find { it.location.street.number == userId }

    Scaffold {
        if (user != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val imageUrl = user.picture.large
                Image(
                    painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {
                            placeholder(R.drawable.profile_default)
                            error(R.drawable.profile_default)
                        }
                    ),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${user.name.first} ${user.name.last}",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = user.phone,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = user.gender,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = user.location.country,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "User not found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    BagTestTheme {
//        UserListScreen(onUserClick = { userId ->
//
//        })
    }
}