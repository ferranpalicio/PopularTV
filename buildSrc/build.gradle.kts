import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("base-android-config") {
            id = "base-android-config"
            implementationClass = "AndroidBaseConfigPlugin"
        }
    }
}
// Required since Gradle 4.10+.
repositories {
    google()
    mavenCentral()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())

    implementation("com.android.tools.build:gradle:3.6.2")
    //https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-gradle-plugin
    implementation(kotlin("gradle-plugin", "1.3.72"))//should be the same than Versions.kotlinVersion?
    implementation(kotlin("android-extensions"))

}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}