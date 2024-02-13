package com.shegs.mintyncodingtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.shegs.mintyncodingtest.data.network.BinDataModel
import com.shegs.mintyncodingtest.ui.theme.MintynCodingTestTheme
import com.shegs.mintyncodingtest.presentation.viewmodel.BinDataViewModel

class MainActivity : ComponentActivity() {
    private val binDataViewModel: BinDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MintynCodingTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column {
                        CardInput(binDataViewModel = binDataViewModel)
                        Spacer(modifier = Modifier.height(10.dp))

                        binDataViewModel.binDataModel?.let { CardWithDetails(it) }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardInput(binDataViewModel: BinDataViewModel) {
    var cardNumber by remember { mutableStateOf(TextFieldValue("")) }

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = if (cardNumber.text.length >= 16) Color.Green else Color.Red,
        //errorBorderColor = Color.Red // Adjust the color for error state as needed
    )

    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text(text = "Card number") },
                placeholder = { Text(text = "Enter your card number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                trailingIcon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Card number")},
                isError = cardNumber.text.length < 16,
                colors = textFieldColors
            )

            Button(
                onClick = {
                    binDataViewModel.fetchBinInfo(cardNumber.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (binDataViewModel.isLoading) {
                    CircularProgressIndicator(
                        color = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Fetch BIN Info")
                }
            }
        }

}


@Composable
fun CardWithDetails(binDataModel: BinDataModel) {
    // Sample data
//    val cardBrand = "Visa"
//    val cardType = "Credit"
//    val bank = "Example Bank"
//    val country = "United States"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp) // Set your desired height here
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CardDetailRow(
                icon = Icons.Default.AccountCircle,
                title = "Card Brand",
                value = binDataModel.brand.orEmpty()
            )

            CardDetailRow(
                icon = Icons.Default.Add,
                title = "Card Type",
                value = binDataModel.type.orEmpty()
            )

            CardDetailRow(
                icon = Icons.Default.AccountBox,
                title = "Bank",
                value = binDataModel.bank?.name.orEmpty()
            )

            CardDetailRow(
                icon = Icons.Default.LocationOn,
                title = "Country",
                value = binDataModel.country?.name.orEmpty()
            )
        }
    }
}

@Composable
fun CardDetailRow(icon: ImageVector, title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = title, style = MaterialTheme.typography.bodySmall)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            if (value.isNotEmpty()) {
                Text(text = value, style = MaterialTheme.typography.bodyLarge)
            } else {
                Text("N/A", style = MaterialTheme.typography.bodyLarge)
                // or customize the "N/A" text further if needed
            }
        }
    }
}


//fun main() = runBlocking {
//    val bin = "429503"
//
//    try {
//        val binInfo = RetrofitInstance.binlistAPI.getBinData(bin)
//        println("Bank: ${binInfo.bank}")
//        println("Type: ${binInfo.type}")
//        println("Brand: ${binInfo.brand}")
//        println("Country: ${binInfo.country?.name}")
//        // Print other fields as needed
//    } catch (e: Exception) {
//        println("Error fetching data: ${e.message}")
//    }
//}