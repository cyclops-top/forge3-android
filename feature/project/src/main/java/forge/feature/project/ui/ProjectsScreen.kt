package forge.feature.project.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import forge.model.Project
import forge.ui.statisticsView
import forge.ui.theme.ForgeTheme
import kotlinx.datetime.Clock


@Composable
fun ProjectsRoute(
    navigationToProject: (Long) -> Unit,
) {
    val viewModel: ProjectsViewModel = hiltViewModel()
    val projectItems = viewModel.projects.collectAsLazyPagingItems()
    statisticsView("projects")
    ProjectsScreen(projectItems, navigationToProject) {
        if (it.isCollected) {
            viewModel.cancelCollect(it.id)
        } else {
            viewModel.collect(it.id)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun ProjectsScreen(
    projectItems: LazyPagingItems<Project>,
    navigationToProject: (Long) -> Unit,
    toggleCollect: (Project) -> Unit,
) {
    val isRefreshing = projectItems.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            projectItems.refresh()
        },
    )
    val behavior = exitUntilCollapsedScrollBehavior()
    Box(Modifier.pullRefresh(pullRefreshState)) {
        Scaffold(
            Modifier.nestedScroll(behavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = { Text(text = "项目") },
                    scrollBehavior = behavior
                )
            }) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(projectItems.itemCount,key = projectItems.itemKey { it }, contentType = projectItems.itemContentType { "project" }){index->
                    ProjectItem(
                        projectItems[index]!!, toggleCollect = toggleCollect,
                        onClick = { project ->
                            navigationToProject(project.id)
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .animateItemPlacement()
                    )
                }
            }

        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ProjectItem(
    project: Project,
    toggleCollect: (Project) -> Unit,
    onClick: (Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = { onClick(project) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = "https://img1.baidu.com/it/u=2893520057,1986940682&fm=253&fmt=auto&app=138&f=JPEG?w=600&h=418",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconToggleButton(
                modifier = Modifier.align(Alignment.Top),
                checked = project.isCollected, onCheckedChange = {
                    toggleCollect(project)
                }) {
                Icon(
                    imageVector = if (project.isCollected) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = "",
                    Modifier.size(16.dp),
                    tint = if (project.isCollected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.3f
                    )
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
                .fillMaxWidth(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = project.name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (project.lastVersion != null) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f)
                    )
                    Text(
                        text = project.lastVersion ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProjectsScreenPreview() {
    val project = remember {
        Project(
            id = 0,
            name = "微信",
            description = "微信，超过10亿人使用，能够通过网络给好友发送文字消息、表情和图片，还可以传送文件，与朋友视频聊天，让你的沟通更方便。并提供有多种语言界面",
            `package` = "com.tencent.wechat",
            sign = "1E:56:27:C8:1A:2D:AD:C7:DA:7C:C0:7D:E9:2C:C1:4D",
            icon = "https://cdn.icon-icons.com/icons2/1488/PNG/512/5368-wechat_102582.png",
            private = true,
            createdTime = Clock.System.now(),
            isCollected = false,
            lastVersion = "v2.1.0"
        )
    }
    ForgeTheme {
        ProjectItem(project, toggleCollect = {}, onClick = {})
    }
}