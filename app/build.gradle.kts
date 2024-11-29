plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("androidx.room") version "2.6.1" apply false
}

android {
    namespace = "com.example.tyme"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tyme"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val lifecycleVersion = "2.8.7"
    val koinVersion = "4.0.0"
    val retrofitVersion = "2.11.0"
    val navVersion = "2.8.3"
    val roomVersion = "2.6.1"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    //koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")
    //glide
    implementation("com.github.bumptech.glide:glide:4.13.0")
    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    //room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    //Testing
    testImplementation("org.mockito:mockito-core:2.11.0")
    testImplementation("android.arch.core:core-testing:1.1.1")
    testImplementation("com.google.truth:truth:1.1")

}