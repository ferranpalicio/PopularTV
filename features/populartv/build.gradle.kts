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
    kotlinOptions {
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
        /*javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.incremental" to "true")
            }
        }*/
    }
}

dependencies {
    implementation(Deps.constraintLayout)
    implementation(Deps.material)
    implementation(Deps.lifecycle)
    implementation(Deps.viewmodel)

    implementation(Deps.retrofit)
    implementation(Deps.gson)
    db()
    di()
    coroutines()

    implementation(Deps.picasso)

    implementation(project(":core"))
}
