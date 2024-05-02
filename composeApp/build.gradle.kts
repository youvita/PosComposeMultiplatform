plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
//    alias(libs.plugins.devToolsKsp)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.nativeCocoapods)
//    kotlin("native.cocoapods")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0"
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = false
        }

//        pod("Printer") {
//            packageName = "print"
//            source = git("https://github.com/KevinGong2013/Printer.git") {
//                branch = "refactor"
//            }
//            extraOpts += listOf("-compiler-option", "-fmodules")
//        }
    }
//    cocoapods {
//        version = "1.0"
//        summary = "Some description for a Kotlin/Native module"
//        homepage = "Link to a Kotlin/Native module homepage"
//        ios.deploymentTarget = "14.1"
//        podfile = project.file("../ios/Podfile")
//        framework {
//            baseName = "shared"
//            isStatic = false
//        }
//    }
    
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "ComposeApp"
//            isStatic = true
//        }
//    }
    
    sourceSets {

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat)

            implementation(libs.koin.android)

            // sql
            implementation(libs.sqlDelight.android)

            implementation(libs.printer.android)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

//            implementation(libs.androidx.constraintlayout)
            implementation(libs.constraintlayout.compose.multiplatform)

            // sql
            implementation(libs.sqlDelight.runtime)
            implementation(libs.sqlDelight.coroutine)
            implementation(libs.primitive.adapters)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.koin)

            implementation(libs.stately.common)

            implementation(libs.kamel.image)

            implementation(libs.share.preference.no.arg)

            implementation(libs.kotlinx.datetime)

            implementation(libs.bluetooth)

            implementation(libs.moko.permission)

            implementation(libs.qr.scanner)

        }

        iosMain.dependencies {
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            // sql
            implementation(libs.sqlDelight.native)
        }

    }
}

android {
    namespace = "org.topteam.pos"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.topteam.pos"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        prefab = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.material3.android)
}

sqldelight {
    databases {
        create("PosDatabase") {
            packageName.set("org.topteam.pos")
        }
    }
}

