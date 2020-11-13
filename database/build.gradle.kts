plugins {
    id("com.android.library")
    id("base-android-config")
    kotlin("android")
    kotlin("kapt")
}

repositories {
    google()
    jcenter()
}

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)

    db()
}
