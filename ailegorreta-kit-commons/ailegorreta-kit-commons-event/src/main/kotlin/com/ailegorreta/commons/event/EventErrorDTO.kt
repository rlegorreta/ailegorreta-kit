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
*  EventErrorDTO.kt
*
*  Developed 2023 by LegoSoft Soluciones, S.C. www.legosoft.com.mx
*/
package com.ailegorreta.commons.event

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import java.io.IOException

/**
 * When we listen an event and some error occurs during the action of
 * taken because the event, we send an error event explaining the cause
 *
 * @author rlh
 * @project : ailegorreta-kit-commons-event
 * @date June 2023
 */
data class EventErrorDTO constructor( @JsonProperty("message") val message:String?,
                                      @JsonProperty("cause") val cause:String,
                                      @JsonProperty("description") val description:String = "") {

    override fun toString() = "message = $message " +
            "cause = $cause " +
            "description = $description "
}


class EventErrorSerializer : Serializer<EventErrorDTO> {
    private val objectMapper = com.ailegorreta.commons.utils.ApplicationContextProvider.getBean(ObjectMapper::class.java)

    override fun serialize(topic: String, data: EventErrorDTO): ByteArray {
        return try {
            objectMapper.writeValueAsBytes(data)
        } catch (e: JsonProcessingException) {
            throw SerializationException(e)
        }
    }
}

class EventErrorDeSerializer : Deserializer<EventErrorDTO> {
    private val objectMapper = com.ailegorreta.commons.utils.ApplicationContextProvider.getBean(ObjectMapper::class.java)

    override fun deserialize(topic: String, data: ByteArray): EventErrorDTO {
        return try {
            objectMapper.readValue(String(data), EventErrorDTO::class.java)
        } catch (e: IOException) {
            throw SerializationException(e)
        }
    }
}
