package com.pal.populartv.data.mapper

interface EntityMapper<in M, out E> {
    fun mapFromRemote(from: M): E
}