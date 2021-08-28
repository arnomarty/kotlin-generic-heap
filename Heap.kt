
/**
 * Generic Kotlin implementation of heaps. You must provide a comparison function in order to make the
 * ordering possible.
 * For example, if you're looking for an Integer max-heap, function would be { a: Int, b: Int -> a > b }.
 *
 * @param  totalSize  The total size of your heap. It will not store more than this amount of items.
 * @param  compare  The function used to compare and order items.
 *
 * @throws  IllegalArgumentException  when totalSize is scrictly inferior to 1.
 **/
class Heap<T: Any>(private val totalSize: Int, private val compare: (T, T) -> Boolean) {

    init {
        if (totalSize <= 0 ) throw IllegalArgumentException("Heap size must be strictly superior than zero.")
    }

    private val empty: T? = null
    private val h: MutableList<T?> = MutableList(totalSize) { empty }
    private var _size = -1

    val size: Int
        get() { return _size.plus(1) }
    val isEmpty: Boolean
        get() { return _size == -1 }



    companion object {
        /**
         * Will turn a given list into a heap.
         * @param  list  The list that must be turned into a heap.
         * @param  comparator  The compare function used to order the heap.
         * @return  A new heap storing the list's elements, sorted with the comparison function.
         * @throws  IllegalArgumentException when the list passed is empty.
         **/
        fun <T : Any> heapify(list: List<T>, comparator: (T, T) -> Boolean): Heap<T> {
            return Heap(list.size, comparator).also { for(item in list) it.insert(item) }
        }
    }


    /**
     * Returns the value at the top of the heap.
     * @return  null if the list is empty.
     */
    fun peek(): T? {
        return h[0]
    }

    /**
     * Pops the value at the top of the heap. That value will be removed.
     * @return  The value at the top of the heap, or null if it's empty.
     */
    fun pop(): T? {
        val target: T? = h[0]
        if (target == empty) return empty
        h[0] = h[_size]
        h[_size] = empty
        _size--
        sortDown(0)
        return target
    }

    /**
     * Inserts a value into the heap, which will then be sorted accordingly.
     * @return  true if the insertion was completed successfully, false if the heap is full.
     */
    fun insert(item: T): Boolean {
        if (_size < totalSize-1) {
            _size++
            h[_size] = item
            sortUp(_size)
            return true
        }
        return false
    }

    /**
     * Returns a list of the n first elements at the top of the heap.
     * If the given number exceeds the amount of elements present in the list, it will return all the elements
     * stored inside and clear the heap.
     * @return  A List containing (if possible) the first n elements of a heap. Its size is inferior or equal to n.
     * @throws  IndexOutOfBoundsException  when n exceeds the total size of the heap.
     */
    fun take(n: Int): List<T> {
        if (n >= totalSize)
            throw IndexOutOfBoundsException("Attempt to take $n elements from a heap that can only store ${totalSize-1}")
        return  when {
            n > size -> mutableListOf()
            else -> List(n) { this.pop() }.filterNotNull()
        }
    }


    private fun sortUp(index: Int) {
        if (index == 0) return

        val parent: Int = (index-1)/2
        if (compare(h[index]!!, h[parent]!!)) {
            swap(index, parent)
            sortUp(parent)
        }

    }

    private fun sortDown(index: Int) {

        val child1Index = 2*index+1
        val child2Index = 2*index+2

        if (child1Index > _size && child2Index > _size)
            return

        // If first child isn't null
        h[child1Index]?.let { child1 ->
            // If second child isn't null
            h[child2Index]?.let { child2 ->
                // child1 < child2 and child1 < index
                if (compare(child1, child2) && compare(child1, h[index]!!)) {
                    swap(child1Index, index)
                    sortDown(child1Index)
                }
                // child2 < child1 and child2 < index
                else if (compare(child2, child1) && compare(child2, h[index]!!)) {
                    swap(child2Index, index)
                    sortDown(child2Index)
                }

            } ?: if (compare(child1, h[index]!!)) { swap(child1Index, index); sortDown(child1Index) }

        }

    }

    private fun swap(a: Int, b: Int) { h[a] = h[b].also { h[b] = h[a] } }

    override fun toString(): String =
        "SIZE=$size, List: " + h.subList(0, size).joinToString(separator=", ", prefix = "[ ", postfix = " ]")
}