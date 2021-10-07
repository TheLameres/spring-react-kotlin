plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    java
}

group = "thelameres.hospital"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}



dependencies {
    project(":client")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("mysql:mysql-connector-java")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.create("wrapper", Wrapper::class.java) {
    gradleVersion = "7.2"
}

tasks.register("prepareKotlinBuildScriptModel") {

}

tasks.findByName("bootJar")?.dependsOn("copyFrontend")


tasks.register<Copy>("copyFrontend") {
    this.dependsOn(":client:build")
    val buildDir1 = project(":client").buildDir.toPath().resolve("distributions")
    println(buildDir1)
    from(buildDir1.toFile())
        .include(
            "client.js",
            "client.js.map",
            "index.html")
        .into("$buildDir/resources/main/static")
}