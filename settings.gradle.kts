pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin2js") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
        }
    }
}
/*
 * This file was generated by the Gradle 'init' task.
 */

rootProject.name = "hospital"
include("client")
include("server")
