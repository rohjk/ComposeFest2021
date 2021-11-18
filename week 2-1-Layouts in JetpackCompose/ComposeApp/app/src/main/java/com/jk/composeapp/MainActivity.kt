package com.jk.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jk.composeapp.ui.theme.ComposeAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                LayoutsCodelab()
            }
        }
    }
}

@Composable
fun PhotographerCard(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(
                onClick = {}
            )
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(
                alpha = 0.2f
            )
        ) {
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Alfred Sisley",
                fontWeight = FontWeight.Bold
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "3 minutes ago",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        PhotographerCard()
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutCodelab")
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        MyOwnColumn(
            modifier = modifier.padding(8.dp)
        ) {
            Text("MyOwnColumn")
            Text("places items")
            Text("vertically.")
            Text("We've done it by hand!")
        }
    }
}

@Preview
@Composable
fun LayoutCodelabPreviwe() {
    ComposeAppTheme {
        LayoutsCodelab()
    }
}

@Composable
fun ImageList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }
            ) {
                Text(text = "Scroll to the top")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }
            ) {
                Text(text = "Scroll to the end")
            }
        }
        LazyColumn(
            state = scrollState
        ) {
            items(100) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Item #$index",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview
@Composable
fun ImageListPreview() {
    ComposeAppTheme {
        ImageList()
    }
}

fun Modifier.firstBaselineToTop(
    firstBaseLineToTop: Dp,
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints = constraints)

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]

        val placeableY = firstBaseLineToTop.roundToPx() - firstBaseLine
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeAppTheme {
        Text(
            text = "Hi there!",
            modifier = Modifier.firstBaselineToTop(32.dp)
        )
    }
}

@Preview
@Composable
fun TextWithMormalPaddingPreview() {
    ComposeAppTheme {
        Text(
            text = "Hi there!",
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val placeable = measurables.map { measurable ->
            measurable.measure(constraints = constraints)
        }
        var yPosition = 0

        layout(
            constraints.maxWidth,
            constraints.maxHeight
        ) {
            placeable.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}
