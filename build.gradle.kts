plugins {
    java
    id("org.springframework.boot") version "2.7.7"
}
apply(plugin = "io.spring.dependency-management")

group = "team.iks"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

springBoot {
    buildInfo()
}

repositories {
    maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    mavenCentral()
}

extra["springBootAdminVersion"] = "2.7.4"

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom("de.codecentric:spring-boot-admin-dependencies:${project.extra["springBootAdminVersion"]}")
    }
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
    implementation("org.xerial:sqlite-jdbc:3.40.0.0")
    implementation("com.github.gwenn:sqlite-dialect:0.1.2")
    annotationProcessor("com.google.auto.service:auto-service:1.0.1")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")

    implementation("de.codecentric:spring-boot-admin-starter-client")
    implementation("de.codecentric:spring-boot-admin-starter-server")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
