package com.example.cryptoapp.pages.portfolio.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import com.example.cryptoapp.model.Coin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    flag: MutableState<Boolean>,
    icon: ImageVector,
    title: String,
    onConfirm: () -> Unit,
    onConfirmText: String,
    expanded: MutableState<Boolean>,
    selectedOption: MutableState<Coin>,
    quantity: MutableState<Int>,
    coins: List<Coin>
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = title)
        },
        title = {
            Text(text = title)
        },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = {
                        expanded.value = !expanded.value
                    }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(),
                        value = selectedOption.value.symbol!!,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                        readOnly = true,
                        onValueChange = {}
                    )

                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false}
                    ) {
                        coins.forEach { entry ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    expanded.value = false
                                    selectedOption.value = entry
                                },
                                text = {
                                    Text(
                                        text = entry.name!!,
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .align(Alignment.Start)
                                    )
                                }
                            )
                        }
                    }
                }

                TextField(
                    value = quantity.value.toString(),
                    onValueChange = {
                        if(it.isEmpty()) {
                            quantity.value = 0
                        } else if (it.matches(Regex("^\\d+\$"))) {
                            quantity.value = it.toInt()
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        onDismissRequest = {
            flag.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(onConfirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    flag.value = false
                }
            ) {
                Text("Cancel")
            }
        }
    )
}