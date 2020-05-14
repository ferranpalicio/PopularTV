/*import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies*/

object DefaultConfig {
    const val minSdk = 21
    const val targetSdk = 28
    const val compileSdk = 28
}

object Versions {

    const val kotlinVersion = "1.3.72"

    const val jetpackVersion = "1.0.0"
    const val ktxVersion = "1.0.1"
    const val constraintLayoutVersion = "1.1.3"

    const val lifecycleVersion = "2.1.0-beta01"

    const val retrofitVersion = "2.6.0"
    const val loggingInterceptorVersion = "3.10.0"
    const val gsonVersion = "2.3.0"
    const val coroutinesAdapterVersion = "0.9.2"

    const val roomVersion = "2.1.0"

    const val picassoVersion = "2.5.2"

    const val daggerVersion = "2.22.1"

    const val kotlinCoroutines = "1.2.1"

    const val junitVersion = "4.12"
    const val mockitoKotlinVersion = "2.1.0"
    const val archCoreTestingVersion = "2.0.1"
}

object Deps {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpackVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxVersion}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.jetpackVersion}"
    const val material = "com.google.android.material:material:${Versions.jetpackVersion}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptorVersion}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.gsonVersion}"
    const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapterVersion}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.picassoVersion}"

    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"

    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomCoroutines = "androidx.room:room-ktx:${Versions.roomVersion}"
}

object TestDeps {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTestingVersion}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
    const val roomTest = "androidx.room:room-testing:${Versions.roomVersion}"
}