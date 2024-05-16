package mario.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.theme.labelTextStyle
import core.utils.DialogAlert
import core.utils.LabelInputRequire
import menu.domain.model.MenuModel
import org.jetbrains.compose.resources.ExperimentalResourceApi


@Composable
fun CreateMenu(
    onCreate: (MenuModel, Boolean) -> Unit = { _, _ -> },
){
    MenuView(
        onCreate = { menu, required ->
            onCreate(menu, required)
        }
    )
}

@Composable
fun EditMenu(
    menuModel: MenuModel? = MenuModel(),
    onUpdate: (MenuModel, Boolean) -> Unit = { _, _ -> },
    onDelete: (MenuModel) -> Unit = {},
){
    MenuView(
        isUpdate = true,
        menu = menuModel,
        onCreate = { menu, required ->
            onUpdate(menu, required)
        },
        onDelete = { onDelete(it) }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class,
    ExperimentalFoundationApi::class
)
@Composable
private fun MenuView(
    isUpdate: Boolean = false,
    menu: MenuModel? = MenuModel(),
    onCreate: (MenuModel, Boolean) -> Unit = { _, _ -> },
    onDelete: (MenuModel) -> Unit = {},
){

    var required by remember { mutableStateOf(true) }
    var deleteConfirm by remember { mutableStateOf(false) }
    var menuModel by remember { mutableStateOf(menu?: MenuModel()) }

    var name by remember { mutableStateOf(menuModel.name) }
    var byteArrayImage by remember { mutableStateOf(menuModel.image) }
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

    LaunchedEffect(name){
        required = name.isNullOrEmpty()
        if(byteArrayImage != null && byteArrayImage!!.isNotEmpty()){
            image = byteArrayImage!!.toImageBitmap()
        }
    }

    if(deleteConfirm){
        DialogAlert(
            title = "Delete Confirmation",
            message = "Are your sure to delete menu?\n[All items inside this menu will move to other category]",
            buttonText = "Delete",
            onDismissRequest = { deleteConfirm = false },
            onButtonClick = {
                deleteConfirm = false
                onDelete(menuModel)
            }
        )
    }

    Column(Modifier.padding(16.dp)) {
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ){
            Column {
                Text(
                    text = "Photo",
                    style = labelTextStyle
                )

                Spacer(modifier = Modifier.height(8.dp))
                PhotoSelectorView (
                    photoPath = image
                ){
                    singleImagePicker.launch()
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(modifier = Modifier.weight(1f)){
                LabelInputRequire(
                    modifier = Modifier.fillMaxWidth(),
                    text = name ?: "",
                    label = "Menu Name",
                    placeholder = "Enter name",
                    onValueChange = {
                        name = it
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ){
            if(isUpdate){
                OutlinedButton(
                    onClick = {
                        deleteConfirm = true
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Red,
                        contentColor = White
                    ),
                    border = null,
                    shape = Shapes.medium,
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                ) {
                    Text(
                        text = "Delete",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
            }


            OutlinedButton(
                onClick = {
                    menuModel = menuModel.copy(
                        name = name,
                        image = byteArrayImage
                    )
                    onCreate(menuModel, required)
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = PrimaryColor,
                    contentColor = White
                ),
                border = null,
                shape = Shapes.medium,
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            ) {
                Text(
                    text = if(isUpdate) "Update" else "Create",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}
