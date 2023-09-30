rootProject.name = "ailegorreta-kit"

include("ailegorreta-kit-commons:ailegorreta-kit-commons-utils")
findProject(":ailegorreta-kit-commons:ailegorreta-kit-commons-utils")?.name = "ailegorreta-kit-commons-utils"

include("ailegorreta-kit-commons:ailegorreta-kit-commons-event")
findProject(":ailegorreta-kit-commons:ailegorreta-kit-commons-event")?.name = "ailegorreta-kit-commons-event"

include("ailegorreta-kit-commons:ailegorreta-kit-commons-security")
findProject(":ailegorreta-kit-commons:ailegorreta-kit-commons-security")?.name = "ailegorreta-kit-commons-security"

include("ailegorreta-kit-resource-sever:ailegorreta-kit-resource-server-security")
findProject(":ailegorreta-kit-resource-sever:ailegorreta-kit-resource-server-security")?.name = "ailegorreta-kit-resource-server-security"


include("ailegorreta-kit-client:ailegorreta-kit-client-security")
findProject(":ailegorreta-kit-client:ailegorreta-kit-client-security")?.name = "ailegorreta-kit-client-security"

include("ailegorreta-kit-data:ailegorreta-kit-data-neo4j")
findProject(":ailegorreta-kit-data:ailegorreta-kit-data-neo4j")?.name = "ailegorreta-kit-data-neo4j"

include("ailegorreta-kit-data:ailegorreta-kit-data-jpa")
findProject(":ailegorreta-kit-data:ailegorreta-kit-data-jpa")?.name = "ailegorreta-kit-data-jpa"

include("ailegorreta-kit-data:ailegorreta-kit-data-mongo")
findProject(":ailegorreta-kit-data:ailegorreta-kit-data-mongo")?.name = "ailegorreta-kit-data-mongo"

include("ailegorreta-kit-client:ailegorreta-kit-client-components")
findProject(":ailegorreta-kit-client:ailegorreta-kit-client-components")?.name = "ailegorreta-kit-client-components"

include("ailegorreta-kit-client:ailegorreta-kit-client-navigation")
findProject(":ailegorreta-kit-client:ailegorreta-kit-client-navigation")?.name = "ailegorreta-kit-client-navigation"

include("ailegorreta-kit-client:ailegorreta-kit-client-dataproviders")
findProject(":ailegorreta-kit-client:ailegorreta-kit-client-dataproviders")?.name = "ailegorreta-kit-client-dataproviders"
