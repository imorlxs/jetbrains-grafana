plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("internship.Main")
}
tasks.register<JavaExec>("generateDashboard") {
    group = "custom"
    description = "Runs Main class to generate JSON"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("internship.Main")
    args = listOf() // optional arguments
}

tasks.register<JavaExec>("generateDashboardForGrafanaCTL") {
    group = "custom"
    description = "Runs Main class to generate JSON"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("internship.Main")
    args = listOf("--grafanactl") // optional arguments
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.grafana:grafana-foundation-sdk:next-1746458649")
}

tasks.test {
    useJUnitPlatform()
}