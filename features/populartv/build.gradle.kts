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
    implementation(Deps.constraintLayout)
    implementation(Deps.material)
    implementation(Deps.lifecycle)
    implementation(Deps.viewmodel)

    network()
    db()
    di()
    coroutines()

    implementation(Deps.picasso)

    implementation(project(":core"))
    implementation(project(":database"))
}
