import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

		plugins {
			id("org.springframework.boot") version "2.6.9"
			id("io.spring.dependency-management") version "1.0.11.RELEASE"
			kotlin("jvm") version "1.6.21"
			kotlin("plugin.spring") version "1.6.21"
			kotlin("plugin.jpa") version "1.6.21"
		}

group = "com.hadi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("org.apache.httpcomponents:httpclient:4.5.14")

  runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test"){
    exclude("org.junit.vintage:junit-vintage-engine")
  }
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
