plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    id ("androidx.navigation.safeargs")

}

android {
    namespace = "com.example.dailydash"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dailydash"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.1")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.11.0")
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.glide)
    implementation(libs.circleimageview)
    implementation(libs.rxandroid)
    implementation(libs.lottie)
    implementation(libs.material)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.rxandroid.v302)
    implementation(libs.firebase.auth)
    implementation(libs.rxjava.v315)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation (libs.navigation.fragment)
    implementation (libs.navigation.ui)
    implementation(libs.constraintlayout)
    implementation(libs.legacy.support.v4)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)

     implementation( libs.adapter.rxjava3)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    implementation (libs.room.rxjava3)
    implementation (libs.room.guava)
}