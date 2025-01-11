package ui.settings.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.data.Resource
import core.data.Status
import core.utils.Constants
import core.utils.SharePrefer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.settings.domain.model.PreferenceData
import ui.settings.domain.repository.SettingRepository

data class PreferState(
    var status: Status? = null,
    var isLoading: Boolean? = null,
    var data: List<PreferenceData>? = null
)

class SettingsViewModel(
    private val repository: SettingRepository
): ScreenModel {

    private val _state = MutableStateFlow(PreferState())
    val state: StateFlow<PreferState> = _state.asStateFlow()

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.AddPreference -> {
                val preference = PreferenceData(preferId = event.preferId, preferItem = event.preferItem)
                addPreference(preference)
            }
            is SettingsEvent.UpdatePreference -> {
                val preference = PreferenceData(preferId = event.preferId, preferItem = event.preferItem)
                updatePreference(preference)
            }
            is SettingsEvent.GetPreference -> {
                getPreference()
            }
        }
    }

    private fun addPreference(preference: PreferenceData) {
        screenModelScope.launch {
            repository.addPreference(preference.preferId, preference.preferItem)
        }
    }

    private fun updatePreference(preference: PreferenceData) {
        screenModelScope.launch {
            repository.updatePreference(preference.preferId, preference.preferItem)
        }
    }

    private fun getPreference() {
        screenModelScope.launch {
            repository.getPreference().collect { preference ->
                when (preference) {
                    is Resource.Success -> {
                        // save local preference
                        saveLocalPreference(preference.data)

                        _state.value = _state.value.copy(
                            status = preference.status,
                            data = preference.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun saveLocalPreference(data: List<PreferenceData>?) {
        // shop header
        val shopPrefer = data?.find { it.preferId == Constants.PreferenceType.SHOP_HEADER }
        shopPrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.SHOP_HEADER}", prefer)
        }

        // invoice no
        val invoicePrefer = data?.find { it.preferId == Constants.PreferenceType.INVOICE_NO }
        invoicePrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.INVOICE_NO}", prefer)
        }

        // exchange rate
        val exchangeRatePrefer = data?.find { it.preferId == Constants.PreferenceType.EXCHANGE_RATE }
        exchangeRatePrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.EXCHANGE_RATE}", prefer)
        }

        // taxation
        val vatPrefer = data?.find { it.preferId == Constants.PreferenceType.VAT }
        vatPrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.VAT}", prefer)
        }

        // customer save point
        val savePointPrefer = data?.find { it.preferId == Constants.PreferenceType.SAVE_POINT }
        savePointPrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.SAVE_POINT}", prefer)
        }

        // payment method
        val paymentPrefer = data?.find { it.preferId == Constants.PreferenceType.PAYMENT_METHOD }
        paymentPrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.PAYMENT_METHOD}", prefer)
        }

        // company seal invoice
        val sealPrefer = data?.find { it.preferId == Constants.PreferenceType.INVOICE_SEAL }
        sealPrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.INVOICE_SEAL}", prefer)
        }

        // wifi
        val wifiPrefer = data?.find { it.preferId == Constants.PreferenceType.WIFI }
        wifiPrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.WIFI}", prefer)
        }

        // queue number
        val queuePrefer = data?.find { it.preferId == Constants.PreferenceType.QUEUE }
        queuePrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.QUEUE}", prefer)
        }

        // invoice footer
        val footerPrefer = data?.find { it.preferId == Constants.PreferenceType.FOOTER }
        footerPrefer?.preferItem?.let { prefer ->
            SharePrefer.putPrefer("${Constants.PreferenceType.FOOTER}", prefer)
        }

        // parking fee
        val parkingFeePrefer = data?.find { it.preferId == Constants.PreferenceType.PARKING_FEE }
        parkingFeePrefer?.preferItem?.let { prefer ->
            println(">>>> prefer: $prefer")
            SharePrefer.putPrefer("${Constants.PreferenceType.PARKING_FEE}", prefer)
        }
    }
}
