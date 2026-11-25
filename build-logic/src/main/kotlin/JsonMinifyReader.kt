/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Archer (KnightOfTheBow)
 * SPDX-License-Identifier: MIT
 */

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.io.FilterReader
import java.io.Reader
import java.io.StringReader

class JsonMinifyReader(reader: Reader) : FilterReader(
    StringReader(JsonOutput.toJson(JSON_SLURPER.get().parse(reader)))
) {
    private companion object {
        val JSON_SLURPER: ThreadLocal<JsonSlurper> = ThreadLocal.withInitial(::JsonSlurper)
    }
}