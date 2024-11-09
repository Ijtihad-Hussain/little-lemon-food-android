package com.bush.littlelemoncappro.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bush.littlelemoncappro.R
import com.bush.littlelemoncappro.componants.AppHeader
import com.bush.littlelemoncappro.data.MenuItem
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.charcoal
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.cloud
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.green
import com.bush.littlelemoncappro.ui.theme.LittleLemonColors.yellow

@Composable
fun HomeScreen(navController: NavHostController, menuItems: List<MenuItem>) {
    Column {
        AppHeader(navController)
        HeroSection(menuItems)
    }
}

@Composable
fun HeroSection(menuItems: List<MenuItem>) {
    var searchTerm by remember {
        mutableStateOf("")
    }
    var filteredList: List<MenuItem>

    Column {
        Column(
            modifier = Modifier
                .background(color = green)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Little Lemon",
                        fontSize = 30.sp,
                        color = yellow,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Text(
                        text = "Chicago",
                        fontSize = 20.sp,
                        color = cloud,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    Text(
                        text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        color = cloud
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "hero image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(top = 40.dp, start = 7.dp)
                        .height(150.dp)
                        .width(140.dp)
                        .clip(RoundedCornerShape(size = 10.dp))
                )
            }
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
                    searchTerm = it
                },
                placeholder = {
                    Text(text = "Enter search phrase")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 7.dp, top = 20.dp, bottom = 40.dp)
                    .background(cloud, RoundedCornerShape(8))
            )
        }
        filteredList = if (searchTerm.isNotBlank()) {
            menuItems.filter {
                it.title.contains(searchTerm, true)
            }
        } else {
            menuItems
        }
        MenuSection(filteredList)
    }
}

@Composable
fun MenuSection(menuItems: List<MenuItem>) {
    var category by remember {
        mutableStateOf("")
    }
    var filteredList: List<MenuItem>

    Column {
        Text(text = "Order for Delivery!", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        Row {
            Button(
                onClick = { category = "starters" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = cloud,
                    contentColor = charcoal
                )
            ) {
                Text(text = "Starters")
            }
            Button(
                onClick = { category = "mains" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = cloud,
                    contentColor = charcoal
                )
            ) {
                Text(text = "Mains")
            }
            Button(
                onClick = { category = "desserts" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = cloud,
                    contentColor = charcoal
                )
            ) {
                Text(text = "Desserts")
            }
            Button(
                onClick = { category = "drinks" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = cloud,
                    contentColor = charcoal
                )
            ) {
                Text(text = "Drinks")
            }
        }
        filteredList = if (category.isNotBlank()) {
            menuItems.filter {
                it.category == category
            }
        } else {
            menuItems
        }
        MenuItemsComposable(filteredList)
    }

}

@Composable
fun MenuItemsComposable(menuItems: List<MenuItem>) {
    LazyColumn {
        itemsIndexed(menuItems) { _, item ->
            MenuItemComposable(item)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemComposable(item: MenuItem) {
    Row(Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp)) {
        Column(Modifier.weight(1f)) {
            Text(text = item.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = item.description, modifier = Modifier.padding(vertical = 5.dp))
            Text(text = item.price.toString() + "$", fontWeight = FontWeight.Bold)
        }
        GlideImage(
            model = item.image,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
        )
    }
    Divider(thickness = 1.dp, color = cloud)
}