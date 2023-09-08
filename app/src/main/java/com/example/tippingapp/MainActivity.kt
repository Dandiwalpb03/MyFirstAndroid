package com.example.tippingapp

import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tippingapp.compoents.InputField
import com.example.tippingapp.ui.theme.TippingAppTheme
import com.example.tippingapp.util.calculateTotalPerPerson
import com.example.tippingapp.util.calculatingTotalTip
import com.example.tippingapp.widgets.RoundIconsButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Myapp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Myapp() {
    TippingAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column {

                TakingInput()
            }
        }
    }
}


@Composable
fun TopHeader(totalperperson: Double = 0.0){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 7.5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color(0xFFD1D1B9))
        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Total Per Person",
                color = Color(0xFFBF310B),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            val total = "%.2f".format(totalperperson)
            Text(
                text = "$$total",
                color = Color(0xFFBF310B),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TakingInput(){
    BillForm(){billAmt ->
        Log.d("Bill amt", "MainContent: $billAmt")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier,
             onValChange: (String) -> Unit = {}){

    // this will track the value in text field
    val totalBillState = remember{
        mutableStateOf("")
    }
    // this will tell us is the field empty ( then return false)
    // or not (then return true)
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val counter = remember{
        mutableStateOf(1)
    }
    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val sliderStateToInt = (sliderPositionState.value*100).toInt()
    val tipAmountState = remember{
        mutableStateOf(0.0)
    }
    val totalperperson = remember {
        mutableStateOf(0.0)
    }

    TopHeader(totalperperson = totalperperson.value)

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 7.5.dp, start = 10.dp, end = 10.dp, bottom = 7.5.dp),
        //.clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, color = Color.Gray),
        colors = CardDefaults.cardColors(Color(0xFFD1D1B9))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                modifier = Modifier.padding(10.dp),
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                }
            )
                Row(
                    modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text ="Spilt",
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically)
                        )
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(3.dp),
                        horizontalArrangement = Arrangement.End,
                        ) {

                            RoundIconsButton(imageVector = Icons.Default.Remove,
                                onClick = {
                                          counter.value = if (counter.value > 1) counter.value - 1 else  1
                                    totalperperson.value = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                        splitBy = counter.value,
                                        tipPercentage = sliderStateToInt
                                    )
                                    Log.d("ADD","Clicked${totalperperson.value} " +
                                            "tipAmount: ${tipAmountState.value}")
                                })
                        Text(text = "${counter.value}",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp))
                        RoundIconsButton(imageVector = Icons.Default.Add,
                            onClick = {if(counter.value < 100 )counter.value +=1 else counter.value = 100
                                totalperperson.value = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                    splitBy = counter.value,
                                    tipPercentage = sliderStateToInt
                                )
                            Log.d("ADD","Clicked${totalperperson.value}")})
                    }
                }
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Tip")
                Spacer(modifier = Modifier.width(120.dp))
                Text(text = "${tipAmountState.value}")
            }
            Column(modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$sliderStateToInt%")
                Spacer(modifier = Modifier.height(20.dp))
                Slider(value = sliderPositionState.value,
                    onValueChange = { newVal ->
                        sliderPositionState.value = newVal
                        tipAmountState.value = calculatingTotalTip(totalBill = totalBillState.value.toDouble(),
                            tipPercentage = sliderStateToInt)
                        totalperperson.value = calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                            splitBy = counter.value,
                            tipPercentage = sliderStateToInt
                            )
                    },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            //                    steps = 5
                                )
            }
        }
    }
}

