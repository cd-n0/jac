plugins {
	alias(libs.plugins.fabric.loom)
	id("maven-publish")
}

version = "${libs.versions.minecraft.get()}-${properties["mod_version"] as String}"
group = properties["maven_group"] as String

base {
	archivesName = properties["archives_base_name"] as String
}

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.
}

loom {
	mods {
		create("jac") {
			sourceSet(sourceSets["main"])
		}
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft(libs.minecraft)
	mappings(libs.yarn)
	modImplementation(libs.fabric.loader)
	modImplementation(libs.fabric.api)
}

tasks {
	processResources {
		val propertyMap = mapOf(
			"version" to project.version,
			"minecraft_version" to libs.versions.minecraft.get()
		)

		inputs.properties(propertyMap)
		filesMatching("fabric.mod.json") {
			expand(propertyMap)
		}
	}

	withType<JavaCompile>().configureEach {
		options.release = 21
	}

	jar {
		inputs.property("archivesName", project.base.archivesName.get())

		from("LICENSE") {
			rename { "${it}_${inputs.properties["archivesName"]}" }
		}
	}
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

// configure the maven publication
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			//artifactId = project.base.archivesName.
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
