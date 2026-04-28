pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		exclusiveContent {
			forRepository {
				maven("https://maven.fabricmc.net/") {
					name = "Fabric"
				}
			}
			filter {
				includeGroup("net.fabricmc")
				includeGroup("fabric-loom")
			}
		}
		exclusiveContent {
			forRepository {
				maven ("https://maven.minecraftforge.net") {
					name = "Forge"
				}
			}
			filter {
				includeGroupAndSubgroups("net.minecraftforge")
			}
		}
		exclusiveContent {
			forRepository {
				maven ("https://repo.spongepowered.org/repository/maven-public") {
					name = "Sponge"
				}
			}
			filter {
				includeGroupAndSubgroups("org.spongepowered")
			}
		}
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "jac"
include("common", "fabric", "neoforge", "forge")