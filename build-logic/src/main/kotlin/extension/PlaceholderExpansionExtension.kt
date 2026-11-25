/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Archer (KnightOfTheBow)
 * SPDX-License-Identifier: MIT
 */

package extension

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.SourceSet
import javax.inject.Inject

abstract class PlaceholderExpansionExtension @Inject constructor(
    private val providers: ProviderFactory
) {
    abstract val files: ListProperty<String>
    abstract val properties: MapProperty<String, String>

    fun addFiles(sourceSet: NamedDomainObjectProvider<SourceSet>, vararg paths: String) {
        files.addAll(sourceSet.map { set -> paths.map { "${set.processResourcesTaskName}/$it" } })
    }

    fun fromGradleProperties(vararg keys: String) {
        keys.forEach { key ->  properties.put(key, providers.gradleProperty(key)) }
    }
}

fun Project.placeholderExpansion(configure: Action<PlaceholderExpansionExtension>) {
    extensions.configure(PlaceholderExpansionExtension::class.java, configure)
}