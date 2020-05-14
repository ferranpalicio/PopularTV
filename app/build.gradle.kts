
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
    compileSdkVersion(DefaultConfig.compileSdk)
    defaultConfig {
        applicationId = "com.pal.populartv"
        minSdkVersion(DefaultConfig.minSdk)
        targetSdkVersion(DefaultConfig.targetSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(Deps.kotlinStdLib)

    implementation(Deps.appCompat)
    implementation(Deps.ktxCore)
    implementation(Deps.constraintLayout)
    implementation(Deps.appCompat)
    implementation(Deps.fragment)
    implementation(Deps.material)
    implementation(Deps.lifecycle)
    implementation(Deps.viewmodel)

    implementation(Deps.retrofit)
    implementation(Deps.loggingInterceptor)
    implementation(Deps.gson)
    implementation(Deps.coroutinesAdapter)

    implementation(Deps.room)
    kapt(Deps.roomCompiler)
    implementation(Deps.roomCoroutines)

    implementation(Deps.picasso)

    implementation(Deps.dagger)
    /*implementation("androidx.legacy:legacy-support-v4:1.0.0")*/
    kapt(Deps.daggerCompiler)

    implementation(Deps.kotlinCoroutines)
    implementation(Deps.kotlinCoroutinesAndroid)

    testImplementation(TestDeps.junit)
    testImplementation(TestDeps.mockitoKotlin)
    testImplementation(TestDeps.archCoreTesting)
    testImplementation(TestDeps.coroutinesTest)
}
