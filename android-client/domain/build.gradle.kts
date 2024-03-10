plugins {
    id(libs.plugins.kotlin.library.get().pluginId)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.inject)
    implementation(libs.kotlinx.coroutinesCore)
}
