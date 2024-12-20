package com.example.composepractise1
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList(){
    var isDialogOpen =  remember { mutableStateOf(false) }
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var editingId by remember { mutableStateOf("") }
    var editedName by remember { mutableStateOf("") }
    var editedQuantity by remember { mutableStateOf("") }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = {
                isDialogOpen.value = true
            }) {
                Text("Add")
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = productItems,
                    key = {item -> item.id}
                ){
                    item ->
                    if(item.id != editingId){
                        Row (modifier = Modifier.fillMaxWidth().height(40.dp).padding(3.dp).border(2.dp,Color.Blue, shape = RoundedCornerShape(2.dp)), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Text(item.name, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text(item.quantity, fontSize = 13.sp)
                            Icon(Icons.Default.Edit, contentDescription = "edit", modifier = Modifier.clickable {
                                editingId = item.id
                                editedName = item.name
                                editedQuantity = item.quantity
                            })
                            Icon(Icons.Default.Delete, contentDescription = "remove",modifier = Modifier.clickable {
                                productItems = updatedListAfterDeletion(productItems,item.id)
                            })
                        }
                    }
                    else{
                        Row(modifier = Modifier.fillMaxWidth().height(40.dp).border(2.dp,Color.Red, shape = RoundedCornerShape(2.dp))){
                            BasicTextField(editedName, onValueChange = {
                                editedName = it

                            })
                            BasicTextField(editedQuantity, onValueChange = {
                                editedQuantity = it
                            })
                            Button(onClick = {
                                    editingId = ""
                                    item.name = editedName
                                    item.quantity = editedQuantity
                            }) {
                                Text("Save")
                            }
                        }

                    }
                }
            }
        }
        if(isDialogOpen.value){
            AlertDialog(onDismissRequest = {isDialogOpen.value = false}, confirmButton = {},
                text = { Column(modifier = Modifier.padding(10.dp)) {
                    OutlinedTextField(productName, onValueChange = {productName = it}, label = { Text("Enter Product Name") })
                    OutlinedTextField(productQuantity, onValueChange = {productQuantity = it}, label = { Text("Enter Quantity") })
                    Row (horizontalArrangement = Arrangement.Center) {
                        Button(modifier = Modifier.padding(7.dp), onClick = {
                            productItems = productItems + ShoppingItem((productItems.size+1).toString(),productName,productQuantity,false)
                            isDialogOpen.value = false
                            productName = ""
                            productQuantity = ""
                        }) {
                            Text("Add")
                        }
                        Button(modifier = Modifier.padding(7.dp), onClick = {
                            isDialogOpen.value = false
                            productName = ""
                            productQuantity = ""
                        }) {
                            Text("Cancel")
                        }
                    }
                }},
                )
            }
        }

data class ShoppingItem (val id:String,var name:String,var quantity:String,var isEditing:Boolean)

fun updatedListAfterDeletion(items:List<ShoppingItem>,currItem:String):List<ShoppingItem>{
    var newProductItemsList = listOf<ShoppingItem>()
    for (i in 0..items.size-1){
        if(items.get(i).id != currItem ) {
            var curr = ShoppingItem((newProductItemsList.size+1).toString(),items.get(i).name,items.get(i).quantity,false)
            newProductItemsList += curr
        }
    }
    return newProductItemsList
}


/*
*
* Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = {
                isDialogOpen.value = true
            }) {
                Text("Add")
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = productItems,
                    key = {item -> item.id}
                ){
                    item ->
                    if(!item.isEditing){
                        Row (modifier = Modifier.fillMaxWidth().height(40.dp).padding(3.dp).border(2.dp,Color.Blue, shape = RoundedCornerShape(2.dp)), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                            Text(item.name, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text(item.quantity, fontSize = 13.sp)
                            Icon(Icons.Default.Edit, contentDescription = "edit", modifier = Modifier.clickable {
                                item.isEditing = true
                                trigger = !trigger
                            })
                            Icon(Icons.Default.Delete, contentDescription = "remove",modifier = Modifier.clickable {
                                productItems = updatedListAfterDeletion(productItems,item.id)
                            })
                        }
                    }
                    else{
                        Row(modifier = Modifier.fillMaxWidth().height(40.dp).border(2.dp,Color.Red, shape = RoundedCornerShape(2.dp))){
                            BasicTextField(item.name, onValueChange = {})
                            BasicTextField(item.quantity, onValueChange = {})
                            Button(onClick = {

                            }) {
                                Text("Save")
                            }
                        }

                    }
                }
            }
        }
*
*
* */































