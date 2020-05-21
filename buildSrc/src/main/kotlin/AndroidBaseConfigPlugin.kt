import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//https://medium.com/wantedly-engineering/managing-android-multi-module-project-with-gradle-plugin-and-kotlin-4fcc126e7e49
class AndroidBaseConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Apply Required Plugins.
        project.plugins.apply("kotlin-android")
        project.plugins.apply("kotlin-android-extensions")

        // Configure common android build parameters.
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            androidExtension.apply {
                //default config
                compileSdkVersion(DefaultConfig.compileSdk)
                defaultConfig {
                    targetSdkVersion(DefaultConfig.targetSdk)
                    minSdkVersion(DefaultConfig.minSdk)

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                // Configure common proguard file settings.
                val proguardFile = "proguard-rules.pro"
                when (this) {
                    is LibraryExtension -> defaultConfig {
                        consumerProguardFiles(proguardFile)
                    }
                    is AppExtension -> buildTypes {
                        getByName("release") {
                            isMinifyEnabled = true
                            isShrinkResources = true
                            isDebuggable = false
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                proguardFile
                            )
                        }
                        getByName("debug") {
                            isDebuggable = true
                        }
                    }
                }

                // Java 8
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
                project.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = "1.8"
                    }
                }
            }
        }

        // Adds required dependencies for all modules.
        project.dependencies {
            add("implementation", Deps.kotlinStdLib)
            add("implementation", Deps.appCompat)
            add("implementation", Deps.ktxCore)
            //add("implementation", Deps.fragment)
        }

    }
}