package ru.mvrlrd.feature_chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import ru.mvrlrd.core_api.database.chat.entity.MessageEntity
import ru.mvrlrd.feature_chat.ChatViewModel
import ru.mvrlrd.feature_chat.domain.api.ClearMessagesUseCase
import ru.mvrlrd.feature_chat.domain.api.DeleteMessageUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.feature_chat.domain.api.GetAnswerUseCase
import ru.mvrlrd.feature_chat.domain.api.GetChatSettingsUseCase
import ru.mvrlrd.feature_chat.domain.api.SaveMessageToChatUseCase

class HomeViewModelTest {
    private var getAnswerUseCase: GetAnswerUseCase = mock()
    private var saveMessageToChatUseCase: SaveMessageToChatUseCase = mock()
    private var getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase = mock()
    private var deleteMessageUseCase: DeleteMessageUseCase = mock()
    private var clearMessagesUseCase: ClearMessagesUseCase = mock()
    private val getChatSettingsUseCase: GetChatSettingsUseCase = mock()
    private val chatId: Long = 1L
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ChatViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ChatViewModel(
            getAnswerUseCase,
            saveMessageToChatUseCase,
            getAllMessagesForChatUseCase,
            deleteMessageUseCase,
            clearMessagesUseCase,
            testDispatcher,
            getChatSettingsUseCase,
            chatId
        ).apply {
            oneShotEventChannel = mock()
        }
    }

    @Test
    fun `test onSuccess when getAnswerUseCase succeed`() {
        runTest(testDispatcher) {
            val expectedMessageEntity = MessageEntity(
                holderChatId = chatId,
                text = "не знаю",
                isReceived = true,
            )
            whenever(
                getAnswerUseCase.invoke(
                    chatId = chatId,
                    query = "столица Монголии?"
                )
            ).thenReturn(
                Result.success(
                    HomeTestUnitFactory.getAIResponse()
                        .copy(text = "не знаю", date = expectedMessageEntity.date)
                )
            )
            viewModel.sendRequest(query = "столица Монголии?")
            testDispatcher.scheduler.advanceUntilIdle()
            verify(saveMessageToChatUseCase).invoke(expectedMessageEntity)
        }
    }

    @Test
    fun `test onFailure when getAnswerUseCase fails`() = runTest(testDispatcher) {
        val expectedThrowable = IllegalArgumentException("oops")
        whenever(getAnswerUseCase.invoke(chatId = chatId, query = "failure request"))
            .thenReturn(Result.failure(expectedThrowable))
        viewModel.sendRequest(query = "failure request")
        testDispatcher.scheduler.advanceUntilIdle()
        verify(viewModel.oneShotEventChannel).send("сообщение не загружено ${expectedThrowable.message}")
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun after() {
        Dispatchers.resetMain()
    }
}