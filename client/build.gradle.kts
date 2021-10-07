import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode

val condition: Boolean = false



group = "thelameres.hospital"
version = "0.0.1-SNAPSHOT"

plugins {
    kotlin("js") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.0")
    implementation(npm("@js-joda/core", "4.0.0"))
    implementation(npm("@js-joda/timezone", "2.6.0"))
    implementation(npm("@popperjs/core", "2.10.2"))
    implementation(npm("bootstrap", "5.1.1"))
    implementation(npm("react", "17.0.2"))
    implementation(npm("react-dom", "17.0.2"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.251-kotlin-1.5.31")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.251-kotlin-1.5.31")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.1-pre.251-kotlin-1.5.31")


}

kotlin {
    js {
        browser {
            webpackTask {
                cssSupport.enabled = true
                mode = Mode.PRODUCTION
            }

            runTask {
                cssSupport.enabled = true
                mode = Mode.DEVELOPMENT
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }
}