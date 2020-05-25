import org.gradle.api.artifacts.dsl.DependencyHandler

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
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val urlconnection = "com.squareup.okhttp3:okhttp-urlconnection:${Versions.okhttpVersion}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.gsonVersion}"
    const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapterVersion}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.picassoVersion}"

    const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"

    const val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomCoroutines = "androidx.room:room-ktx:${Versions.roomVersion}"
}

object TestDeps {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val mockitoKotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTestingVersion}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
    const val roomTest = "androidx.room:room-testing:${Versions.roomVersion}"
}

fun DependencyHandler.network() {
    add("implementation", Deps.retrofit)
    add("implementation", Deps.loggingInterceptor)
    add("implementation", Deps.gson)
    add("implementation", Deps.coroutinesAdapter)
}

fun DependencyHandler.db() {
    add("implementation", Deps.room)
    add("kapt", Deps.roomCompiler)
    add("implementation", Deps.roomCoroutines)

}

fun DependencyHandler.di() {
    add("implementation", Deps.dagger)
    add("kapt", Deps.daggerCompiler)
}

fun DependencyHandler.coroutines() {
    add("implementation", Deps.kotlinCoroutines)
    add("implementation", Deps.kotlinCoroutinesAndroid)
}

fun DependencyHandler.unitTest() {
    add("testImplementation", TestDeps.junit)
    add("testImplementation", TestDeps.mockitoKotlin)
    add("testImplementation", TestDeps.archCoreTesting)
    add("testImplementation", TestDeps.coroutinesTest)
}
