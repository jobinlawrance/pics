package com.jobinlawrance.pics

/**
 * Created by jobinlawrance on 6/10/17.
 */

class Queue<E> {
    private var list: MutableList<E>

    init {
        list = ArrayList<E>()
    }

    fun isEmpty(): Boolean = list.isEmpty()

    fun count(): Int = list.size

    fun add(item: E) {
        list.add(item)
    }

    fun remove(): E =
            if (list.isEmpty())
                throw IndexOutOfBoundsException("Queue is empty")
            else
                list.removeAt(0)
}