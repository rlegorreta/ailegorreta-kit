rootProject.name = "ailegorreta-kit"

include("ailegorreta-kit-commons-utils")
project(":ailegorreta-kit-commons-utils").projectDir = file("ailegorreta-kit-commons/ailegorreta-kit-commons-utils")

include("ailegorreta-kit-commons-event")
project(":ailegorreta-kit-commons-event").projectDir = file("ailegorreta-kit-commons/ailegorreta-kit-commons-event")

include("ailegorreta-kit-resource-sever:ailegorreta-kit-resource-server-security")
findProject(":ailegorreta-kit-resource-sever:ailegorreta-kit-resource-server-security")?.name = "ailegorreta-kit-resource-server-security"
