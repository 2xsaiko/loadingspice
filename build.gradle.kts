import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
  id("fabric-loom") version "0.2.0-SNAPSHOT"
}

base {
  archivesBaseName = "loadingspice"
}

group = "therealfarfetchd.loadingspice"
version = "1.1.1"

java {
  sourceCompatibility = VERSION_1_8
  targetCompatibility = VERSION_1_8
}

minecraft {
}

dependencies {
  minecraft("com.mojang:minecraft:19w07a")
  mappings("net.fabricmc:yarn:19w07a.6")
  modCompile("net.fabricmc:fabric-loader:0.3.6.107")

  // Fabric API. This is technically optional, but you probably want it anyway.
  modCompile("net.fabricmc:fabric:0.2.1.94")
}
