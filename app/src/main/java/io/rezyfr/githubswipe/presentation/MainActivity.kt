package io.rezyfr.githubswipe.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.skydoves.landscapist.coil.CoilImage
import dagger.hilt.android.AndroidEntryPoint
import io.rezyfr.githubswipe.domain.model.UserItem
import io.rezyfr.githubswipe.ui.component.SearchBox
import io.rezyfr.githubswipe.ui.theme.GithubSwipeTheme
import io.rezyfr.githubswipe.utils.Direction
import io.rezyfr.githubswipe.utils.ExperimentalSwipeableCardApi
import io.rezyfr.githubswipe.utils.rememberSwipeableCardState
import io.rezyfr.githubswipe.utils.swipeableCard
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalSwipeableCardApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubSwipeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(Modifier.fillMaxSize()) {
                        val uiState = viewModel.uiState.collectAsState().value
                        val snapshot = uiState.users.map {
                            it to rememberSwipeableCardState()
                        }
                        val coroutineScope = rememberCoroutineScope()
                        val showSuccessState = !uiState.showEmptyState && !uiState.showErrorState && snapshot.isNotEmpty()
                        SearchBox() {
                            viewModel.processEvent(MainViewModel.ViewEvent.Search(query = it))
                        }
                        Box(
                            Modifier
                                .padding(24.dp)
                                .aspectRatio(1f)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                            if (uiState.showEmptyState) {
                                EmptyView()
                            }
                            if (uiState.showErrorState) {
                                ErrorView()
                            }
                            if (showSuccessState) {
                                snapshot.asReversed().forEachIndexed { index, pair ->
                                    val state = pair.second
                                    val user = pair.first
                                    if (state.swipedDirection == null) {
                                        UserCard(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .swipeableCard(
                                                    state = state,
                                                    onSwipeCancel = {
                                                        Log.d("Swipeable-Card", "Cancelled swipe")
                                                    }
                                                ),
                                            user = user
                                        )
                                    }
                                    LaunchedEffect(user, state.swipedDirection) {
                                        if (state.swipedDirection != null) {
                                            /**
                                             * TODO Handle what the direction should do
                                             * if (state.swipedDirection == Direction.Left) handle left swipe
                                             * else handle right swipe
                                             */
                                            Log.d("Swipe Direction", state.swipedDirection!!.name)
                                            val parsedIndex = (snapshot.size - index)
                                            if (parsedIndex == snapshot.size - 2) {
                                                viewModel.processEvent(MainViewModel.ViewEvent.LoadNextPage)
                                                return@LaunchedEffect
                                            }
                                            if (parsedIndex == snapshot.size) {
                                                state.reset()
                                                viewModel.processEvent(MainViewModel.ViewEvent.ShowEmptyState)
                                                return@LaunchedEffect
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        ButtonContainer(
                            isSuccessState = showSuccessState,
                            onDislikeButtonClick = {
                                viewModel.processEvent(
                                    MainViewModel.ViewEvent.OnSwipeButtonClick(
                                        snapshot,
                                        Direction.Left
                                    )
                                )
                            }, onLikeButtonClick = {
                                viewModel.processEvent(
                                    MainViewModel.ViewEvent.OnSwipeButtonClick(
                                        snapshot,
                                        Direction.Right
                                    )
                                )
                            }
                        )
                        lifecycleScope.launchWhenStarted {
                            viewModel.effect.collectLatest {
                                when (it) {
                                    is MainViewModel.ViewEffect.SwipeCard -> {
                                        coroutineScope.launch {
                                            it.state.swipe(it.direction)
                                        }
                                    }
                                    is MainViewModel.ViewEffect.ShowErrorMessage -> {
                                        Toast.makeText(
                                            this@MainActivity,
                                            it.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ButtonContainer(
        isSuccessState: Boolean,
        onDislikeButtonClick: () -> Unit,
        onLikeButtonClick: () -> Unit
    ) {
        if (isSuccessState) {
            Row(Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            onDislikeButtonClick.invoke()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            onLikeButtonClick.invoke()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ThumbUp,
                            contentDescription = null,
                            tint = Color.Green,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun UserCard(
        modifier: Modifier,
        user: UserItem,
    ) {
        Card(modifier) {
            Box {
                CoilImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = user.avatarUrl,
                    contentScale = ContentScale.Crop,
                )
                Scrim(Modifier.align(Alignment.BottomCenter))
                Column(Modifier.align(Alignment.BottomStart)) {
                    Text(
                        text = user.username,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun Scrim(modifier: Modifier = Modifier) {
        Box(
            modifier
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
                .height(180.dp)
                .fillMaxWidth()
        )
    }

    @Composable
    private fun EmptyView() {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                Modifier
                    .size(72.dp)
                    .padding(top = 16.dp),
                tint = Color.LightGray
            )
            Text("No more result...", style = TextStyle(fontSize = 24.sp))
        }
    }

    @Composable
    private fun ErrorView() {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                Modifier
                    .size(72.dp)
                    .padding(top = 16.dp),
                tint = Color.LightGray
            )
            Text("Error", style = TextStyle(fontSize = 24.sp))
        }
    }
}