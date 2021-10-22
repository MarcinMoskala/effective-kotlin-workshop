package design

class SynchronizeCardsUseCase(
    private val view: View,
    private val networkRepository: AnkiNetworkRepository,
    private val cardsRepository: CardsRepository,
) {

    suspend fun start() {
        val progressBar = ProgressBar()
        view.show(progressBar)

        try {
            val cards = networkRepository.fetchCards()
            cardsRepository.updateCards(cards)
        } catch (e: ApiException) {
            view.show(Dialog("Network exception", e.message))
        }

        view.hide(progressBar)
    }
}

class SynchronizeDecksUseCase(
    private val view: View,
    private val networkRepository: AnkiNetworkRepository,
    private val cardsRepository: CardsRepository,
) {

    suspend fun start() {
        val progressBar = ProgressBar()
        view.show(progressBar)

        try {
            val cards = networkRepository.fetchCards()
            cardsRepository.updateCards(cards)
        } catch (e: ApiException) {
            view.show(Dialog("Network exception", e.message))
        }

        view.hide(progressBar)
    }
}

class CheckCardsUseCase(
    private val view: View,
    private val cardsRepository: CardsRepository,
) {

    suspend fun start() {
        val progressBar = ProgressBar()
        view.show(progressBar)

        cardsRepository.correctCards()

        // show success
        view.show(Dialog("Success", ""))

        view.hide(progressBar)
    }
}

interface View {
    fun show(element: ViewElement)
    fun hide(element: ViewElement)
}

interface AnkiNetworkRepository {
    @Throws(ApiException::class)
    suspend fun fetchCards(): List<Card>
}

interface CardsRepository {
    suspend fun updateCards(cards: List<Card>)
    suspend fun correctCards()
}

class Card
class ApiException(val code: Int, override val message: String): Throwable()

sealed class ViewElement
class ProgressBar: ViewElement()
class Dialog(var title: String, var text: String): ViewElement()