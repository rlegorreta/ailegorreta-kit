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
*  EntityDTOMapper.kt    
*
 *  Developed 2023 by LegoSoftSoluciones, S.C. www.legosoft.com.mx
*/
package com.ailegorreta.commons.dtomappers

import java.util.HashMap

/**
 * Generic interface to convert from entity persistent object to
 * a DTO object
 *
 * @author rlh
 * @project : ailegorreta-kit-commons
 * @date May 2023
 */
interface EntityDTOMapper<in E, out D> {
    var dtos: HashMap<Int,Any>		// This is to avoid circular references

    fun mapFromEntities(entities: Collection<E>?): Collection<D> {
        if (entities == null)
            return ArrayList<D>()

        return entities.map { fromEntityRecursive(it) }
    }

    fun fromEntity(entity: E): D {
        dtos = HashMap()

        return fromEntityRecursive(entity)
    }

    fun fromEntityRecursive(entity: E): D
}

/**
 * Generic interface to convert from entity persistent object to
 * a DTO object with one relationship with attributes
 *
 * @author rlh
 * @project : ailegorreta-kit-commons
 * @date May 2023
 */
interface EntityRelationshipDTOMapper<E, R, D, RO>: EntityDTOMapper<E, D> {

    fun mapFromRelationshipEntities(relationship: Map<E, R>): Map<D, RO> {
        val res = HashMap<D, RO>()

        for ((k, v) in relationship)
            res.put(fromEntityRecursive(k), fromRelationship(v))

        return res
    }

    fun fromRelationship(entity: R): RO
}

/**
 * Generic interface to convert from a DTO object to a simple DTO object.
 *
 * Simple DTO objects are objects without circular relationships between
 * several DTOs.
 *
 * @author rlh
 * @project :  ailegorreta-kit-commons
 * @date May 2023
 */
interface DTOSimpleMapper<in E, out D> {

    fun mapFromEntities(dtos: Collection<E>): Collection<D> {
        return dtos.map { fromDTO(it) }
    }

    fun fromDTO(dto: E): D
}

/**
 * Generic interface to convert from and Id (Long or String ) and an entity object to
 * a DTO object
 *
 * @author rlh
 * @project : ailegorreta-kit-commons
 * @date May 2023
 */
interface IdDTOMapper<in I, in E, out D> {

    fun mapFromEntities(id: I, entities: Collection<E>?): Collection<D> {
        if (entities == null)
            return ArrayList<D>()
        return entities.map { fromEntity(id, it) }
    }

    fun fromEntity(id: I, entity: E): D
}
