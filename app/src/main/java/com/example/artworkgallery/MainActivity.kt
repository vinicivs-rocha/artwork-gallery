package com.example.artworkgallery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artworkgallery.dtos.ArtworkDto
import com.example.artworkgallery.ui.theme.ArtworkGalleryTheme
import java.time.LocalDate
import java.time.Month


@SuppressLint("NewApi")
private val artworks = listOf(
    ArtworkDto(
        1,
        R.drawable.tomorrow_i_may_be_far_away,
        R.string.tomorrow_i_may_be_far_away,
        "Romare Bearden",
        LocalDate.of(1967, Month.JANUARY, 1)
    ),
    ArtworkDto(
        2,
        R.drawable.a_poet_reading,
        R.string.a_poet_reading,
        "Master of the Playing Cards",
        LocalDate.of(1430, Month.JANUARY, 1)
    ),
    ArtworkDto(
        3,
        R.drawable.fruit_and_flowers,
        R.string.fruit_and_flowers,
        "Roger Fenton",
        LocalDate.of(1860, Month.JANUARY, 1)
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtworkGalleryTheme {
                ArtworkGalleryApp()
            }
        }
    }
}

@Composable
fun ArtworkGalleryApp(modifier: Modifier = Modifier) {
    var currentArtwork by remember {
        mutableStateOf(artworks[0])
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp)
    ) {
        Artwork(artwork = currentArtwork, modifier = Modifier.weight(1f))
        GalleryNavigator(
            onNavigationClick = { targetId ->
                currentArtwork = artworks.find { it.id == targetId } ?: artworks[0]
            },
            currentArtworkId = currentArtwork.id,
            artworksCount = artworks.size
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun Artwork(artwork: ArtworkDto, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ArtworkFrame(artworkImage = artwork.artworkImage, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(20.dp))
        ArtworkDescription(
            artworkTitle = artwork.artworkTitle,
            artworkAuthor = artwork.artworkAuthor,
            artworkReleaseYear = artwork.artworkReleaseDate.year
        )
    }
}

@Composable
fun ArtworkFrame(@DrawableRes artworkImage: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.wrapContentSize(),
        tonalElevation = 8.dp,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            painter = painterResource(id = artworkImage),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White)
                .padding(24.dp)
        )
    }
}

@Composable
fun ArtworkDescription(
    @StringRes artworkTitle: Int,
    artworkAuthor: String,
    artworkReleaseYear: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.padding(bottom = 20.dp),
        color = Color.LightGray,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier
                .width(368.dp)
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = artworkTitle), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = artworkAuthor, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "($artworkReleaseYear)")
            }
        }
    }
}

@Composable
fun GalleryNavigator(
    onNavigationClick: (Int) -> Unit,
    currentArtworkId: Int,
    artworksCount: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Button(
            modifier = Modifier
                .widthIn(max = 160.dp)
                .fillMaxWidth(),
            onClick = { onNavigationClick(currentArtworkId - 1) },
            enabled = currentArtworkId > 1
        ) {
            Text(text = stringResource(id = R.string.previous))
        }
        Button(
            modifier = Modifier
                .widthIn(max = 160.dp)
                .fillMaxWidth(),
            onClick = { onNavigationClick(currentArtworkId + 1) },
            enabled = currentArtworkId < artworksCount
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtworkGalleryAppPreview() {
    ArtworkGalleryTheme {
        ArtworkGalleryApp()
    }
}