package com.example.navigation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(
                            userName = "Nguyen Ngoc Mai Thanh",
                            userId = "077305003818",
                            onReadyClick = { navController.navigate("component_list") }
                        )
                    }
                    composable("component_list") {
                        ComponentListScreen(onItemClick = { component ->
                            when (component) {
                                "Text" -> navController.navigate("text_detail")
                                "Image" -> navController.navigate("images")
                                "TextField" -> navController.navigate("textfield")
                                "PasswordField" -> {} // bạn có thể bổ sung sau
                                "Column" -> navController.navigate("column_layout")
                                "Row" -> navController.navigate("row_layout")
                            }
                        })
                    }
                    composable("text_detail") { TextDetailScreen() }
                    composable("images") { ImagesScreen() }
                    composable("textfield") { TextFieldScreen() }
                    composable("row_layout") { RowLayoutScreen() }
                    composable("column_layout") { ColumnLayoutScreen() }
                }
            }
        }
    }
}



                @Composable
fun WelcomeScreen(userName: String, userId: String, onReadyClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = userName, fontWeight = FontWeight.Bold)
            Text(text = userId)
        }
        val iconPainter = painterResource(id = R.drawable.jetpack_logo)
        Image(
            iconPainter,
            contentDescription = "Compose Logo",
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = "Jetpack Compose",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onReadyClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("I'm ready")
        }
    }
}
@Composable
fun ComponentListScreen(onItemClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "UI Components List",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val components = listOf(
            "Text" to "Displays text",
            "Image" to "Displays an image",
            "TextField" to "Input field for text",
            "PasswordField" to "Input field for passwords",
            "Column" to "Arranges elements vertically",
            "Row" to "Arranges elements horizontally"
        )

        components.forEach { (title, desc) ->
            ComponentCard(title, desc, onClick = { onItemClick(title) })
        }

        ComponentCard(
            title = "Tự tìm hiểu",
            description = "Tìm ra tất cả các thành phần UI cơ bản",
            backgroundColor = Color(0xFFFFCDD2),
            onClick = { }
        )
    }
}

@Composable
fun ComponentCard(title: String, description: String, backgroundColor: Color = Color(0xFFBBDEFB), onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun TextDetailScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Text Detail",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            buildAnnotatedString {
                append("The ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append("quick ")
                }
                withStyle(style = SpanStyle(color = Color(0xFFB76E22), fontWeight = FontWeight.Bold)) {
                    append("Brown ")
                }
                append("fox ")
                withStyle(style = SpanStyle(letterSpacing = 4.sp)) {
                    append("jumps ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("over ")
                }
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("the ")
                }
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                    append("lazy ")
                }
                append("dog.")
            },
            fontSize = 20.sp
        )
    }
}
@Composable
fun ImagesScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Images", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.hcm_ut),
            contentDescription = "Image 1"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.hcm_utt),
            contentDescription = "Image 2"
        )
    }
}
@Composable
fun TextFieldScreen() {
    var input by remember { mutableStateOf("") }
    val error = input.isBlank()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("TextField", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Thông tin nhập") },
            isError = error,
            modifier = Modifier.fillMaxWidth()
        )
        if (error) {
            Text("Tự động cập nhật nội dung theo TextField", color = Color.Red)
        }
    }
}
@Composable
fun RowLayoutScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Row Layout", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        repeat(3) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                repeat(3) {
                    Box(modifier = Modifier.size(60.dp).background(Color(0xFF90CAF9)))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun ColumnLayoutScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Column Layout", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        repeat(3) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color(0xFFA5D6A7))
                    .height(60.dp)
            ) {}
        }
    }
}