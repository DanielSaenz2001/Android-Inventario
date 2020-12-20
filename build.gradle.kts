// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.10")
    //ext.kotlin_version="1.4.10"
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
        //
    }
    dependencies {
        classpath(Dependencies.gradle)
        classpath(Dependencies.kotlinGradle)
        classpath(Dependencies.daggerHilt)
        classpath(Dependencies.ktLint)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete (rootProject.buildDir)
}