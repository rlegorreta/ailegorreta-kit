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
*  RestClientException.kt
*
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
*/
package com.ailegorreta.resourceserver.rest.exception

/**
 * This class is to return a LegoSoft standard REST exception.
 *
 * @author rlh
 * @project : ailegorreta-kit-resource-server
 * @date June 2023
 *
 */
class RestClientException : RuntimeException {
    var status: Int
        private set

    constructor(status: Int, mensaje: String?, causa: Throwable?) : super(mensaje, causa) {
        this.status = status
    }

    constructor(status: Int, causa: Throwable?) : super(causa) {
        this.status = status
    }

    constructor(status: Int) {
        this.status = status
    }

    constructor(status: Int, mensaje: String?) : super(mensaje) {
        this.status = status
    }

    override fun toString(): String {
        return "Rest exception status:$status message :$message"
    }

    companion object {
        const val STATUS_JSON_CONVERSION = 7
    }
}
