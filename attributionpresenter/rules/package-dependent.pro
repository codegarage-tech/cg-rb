# Nine Old Android
# This is due to ObjectAnimator using reflection to access get/sets
-keep class tech.codegarage.recyclebin.ClassThatUsesObjectAnimator { *; }

# Litepal
# Ensures entities remain un-obfuscated so table and columns are named correctly
# GSON
# Application classes that will be serialized/deserialized over Gson
-keep class tech.codegarage.recyclebin.model.** { *; }
-keepnames class tech.codegarage.recyclebin.model.** { *; }

# Keep the BuildConfig
-keep class tech.codegarage.recyclebin.en.debug.BuildConfig { *; }
-keep class tech.codegarage.recyclebin.en.BuildConfig { *; }

# Realm
# Additionally you need to keep your Realm Model classes as well
 -keep class tech.codegarage.recyclebin.model.** { *; }