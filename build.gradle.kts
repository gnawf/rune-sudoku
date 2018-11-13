plugins {
  application
  kotlin("jvm") version "1.2.61"
  id("com.github.johnrengelman.shadow") version "4.0.2"
}

application {
  mainClassName = "com.gnawf.MainKt"
}

repositories {
  jcenter()
}

dependencies {
  compile(group = "org.jetbrains.kotlin", name = "kotlin-stdlib")
}
