// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // plugins -->
    alias(libs.plugins.devtools.ksp) apply  false
    alias(libs.plugins.dagger.hilt.plugin) apply false
//    alias(libs.plugins.kotlin.serialization) apply false
}