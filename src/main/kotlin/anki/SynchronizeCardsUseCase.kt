package anki

import anki.AnkiProgressBar.Size.Small

class SynchronizeCardsUseCase(
    private val view: AnkiView,
    private val networkRepository: AnkiNetworkRepository,
    private val cardsRepository: AnkiCardsRepository,
) {
    private val progressDisplay = ProgressDisplay(view)
    private val errorDisplay = ErrorDisplay(view, "Cards synchronization exception")

    suspend fun start() {
        progressDisplay.showProgress()
        errorDisplay.runWithErrorDisplay {
            val cards = networkRepository.fetchCards()
            cardsRepository.updateCards(cards)
        }
        progressDisplay.hideProgress()
    }
}

class ProgressDisplay(
    private val view: AnkiView
) {
    private var progressBarRef: AnkiProgressBar? = null

    fun showProgress() {
        val ref = AnkiProgressBar(size = Small)
        progressBarRef = ref
        view.show(ref)
    }

    fun hideProgress() {
        progressBarRef?.let { view.hide(it) }
    }
}

class ErrorDisplay(
    private val view: AnkiView,
    private val title: String,
) {
    fun showError(e: Throwable) {
        val dialog = AnkiDialog(
            title = title,
            text = e.message.orEmpty(),
            okButton = AnkiDialog.Button("OK")
        )
        view.show(dialog)
    }

    inline fun runWithErrorDisplay(body: () -> Unit) {
        try {
            body()
        } catch (e: Throwable) {
            showError(e)
        }
    }
}

class SuccessDisplay(
    private val view: AnkiView
) {
    fun showSuccess(title: String) {
        val dialog = AnkiDialog(
            title = "Success",
            text = title,
            okButton = AnkiDialog.Button("OK"),
        )
        view.show(dialog)
    }
}