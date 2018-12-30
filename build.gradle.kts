plugins {
  id("fabric-loom") version "0.2.0-SNAPSHOT"
}

base {
  archivesBaseName = "loadingspice"
}

group = "therealfarfetchd.loadingspice"
version = "1.0.0"

minecraft {
}

dependencies {
  minecraft("com.mojang:minecraft:18w50a")
  mappings("net.fabricmc:yarn:18w50a.75")
  modCompile("net.fabricmc:fabric-loader:0.3.1.80")

  // Fabric API. This is technically optional, but you probably want it anyway.
  modCompile("net.fabricmc:fabric:0.1.3.68")
}
