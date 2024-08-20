// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    dependencies {
        classpath(libs.gradle)
        classpath(libs.google.services)
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}

plugins {

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

