rootProject.name = "seasar-openid4java"

dependencyResolutionManagement {
    components {
        all {
            allVariants {
                withDependencies {
                    removeAll {
                        it.group in listOf("commons-logging")
                    }
                }
            }
        }
    }
}