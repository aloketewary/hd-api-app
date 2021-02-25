package com.hardwaredash.app.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent


class ConfigCascadeSaveMongoEventListener : AbstractMongoEventListener<Any>() {
//    val mongoOperations: MongoOperations
//) {
//
//
//    override fun onBeforeConvert(event: BeforeConvertEvent<Any?>) {
//        val source = event.source
//        if (source is User && (source as User).getEmailAddress() != null) {
//            mongoOperations.save((source as User).getEmailAddress())
//        }
//    }
}