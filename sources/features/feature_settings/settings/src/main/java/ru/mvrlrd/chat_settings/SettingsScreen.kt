package ru.mvrlrd.chat_settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mvrlrd.base_chat_home.model.composables.ShowToast
import ru.mvrlrd.chat_settings.di.SettingsComponent
import ru.mvrlrd.core_api.mediators.AppWithFacade


@Composable
fun SettingsScreen(chatId: Long, action: ()-> Unit) {
    val providersFacade = (LocalContext.current.applicationContext as AppWithFacade).getFacade()
    val settingsComponent = remember {
        SettingsComponent.create(providersFacade)
    }
    val saveSettingsUseCase = remember {
        settingsComponent.provideSaveSettingsUseCase()
    }
    val getChatSettingsUseCase = remember {
        settingsComponent.provideGetChatSettingsUseCase()
    }

    val viewModel : SettingsViewModel = viewModel(
        factory = SettingsViewModel.SettingsViewModelFactory(
            getChatSettingsUseCase= getChatSettingsUseCase,
            saveSettingsUseCase = saveSettingsUseCase,
            chatId = chatId
        )

    )
    val state = viewModel.state.collectAsState()

    val oneShotEvent = remember {
        viewModel.channel.receiveAsFlow()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        EditableSettingItem(icon = R.drawable.baseline_medication_24,
            title = "name",
            field = state.value.name,
            placeholder = "name"
        ) {
            viewModel.updateName(it)
        }
        HorizontalDivider()
        EditableSettingItem(
            icon = R.drawable.baseline_medication_24,
            title = "role",
            field = state.value.systemRole,
            placeholder = "role"
        ) {
            viewModel.updateRole(it)
        }
        HorizontalDivider()
        EditableSettingItem(
            icon = R.drawable.baseline_medication_24,
            title = "maxTokens",
            field = state.value.maxTokens.value,
            placeholder = "max tokens 10 .. 2000"
        ) {
            viewModel.updateMaxTokens(it)
        }
        HorizontalDivider()
        StreamSwitch(state.value.stream){
            viewModel.updateStreamSwitch(it)
        }

        HorizontalDivider()
       TemperatureSettingItem(temperature = state.value.temperature,){
           viewModel.updateTemperature(it)
       }
        HorizontalDivider()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Button(
                onClick = {
                    viewModel.saveChatSettings(chatId= chatId){
                        action()
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),

                ) {
                Text(text = "submit".uppercase())
            }

        }
    }
    ShowToast(flow = oneShotEvent)
}

@Composable
fun TemperatureSettingItem(temperature: Temperature, updateTemperature: (Float)-> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_medication_24),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "temperature",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )
        }
        Slider(
            value = temperature.value,
            onValueChange = { updateTemperature(it) },
            valueRange = 0.0f..1.0f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun StreamSwitch(isChecked: Boolean, updateStream: (Boolean)-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id =  R.drawable.baseline_medication_24),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "stream", style = MaterialTheme.typography.titleSmall)
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = { updateStream(it) }
        )
    }
}

@Composable
fun EditableSettingItem(icon: Int, title: String, field: String, placeholder: String, updateText: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {

            TextField(
                value = field,
                onValueChange = {
                                updateText(it)
                                },
                placeholder = { Text(placeholder) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}