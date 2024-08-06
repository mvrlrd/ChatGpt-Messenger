package ru.mvrlrd.home

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import ru.mvrlrd.core_api.database.chat.entity.Message
import ru.mvrlrd.home.domain.api.ClearMessagesUseCase
import ru.mvrlrd.home.domain.api.DeleteMessageUseCase
import ru.mvrlrd.home.domain.api.GetAllMessagesForChatUseCase
import ru.mvrlrd.home.domain.api.GetAnswerUseCase
import ru.mvrlrd.home.domain.api.SaveMessageToChatUseCase

class HomeViewModelTest {
    private var getAnswerUseCase: GetAnswerUseCase = mock()
    private var saveMessageToChatUseCase: SaveMessageToChatUseCase = mock()
    private var getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase = mock()
    private var deleteMessageUseCase: DeleteMessageUseCase = mock()
    private var clearMessagesUseCase: ClearMessagesUseCase = mock()
    private val chatId: Long = 1L
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(
            getAnswerUseCase,
            saveMessageToChatUseCase,
            getAllMessagesForChatUseCase,
            deleteMessageUseCase,
            clearMessagesUseCase,
            testDispatcher,
            chatId
        ).apply {
            oneShotEventChannel = mock()
        }
    }


    @Test
    fun `test onSuccess when getAnswerUseCase succeed`() {
        runTest(testDispatcher) {
            val expectedMessage = Message(
                holderChatId = chatId,
                text = "не знаю",
                isReceived = true,
                date = 1L
            )
            whenever(getAnswerUseCase.invoke(query = "столица Монголии?")).thenReturn(
                Result.success(
                    HomeTestUnitFactory.getAIResponse().copy(answer = "не знаю", date = 1L)
                )
            )
            viewModel.sendRequest(query = "столица Монголии?")
            testDispatcher.scheduler.advanceUntilIdle()
            verify(saveMessageToChatUseCase).invoke(expectedMessage)
        }
    }
    @Test
    fun `test onFailure when getAnswerUseCase fails`() = runTest(testDispatcher) {
        val expectedThrowable = IllegalArgumentException("oops")
        whenever(getAnswerUseCase.invoke(systemRole = "", query = "failure request"))
            .thenReturn(Result.failure(expectedThrowable))
        viewModel.sendRequest(query = "failure request")
        testDispatcher.scheduler.advanceUntilIdle()
        verify(viewModel.oneShotEventChannel).send("сообщение не загружено ${expectedThrowable.message}")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun after(){
        Dispatchers.resetMain()
    }
}