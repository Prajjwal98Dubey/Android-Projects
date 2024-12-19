package com.example.composepractise1

import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractise1.ui.theme.ComposePractise1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverter()
        }
    }
}
@Composable
fun UnitConverter(){
    var inputValue by remember { mutableStateOf("1") }
    var inputSelect by remember { mutableStateOf("meter") }
    var outputSelect by remember { mutableStateOf("centimeter") }
    var res by remember {  mutableStateOf("100") }
    var isInputExpanded by remember { mutableStateOf(false) }
    var isOutExpanded by remember { mutableStateOf(false) }
    var inputIndicator by remember { mutableStateOf("m") }
    var outputIndicator by remember { mutableStateOf("cm") }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Unit Converter", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
            res = findResult(inputSelect,outputSelect,it)
        }, label = { Text("Enter Value") })
        Row(modifier = Modifier.padding(5.dp)){
            Box {
                Button(modifier = Modifier
                    .width(140.dp)
                    .height(45.dp), onClick = {
                    isInputExpanded = true
                }){
                    Text("Input")
                    Text("($inputIndicator)")
                    Icon(Icons.Default.ArrowDropDown,"Drop Down Icon")
                    DropdownMenu(expanded = isInputExpanded, onDismissRequest = {
                        isInputExpanded = false
                    }) {
                        DropdownMenuItem(text = { Text("centimeter") }, onClick = {
                            inputSelect = "centimeter"
                            isInputExpanded = false
                            inputIndicator = "cm"
                            res = findResult("centimeter",outputSelect,inputValue)
                        })
                        DropdownMenuItem(text = { Text("meter") }, onClick = {
                            inputSelect  = "meter"
                            isInputExpanded = false
                            inputIndicator = "m"
                            res = findResult("meter",outputSelect,inputValue)
                        })
                        DropdownMenuItem(text = { Text("kilometer") }, onClick = {
                            inputSelect = "kilometer"
                            isInputExpanded = false
                            inputIndicator = "km"
                            res = findResult("kilometer",outputSelect,inputValue)
                        })

                    }
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
            Box {
                Button(modifier = Modifier
                    .width(140.dp)
                    .height(45.dp), onClick = {
                    isOutExpanded = true
                }){

                    Text("Output")
                    Text("($outputIndicator)")
                    Icon(Icons.Default.ArrowDropDown,"Drop Down Icon")
                    DropdownMenu(expanded = isOutExpanded, onDismissRequest = {
                        isOutExpanded = false
                    }) {
                        DropdownMenuItem(text = { Text("centimeter") }, onClick = {
                            outputSelect = "centimeter"
                            isOutExpanded = false
                            outputIndicator = "cm"
                            res = findResult(inputSelect,"centimeter",inputValue)
                        })
                        DropdownMenuItem(text = { Text("meter") }, onClick = {
                            outputSelect = "meter"
                            isOutExpanded  = false
                            outputIndicator = "m"
                            res = findResult(inputSelect,"meter",inputValue)
                        })
                        DropdownMenuItem(text = { Text("kilometer") }, onClick = {
                            outputSelect = "kilometer"
                            isOutExpanded = false
                            outputIndicator = "km"
                            res = findResult(inputSelect,"kilometer",inputValue)
                        })

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Text("Result: $res $outputIndicator", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp,)
        }

    }

}
fun findResult(inp:String,out:String,numValue:String) : String{
    if(numValue.length == 0) return ""
    var ans = numValue.toDouble()
    if (inp == "centimeter"){
        if (out == "meter"){
            ans *= 0.01
        }
        else if(out == "kilometer"){
            ans *= 0.00001
        }
    }
    else if (inp == "meter"){
        if (out == "centimeter"){
            ans *= 100
        }
        else if (out == "kilometer"){
            ans *= 0.001
        }
    }
    else if (inp == "kilometer"){
        if (out == "meter"){
            ans *= 1000
        }
        else if (out == "centimeter"){
            ans *= 100000
        }
    }
    return ans.toString()
}
