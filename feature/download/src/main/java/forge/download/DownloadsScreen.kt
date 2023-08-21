package forge.download

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import forge.feature.download.R
import forge.ui.CollapsingLayout


@Composable
fun DownloadsScreen() {
    val data = remember {
        buildList {
            repeat(100) {
                add(it)
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                var selectedTab by remember {
                    mutableIntStateOf(0)
                }
                TabRow(selectedTabIndex = selectedTab, indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTab])
                            .width(50.dp)
                            .fillMaxHeight()
                            .clip(MaterialTheme.shapes.large),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                }) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                    ) {
                        Text(text = "1")
                    }
                    Tab(
                        selected = selectedTab == 1, onClick = { selectedTab = 1 },
                    ) {
                        Text(text = "2")
                    }
                    Tab(
                        selected = selectedTab == 2, onClick = { selectedTab = 2 },
                    ) {
                        Text(text = "3")
                    }
                }
            })
        }
    ) { paddingValues ->


        CollapsingLayout(
            modifier = Modifier.padding(paddingValues), collapsingTop = {
                Box(modifier = Modifier.fillMaxWidth().clipToBounds()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_android_black_24dp),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
//                            .graphicsLayer {
//                                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
//                                translationY = scrolledY * 0.5f
//                                previousOffset = lazyListState.firstVisibleItemScrollOffset
//                            }
                            .height(240.dp)
                            .fillMaxWidth()
                    )
                }
            },
        ) {


            val lazyListState = rememberLazyStaggeredGridState()
//            var scrolledY = 0f
//            var previousOffset = 0
            LazyVerticalStaggeredGrid(
//                modifier = Modifier.padding(paddingValues),
                columns = StaggeredGridCells.Fixed(3),
                state = lazyListState
            ) {
//                item(span = StaggeredGridItemSpan.FullLine) {
//                    Box(
//                        modifier = Modifier.fillMaxWidth().clipToBounds()
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_android_black_24dp),
//                            contentDescription = null,
//                            contentScale = ContentScale.FillWidth,
//                            modifier = Modifier
//                                .graphicsLayer {
//                                    scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
//                                    translationY = scrolledY * 0.5f
//                                    previousOffset = lazyListState.firstVisibleItemScrollOffset
//                                }
//                                .height(240.dp)
//                                .fillMaxWidth()
//                        )
//                    }
//                }

                items(
                    data,
                    key = { item -> item },
                    contentType = { item -> item.contentType() }
                ) { item ->
                    when (item.contentType()) {
                        1 -> Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(4.dp),

                            ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "# %03d".format(item),
                                )
                            }

                        }

                        2 -> Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "# %03d".format(item),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                }
            }
        }
    }

//    LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(bottom = 120.dp)) {
//        items(
//            data,
//            key = { item -> item },
//            span = { item -> if (item % 5 == 1) GridItemSpan(2) else GridItemSpan(1) },
//            contentType = { item -> item.contentType() }
//        ) { item ->
//            when (item.contentType()) {
//                1 -> Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(80.dp)
//                        .padding(4.dp),
//
//                    ) {
//                    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
//                        Text(
//                            text = "# %03d".format(item),
//                        )
//                    }
//
//                }
//
//                2 -> Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(80.dp)
//                        .padding(4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
//                ) {
//                    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
//                        Text(
//                            text = "# %03d".format(item),
//                            color = MaterialTheme.colorScheme.onPrimaryContainer
//                        )
//                    }
//                }
//            }
//
//        }
//    }
}

private fun Int.contentType(): Int {
    return if (this % 5 == 1) 1 else 2
}
