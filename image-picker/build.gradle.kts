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
    namespace = "com.dre.image_picker"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
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
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
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

    implementation(libs.bundles.common)
    implementation(libs.coil)
    implementation(libs.coilVideo)
    implementation(libs.coilGif)
    implementation(libs.composeAccompanistAnimation)
    implementation(libs.composeAccompanistPermission)
    implementation(libs.composeAccompanistPager)
    implementation(libs.composeAccompanistPagerIndicator)
    implementation(libs.composeMaterial3)

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