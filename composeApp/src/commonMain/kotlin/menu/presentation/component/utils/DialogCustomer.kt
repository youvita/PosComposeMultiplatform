package menu.presentation.component.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.theme.ColorD9D9D9
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.DialogLoading
import customer.domain.model.CustomerModel
import customer.presentation.CustomerEvent
import customer.presentation.CustomerState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_exit

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
fun DialogCustomer(
    customerState: CustomerState? = null,
    customerEvent: (CustomerEvent) -> Unit = {},
    onClose: () -> Unit = {},
    onSelectCustomer: (CustomerModel) -> Unit = {},
){
    var cusName by rememberSaveable { mutableStateOf("") }
    var cusPhoneNumber by rememberSaveable { mutableStateOf("") }
    var cusAddress by rememberSaveable { mutableStateOf("") }
    var cusRemark by rememberSaveable { mutableStateOf("") }
    var cusPoint by rememberSaveable { mutableIntStateOf(0) }
    var list by rememberSaveable { mutableStateOf(customerState?.customers?: arrayListOf()) }

    LaunchedEffect(customerState?.isSuccess){
        if(customerState?.isSuccess == true){
            if(customerState.customers?.isNotEmpty() == true){
                onSelectCustomer(customerState.customers[0])
            }
            customerEvent(CustomerEvent.ClearEvent)
        }
    }

    LaunchedEffect(customerState?.customers){
        if(customerState?.customers != null) {
            list = customerState.customers
        }
    }

    if(customerState?.isLoading == true){
        DialogLoading()
    }

    Dialog(
        onDismissRequest = { },
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)

        ) {

            Row{
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    text = "Customer Information",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                IconButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_exit),
                        contentDescription = ""
                    )
                }
            }

            FlowRow(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = Shapes.medium)
                    .background(Color(0x5EDDE3F9))
                    .padding(10.dp),
            ) {

                // Customer name
                TextInput(
                    label = "Customer",
                    onValueChange = {
                        cusName = it
                    }
                )

                // Phone number
                TextInput(
                    label = "Phone number",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        cusPhoneNumber = it
                    }
                )

                // Address
                TextInput(
                    label = "Address",
                    onValueChange = {
                        cusAddress = it
                    }
                )

                // Remark
                TextInput(
                    label = "Remark",
                    onValueChange = {
                        cusRemark = it
                    }
                )

                // Point
                TextInput(
                    label = "Point",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        cusPoint = it.toInt()
                    }
                )
            }

            OutlinedButton(
                onClick = {
                    val customer = CustomerModel(
                        name = cusName.ifEmpty { "Unknown" },
                        phone_number = cusPhoneNumber.ifEmpty { "N/A" },
                        remark = cusRemark.ifEmpty { "N/A" },
                        address = cusAddress.ifEmpty { "N/A" },
                        point = cusPoint
                    )

                    customerEvent(CustomerEvent.AddCustomerEvent(customer))
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = PrimaryColor,
                    contentColor = White
                ),
                border = null,
                shape = Shapes.medium,
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            ) {
                Text(
                    text = "Save new customer",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            if(list.isNotEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = Shapes.medium)
                        .background(White)
                        .padding(10.dp)
                ){
                    // customer list
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Customer list:",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Here is the header
                    Row(
                        modifier = Modifier
                            .clip(shape = Shapes.medium)
                            .background(Color(0x5EDDE3F9))
                    ) {
                        TableCell(text = "Name", weight = 1f)
                        TableCell(text = "Phone", weight = 1f)
                        TableCell(text = "Address", weight = 1f)
                        TableCell(text = "Remark", weight = 1f)
                        TableCell(text = "Point", weight = 1f)
                        TableCell(text = "", weight = 1f)
                    }

                    LazyColumn{
                        items(list){ customer ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TableCell(text = customer.name, weight = 1f)
                                TableCell(text = customer.phone_number, weight = 1f)
                                TableCell(text = customer.address, weight = 1f)
                                TableCell(text = customer.remark, weight = 1f)
                                TableCell(text = customer.point.toString(), weight = 1f)

                                OutlinedButton(
                                    enabled = true,
                                    onClick = {
                                        onSelectCustomer(customer)
                                    },
                                    colors = ButtonDefaults.elevatedButtonColors(
                                        containerColor = Color(0xFFC1F2B0),
                                        contentColor = Color(0xFF318B0A)
                                    ),
                                    border = null,
                                    contentPadding = PaddingValues(8.dp),
                                    shape = Shapes.medium,
                                    modifier = Modifier
                                        .weight(1f)
                                        .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                                ) {
                                    Text(
                                        text = "Select",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                }
                            }

                            HorizontalDivider(thickness = 1.dp, color = ColorD9D9D9)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String? = null,
    weight: Float
) {
    Text(
        text = text?: "",
        modifier = Modifier
            .weight(weight)
            .padding(10.dp),
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun TextInput(
    label: String = "Label",
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(text)
        },
        modifier = Modifier
            .widthIn(min = 200.dp)
            .clip(shape = Shapes.medium),
        label = {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
         },
        trailingIcon = {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.Clear,
                contentDescription = "contentDescription",
                tint = PrimaryColor,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(0xFFE7EBFD),
            unfocusedContainerColor = Color(0xFFE7EBFD)
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
    )
}

