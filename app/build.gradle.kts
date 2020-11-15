plugins {
    id("com.android.application")
    kotlin("android")
    id("base-android-config")
    kotlin("kapt")
}

repositories {
    google()
    jcenter()
}

android {
    defaultConfig {
        applicationId = "com.pal.populartv"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {

    network()
    di()

    implementation(project(":core"))
    implementation(project(":database"))
    implementation(project(":features:populartv"))
}
