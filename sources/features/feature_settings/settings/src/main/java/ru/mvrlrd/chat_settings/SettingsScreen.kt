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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mvrlrd.base_chat_home.model.composables.ShowToast
import ru.mvrlrd.chat_settings.di.SettingsComponent
import ru.mvrlrd.core_api.mediators.AppWithFacade


@Composable
fun SettingsScreen(chatId: Long, action: () -> Unit) {
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

    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModel.SettingsViewModelFactory(
            getChatSettingsUseCase = getChatSettingsUseCase,
            saveSettingsUseCase = saveSettingsUseCase,
            chatId = chatId
        )

    )
    val state = viewModel.state.collectAsState()

    val oneShotEvent = remember {
        viewModel.channel.receiveAsFlow()
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        EditableSettingItem(
            field = state.value.name,
            placeholder = stringResource(R.string.ed_name_placeholder)
        ) {
            viewModel.updateName(it)
        }
        HorizontalDivider()
        EditableSettingItem(
            field = state.value.systemRole,
            placeholder = stringResource(R.string.ed_role_placeholder)
        ) {
            viewModel.updateRole(it)
        }
        HorizontalDivider()
        EditableSettingItem(
            field = state.value.maxTokens.value,
            placeholder = stringResource(R.string.ed_max_tokens_placeholder)
        ) {
            viewModel.updateMaxTokens(it)
        }
        HorizontalDivider()
        SwitchItem(icon = R.drawable.baseline_quickreply_24,
            text = stringResource(R.string.prompt),
            isChecked = state.value.prompt) {
            viewModel.updatePromptSwitch(it)
        }
        HorizontalDivider()
        SwitchItem(
            icon = R.drawable.baseline_subject_24,
            text = stringResource(R.string.stream),
            isChecked = state.value.stream) {
            viewModel.updateStreamSwitch(it)
        }

        HorizontalDivider()
        TemperatureSettingItem(temperature = state.value.temperature) {
            viewModel.updateTemperature(it)
        }
//        HorizontalDivider()
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    viewModel.saveChatSettings(chatId = chatId) {
                        action()
                    }
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = if (state.value.name.isBlank()) Color.Red else MaterialTheme.colorScheme.secondary,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                    ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),

                ) {
                Text(
                    text = stringResource(R.string.submit).uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }
    }
    ShowToast(flow = oneShotEvent)
}

@Composable
fun TemperatureSettingItem(temperature: Temperature, updateTemperature: (Float) -> Unit) {

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
                painter = painterResource(id = R.drawable.baseline_device_thermostat_24),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.temperature),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
        Slider(
            value = temperature.value,
            onValueChange = { updateTemperature(it) },
            valueRange = 0.0f..1.0f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                thumbColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
fun SwitchItem(icon: Int, text: String, isChecked: Boolean, updateStream: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)

        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            }
        }
        Switch(
            colors = SwitchDefaults.colors().copy(
                checkedTrackColor = MaterialTheme.colorScheme.secondary
            ),
            checked = isChecked,
            onCheckedChange = { updateStream(it) }
        )
    }
}

@Composable
fun EditableSettingItem(
    field: String,
    placeholder: String,
    updateText: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            TextField(
colors = TextFieldDefaults.colors().copy(
    unfocusedContainerColor = Color.Transparent,
    cursorColor = MaterialTheme.colorScheme.secondary,

    )
        ,
                value = field,
                onValueChange = {
                    updateText(it)
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = { Text(text = placeholder, style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.surface
                ) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}