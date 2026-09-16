package com.example.listycity3

import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onModifyCity: (City, Int) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        item { addCity(onModifyCity, cities.size) }
        itemsIndexed(cities) { index, city ->
            CityRow(city = city, onModifyCity, index)

            if (index < cities.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}


@Composable
fun addCity(onModifyCity: (City, Int) -> Unit, index: Int){
    var addCityFlag by remember { mutableStateOf(false) }
    Button(onClick = {
        addCityFlag = true
    }) {
        Text("Add City")
    }
    if (addCityFlag) {
        HandleModify(onModifyCity, index, onDismiss = {addCityFlag = false}, "add")
    }
}
@Composable
fun CityRow(city: City, onModifyCity: (City, Int) -> Unit, index: Int) {
    var modify by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable(
                true, onClick = {
                    modify = true
                }),

    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
    if (modify) {
        HandleModify(onModifyCity, index, onDismiss = {modify = false}, "modify")
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CityListScreenPreview() {
//    ListyCity3Theme {
//        CityListScreen(
//            cities = listOf(
//                City("Edmonton", "AB"),
//                City("Vancouver", "BC"),
//                City("Calgary", "AB")
//            ),
//            onModifyCity = ,
//        )
//    }
//}

@Composable
fun HandleModify(onModifyCity: (City, Int) -> Unit, index: Int, onDismiss:() -> Unit, source: String){
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = {
            newCityName = ""
            newProvinceName = ""
            onDismiss()
        },
        title = {
            if (source == "add"){Text("Add City")}
            else {Text("Edit City")}
        },
        text = {
            Column {
                OutlinedTextField (
                value = newCityName,
                onValueChange = {newCityName = it},
                label = {
                    Text("City")
                },
                modifier = Modifier.fillMaxWidth()
            )
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = {newProvinceName = it},
                    label = {
                        Text("Province")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if ((newCityName.isNotBlank() && (newProvinceName.isNotBlank()))){
                    onModifyCity(City(newCityName, newProvinceName), index)
                    newCityName = ""
                    newProvinceName = ""
                }
                onDismiss()

            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                newCityName = ""
                newProvinceName = ""
                onDismiss()

            }) {
                Text("Cancel")
            }
        }
    )
}