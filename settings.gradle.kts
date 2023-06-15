rootProject.name = "ailegorreta-kit"

include("ailegorreta-kit-commons-utils")
project(":ailegorreta-kit-commons-utils").projectDir = file("ailegorreta-kit-commons/ailegorreta-kit-commons-utils")

rootProject.children.forEach {
    it.name = "chafaxxx"
}