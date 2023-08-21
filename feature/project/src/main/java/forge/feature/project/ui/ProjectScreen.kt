package forge.feature.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import forge.model.Project
import forge.model.Version
import forge.ui.DateTime
import forge.ui.statisticsView
import kotlinx.datetime.Clock

@Composable
internal fun ProjectRoute(onBackClick: () -> Unit) {
    val viewModel: ProjectViewModel = hiltViewModel()

    val project = viewModel.projectStream.collectAsStateWithLifecycle()
    val versions = viewModel.versions.collectAsLazyPagingItems()
    statisticsView("project", "id" to viewModel.args.projectId)
    ProjectScreen(project.value, versions, onBackClick) {
        if (it.isCollected) {
            viewModel.cancelCollect(it.id)
        } else {
            viewModel.collect(it.id)
        }
    }
}

@Composable
fun ProjectScreen(
    project: Project?,
    versions: LazyPagingItems<Version>,
    onBackClick: () -> Unit,
    toggleCollect: (Project) -> Unit,
) {
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = project?.name ?: "版本") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                scrollBehavior = behavior
            )
        }) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 200.dp)
        ) {
            if (project != null) {
                item(key = "project") {
                    ProjectInformation(project, toggleCollect)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(
                    versions.itemCount,
                    key = versions.itemKey { it },
                    contentType = versions.itemContentType { "Version" }) { index ->
                    val item = versions[index]
                    VersionItem(project, item)
                }
            }

        }
    }
}

@Composable
private fun ProjectInformation(
    project: Project,
    toggleCollect: (Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            AsyncImage(
                model = "https://img1.baidu.com/it/u=2893520057,1986940682&fm=253&fmt=auto&app=138&f=JPEG?w=600&h=418",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp)
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
        ProvideTextStyle(
            value = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        ) {
            Text(
                text = "包名：${project.`package`}",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )
            Text(
                text = "签名：${project.sign.uppercase()}",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            )
            DateTime(
                time = project.createdTime,
                onlyDate = true,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun VersionItem(
    project: Project,
    version: Version?,
) {
    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(1.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        )
        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 30.dp)
                .size(13.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .padding(3.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
        )
        Column(
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp, end = 16.dp)
                .padding(start = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = version?.name ?: "", style = MaterialTheme.typography.titleMedium)
                if (project.sign != version?.sign) {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .background(
                                MaterialTheme.colorScheme.errorContainer,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "签名异常",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.defaultMinSize(minWidth = 60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CloudDownload,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                    Text(
                        text = version?.file?.displaySize ?: "",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }
            }
            DateTime(
                time = version?.createdTime ?: Clock.System.now(),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.padding(top = 4.dp)
            )
            Row(modifier = Modifier.padding(top = 8.dp)) {
                for (tag in (version?.tags ?: emptyList())) {
                    Box(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                MaterialTheme.shapes.small
                            )

                    ) {
                        Text(
                            text = tag.uppercase(),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }

            Text(
                text = version?.description ?: "",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )

        }

    }
}