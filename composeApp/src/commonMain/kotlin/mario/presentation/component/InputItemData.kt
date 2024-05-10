//package com.topteam.pos.ui.features.mario.presentation.component
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.CheckboxDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.CompositionLocalProvider
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableDoubleStateOf
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Devices
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.share.module.R
//import com.share.module.ui.theme.Black
//import com.share.module.ui.theme.Primary
//import com.topteam.pos.database.table.ItemOption
//import com.topteam.pos.ui.features.menu.domain.model.MenuModel
//import com.topteam.pos.ui.features.setting.domain.model.ItemModel
//import com.topteam.pos.ui.features.setting.presentation.components.PhotoLabelSelectorRequire
//import com.topteam.pos.ui.utils.LabelInputNormal
//import com.topteam.pos.ui.utils.LabelInputRequire
//import com.topteam.pos.ui.utils.TextInputNormal
//import com.topteam.pos.ui.utils.stringToDouble
//import com.topteam.pos.ui.utils.stringToInt
//
//@Preview(showBackground = true, device = Devices.DEFAULT)
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun InputItemData(
//    modifier: Modifier = Modifier,
//    menu: MenuModel? = null,
//    onRequire: (Boolean) -> Unit = {},
//    onItemChange: (ItemModel) -> Unit = {}
//) {
//    var itemModel by rememberSaveable { mutableStateOf(ItemModel()) }
//    var itemPhoto by rememberSaveable { mutableStateOf("") }
//    var itemName by rememberSaveable { mutableStateOf("") }
//    var itemDesc by rememberSaveable { mutableStateOf("") }
//    var itemPrice by rememberSaveable { mutableDoubleStateOf(0.0) }
//    var itemPriceText by rememberSaveable { mutableStateOf("") }
//    var itemDiscount by rememberSaveable { mutableIntStateOf(0) }
//    var itemDiscountText by rememberSaveable { mutableStateOf("") }
//
//    fun checkRequire(){
//        onRequire(itemPhoto.isEmpty() || itemName.isEmpty() || itemPriceText.isEmpty())
//    }
//
//    LaunchedEffect(itemModel){
//        onItemChange(itemModel)
//        checkRequire()
//    }
//
//    Column(
//        modifier
//            .fillMaxHeight()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//            .padding(bottom = 30.dp)
//    ) {
//        FlowRow(
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//        ){
//            PhotoLabelSelectorRequire(
//                label = "Photo",
//                photoPath = itemPhoto,
//                onPhotoSelected = {
//                    itemPhoto = it
//                    itemModel = itemModel.copy(
//                        imageUrl = it
//                    )
//                }
//            )
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            Box(modifier = Modifier.weight(1f)){
//                LabelInputNormal(
//                    modifier = Modifier.fillMaxWidth(),
//                    keyboardType = KeyboardType.Number,
//                    text = menu?.name?: "",
//                    label = "Menu",
//                    enabled = false
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputRequire(
//            modifier = Modifier.fillMaxWidth(),
//            text = itemName,
//            label = "Item Name",
//            placeholder = "Enter name",
//            onValueChange = {
//                itemName = it
//                itemModel = itemModel.copy(
//                    name = it
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputRequire(
//            modifier = Modifier.fillMaxWidth(),
//            text = itemPriceText,
//            label = "Unit Price $",
//            placeholder = "Enter unit price",
//            keyboardType = KeyboardType.Decimal,
//            onValueChange = {
//                itemPriceText = it
//                itemPrice = it.stringToDouble()
//                itemModel = itemModel.copy(
//                    price = itemPrice
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputNormal(
//            modifier = Modifier.fillMaxWidth(),
//            keyboardType = KeyboardType.Number,
//            label = "Discount",
//            text = itemDiscountText,
//            onValueChange = {
//                itemDiscountText = it
//                itemDiscount = it.stringToInt()
//                itemModel = itemModel.copy(
//                    discount = itemDiscount
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputNormal(
//            modifier = Modifier.fillMaxWidth(),
//            label = "Description",
//            text = itemDesc,
//            onValueChange = {
//                itemDesc = it
//                itemModel = itemModel.copy(
//                    description = it
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Mood",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "Hot",
//                checked = itemModel.mood?.find { it.option == "hot" } != null,
//                price = itemModel.mood?.find { it.option == "hot" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        mood = itemModel.mood.run {
//                            val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "hot" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "mood", option = "hot", image = R.drawable.ic_fire, price = 0.0))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.mood?.find { it.option == "hot" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "Ice",
//                checked = itemModel.mood?.find { it.option == "ice" } != null,
//                price = itemModel.mood?.find { it.option == "ice" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        mood = itemModel.mood.run {
//                            val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "ice" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "mood", option = "ice", image = R.drawable.ic_ice, price = 0.0))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.mood?.find { it.option == "ice" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Text(
//            text = "Size",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "S",
//                checked = itemModel.size?.find { it.option == "S" } != null,
//                price = itemModel.size?.find { it.option == "S" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        size = itemModel.size.run {
//                            val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "S" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "S"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.size?.find { it.option == "S" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "M",
//                checked = itemModel.size?.find { it.option == "M" } != null,
//                price = itemModel.size?.find { it.option == "M" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        size = itemModel.size.run {
//                            val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "M" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "M"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.size?.find { it.option == "M" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "L",
//                checked = itemModel.size?.find { it.option == "L" } != null,
//                price = itemModel.size?.find { it.option == "L" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        size = itemModel.size.run {
//                            val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "L" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "L"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.size?.find { it.option == "L" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Text(
//            text = "Sugar",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "0%",
//                checked = itemModel.sugar?.find { it.option == "0%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "0%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "0%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "20%",
//                checked = itemModel.sugar?.find { it.option == "20%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "20%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "20%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "50%",
//                checked = itemModel.sugar?.find { it.option == "50%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "50%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "50%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "70%",
//                checked = itemModel.sugar?.find { it.option == "70%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "70%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "70%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "100%",
//                hasNoPrice = true,
//                checked = itemModel.sugar?.find { it.option == "100%" } != null,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "100%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "100%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Text(
//            text = "Ice",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "No",
//                hasNoPrice = true,
//                checked = itemModel.ice?.find { it.option == "No" } != null,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "ice", option = "No"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.ice?.find { it.option == "No" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "Less",
//                checked = itemModel.ice?.find { it.option == "Less" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "ice", option = "Less"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.ice?.find { it.option == "Less" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "Normal",
//                checked = itemModel.ice?.find { it.option == "Normal" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "ice", option = "Normal"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.ice?.find { it.option == "Normal" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//    }
//}
//
//@Preview(showBackground = true, device = Devices.DEFAULT)
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun InputItemView(
//    modifier: Modifier = Modifier,
//    menu: MenuModel? = null,
//    item: ItemModel? = null,
//    onRequire: (Boolean) -> Unit = {},
//    onItemChange: (ItemModel) -> Unit = {}
//){
//    var itemModel by rememberSaveable { mutableStateOf(ItemModel()) }
//
//    fun checkRequire(){
//        onRequire(itemModel.imageUrl.isNullOrEmpty()
//                || itemModel.name.isNullOrEmpty()
//                || itemModel.price != null
//        )
//    }
//
//    LaunchedEffect(item){
//        itemModel = itemModel.copy(name = item?.name)
//    }
//
//    LaunchedEffect(itemModel){
//        onItemChange(itemModel)
//        checkRequire()
//    }
//
//    Column(
//        modifier
//            .fillMaxHeight()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//            .padding(bottom = 30.dp)
//    ) {
//        FlowRow(
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//        ){
//            PhotoLabelSelectorRequire(
//                label = "Photo",
//                photoPath = itemModel.imageUrl?: "",
//                onPhotoSelected = {
//                    itemModel = itemModel.copy(
//                        imageUrl = it
//                    )
//                }
//            )
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            Box(modifier = Modifier.weight(1f)){
//                LabelInputNormal(
//                    modifier = Modifier.fillMaxWidth(),
//                    keyboardType = KeyboardType.Number,
//                    text = menu?.name?: "",
//                    label = "Menu",
//                    enabled = false
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputRequire(
//            modifier = Modifier.fillMaxWidth(),
//            text = itemModel.name?: "",
//            label = "Item Name",
//            placeholder = "Enter name",
//            onValueChange = {
//                itemModel = itemModel.copy(
//                    name = it
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputRequire(
//            modifier = Modifier.fillMaxWidth(),
//            text = itemModel.price?.toString()?: "",
//            label = "Unit Price $",
//            placeholder = "Enter unit price",
//            keyboardType = KeyboardType.Decimal,
//            onValueChange = {
//                itemModel = itemModel.copy(
//                    price = it.stringToDouble()
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputNormal(
//            modifier = Modifier.fillMaxWidth(),
//            keyboardType = KeyboardType.Number,
//            label = "Discount",
//            text = itemModel.discount?.toString()?: "",
//            onValueChange = {
//                itemModel = itemModel.copy(
//                    discount = it.stringToInt()
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        LabelInputNormal(
//            modifier = Modifier.fillMaxWidth(),
//            label = "Description",
//            text = itemModel.description?.toString()?: "",
//            onValueChange = {
//                itemModel = itemModel.copy(
//                    description = it
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Mood",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "Hot",
//                checked = itemModel.mood?.find { it.option == "hot" } != null,
//                price = itemModel.mood?.find { it.option == "hot" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        mood = itemModel.mood.run {
//                            val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "hot" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "mood", option = "hot", image = R.drawable.ic_fire, price = 0.0))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.mood?.find { it.option == "hot" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "Ice",
//                checked = itemModel.mood?.find { it.option == "ice" } != null,
//                price = itemModel.mood?.find { it.option == "ice" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        mood = itemModel.mood.run {
//                            val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "ice" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "mood", option = "ice", image = R.drawable.ic_ice, price = 0.0))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            mood = itemModel.mood.run {
//                                val moods = itemModel.mood?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.mood?.find { it.option == "ice" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Text(
//            text = "Size",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "S",
//                checked = itemModel.size?.find { it.option == "S" } != null,
//                price = itemModel.size?.find { it.option == "S" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        size = itemModel.size.run {
//                            val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "S" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "S"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.size?.find { it.option == "S" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "M",
//                checked = itemModel.size?.find { it.option == "M" } != null,
//                price = itemModel.size?.find { it.option == "M" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        size = itemModel.size.run {
//                            val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "M" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "M"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.size?.find { it.option == "M" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "L",
//                checked = itemModel.size?.find { it.option == "L" } != null,
//                price = itemModel.size?.find { it.option == "L" }?.price?: 0.0,
//                onPriceChange = { price ->
//                    itemModel = itemModel.copy(
//                        size = itemModel.size.run {
//                            val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                            moods.find { it.option == "L" }?.price = price
//                            moods
//                        }
//                    )
//                },
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "L"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            size = itemModel.size.run {
//                                val moods = itemModel.size?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.size?.find { it.option == "L" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Text(
//            text = "Sugar",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "0%",
//                checked = itemModel.sugar?.find { it.option == "0%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "0%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "0%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "20%",
//                checked = itemModel.sugar?.find { it.option == "20%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "20%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "20%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "50%",
//                checked = itemModel.sugar?.find { it.option == "50%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "50%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "50%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "70%",
//                checked = itemModel.sugar?.find { it.option == "70%" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "70%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "70%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "100%",
//                hasNoPrice = true,
//                checked = itemModel.sugar?.find { it.option == "100%" } != null,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "size", option = "100%"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            sugar = itemModel.sugar.run {
//                                val moods = itemModel.sugar?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.sugar?.find { it.option == "100%" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Text(
//            text = "Ice",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//        )
//
//        Column(Modifier.padding(10.dp)) {
//            OptionCheckBox(
//                title = "No",
//                hasNoPrice = true,
//                checked = itemModel.ice?.find { it.option == "No" } != null,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "ice", option = "No"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.ice?.find { it.option == "No" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "Less",
//                checked = itemModel.ice?.find { it.option == "Less" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "ice", option = "Less"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.ice?.find { it.option == "Less" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            OptionCheckBox(
//                title = "Normal",
//                checked = itemModel.ice?.find { it.option == "Normal" } != null,
//                hasNoPrice = true,
//                onCheckedChange = { checked ->
//                    if(checked){
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.add(ItemOption(type = "ice", option = "Normal"))
//                                moods
//                            }
//                        )
//                    }else{
//                        itemModel = itemModel.copy(
//                            ice = itemModel.ice.run {
//                                val moods = itemModel.ice?.toMutableList()?: arrayListOf()
//                                moods.remove(itemModel.ice?.find { it.option == "Normal" })
//                                moods
//                            }
//                        )
//                    }
//                }
//            )
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun OptionCheckBox(
//    modifier: Modifier = Modifier,
//    title: String? = "",
//    checked: Boolean = false,
//    price: Double = 0.0,
//    hasNoPrice: Boolean? = false,
//    onCheckedChange: (Boolean) -> Unit = {},
//    onPriceChange: (Double) -> Unit = {},
//){
//    var check by rememberSaveable {
//        mutableStateOf(checked)
//    }
//
//    var priceText by rememberSaveable { mutableStateOf(price.toString()) }
//
//    Row(
//        modifier =  modifier
//    ) {
//        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false){
//            Checkbox(
//                modifier =  Modifier.align(Alignment.CenterVertically),
//                colors = CheckboxDefaults.colors(
//                    checkedColor = Primary
//                ),
//                checked = check,
//                onCheckedChange = {
//                    check = it
//                    onCheckedChange(it)
//                }
//            )
//        }
//        Spacer(modifier = Modifier.width(10.dp))
//
//        Text(
//            modifier = Modifier.align(Alignment.CenterVertically),
//            text = title?: "",
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Normal,
//                color = Black
//            )
//        )
//
//        Spacer(modifier = Modifier.width(10.dp))
//
//        AnimatedVisibility(visible = check && hasNoPrice == false) {
//            TextInputNormal(
//                keyboardType = KeyboardType.Decimal,
//                text = priceText,
//                placeholder = "Enter price",
//                onValueChange = {
//                    priceText = it
//                    onPriceChange(it.stringToDouble())
//                }
//            )
//        }
//    }
//}