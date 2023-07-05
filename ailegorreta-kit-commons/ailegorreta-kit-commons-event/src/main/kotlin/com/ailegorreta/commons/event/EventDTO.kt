/* Copyright (c) 2023, LegoSoft Soluciones, S.C.
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are not permitted.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*
*  EventDTO.kt
*
*  Developed 2023 by LegoSoft Soluciones, S.C. www.legosoft.com.mx
*/
package com.ailegorreta.commons.event

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import java.io.IOException

/**
 * Data class for Events. This DTO is for send events and listen events
 * from the Event Logger.
 *
 * @author rlh
 * @project : lmasskit-commons-event
 * @date July 2023
 */
data class EventDTO constructor(@JsonProperty("correlationId") var correlationId:String?,
                                @JsonProperty("eventType") var eventType: EventType,
                                @JsonProperty("username") var username:String,
                                @JsonProperty("eventName") var eventName:String,
                                @JsonProperty("applicationName") var applicationName:String,
                                @JsonProperty("coreName") var coreName: String,
                                @JsonProperty("eventBody") var eventBody: Any) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EventDTO) return false

        if (correlationId != other.correlationId) return false

        return true
    }

    override fun hashCode(): Int = correlationId.hashCode()

    override fun toString() = "username = $username " +
            "correlationId = $correlationId " +
            "eventType = $eventType " +
            "eventName = $eventName " +
            "applicationName = $applicationName " +
            "coreName = $coreName " +
            "eventBody = " + eventBody.toString()
}

class EventDTOSerializer : Serializer<EventDTO> {
    private val objectMapper = com.ailegorreta.commons.utils.ApplicationContextProvider.getBean(ObjectMapper::class.java)

    override fun serialize(topic: String, data: EventDTO): ByteArray {
        return try {
            objectMapper.writeValueAsBytes(data)
        } catch (e: JsonProcessingException) {
            throw SerializationException(e)
        }
    }
}

class EventDTODeSerializer : Deserializer<EventDTO> {
    private val objectMapper = com.ailegorreta.commons.utils.ApplicationContextProvider.getBean(ObjectMapper::class.java)

    override fun deserialize(topic: String, data: ByteArray): EventDTO {
        return try {
            objectMapper.readValue(String(data), EventDTO::class.java)
        } catch (e: IOException) {
            throw SerializationException(e)
        }
    }
}

enum class EventType {
    /**
     * Only store into a database
     */
    DB_STORE,
    /**
     * Only store into a TXT file
     */
    FILE_STORE,
    /**
     * Store into a data base and a TXT file
     */
    FULL_STORE,
    /**
     * Error event, something happen in the system
     */
    ERROR_EVENT,
    /**
     * No store the event
     */
    NON_STORE
}
