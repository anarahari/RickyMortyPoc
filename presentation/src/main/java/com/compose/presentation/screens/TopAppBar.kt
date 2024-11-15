package com.compose.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.presentation.R

@Composable
fun CustomAppBar(
    title: String,
    backAction: (() -> Unit)? = null
) {
    Row(modifier = Modifier.fillMaxWidth()
        .height(70.dp)
        .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        backAction?.let {
            Box(modifier = Modifier.clickable { backAction }
                .padding(5.dp)) {
                Icon(painter = painterResource(R.drawable.ic_back_arrow), contentDescription = "Back Arrow")
            }
        }
        Text(text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground)
    }
}

@Preview(showBackground = true,widthDp = 200, heightDp = 100)
@Composable
fun ComposablePreview() {
    CustomAppBar("All characters")
}