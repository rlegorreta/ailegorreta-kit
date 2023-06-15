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
*  GraphDTOMapper.kt
*
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
*/
package com.ailegorreta.commons.dtomappers

/**
 * Class to generate nodes for the graph. Nodes in d3.js graph structure
 *
 * @author rlh
 * @project : ailegorreta-kit-commons
 * @date May 2023
 */
data class NodeDTO(val id: Int,
                   val idNeo4j: Long,
                   val caption: String,
                   val subType: Boolean = false,
                   val subTypeVal: Int = 0,
                   val type: String? = null) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NodeDTO) return false

        if (id != other.id) return false

        return true
    }
}

/**
 * Relationship graph class.  Nodes in d3.js graph structure
 *
 * @author rlh
 * @project : ailegorreta-kit-commons
 * @date May 2023
 */
data class LinkDTO(val caption: String? = null,
                   val type: Int = 1,
                   val source: Int,
                   val target: Int)
