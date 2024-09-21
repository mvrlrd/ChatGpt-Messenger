package ru.mvrlrd.chat_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mvrlrd.chat_settings.domain.ChatSettings
import ru.mvrlrd.chat_settings.domain.api.GetChatSettingsUseCase
import ru.mvrlrd.chat_settings.domain.api.SaveSettingsUseCase
import java.lang.NumberFormatException
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getChatSettingsUseCase: GetChatSettingsUseCase,
    private val chatId:Long
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> get() = _state.asStateFlow()
    val channel = Channel<String>()

    init {
        if (chatId!=0L){
            getChatSettings(chatId)
        }

    }

    fun saveChatSettings(chatId: Long, popUp: () -> Unit) {
        with(_state.value) {
            viewModelScope.launch {
                if (name.isBlank()) {
                    channel.send("Введите имя")
                } else {
                    delay(200)
                    val chatSettings = ChatSettings(
                        chatId = chatId,
                        name = name,
                        systemRole = systemRole,
                        maxTokens = maxTokens,
                        prompt= prompt,
                        stream = stream,
                        temperature = temperature
                    )
                    saveSettingsUseCase(chatSettings)
                    popUp()
                }
            }
        }
    }
    private fun getChatSettings(chatId: Long){

        viewModelScope.launch {
            val chatSettings = getChatSettingsUseCase(chatId)
            with(chatSettings){
                _state.value = _state.value.copy(
                    name=name,
                    systemRole=systemRole,
                    maxTokens=maxTokens,
                    prompt = prompt,
                    stream= stream,
                    temperature=temperature
                )
            }
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

    fun updatePromptSwitch(isChecked: Boolean){
        _state.value = _state.value.copy(prompt = isChecked)
    }

    class SettingsViewModelFactory(
        private val saveSettingsUseCase: SaveSettingsUseCase,
        private val getChatSettingsUseCase: GetChatSettingsUseCase,
        private val chatId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(saveSettingsUseCase,
                    getChatSettingsUseCase,
                    chatId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

@JvmInline
value class MaxTokens(val value: String)

@JvmInline
value class Temperature(val value: Float)