plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.detekt)
    alias(libs.plugins.junit)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktLint)
}

android {
    namespace = "com.dre.receipt_recognition"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    sourceSets {
        getByName("test") {
            java.srcDir(project(":core").file("src/test/java"))
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":image-picker"))

    implementation(libs.bundles.common)
    implementation(libs.coil)
    implementation(libs.composeAccompanistSwipeRefresh)
    implementation(libs.composeNavigationHilt)
    implementation(libs.kotlinSerialization)
    implementation(libs.retrofit)
    implementation(libs.room)
    testImplementation(libs.bundles.commonTest)
    androidTestImplementation(libs.testAndroidCompose)
    debugImplementation(libs.debugComposeManifest)

    kapt(libs.hiltCompiler)

    detektPlugins(libs.detektTwitterCompose)
}