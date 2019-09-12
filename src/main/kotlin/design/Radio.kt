package design

interface Radio {
    fun start()
    fun stop()
}

fun main() {
//    val radio: Radio = Radio()
//    val sonyRadio: Radio = Radio.sony()
//    val panasonicRadio: Radio = Radio.panasonic()
}

private class SonyRadio(): Radio {
    override fun start() {
        print("Start Sony radio")
    }

    override fun stop() {
        print("Stop Sony radio")
    }
}

private class PanasonicRadio: Radio {
    override fun start() {
        print("Start Sony radio")
    }

    override fun stop() {
        print("Stop Sony radio")
    }
}