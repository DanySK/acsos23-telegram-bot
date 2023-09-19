package org.danilopianini.acsos23

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.ConfigSpec
import com.uchuhimo.konf.Feature
import java.io.File
import java.net.URI
import kotlin.time.Duration.Companion.hours
import kotlin.time.TimeSource

object Committees {
    data class Person(
        val FullName: String,
        val FirstName: String,
        val LastName: String,
        val Affiliation: String,
        val Country: String?,
        val ProfileURL: String,
        val DisplayPosition: Int?,
        val roles: List<Map<String, String>>,
    )

    data class Committee(
        val CommitteeName: String,
        val ContextType: String,
        val ContextName: String,
        val Conference: String,
        val Members: List<Person>,
    )

    object People : ConfigSpec() {
        val committees by required<List<Committee>>()
    }

    private val file = File.createTempFile("acsos23", "json")
    private val url =
        URI("https://2023.acsos.org/dataexport/02ea9823-aed3-4fc0-8f1f-117ff63ee167/committee-data.json/false").toURL()
    private var lastUpdate = TimeSource.Monotonic.markNow().also { readData() }

    private fun readData() = file.writeText("""{ "people": ${url.readText()} }""")
    private val committees = Config {
        addSpec(People)
        disable(Feature.FAIL_ON_UNKNOWN_PATH)
    }.from.json.file(file)

    fun committes() = committees[People.committees].also {
        if (lastUpdate.elapsedNow() > 1.hours) {
            lastUpdate = TimeSource.Monotonic.markNow()
            readData()
        }
    }
}
