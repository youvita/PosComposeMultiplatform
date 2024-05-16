package mario.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.utils.LabelInputNormal
import core.utils.LabelInputRequire
import core.utils.TextRequire
import menu.domain.model.MenuModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import setting.domain.model.ItemModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputItemData(
    modifier: Modifier = Modifier,
    menu: MenuModel? = null,
    oldItem: ItemModel? = null,
    onRequire: (Boolean) -> Unit = {},
    onItemChange: (ItemModel) -> Unit = {}
) {
    var itemModel by remember { mutableStateOf(ItemModel(menuId = menu?.menuId)) }
    var itemName by remember { mutableStateOf(oldItem?.name?:"") }
    var itemCode by remember { mutableStateOf(oldItem?.itemCode?:0L) }
    var itemDesc by remember { mutableStateOf(oldItem?.description?:"") }
    var itemPrice by remember { mutableDoubleStateOf(oldItem?.price?:0.0) }
    var itemQty by remember { mutableIntStateOf(oldItem?.qty?:0) }

    var byteArrayImage by remember { mutableStateOf(menu?.image) }
    var image by remember { mutableStateOf(ImageBitmap(1,1)) }

    val scope = rememberCoroutineScope()

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                // Process the selected images' ByteArrays.
                byteArrays.firstOrNull()?.let {byteArrays ->

                    itemModel = itemModel.copy(
                        image_product = it
                    )

                    byteArrayImage = byteArrays
                    image = byteArrays.toImageBitmap()
                }

            }
        }
    )

    fun checkRequire(){
        onRequire(byteArrayImage?.isEmpty() == true || itemName.isEmpty() || itemPrice.toString().isEmpty())
    }

    LaunchedEffect(itemModel){
        onItemChange(itemModel)
        checkRequire()
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 30.dp)
    ) {
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ){

            //photo picker and preview
            Column {
                TextRequire("Photo")
                Spacer(modifier = Modifier.height(8.dp))
                PhotoSelectorView (
                    photoPath = image
                ){
                    singleImagePicker.launch()
                }
            }
            Spacer(modifier = Modifier.width(16.dp))

            Box(modifier = Modifier.weight(1f)){
                LabelInputNormal(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Number,
                    text = menu?.name?: "",
                    label = "Menu",
                    enabled = false
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputRequire(
            modifier = Modifier.fillMaxWidth(),
            text = itemName,
            label = "Item Name",
            placeholder = "Enter name",
            onValueChange = {
                itemName = it
                itemModel = itemModel.copy(
                    name = it
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputRequire(
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Number,
            text = if (itemCode == 0L) "" else itemCode.toString(),
            label = "Item Code",
            placeholder = "Enter Item Code",
            onValueChange = { value->
                itemCode = if (value.isNotEmpty()) value.toLong() else 0L
                itemModel = itemModel.copy(
                    itemCode = itemCode
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputRequire(
            modifier = Modifier.fillMaxWidth(),
            text = if (itemPrice == 0.0) "" else itemPrice.toString(),
            label = "Unit Price $",
            placeholder = "Enter unit price",
            keyboardType = KeyboardType.Decimal,
            onValueChange = {
                itemPrice = it.toDouble()
                itemModel = itemModel.copy(
                    price = itemPrice
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputRequire(
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Number,
            label = "Qty",
            placeholder = "Enter Qty",
            text = if (itemQty == 0) "" else itemQty.toString(),
            onValueChange = {value->
                itemQty = if (value.isNotEmpty()) value.toInt() else 0
                itemModel = itemModel.copy(
                    qty = itemQty
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputNormal(
            modifier = Modifier.fillMaxWidth(),
            label = "Description",
            text = itemDesc,
            onValueChange = {
                itemDesc = it
                itemModel = itemModel.copy(
                    description = it
                )
            }
        )
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
fun InputItemView(
    modifier: Modifier = Modifier,
    menu: MenuModel? = null,
    item: ItemModel? = null,
    onRequire: (Boolean) -> Unit = {},
    onItemChange: (ItemModel) -> Unit = {}
){
    var itemModel by remember { mutableStateOf(ItemModel()) }
    var byteArrayImage by remember { mutableStateOf(menu?.image) }
    var image by remember { mutableStateOf(ImageBitmap(1,1)) }

    val scope = rememberCoroutineScope()

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                // Process the selected images' ByteArrays.
                byteArrays.firstOrNull()?.let {byteArrays ->
                    byteArrayImage = byteArrays
                    image = byteArrays.toImageBitmap()
                }

            }
        }
    )

    fun checkRequire(){
        onRequire(itemModel.imageUrl.isNullOrEmpty()
                || itemModel.name.isNullOrEmpty()
                || itemModel.price != null
        )
    }

    LaunchedEffect(item){
        itemModel = itemModel.copy(name = item?.name)
    }

    LaunchedEffect(itemModel){
        onItemChange(itemModel)
        checkRequire()
    }

    Column(
        modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 30.dp)
    ) {
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ){

            PhotoSelectorView (
                photoPath = image
            ){
                singleImagePicker.launch()
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(modifier = Modifier.weight(1f)){
                LabelInputNormal(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardType = KeyboardType.Number,
                    text = menu?.name?: "",
                    label = "Menu",
                    enabled = false
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputRequire(
            modifier = Modifier.fillMaxWidth(),
            text = itemModel.name?: "",
            label = "Item Name",
            placeholder = "Enter name",
            onValueChange = {
                itemModel = itemModel.copy(
                    name = it
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputRequire(
            modifier = Modifier.fillMaxWidth(),
            text = itemModel.price?.toString()?: "",
            label = "Unit Price $",
            placeholder = "Enter unit price",
            keyboardType = KeyboardType.Decimal,
            onValueChange = {
                itemModel = itemModel.copy(
                    price = it.toDouble()
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputNormal(
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Number,
            label = "Discount",
            text = itemModel.discount?.toString()?: "",
            onValueChange = {
                itemModel = itemModel.copy(
                    discount = it.toInt()
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        LabelInputNormal(
            modifier = Modifier.fillMaxWidth(),
            label = "Description",
            text = itemModel.description?.toString()?: "",
            onValueChange = {
                itemModel = itemModel.copy(
                    description = it
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}


