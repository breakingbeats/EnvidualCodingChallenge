import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kVision)
    //alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqldelight)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    jvm {  }

    js(IR) {
        browser {
            commonWebpackConfig(Action {
                cssSupport {
                    enabled.set(true)
                }
            })
            runTask(Action {
                mainOutputFileName.set("main.bundle.js")
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableMapOf(
                        "/kv/*" to "http://localhost:8080",
                        "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    ),
                    static = mutableListOf("$buildDir/processedResources/js/main")
                )
            })
            webpackTask(Action {
                mainOutputFileName.set("main.bundle.js")
            })
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime.core)
                implementation(libs.kotlinx.serialization.json)

                implementation(libs.ktor.client.core)
                implementation(libs.sqldelight.coroutines)

                implementation(compose.runtime)
            }
        }
        val mobileCommonMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.animation)
                implementation(compose.material3)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.contentNegotiation)
                implementation(libs.ktor.server.serialization)
                implementation(libs.ktor.client.cio)

                implementation(libs.sqldelight.jvmDriver)
            }
        }
        val androidMain by getting {
            dependsOn(mobileCommonMain)
            dependencies {
                implementation(libs.ktor.client.cio)
                implementation(libs.sqldelight.androidDriver)
            }
        }
        val iosMain by getting {
            dependsOn(mobileCommonMain)
            dependencies {
                implementation(libs.ktor.client.cio)
                implementation(libs.sqldelight.nativeDriver)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
                implementation(libs.sqldelight.jsDriver)
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))

                implementation(libs.kvision.core)
                implementation(libs.kvision.bootstrap)
            }
        }

    }
}

sqldelight {
    databases {
        create("CommonDatabase") {
            packageName.set("de.hergt.envidualcodingchallenge")
            generateAsync.set(true)
        }
    }
}

android {
    namespace = "de.hergt.envidualcodingchallenge"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}

//js frontend as static asset
tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    //val jsBrowserDistribution = tasks.named("jsBrowserDevelopmentExecutableDistribution")
    from(jsBrowserDistribution)
}
