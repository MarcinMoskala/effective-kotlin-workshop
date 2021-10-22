package anki

import anki.AnkiProgressBar.Size.Small
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SynchronizeCardsUseCaseMockTest {

    @Test
    fun `should synchronize cards`() = runBlockingTest {
        // given
        val view = mockk<AnkiView>(relaxed = true)
        val cards = listOf(AnkiCard("A", "B"), AnkiCard("C", "D"))
        val cardsRepo = mockk<AnkiCardsRepository>(relaxed = true)
        val networkRepo = mockk<AnkiNetworkRepository>()
        coEvery { networkRepo.fetchCards() } returns cards
        val useCase = SynchronizeCardsUseCase(view, networkRepo, cardsRepo)

        // when
        useCase.start()

        // then
        coVerify {
            cardsRepo.updateCards(cards)
        }
    }

    @Test
    fun `should show progress bar`() = runBlockingTest {
        // given
        val view = mockk<AnkiView>(relaxed = true)
        val cards = listOf(AnkiCard("A", "B"), AnkiCard("C", "D"))
        val cardsRepo = mockk<AnkiCardsRepository>(relaxed = true)
        val networkRepo = mockk<AnkiNetworkRepository>()
        coEvery { networkRepo.fetchCards() } returns cards
        val useCase = SynchronizeCardsUseCase(view, networkRepo, cardsRepo)

        // when
        useCase.start()

        // then
        coVerifyOrder {
            view.show(AnkiProgressBar(Small))
            cardsRepo.updateCards(cards)
            view.hide(AnkiProgressBar(Small))
        }
    }

    @Test
    fun `should show exceptions`() = runBlockingTest {
        // given
        val view = mockk<AnkiView>(relaxed = true)
        val apiException = AnkiApiException(401, "User not found")
        val cardsRepo = mockk<AnkiCardsRepository>(relaxed = true)
        val networkRepo = mockk<AnkiNetworkRepository>()
        coEvery { networkRepo.fetchCards() } throws apiException
        val useCase = SynchronizeCardsUseCase(view, networkRepo, cardsRepo)

        // when
        useCase.start()

        // then
        val shown = mutableListOf<AnkiDialog>()
        coVerify {
            view.show(capture(shown))
        }
        val dialog = shown.last()
        assertEquals("Cards synchronization exception", dialog.title)
        assertEquals("User not found", dialog.text)
        assertEquals(AnkiDialog.Button("OK"), dialog.okButton)
        assertEquals(null, dialog.cancelButton)
    }
}
