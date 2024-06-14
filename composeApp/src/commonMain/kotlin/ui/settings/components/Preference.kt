package ui.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import core.app.convertToObject
import core.app.convertToString
import core.data.Status
import core.theme.Black
import core.theme.ColorD9D9D9
import core.theme.ColorDDE3F9
import core.theme.ColorE4E4E4
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.theme.fontSizeTitle
import core.theme.labelTextStyle
import core.utils.Constants
import core.utils.LabelInputNormal
import core.utils.LabelInputRequire
import core.utils.LabelSelectInput
import core.utils.Toggle
import core.utils.countingSequence
import core.utils.dateFormatList
import core.utils.vats
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_upload
import ui.settings.domain.model.ExchangeRateData
import ui.settings.domain.model.InvoiceData
import ui.settings.domain.model.InvoiceFooterData
import ui.settings.domain.model.InvoiceSealData
import ui.settings.domain.model.PaymentData
import ui.settings.domain.model.QueueData
import ui.settings.domain.model.SavePointData
import ui.settings.domain.model.ShopData
import ui.settings.domain.model.VatData
import ui.settings.domain.model.WifiData
import ui.settings.presentation.PreferState
import ui.settings.presentation.SettingsEvent

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
fun Preference(
    state: PreferState? = null,
    onEvent: (SettingsEvent) -> Unit = {}
) {

    var preview by rememberSaveable { mutableStateOf(false) }
    var required by rememberSaveable { mutableStateOf(false) }

    var hasBillHeader by rememberSaveable { mutableStateOf(false) }
    var hasWifi by rememberSaveable { mutableStateOf(false) }
    var hasInvoiceNo by rememberSaveable { mutableStateOf(false) }
    var hasExchangeRate by rememberSaveable { mutableStateOf(false) }
    var hasStartEndTime by rememberSaveable { mutableStateOf(false) }
    var hasTableNo by rememberSaveable { mutableStateOf(false) }
    var hasCustomer by rememberSaveable { mutableStateOf(false) }
    var hasVat by rememberSaveable { mutableStateOf(false) }
    var hasSavePoint by rememberSaveable { mutableStateOf(false) }
    var hasPaymentMethod by rememberSaveable { mutableStateOf(false) }
    var hasCompanySeal by rememberSaveable { mutableStateOf(false) }
    var hasQueueNumber by rememberSaveable { mutableStateOf(false) }
    var hasInvoiceFooter by rememberSaveable { mutableStateOf(false) }

    var shopLogo by rememberSaveable { mutableStateOf(ByteArray(0)) }
    var shopAddress by rememberSaveable { mutableStateOf("") }
    var shopNameEN by rememberSaveable { mutableStateOf("") }
    var shopNameKH by rememberSaveable { mutableStateOf("") }
    var shopTaxId by rememberSaveable { mutableStateOf("") }
    var indexIssueDateFormat by rememberSaveable { mutableIntStateOf(0) }
    var indexCountingSequence by rememberSaveable { mutableIntStateOf(0) }
    var rateKHR by rememberSaveable { mutableDoubleStateOf(4000.0) }
    var vat by rememberSaveable { mutableIntStateOf(vats[0].toInt()) }
    var amtUsdExchange by rememberSaveable { mutableDoubleStateOf(0.0) }
    var point by rememberSaveable { mutableIntStateOf(0) }
    var imageKHQR by rememberSaveable { mutableStateOf(ByteArray(0)) }
    var bankName by rememberSaveable { mutableStateOf("") }
    var accountNo by rememberSaveable { mutableStateOf("") }
    var accountName by rememberSaveable { mutableStateOf("") }
    var signatureImage by rememberSaveable { mutableStateOf(ByteArray(0)) }
    var wifiPassword by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }
    var indexQueueNumber by rememberSaveable { mutableIntStateOf(0) }


    val isLoading by rememberUpdatedState(newValue = state?.isLoading)
    LaunchedEffect(isLoading) {
        if (state?.status == Status.SUCCESS) {

            // shop header
            val shopPrefer = state.data?.find { it.preferId == Constants.PreferenceType.SHOP_HEADER }
            shopPrefer?.preferItem?.let { prefer ->
                val shop = convertToObject<ShopData>(prefer)
                shop.shopLogo?.let {
                    shopLogo = it
                }
                hasBillHeader = shop.isUsed
                shopNameKH = shop.shopNameKhmer.orEmpty()
                shopNameEN = shop.shopNameEnglish.orEmpty()
                shopAddress = shop.shopAddress.orEmpty()
                shopTaxId = shop.shopTaxId.orEmpty()
            }

            // invoice no
            val invoicePrefer = state.data?.find { it.preferId == Constants.PreferenceType.INVOICE_NO }
            invoicePrefer?.preferItem?.let { prefer ->
                val invoice = convertToObject<InvoiceData>(prefer)
                hasInvoiceNo = invoice.isUsed
                indexIssueDateFormat = invoice.dateFormat ?: 0
                indexCountingSequence = invoice.countingSequence ?: 0
            }

            // exchange rate
            val exchangeRatePrefer = state.data?.find { it.preferId == Constants.PreferenceType.EXCHANGE_RATE }
            exchangeRatePrefer?.preferItem?.let { prefer ->
                val exchangeRate = convertToObject<ExchangeRateData>(prefer)
                hasExchangeRate = exchangeRate.isUsed
                rateKHR = exchangeRate.rateKHR ?: 0.0
            }

            // taxation
            val vatPrefer = state.data?.find { it.preferId == Constants.PreferenceType.VAT }
            vatPrefer?.preferItem?.let { prefer ->
                val vatData = convertToObject<VatData>(prefer)
                hasVat = vatData.isUsed
                vat = vatData.taxValue ?: 0
            }

            // customer save point
            val savePointPrefer = state.data?.find { it.preferId == Constants.PreferenceType.SAVE_POINT }
            savePointPrefer?.preferItem?.let { prefer ->
                val savePoint = convertToObject<SavePointData>(prefer)
                hasSavePoint = savePoint.isUsed
                amtUsdExchange = savePoint.amtUsdExchange ?: 0.0
            }

            // payment method
            val paymentPrefer = state.data?.find { it.preferId == Constants.PreferenceType.PAYMENT_METHOD }
            paymentPrefer?.preferItem?.let { prefer ->
                val payment = convertToObject<PaymentData>(prefer)
                hasPaymentMethod = payment.isUsed
                payment.imageKHQR?.let {
                    imageKHQR = it
                }
                bankName = payment.bankName.orEmpty()
                accountNo = payment.accountNumber.orEmpty()
                accountName = payment.accountName.orEmpty()
            }

            // company seal invoice
            val sealPrefer = state.data?.find { it.preferId == Constants.PreferenceType.INVOICE_SEAL }
            sealPrefer?.preferItem?.let { prefer ->
                val seal = convertToObject<InvoiceSealData>(prefer)
                hasCompanySeal = seal.isUsed
                seal.sellerSignature?.let {
                    signatureImage = it
                }
            }

            // wifi
            val wifiPrefer = state.data?.find { it.preferId == Constants.PreferenceType.WIFI }
            wifiPrefer?.preferItem?.let { prefer ->
                val wifi = convertToObject<WifiData>(prefer)
                hasWifi = wifi.isUsed
                wifiPassword = wifi.password.orEmpty()
            }

            // queue number
            val queuePrefer = state.data?.find { it.preferId == Constants.PreferenceType.QUEUE }
            queuePrefer?.preferItem?.let { prefer ->
                val queue = convertToObject<QueueData>(prefer)
                hasQueueNumber = queue.isUsed
                indexQueueNumber = queue.queueNumber ?: 0
            }

            // invoice footer
            val footerPrefer = state.data?.find { it.preferId == Constants.PreferenceType.FOOTER }
            footerPrefer?.preferItem?.let { prefer ->
                val footer = convertToObject<InvoiceFooterData>(prefer)
                hasInvoiceFooter = footer.isUsed
                note = footer.note.orEmpty()
            }

        }
    }


    val scope = rememberCoroutineScope()
    val shopLogoPicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = {
            it.firstOrNull()?.let { image ->
                shopLogo = image
            }
        }
    )

    val paymentPicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = {
            it.firstOrNull()?.let { image ->
                imageKHQR = image
            }
        }
    )

    val signaturePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = {
            it.firstOrNull()?.let { image ->
                signatureImage = image
            }
        }
    )

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = "Preferences",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

//            OutlinedButton(
//                onClick = {
//                    preview = !preview
////                    settingEvent(SettingEvent.PreviewSettingEvent(
////                        PreferenceModel(
////                            hasBillHeader = hasBillHeader,
////                            shopLogo = shopLogo,
////                            shopNameKH = shopNameKH,
////                            shopNameEN = shopNameEN,
////                            shopAddress = shopAddress,
////                            hasInvoiceNo = hasInvoiceNo,
////                            indexIssueDateFormat = indexIssueDateFormat,
////                            indexCountingSequence = indexCountingSequence,
////                            hasExchangeRate = hasExchangeRate,
////                            hasStartEndTime = hasStartEndTime,
////                            hasTableNo = hasTableNo,
////                            hasCustomer = hasCustomer,
////                            hasVat = hasVat,
////                            hasSavePoint = hasSavePoint,
////                            hasPaymentMethod = hasPaymentMethod,
////                            rateKHR = rateKHR,
////                            vat = vat,
////                            amtUsdExchange = amtUsdExchange,
////                            point = point,
////                            imageKHQR = imageKHQR,
////                            bankName = bankName,
////                            accountNo = accountNo,
////                            accountName = accountName,
////                            signatureImage = signatureImage,
////                            hasCompanySeal = hasCompanySeal,
////                            hasWifiPassword = hasWifi,
////                            wifiPassword = wifiPassword,
////                            hasQueueNumber = hasQueueNumber,
////                            indexQueueNumber = indexQueueNumber,
////                            hasInvoiceFooter = hasInvoiceFooter,
////                            note = note
////                        )
////                    ))
//                },
//                colors = ButtonDefaults.elevatedButtonColors(
//                    containerColor = ColorDDE3F9,
//                    contentColor = PrimaryColor
//                ),
//                border = null,
//                shape = Shapes.medium,
//                contentPadding = PaddingValues(10.dp),
//                modifier = Modifier
//                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
//            )  {
//                Text(
//                    text = "Preview",
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Normal
//                    )
//                )
//            }
//
//            Spacer(modifier = Modifier.width(16.dp))

            OutlinedButton(
                onClick = {

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.SHOP_HEADER,
                        preferItem = convertToString(
                            ShopData(
                                isUsed = hasBillHeader,
                                shopLogo = shopLogo,
                                shopNameKhmer = shopNameKH,
                                shopNameEnglish = shopNameEN,
                                shopAddress = shopAddress,
                                shopTaxId = shopTaxId
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.INVOICE_NO,
                        preferItem = convertToString(
                            InvoiceData(
                                isUsed = hasInvoiceNo,
                                dateFormat = indexIssueDateFormat,
                                countingSequence = indexCountingSequence
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.EXCHANGE_RATE,
                        preferItem = convertToString(
                            ExchangeRateData(
                                isUsed = hasExchangeRate,
                                rateKHR = rateKHR
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.VAT,
                        preferItem = convertToString(
                            VatData(
                                isUsed = hasVat,
                                taxValue = vat
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.SAVE_POINT,
                        preferItem = convertToString(
                            SavePointData(
                                isUsed = hasSavePoint,
                                amtUsdExchange = amtUsdExchange,
                                point = point
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.PAYMENT_METHOD,
                        preferItem = convertToString(
                            PaymentData(
                                isUsed = hasPaymentMethod,
                                imageKHQR = imageKHQR,
                                bankName = bankName,
                                accountNumber = accountNo,
                                accountName = accountName
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.INVOICE_SEAL,
                        preferItem = convertToString(
                            InvoiceSealData(
                                isUsed = hasCompanySeal,
                                sellerSignature = signatureImage
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.WIFI,
                        preferItem = convertToString(
                            WifiData(
                                isUsed = hasWifi,
                                password = wifiPassword
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.QUEUE,
                        preferItem = convertToString(
                            QueueData(
                                isUsed = hasQueueNumber,
                                queueNumber = indexQueueNumber
                            )
                        )
                    ))

                    onEvent(SettingsEvent.AddPreference(
                        preferId = Constants.PreferenceType.FOOTER,
                        preferItem = convertToString(
                            InvoiceFooterData(
                                isUsed = hasInvoiceFooter,
                                note = note
                            )
                        )
                    ))
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = PrimaryColor,
                    contentColor = White
                ),
                border = null,
                shape = Shapes.medium,
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            )  {
                Text(
                    text = "Save",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Toggle(
                text = "Display Bill Header",
                checked = hasBillHeader,
                onCheckedChange = { hasBillHeader = it }
            )

            AnimatedVisibility(visible = hasBillHeader){
                Column {
                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ){
                        Box(modifier = Modifier.padding(end = 20.dp)) {
                            Column {
                                Text(text = buildAnnotatedString {
                                    append("Shop Logo")
                                    withStyle(
                                        SpanStyle(
                                            color = Color.Red
                                        )
                                    ) {
                                        append("")
                                        append("*")
                                    }
                                }, style = labelTextStyle)

                                Spacer(modifier = Modifier.height(10.dp))

                                Card(
                                    modifier = Modifier
                                        .size(width = 120.dp, height = 120.dp)
                                        .clip(Shapes.medium)
                                        .clickable {
                                            shopLogoPicker.launch()
                                        },
                                    shape = Shapes.medium,
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    elevation = CardDefaults.cardElevation(2.dp),
                                    border = BorderStroke(1.dp, ColorE4E4E4)
                                ) {
                                    if (!shopLogo.contentEquals(ByteArray(0))) {
                                        Image(
                                            modifier = Modifier.fillMaxSize(),
                                            bitmap = shopLogo.toImageBitmap(),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds
                                        )
                                    } else {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                modifier = Modifier.size(62.dp),
                                                contentDescription = null,
                                                painter = painterResource(resource = Res.drawable.ic_upload)
                                            )

                                            Text(text = "Upload", color = Black, style = labelTextStyle)
                                        }
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            )  {
                                Column(Modifier.weight(1f)) {
                                    LabelInputRequire(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = shopNameKH,
                                        label = "Shop name in Khmer",
                                        placeholder = "Enter name",
                                        onValueChange = { shopNameKH = it }
                                    )
                                }

                                Column(Modifier.weight(1f)) {
                                    LabelInputRequire(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = shopNameEN,
                                        label = "Shop name in English",
                                        placeholder = "Enter name",
                                        onValueChange = { shopNameEN = it }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Column {
                                LabelInputRequire(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = shopAddress,
                                    label = "Shop Address",
                                    placeholder = "Enter address",
                                    onValueChange = { shopAddress = it }
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                LabelInputRequire(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = shopTaxId,
                                    label = "TAX TIN",
                                    placeholder = "Enter TAX TIN",
                                    onValueChange = { shopTaxId = it }
                                )

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    Divider()
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Display Bill Information",
                style = TextStyle(
                    fontSize = fontSizeTitle,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Toggle(
                text = "Invoice No",
                checked = hasInvoiceNo,
                onCheckedChange = { hasInvoiceNo = it }
            )

            AnimatedVisibility(visible = hasInvoiceNo){
                Column {
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    )  {
                        LabelSelectInput(
                            label = "Issue Date Format",
                            enabledInput = false,
                            text = dateFormatList[indexIssueDateFormat],
                            list = dateFormatList,
                            onSelectChange = { indexIssueDateFormat = it }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        LabelSelectInput(
                            label = "Counting Sequence",
                            enabledInput = false,
                            text = countingSequence[indexCountingSequence],
                            list = countingSequence,
                            onSelectChange = { indexCountingSequence = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Toggle(
                text = "Exchange Rate",
                checked = hasExchangeRate,
                onCheckedChange = { hasExchangeRate = it }
            )

            AnimatedVisibility(visible = hasExchangeRate){
                Column {
                    LabelInputRequire(
                        label = "Rate KHR of 1 USD",
                        text = rateKHR.toString(),
                        keyboardType = KeyboardType.Decimal,
                        onValueChange = { rateKHR = it.toDouble() }
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

//            Toggle(
//                text = "Start time & end time",
//                checked = hasStartEndTime,
//                enabled = false,
//                onCheckedChange = { hasStartEndTime = it }
//            )

//            Toggle(
//                text = "Table No",
//                checked = hasTableNo,
//                onCheckedChange = { hasTableNo = it }
//            )
//
//            Toggle(
//                text = "Customer (VAT IN, Name, Address)",
//                checked = hasCustomer,
//                onCheckedChange = { hasCustomer = it }
//            )

            Toggle(
                text = "VAT",
                checked = hasVat,
                onCheckedChange = { hasVat = it}
            )
            AnimatedVisibility(visible = hasVat){
                Column {
                    LabelSelectInput(
                        label = "Taxation (VAT %)",
                        text = vat.toString(),
                        list = vats,
                        keyboardType = KeyboardType.Number,
                        onValueChange = { vat = it.toInt() },
                        onSelectChange = { vat = vats[it].toInt() }
                    )

                    Divider()
                }
            }

            Toggle(
                text = "Display Customer save point",
                checked = hasSavePoint,
                onCheckedChange = { hasSavePoint = it }
            )
            AnimatedVisibility(visible = hasSavePoint){
                Column {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Save Point Settings, ex) 10 USD = 1",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    )  {
                        LabelInputRequire(
                            text = amtUsdExchange.toString(),
                            label = "Amount in USD for Exchange",
                            placeholder = "Enter amount",
                            keyboardType = KeyboardType.Decimal,
                            onValueChange = { amtUsdExchange = it.toDouble() }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        LabelInputRequire(
                            text = point.toString(),
                            label = "To Point Value",
                            placeholder = "Enter value",
                            keyboardType = KeyboardType.Number,
                            onValueChange = { point = it.toInt() }
                        )
                    }

                    Divider()
                }

            }

            Toggle(
                text = "Display Payment Method",
                checked = hasPaymentMethod,
                onCheckedChange = { hasPaymentMethod = it }
            )

            AnimatedVisibility(visible = hasPaymentMethod){
                Column {
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Box(modifier = Modifier.padding(end = 20.dp)){
                            Column {
                                Text(text = buildAnnotatedString {
                                    append("KHQR")
                                    withStyle(
                                        SpanStyle(
                                            color = Color.Red
                                        )
                                    ) {
                                        append("")
                                        append("*")
                                    }
                                }, style = labelTextStyle)

                                Spacer(modifier = Modifier.height(10.dp))

                                Card(
                                    modifier = Modifier
                                        .size(width = 120.dp, height = 120.dp)
                                        .clip(Shapes.medium)
                                        .clickable {
                                            paymentPicker.launch()
                                        },
                                    shape = Shapes.medium,
                                    colors = CardDefaults.cardColors(
                                        containerColor = White
                                    ),
                                    elevation = CardDefaults.cardElevation(2.dp),
                                    border = BorderStroke(1.dp, ColorE4E4E4)
                                ) {
                                    if (!imageKHQR.contentEquals(ByteArray(0))) {
                                        Image(
                                            modifier = Modifier.fillMaxSize(),
                                            bitmap = imageKHQR.toImageBitmap(),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillBounds
                                        )
                                    } else {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                modifier = Modifier.size(62.dp),
                                                contentDescription = null,
                                                painter = painterResource(resource = Res.drawable.ic_upload)
                                            )

                                            Text(text = "Upload", color = Black, style = labelTextStyle)
                                        }
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(1f)
                        ) {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            )  {
                                Column(Modifier.weight(1f)) {
                                    LabelInputRequire(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = bankName,
                                        label = "Bank name",
                                        placeholder = "Enter name",
                                        onValueChange = { bankName = it }
                                    )
                                }

                                Column(Modifier.weight(1f)) {
                                    LabelInputRequire(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = accountNo,
                                        label = "Bank Account No",
                                        placeholder = "Enter no",
                                        keyboardType = KeyboardType.Number,
                                        onValueChange = { accountNo = it },
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Column {
                                LabelInputRequire(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = accountName,
                                    label = "Account Name",
                                    placeholder = "Enter name",
                                    onValueChange = { accountName = it }
                                )
                            }
                        }
                    }

                    Divider()
                }
            }

            Toggle(
                text = "Display company seal on the invoice",
                checked = hasCompanySeal,
                onCheckedChange = { hasCompanySeal = it }
            )

            AnimatedVisibility(visible = hasCompanySeal) {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Column(
                            Modifier
                                .widthIn(max = 200.dp)
                                .padding(end = 20.dp)) {
                            Box(modifier = Modifier.size(120.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(thickness = 1.dp, color = ColorD9D9D9)
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "ហត្ថលេខា និង ឈ្មោះអ្នកទិញ\nBuyer's Signature & Name",
                                style = labelTextStyle,
                                textAlign = TextAlign.Start
                            )
                        }

                        Column(
                            Modifier.widthIn(max = 200.dp).padding(end = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Card(
                                modifier = Modifier
                                    .size(width = 120.dp, height = 120.dp)
                                    .clip(Shapes.medium)
                                    .clickable {
                                        signaturePicker.launch()
                                    },
                                shape = Shapes.medium,
                                colors = CardDefaults.cardColors(
                                    containerColor = White
                                ),
                                elevation = CardDefaults.cardElevation(2.dp),
                                border = BorderStroke(1.dp, ColorE4E4E4)
                            ) {
                                if (!signatureImage.contentEquals(ByteArray(0))) {
                                    Image(
                                        modifier = Modifier.fillMaxSize(),
                                        bitmap = signatureImage.toImageBitmap(),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillBounds
                                    )
                                } else {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            modifier = Modifier.size(62.dp),
                                            contentDescription = null,
                                            painter = painterResource(resource = Res.drawable.ic_upload)
                                        )

                                        Text(text = "Upload", color = Black, style = labelTextStyle)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            HorizontalDivider(thickness = 1.dp, color = ColorD9D9D9)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "ហត្ថលេខា និង ឈ្មោះអ្នកលក់\nSeller's Signature & Name",
                                style = labelTextStyle,
                                textAlign = TextAlign.End
                            )
                        }
                    }

                    Divider()
                }
            }


            Toggle(
                text = "Display Wifi Password",
                checked = hasWifi,
                onCheckedChange = { hasWifi = it }
            )

            AnimatedVisibility(visible = hasWifi){
                Column {
                    LabelInputRequire(
                        text = wifiPassword,
                        label = "Enter Password here",
                        placeholder = "Enter password",
                        onValueChange = { wifiPassword = it }
                    )

                    Divider()
                }
            }

            Toggle(
                text = "Display Queue Number",
                checked = hasQueueNumber,
                onCheckedChange = { hasQueueNumber = it }
            )

            AnimatedVisibility(visible = hasQueueNumber){
                Column {
                    LabelSelectInput(
                        label = "Counting Sequence",
                        enabledInput = false,
                        text = countingSequence[indexQueueNumber],
                        list = countingSequence,
                        onSelectChange = { indexQueueNumber = it }
                    )

                    Divider()
                }
            }

            Toggle(
                text = "Display Invoice Footer",
                checked = hasInvoiceFooter,
                onCheckedChange = { hasInvoiceFooter = it }
            )

            AnimatedVisibility(visible = hasInvoiceFooter) {
                LabelInputNormal(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default,
                    label = "Enter note here",
                    text = note,
                    onValueChange = { note = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}

@Composable
private fun Divider(){
    Spacer(modifier = Modifier.height(10.dp))
    HorizontalDivider(thickness = 1.dp, color = ColorD9D9D9)
    Spacer(modifier = Modifier.height(10.dp))
}