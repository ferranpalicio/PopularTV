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
    /*kotlinOptions {
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }*/
    defaultConfig {
        applicationId = "com.pal.populartv"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {

    network()
    db()
    di()

    unitTest()
    testImplementation(TestDeps.roomTest)

    //implementation(project(":core"))
    implementation(project(":core"))
    implementation(project(":features:populartv"))
}
