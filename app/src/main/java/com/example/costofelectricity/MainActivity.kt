package com.example.costofelectricity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.costofelectricity.ui.theme.CostOfElectricityTheme
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CostOfElectricityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CostOfElectricity(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CostOfElectricity(modifier: Modifier= Modifier){
    var consumptionInput by remember { mutableStateOf("") }
    var priceInput by remember {mutableStateOf(0.25f)}
    var reducedVat by remember { mutableStateOf(false) }
    val consumption = consumptionInput.toFloatOrNull() ?: 0f
    val newPrice = priceInput.toDouble()
    val roundedPrice = ceil(newPrice * 100) / 100
    val vatRate = if (reducedVat) 0.10f else 0.24f

    val cost = consumption * newPrice
    val vatAmount= cost * vatRate
    val totalPrice= cost + vatAmount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Electricity Cost Calculator",
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = consumptionInput,
            onValueChange = { consumptionInput = it },
            label = { Text("Consumption (kWh)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Price per kWh: €${"%.2f".format(roundedPrice)}"
        )
        Slider(
            value = priceInput,
            onValueChange = {priceInput= it},

        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reducedVat,
                onCheckedChange = {reducedVat = it }
            )
            Text(
                text = "Reduced VAT (10%)"
            )
        }
        Surface(tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text("Cost: €${"%.2f".format(cost)}")
                Text("VAT (${if (reducedVat) 10 else 24}%): €${"%.2f".format(vatAmount)}")
                Text(
                    text = "Total payment : €${"%.2f". format(totalPrice)} ",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}