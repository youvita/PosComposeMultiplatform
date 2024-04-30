package customer.presentation

import customer.domain.model.CustomerModel

sealed class CustomerEvent {
    data class AddCustomerEvent(val customer: CustomerModel): CustomerEvent()
    object GetAllCustomersEvent: CustomerEvent()
    object ClearEvent: CustomerEvent()
}