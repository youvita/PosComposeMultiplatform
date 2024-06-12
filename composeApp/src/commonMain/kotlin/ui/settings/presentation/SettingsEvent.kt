package ui.settings.presentation

sealed class SettingsEvent {
    class AddPreference(val preferId: Int, val preferItem: String) : SettingsEvent()
    class UpdatePreference(val preferId: Int, val preferItem: String) : SettingsEvent()
    class GetPreference : SettingsEvent()
}