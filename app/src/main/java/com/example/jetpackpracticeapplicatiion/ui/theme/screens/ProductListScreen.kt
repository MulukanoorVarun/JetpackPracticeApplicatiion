import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.jetpackpracticeapplicatiion.ui.theme.models.Product
import com.example.jetpackpracticeapplicatiion.ui.theme.viewmodels.ProductViewModel
import okhttp3.internal.wait


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProductListScreen(navController: NavHostController) {
    // Use the viewModel() function to ensure ViewModel is persisted across recompositions
    val viewModel: ProductViewModel = viewModel()

    // Observe the products and loading states from ViewModel
    val products by viewModel.products.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    // Set up LazyVerticalGrid state to monitor scroll position
    val gridState = rememberLazyGridState()

    // Fetch initial data (first page) when the screen first appears
    LaunchedEffect(Unit) {
        viewModel.fetchNextPage()  // Load initial products
    }

    // Pagination logic: Detect when you scroll to the bottom
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                // If the last visible item is the last item in the grid, trigger fetching more data
                if (lastVisibleItem >= totalItems - 1 && !isLoading) {
                    viewModel.fetchNextPage()  // Trigger next page fetch
                }
            }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Product List", style = TextStyle(color = Color.Black, fontSize = 20.sp)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary),
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // LazyGrid to display products
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(2), // Set two columns for the grid
                contentPadding = PaddingValues(top = 65.dp, bottom = 40.dp), // Ensure there's space below the grid
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                // Loop through the product list
                items(products) { product ->
                    ProductGridItem(product = product, onClick = {
                        navController.navigate("product_detail/${product.id}")
                    })
                }
            }

            // Show a loader when fetching new data at the bottom of the grid
            if (isLoading && products.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(top = 26.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator() // Loading spinner
                }
            }

            // Show a loader while the product list is being initially loaded
            if (isLoading && products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
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
                painter = rememberImagePainter(product.image),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product name
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,           // Set the font size
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2
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
                text = "Rating: ${product.rating.rate}",
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
