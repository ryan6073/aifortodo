plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    kotlin("kapt")
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigationSafeArgs)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.detekt)
}

if (file("google-services.json").exists()) {
    apply(plugin = libs.plugins.firebaseCrashlytics.get().pluginId)
    apply(plugin = libs.plugins.googleServices.get().pluginId)
}

android {
    namespace = "com.intern.aifortodo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.intern.aifortodo"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildTypes {
        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            extra["enableCrashlytics"] = false
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(fileTree("libs") { include(listOf("*.jar")) })
    implementation("pub.devrel:easypermissions:3.0.0")
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.coreKtx)
    implementation(libs.androidx.core.coreSplashScreen)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.fragment.fragmentKtx)
    implementation(libs.androidx.lifecycle.livedataKtx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.lifecycle.viewmodelKtx)
    implementation(libs.androidx.navigation.navigationFragmentKtx)
    implementation(libs.androidx.navigation.navigationUiKtx)
    implementation(libs.androidx.paging.pagingRuntimeKtx)
    implementation(libs.androidx.room.roomKtx)
    implementation(libs.androidx.room.roomRuntime)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    ksp(libs.androidx.room.roomCompiler)

    implementation(libs.google.dagger.hiltAndroid)
    implementation(platform(libs.google.firebase.firebaseBom))
    implementation(libs.google.firebase.firebaseAnalyticsKtx)
    implementation(libs.google.firebase.firebaseCrashlyticsKtx)
    ksp(libs.google.dagger.hiltAndroidCompiler)
    implementation(libs.google.material)
    implementation(libs.google.playServicesOssLicenses)
    implementation(libs.timber)
    implementation(libs.companion)

    // 添加 Retrofit 相关依赖
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)

    testImplementation(libs.androidx.test.coreKtx)
    testImplementation(libs.androidx.test.ext.junitKtx)
    testImplementation(libs.google.dagger.hiltAndroidTesting)
    kspTest(libs.google.dagger.hiltAndroidCompiler)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.mockk)
    testImplementation(libs.robolectric.robolectric)

    androidTestImplementation(libs.androidx.arch.coreTesting)
    androidTestImplementation(libs.androidx.room.roomTesting)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espressoCore)
    androidTestImplementation(libs.google.dagger.hiltAndroidTesting)
    kspAndroidTest(libs.google.dagger.hiltAndroidCompiler)
}
