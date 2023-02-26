import com.google.protobuf.gradle.id

plugins {
	java
	id("com.google.protobuf") version "0.9.2"
	id("org.springframework.boot") version "2.7.8"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "ru.tinkoff"
version = "0.2.0"
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
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	implementation("io.micrometer:micrometer-registry-prometheus:1.10.4")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:junit-jupiter")
	implementation("org.springframework.boot:spring-boot-starter")

	implementation("com.google.protobuf:protobuf-java:3.22.0")
	runtimeOnly("com.google.protobuf:protobuf-java-util:3.22.0")
	implementation("io.grpc:grpc-netty:1.53.0")
	implementation("io.grpc:grpc-protobuf:1.53.0")
	implementation("io.grpc:grpc-stub:1.53.0")
	implementation("net.devh:grpc-client-spring-boot-starter:2.14.0.RELEASE")
	testImplementation("org.mockito:mockito-all")
	if (JavaVersion.current().isJava9Compatible()) {
		implementation("javax.annotation:javax.annotation-api:1.3.2")
	}

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
