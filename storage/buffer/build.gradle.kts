plugins {
    id("buildlogic.java-library-conventions")
}
dependencies {
    implementation(project(":common"))
    implementation(project(":storage:disk"))
    implementation(project(":storage:page"))
}