import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import javax.imageio.ImageIO

@Composable
@Preview
fun FilePick() {
    var imagePath by remember { mutableStateOf<String?>(null) }
    var imagePainter by remember { mutableStateOf<BitmapPainter?>(null) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                imagePath = openFilePicker()
                imagePath?.let { path ->
                    val file = File(path)
                    val bufferedImage = ImageIO.read(file)
                    imagePainter = BitmapPainter(bufferedImage.toComposeImageBitmap())
                }
            }) {
                Text("Upload Image")
            }

            Spacer(modifier = Modifier.height(20.dp))

            imagePainter?.let {
                Image(
                    painter = it,
                    contentDescription = "Uploaded Image",
                    modifier = Modifier.size(300.dp)
                )
            }
        }
    }
}

fun openFilePicker(): String? {
    val fileDialog = FileDialog(Frame(), "Select an Image", FileDialog.LOAD)
    fileDialog.isVisible = true
    return fileDialog.file?.let { fileDialog.directory + it }
}
