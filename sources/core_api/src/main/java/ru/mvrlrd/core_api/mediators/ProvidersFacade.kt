package ru.mvrlrd.core_api.mediators

import ru.mvrlrd.core_api.database.answer.DatabaseProvider
import ru.mvrlrd.core_api.database.chat.ChatDatabaseProvider
import ru.mvrlrd.core_api.network.NetworkClientProvider

interface ProvidersFacade : NetworkClientProvider, AppProvider, DatabaseProvider, ChatDatabaseProvider