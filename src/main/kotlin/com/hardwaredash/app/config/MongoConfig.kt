package com.hardwaredash.app.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.WriteConcern
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*


@Configuration
@EnableMongoRepositories(basePackages = ["com.hardwaredash.app"])
@ComponentScan(basePackages = ["com.hardwaredash.app.*"])
@EnableTransactionManagement
class MongoConfig(
    val appConfig: AppConfig,
    val mongoConfig: MongoConfiguration
) : AbstractMongoClientConfiguration() {

    private val mongoConverters = mutableListOf<Converter<*, *>>()

    override fun getDatabaseName() = mongoConfig.database


    @Bean
    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(getProfileBasedConnectionString())
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .retryWrites(true)
            .writeConcern(WriteConcern("majority"))
            .build()
        return MongoClients.create(mongoClientSettings)
    }

    private fun getProfileBasedConnectionString(): String {
        return when (appConfig.environment) {
            "dev" -> "mongodb://${mongoConfig.host}:${mongoConfig.port}/${mongoConfig.database}?retryWrites=false"
            else -> "mongodb+srv://${mongoConfig.username}:${mongoConfig.password}@${mongoConfig.host}/${mongoConfig.database}"
        }
    }

    public override fun getMappingBasePackages(): List<String> {
        return listOf("com.hardwaredash")
    }

    //    @Bean
//    fun userCascadingMongoEventListener(): UserCascadeSaveMongoEventListener {
//        return UserCascadeSaveMongoEventListener()
//    }
//
//    @Bean
//    fun cascadingMongoEventListener(): CascadeSaveMongoEventListener {
//        return CascadeSaveMongoEventListener()
//    }
//
    override fun customConversions(): MongoCustomConversions {
        mongoConverters.add(OffsetDateTimeWriteConverter())
        mongoConverters.add(OffsetDateTimeReadConverter())
        return MongoCustomConversions(mongoConverters)
    }

    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory) = MongoTransactionManager(dbFactory)

    override fun autoIndexCreation() = true

    internal class OffsetDateTimeWriteConverter : Converter<OffsetDateTime, Date> {
        override fun convert(source: OffsetDateTime): Date {
            return Date.from(source.toInstant().atZone(ZoneOffset.UTC).toInstant())
        }
    }

    internal class OffsetDateTimeReadConverter : Converter<Date, OffsetDateTime> {
        override fun convert(source: Date): OffsetDateTime {
            return source.toInstant().atOffset(ZoneOffset.UTC)
        }
    }


}