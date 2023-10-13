plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
}

android {

    signingConfigs {
        create("release") {
            if (project.hasProperty("MYAPP_RELEASE_STORE_FILE")) {
                storeFile = file(project.findProperty("MYAPP_RELEASE_STORE_FILE"))
                storePassword = project.findProperty("MYAPP_RELEASE_STORE_PASSWORD") as String
                keyAlias = project.findProperty("MYAPP_RELEASE_KEY_ALIAS") as String
                keyPassword = project.findProperty("MYAPP_RELEASE_KEY_PASSWORD") as String
            }
        }
    }


    namespace = "jp.thinkdifferent.devsurface"
    compileSdk = 34

    defaultConfig {
        applicationId = "jp.thinkdifferent.devsurface"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    viewBinding {
        enable = true
    }


}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")


    //Storage
    implementation ("io.github.pilgr:paperdb:2.7.2")


    //Second Storage
    implementation ("androidx.room:room-runtime:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")

    //di
    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt ("com.google.dagger:hilt-compiler:2.46.1")

    //Track
    implementation ("com.onesignal:OneSignal:[4.0.0, 4.99.99]")
    implementation ("com.appsflyer:af-android-sdk:6.12.0")
    implementation ("com.facebook.android:facebook-android-sdk:16.1.3")


}