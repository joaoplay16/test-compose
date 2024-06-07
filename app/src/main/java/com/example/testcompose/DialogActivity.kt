package com.example.testcompose

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.testcompose.ui.theme.TestComposeTheme

class DialogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                AlertDialog()
            }
        }
    }
}

@Composable
fun AlertDialog() {
    val openDialog = remember { mutableStateOf(false) }
    val dialogWidth = 200.dp
    val dialogHeight = 50.dp

    Column(verticalArrangement = Arrangement.Top) {
        Button(onClick = { openDialog.value = !openDialog.value }) {
            Text(text = "Abrir")
        }

        if (openDialog.value) {
            Dialog(onDismissRequest = { openDialog.value = false }) {
                // Draw a rectangle shape with rounded corners inside the dialog
                Text(
                    text = "Voce aceita?",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(dialogWidth, dialogHeight)
                        .background(Color.White)
                )
            }
        }
    }
}



@Composable
fun Popuper() {
    val openDialog = remember { mutableStateOf(false) }

    ConstraintLayout(modifier = Modifier.fillMaxSize()){

        val (button, popup) = createRefs()

        Button(
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 120.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onClick = { openDialog.value = !openDialog.value }
        ) {
            Text(text = "Abrir")
        }

        if (openDialog.value) {
            Box(modifier = Modifier.constrainAs(popup) {
                bottom.linkTo(button.top, margin = 4.dp)
                start.linkTo(button.start)
            }.background(Color.Blue)){
                val cornerSize = 16.dp

                Popup(
                    offset = IntOffset(50, 0),
                    alignment = Alignment.BottomStart,
                    onDismissRequest = { openDialog.value = false },
                ) {
                    // Draw a rectangle shape with rounded corners inside the dialog

                    Text(
                        text = "Copiar",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(cornerSize))
                            .padding(6.dp)
                    )
                }
            }
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable()
fun PreviewAlertDialogDark() {
    TestComposeTheme {
        AlertDialog()
    }
}

@Preview
@Composable()
fun PreviewAlertDialog() {
    TestComposeTheme {
        AlertDialog()
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable()
fun PreviewPopuper() {
    TestComposeTheme {
        Scaffold() {
            Popuper()
        }
    }
}