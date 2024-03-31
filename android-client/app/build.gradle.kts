import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.ksp)
}

val keystorePropertiesFile: File = rootProject.file("signing.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "ru.hits.hubabank"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.hits.hubabank"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs  {
        getByName("debug") {
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            keyAlias = keystoreProperties.getProperty("keyAlias")
            storePassword = keystoreProperties.getProperty("storePassword")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
        create("release") {
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            keyAlias = keystoreProperties.getProperty("keyAlias")
            storePassword = keystoreProperties.getProperty("storePassword")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        release {
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }

        }

        debug {
            getByName("debug") {
                signingConfig = signingConfigs.getByName("debug")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Android Core
    implementation(libs.core.ktx)

    // Android lifecycle
    implementation(libs.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material)

    // Splash screen
    implementation(libs.core.splashscreen)

    // Navigation
    implementation(libs.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.timber)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(project(":domain"))
    implementation(project(":data"))
}
