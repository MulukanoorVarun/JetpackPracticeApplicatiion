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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackpracticeapplicatiion.ui.theme.viewmodels.ProductDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: Int?, navController: NavController) {
    val viewModel: ProductDetailViewModel = viewModel()

    // Observe LiveData for product details
    val product = viewModel.product.observeAsState()

    // Fetch product details when the screen is created
    LaunchedEffect(productId) {
        viewModel.fetchProductDetail(productId ?: 1)
    }

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val currentProduct = product.value) {
                // Check if the product is not null and has been fetched successfully
                null -> {
                    // Show loading spinner if product is null or still loading
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                else -> {
                    // Display product details if product is fetched successfully
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Product Image
                        Image(
                            painter = rememberAsyncImagePainter(model = currentProduct.image),
                            contentDescription = currentProduct.title,
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
                            text = currentProduct.title,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Product Price
                        Text(
                            text = "$${currentProduct.price}",
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
                                text = "${currentProduct.rating.rate} / 5",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Product Description
                        Text(
                            text = currentProduct.description,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}




