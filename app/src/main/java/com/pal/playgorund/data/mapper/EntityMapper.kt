package com.pal.playgorund.data.mapper

interface EntityMapper<in M, out E> {
    fun mapFromRemote(from: M): E
}