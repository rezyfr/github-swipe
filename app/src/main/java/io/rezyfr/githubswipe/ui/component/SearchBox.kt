package io.rezyfr.githubswipe.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    onActionImeClick: (String) -> Unit
) {
    var query by rememberSaveable  { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(36.dp),
            value = query,
            onValueChange = {
                query = it
            },
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(Color( 0xFFF5F5F5), RoundedCornerShape(10.dp))
                        .align(Alignment.Center)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.Search, null, tint = Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        if (query.isEmpty()) {
                            Text("Search user...", style = TextStyle(color = Color.Gray))
                        }
                        innerTextField()
                    }
                }
            },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(Color.Black),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onActionImeClick.invoke(query)
                    keyboardController?.hide()
                }
            )
        )
    }
}