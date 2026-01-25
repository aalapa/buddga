# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Hilt
-keepclasseswithmembers class * {
    @dagger.hilt.* <methods>;
}

# Keep Room entities
-keep class com.buddga.data.local.database.entity.** { *; }
