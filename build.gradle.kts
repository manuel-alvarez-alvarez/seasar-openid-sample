plugins {
    id("java")
    war
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://www.seasar.org/maven/maven2/")
    }
    mavenLocal()
}

val seasarVersion = "2.3.23"

dependencies {
    implementation("org.seasar.container:s2-extension:$seasarVersion")
    implementation("org.seasar.container:s2-framework:$seasarVersion")
    implementation("org.seasar.struts:s2-struts:1.2.12")

    // you have to compile your own
    // https://github.com/jbufu/openid4java
    implementation("org.openid4java:openid4java:0.9.5-manu")

    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:jcl-over-slf4j:2.0.13")
    implementation("org.slf4j:slf4j-log4j12:2.0.13")

    implementation("javax.servlet:jstl:1.1.2")
    implementation("taglibs:standard:1.1.2")

    compileOnly("javax.servlet:javax.servlet-api:3.1.0")
}

tasks.test {
    useJUnitPlatform()
}