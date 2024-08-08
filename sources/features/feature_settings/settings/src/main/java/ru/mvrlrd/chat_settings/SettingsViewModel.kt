package ru.mvrlrd.chat_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.chat_settings.domain.api.SaveSettingsUseCase
import java.lang.NumberFormatException
import javax.inject.Inject

class SettingsViewModel@Inject constructor(private val saveSettingsUseCase: SaveSettingsUseCase): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> get()= _state.asStateFlow()

    fun saveChatSettings(){
        val chatSettings = ChatSettings(
            name = _state.value.name,
            systemRole = _state.value.systemRole,
            maxTokens = _state.value.maxTokens,
            stream = _state.value.stream,
            temperature = _state.value.temperature
        )
        viewModelScope.launch {
            saveSettingsUseCase(chatSettings)
        }
    }
    fun updateName(newName: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(name = newName)
        }
    }

    fun updateRole(newRole: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(systemRole=newRole)
        }
    }

    fun updateMaxTokens(newMaxTokens: String) {
        val maxTokens = if (newMaxTokens.isEmpty()){
            ""
        }else{
            try {
                if (newMaxTokens.toInt()>2000){
                    "2000"
                }else{
                    if (newMaxTokens.toInt()<0){
                        "0"
                    }else{
                        newMaxTokens
                    }
                }

            } catch (e: NumberFormatException) {
                _state.value.maxTokens.value
            }
        }
        _state.value = _state.value.copy(maxTokens = MaxTokens(maxTokens))
    }

    fun updateTemperature(newTemperature: Float){
        _state.value = _state.value.copy(temperature = Temperature(newTemperature))
    }

    fun updateStreamSwitch(isChecked: Boolean){
        _state.value = _state.value.copy(stream = isChecked)
    }

    class SettingsViewModelFactory(
        private val saveSettingsUseCase: SaveSettingsUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(saveSettingsUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

@JvmInline
value class MaxTokens(val value: String)

@JvmInline
value class Temperature(val value: Float)