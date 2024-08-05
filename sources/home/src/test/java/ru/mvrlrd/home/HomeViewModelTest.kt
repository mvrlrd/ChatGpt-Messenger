package ru.mvrlrd.home

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
     private  var getAnswerUseCase: GetAnswerUseCase = mock()
    private  var saveMessageToChatUseCase: SaveMessageToChatUseCase = mock()
    private  var getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase = mock()
    private  var deleteMessageUseCase: DeleteMessageUseCase = mock()
    private var clearMessagesUseCase: ClearMessagesUseCase = mock()
    private val chatId: Long = 1L
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var  viewModel :HomeViewModel



    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun before(){
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(
                getAnswerUseCase,
        saveMessageToChatUseCase,
        getAllMessagesForChatUseCase,
        deleteMessageUseCase,
        clearMessagesUseCase,
        testDispatcher,
        chatId
        )


    }
    @Test
    fun `test send request`(){
        runTest(testDispatcher) {
            val expetedMessage = Message(
                holderChatId = chatId,
                text = "не знаю",
                isReceived = true,
                date = 1L
            )
                whenever(getAnswerUseCase.invoke(query = "столица Монголии?")).thenReturn(
                    Result.success(HomeTestUnitFactory.getAIResponse().copy(answer = "не знаю"))
                )
            viewModel.sendRequest(query = "столица Монголии?")

            // Advance until all coroutines are completed
            testDispatcher.scheduler.advanceUntilIdle()

            verify(saveMessageToChatUseCase).invoke(expetedMessage)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun after(){
        Dispatchers.resetMain()
    }
}