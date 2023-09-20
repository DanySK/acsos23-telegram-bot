package org.danilopianini.acsos23

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import com.uchuhimo.konf.Feature
import java.io.File
import java.net.URI
import kotlin.time.Duration.Companion.hours
import kotlin.time.TimeSource

@Suppress("ConstructorParameterNaming", "UndocumentedPublicProperty", "UndocumentedPublicClass")
object Program {

    private object Program : ConfigSpec("") {
        val description by required<String>()
        val sponsors by required<String>()
    }

    private val file = File.createTempFile("acsos23-program", "json")
    private val url =
        URI("https://2023.acsos.org/dataexport/02ea9823-aed3-4fc0-8f1f-117ff63ee167/confero.json/false").toURL()
    private var lastUpdate = TimeSource.Monotonic.markNow().also { readData() }

    private fun readData() = file.writeText(url.readText())
    private val program = Config {
        addSpec(Program)
        disable(Feature.FAIL_ON_UNKNOWN_PATH)
    }.from.json.file(file)

    /**
     * Returns the description of the conference.
     */
    fun description() = program[Program.description].alsoUpdate()

    /**
     * Returns the sponsors of the conference.
     */
    fun sponsors() = program[Program.sponsors].alsoUpdate()

    private fun <T> T.alsoUpdate(): T = also {
        if (lastUpdate.elapsedNow() > 1.hours) {
            lastUpdate = TimeSource.Monotonic.markNow()
            readData()
        }
    }
}

/*
fun main() {
    println(Program.sponsors())
}
*/
