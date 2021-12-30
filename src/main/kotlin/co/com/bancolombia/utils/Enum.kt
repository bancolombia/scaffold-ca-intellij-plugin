package co.com.bancolombia.utils

enum class Language {
    JAVA,
    KOTLIN
}

enum class ProjectType {
    IMPERATIVE,
    REACTIVE
}

enum class ProjectCoverage {
    JACOCO,
    COVERAGE
}

enum class ServerOptions {
    UNDERTOW,
    TOMCAT,
    JETTY
}

enum class EntryPoints {
    NONE,
    GENERIC,
    RESTMVC,
    WEBFLUX,
    RSOCKET,
    GRAPHQL,
    ASYNCEVENTHANDLER,
    MQ
}

enum class DriverAdapters {
    NONE,
    GENERIC,
    JPA,
    MONGODB,
    ASYNCEVENTBUS,
    RESTCONSUMER,
    REDIS,
    RSOCKET,
    R2DBC,
    KMS,
    SECRETS,
    S3,
    MQ
}

enum class ModeOptions {
    TEMPLATE,
    REPOSITORY
}

enum class PipelineOptions {
    AZURE,
    GITHUB
}





