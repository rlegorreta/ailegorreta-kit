/* Copyright (c) 2021, LegoSoft Soluciones, S.C.
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
*  EventGraphqlError.kt
*
*  Developed 2021 by LegoSoft Soluciones, S.C. www.legosoft.com.mx
*/
package com.ailegorreta.commons.event

import com.ailegorreta.commons.utils.HasLogger
/**
 * Data class for Events when a Graphql error exists and permits to add new information
 * like the operation number.
 *
 * @author rlh
 * @project : ailegorreta-kit--commons-event
 * @date June, 2023
 */
data class EventGraphqlError constructor (val errors: Collection<Map<String, Any>>? = null,
                                          var extraData: MutableMap<String, Any>? = null): HasLogger {

    fun addExtraData( key: String, data: Any) {
        logger.info("Hola")
        if (extraData == null)
            extraData = mutableMapOf(key to data)
        else
            extraData!![key] = data
    }
}
