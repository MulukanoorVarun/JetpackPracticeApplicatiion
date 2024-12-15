package com.example.jetpackpracticeapplicatiion.ui.theme.screens

// ProductDetailScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackpracticeapplicatiion.ui.theme.models.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int?, navController: NavController) {
    // Sample product data
    val products = listOf(
        Product(1, "Product 1", "Description for product 1", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 19.99, 4.5),
        Product(2, "Product 2", "Description for product 2", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 29.99, 4.7),
        Product(3, "Product 3", "Description for product 3", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 39.99, 4.2),
        Product(4, "Product 4", "Description for product 4", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 49.99, 4.8),
        Product(5, "Product 5", "Description for product 5", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 59.99, 4.9),
    )

    val product = products.find { it.id == productId }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Product Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        product?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Product Image
                Image(
                    painter = rememberAsyncImagePainter(model = product.imageUrl),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(0.5.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product Name
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Product Price
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Product Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${product.rating} / 5",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Product Description
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } ?: run {
            // Display if product not found
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Product not found", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
