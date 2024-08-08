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
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.mvrlrd.chat_settings.di.SettingsComponent
import ru.mvrlrd.core_api.mediators.AppWithFacade


@Composable
fun SettingsScreen(action: ()-> Unit) {

    val providersFacade = (LocalContext.current.applicationContext as AppWithFacade).getFacade()
    val settingsComponent = remember {
        SettingsComponent.create(providersFacade)
    }
    val saveSettingsUseCase = remember {
        settingsComponent.provideSaveSettingsUseCase()
    }

    val viewModel : SettingsViewModel = viewModel(
        factory = SettingsViewModel.SettingsViewModelFactory(
            saveSettingsUseCase = saveSettingsUseCase
        )

    )

    val state = viewModel.state.collectAsState()

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
        Divider()
        EditableSettingItem(
            icon = R.drawable.baseline_medication_24,
            title = "role",
            field = state.value.systemRole,
            placeholder = "role"
        ) {
            viewModel.updateRole(it)
        }
        Divider()
        EditableSettingItem(
            icon = R.drawable.baseline_medication_24,
            title = "maxTokens",
            field = state.value.maxTokens.value,
            placeholder = "max tokens 10 .. 2000"
        ) {
            viewModel.updateMaxTokens(it)
        }
        Divider()
        StreamSwitch(state.value.stream){
            viewModel.updateStreamSwitch(it)
        }

        Divider()
       TemperatureSettingItem(temperature = state.value.temperature,){
           viewModel.updateTemperature(it)
       }
        // Setting Option 2: Privacy


        Divider()
        Box(modifier = Modifier
            .fillMaxSize()

            .background(Color.Blue)){
            Button(onClick = {
                             viewModel.saveChatSettings()
                            action.invoke()
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),

                ) {
                Text(text = "submit".uppercase())
            }

        }


    }
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
                style = MaterialTheme.typography.h6,
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
                Text(text = "stream", style = MaterialTheme.typography.h6)
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = { updateStream(it) }
        )
    }
}



//    val completionOptions: CompletionOptions = CompletionOptions(),




//data class CompletionOptions(
//    val stream: Boolean = false,
//    val temperature: Double = 0.6,
//    val maxTokens: Int = 2000
//)

@Composable
fun EditableSettingItem(icon: Int, title: String, field: String, placeholder: String, updateText: (String) -> Unit) {
//    var text by remember { mutableStateOf("") }

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
                    .background(MaterialTheme.colors.surface)
            )
        }
    }
}

//            {

//                viewModel.createChat(
//                    Chat(
//                        chatId = 0,
//                        title = "lalaland",
//                        roleText = "ты пяти летний ребенок",
//                        modelVer = "",
//                        completionOptions = CompletionOptions(
//                            stream = false,
//                            temperature = 0.9,
//                            maxTokens = 500
//                        ),
//                        date = Date().time
//                    )
//                )
//                viewModel.getAllChats()
//            }

@Preview
@Composable
fun chatSettingsScreenPreview(){
    SettingsScreen({})
}