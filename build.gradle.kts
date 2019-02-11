import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
  id("fabric-loom") version "0.2.0-SNAPSHOT"
}

base {
  archivesBaseName = "loadingspice"
}

group = "therealfarfetchd.loadingspice"
version = "1.1.0"

java {
  sourceCompatibility = VERSION_1_8
  targetCompatibility = VERSION_1_8
}

minecraft {
}

dependencies {
  minecraft("com.mojang:minecraft:19w06a")
  mappings("net.fabricmc:yarn:19w06a.3")
  modCompile("net.fabricmc:fabric-loader:0.3.5.106")

  // Fabric API. This is technically optional, but you probably want it anyway.
  modCompile("net.fabricmc:fabric:0.2.0.90")
}
