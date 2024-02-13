package com.shegs.mintyncodingtest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.AccountBox
import androidx.compose.material3.icons.filled.AccountCircle
import androidx.compose.material3.icons.filled.Add
import androidx.compose.material3.icons.filled.LocationOn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shegs.mintyncodingtest.data.network.BinDataModel
import com.shegs.mintyncodingtest.presentation.viewmodel.BinDataViewModel
import com.shegs.mintyncodingtest.ui.theme.MintynCodingTestTheme

const val CAMERA_PERMISSION_REQUEST_CODE = 123

class MainActivity : ComponentActivity() {
    private val binDataViewModel: BinDataViewModel by viewModels()

    private val ocrActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    val bitmap = intent.extras?.get("data") as? Bitmap
                    bitmap?.let { handleOCRResult(it) }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MintynCodingTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        CardInput(
                            binDataViewModel = binDataViewModel,
                            ocrActivityResultLauncher = ocrActivityResultLauncher
                        )
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
fun CardInput(
    binDataViewModel: BinDataViewModel,
    ocrActivityResultLauncher: ActivityResultLauncher<Intent>
) {
    var cardNumber by remember { mutableStateOf(TextFieldValue("")) }

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = if (cardNumber.text.length >= 16) Color.Green else Color.Red,
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
            trailingIcon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Card number") },
            isError = cardNumber.text.length < 16,
            colors = textFieldColors
        )

        LaunchOCRButton(ocrActivityResultLauncher, cardNumber)

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

        Button(
            onClick = {
                launchOCRActivity(ocrActivityResultLauncher)
            },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            Text("OCR")
        }
    }
}

@Composable
fun LaunchOCRButton(ocrActivityResultLauncher: ActivityResultLauncher<Intent>, cardNumber: TextFieldValue) {
    Button(
        onClick = {
            launchOCRActivity(ocrActivityResultLauncher)
        },
        modifier = Modifier
            .weight(1f)
            .padding(start = 4.dp)
    ) {
        Text("OCR")
    }
}

private fun launchOCRActivity(ocrActivityResultLauncher: ActivityResultLauncher<Intent>) {
    val context = LocalContext.current
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        ocrActivityResultLauncher.launch(takePictureIntent)
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }
}

private fun handleOCRResult(bitmap: Bitmap) {
    // Process OCR result and update the text field
    // You need to implement this logic
    // val ocrResult = OCRUtils.getTextFromBitmap(this, bitmap)
    // cardNumber = TextFieldValue(ocrResult)
}

@Composable
fun CardWithDetails(binDataModel: BinDataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp)
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
            }
        }
    }
}
