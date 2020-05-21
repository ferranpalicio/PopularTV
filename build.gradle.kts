// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*
todo
plugins {
    id("io.gitlab.arturbosch.detekt").version("1.8.0")
    id("com.github.ben-manes.versions").version("0.28.0")
}*/

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
