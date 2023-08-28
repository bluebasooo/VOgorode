import com.google.protobuf.gradle.*

plugins {
	java
	id("org.springframework.boot") version "2.7.8"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("com.google.protobuf") version "0.9.2"
}

group = "ru.tinkoff"
version = "0.7.0"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["testcontainersVersion"] = "1.17.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.micrometer:micrometer-registry-prometheus:1.10.4")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	testImplementation("org.testng:testng:7.1.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.modelmapper:modelmapper:3.1.1")


	implementation("com.google.protobuf:protobuf-java:3.22.0")
	runtimeOnly("com.google.protobuf:protobuf-java-util:3.22.0")
	implementation("io.grpc:grpc-netty:1.53.0")
	implementation("io.grpc:grpc-protobuf:1.53.0")
	implementation("io.grpc:grpc-stub:1.53.0")
	implementation("net.devh:grpc-server-spring-boot-starter:2.14.0.RELEASE")
	implementation("net.devh:grpc-client-spring-boot-starter:2.14.0.RELEASE")
	if (JavaVersion.current().isJava9Compatible()) {
		implementation("javax.annotation:javax.annotation-api:1.3.2")
	}

	implementation("org.liquibase:liquibase-core")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("io.hypersistence:hypersistence-utils-hibernate-55:3.5.0")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.postgresql:postgresql:42.6.0")
	implementation("org.mongodb:bson:4.9.0")
	implementation("org.mongodb:mongodb-driver-core:4.9.0")
	implementation("org.mongodb:mongodb-driver-sync:4.9.0")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.7")

	testCompileOnly("org.junit.jupiter:junit-jupiter:5.6.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mongodb:1.18.3")
	testImplementation("org.testcontainers:postgresql:1.18.0")
	testImplementation("com.github.tomakehurst:wiremock-standalone:2.27.2")
	implementation("org.hamcrest:hamcrest-all:1.3")
	implementation("io.rest-assured:rest-assured:3.3.0")
	testImplementation("io.grpc:grpc-testing:1.53.0")

}

sourceSets {
	main {
		java {
			srcDirs("build/generated/source/proto/main/grpc")
			srcDirs("build/generated/source/proto/main/java")
		}
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.22.0"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.53.0"
		}
	}
	generateProtoTasks {
		ofSourceSet("main").forEach {
			it.plugins {
				id("grpc") { }
			}
		}
	}
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBoot {
	buildInfo()
}
