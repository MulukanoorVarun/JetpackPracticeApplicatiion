import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.jetpackpracticeapplicatiion.ui.theme.models.Product
import okhttp3.internal.wait


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavHostController) {
    // Sample product data
    val products = listOf(
        Product(1, "Product 1", "Description for product 1", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 19.99, 4.5),
        Product(2, "Product 2", "Description for product 2", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 29.99, 4.7),
        Product(3, "Product 3", "Description for product 3", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 39.99, 4.2),
        Product(4, "Product 4", "Description for product 4", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 49.99, 4.8),
        Product(5, "Product 5", "Description for product 5", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 59.99, 4.9),
        Product(1, "Product 1", "Description for product 1", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 19.99, 4.5),
        Product(2, "Product 2", "Description for product 2", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 29.99, 4.7),
        Product(3, "Product 3", "Description for product 3", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 39.99, 4.2),
        Product(4, "Product 4", "Description for product 4", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 49.99, 4.8),
        Product(5, "Product 5", "Description for product 5", "https://app.nutsby.com/common_assets/admin/images/product_images/1728569405_1.png", 59.99, 4.9)
    )

    // Detect screen width to adjust grid layout for tablets or phones
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600 // 600dp threshold for tablet
    val numColumns = if (isTablet) 3 else 2 // 3 columns for tablets, 2 for phones

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text("Product List", style = TextStyle(color = Color.Black, fontSize = 20.sp))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                windowInsets = WindowInsets.safeDrawing,
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }
    ) {
        Box {
            LazyVerticalGrid(
                contentPadding = PaddingValues(top = 65.dp),
                columns = GridCells.Fixed(numColumns), // Dynamically choose column count
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Loop through the product list
                items(products) { product ->
                    ProductGridItem(product = product, onClick = {
                        navController.navigate("product_detail/${product.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun ProductGridItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Padding inside the card
        ) {
            // Display image
            Image(
                painter = rememberImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product name
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Product price
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Product rating
            Text(
                text = "Rating: ${product.rating} / 5",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductList() {
    ProductListScreen(navController = NavHostController(context = LocalContext.current)) // Preview with a mock controller
}
