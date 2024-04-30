package customer.presentation

import customer.domain.model.CustomerModel
import core.data.Status

data class CustomerState(
    val status: Status? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val customers: List<CustomerModel>? = null
)