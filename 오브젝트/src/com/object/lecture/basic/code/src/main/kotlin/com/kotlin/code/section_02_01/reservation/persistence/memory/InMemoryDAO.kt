package com.kotlin.code.section_02_01.reservation.persistence.memory

import java.util.function.Predicate
import java.util.stream.Collectors


abstract class InMemoryDAO<T: Any> {

    private var currentId: Long = 1L
    private val objects: MutableList<T> = mutableListOf()

    protected fun findMany(condition: (T) -> Boolean): MutableList<T> {
        return objects.filter(condition).toMutableList()
    }

    protected fun findOne(condition: Predicate<T>?): T {
        return objects.stream().filter(condition).findFirst().orElse(null)
    }

    open fun insert(`object`: T) {
        setIdIfPossible(`object`)
        objects.add(`object`)
    }

    private fun <T> setIdIfPossible(obj: T) {
        try {
            val idField = obj!!::class.java.getDeclaredField("id")
            idField.isAccessible = true
            idField.set(obj, currentId)
            currentId++
        } catch (e: Exception) {
            // 예외 처리
        }
    }
}