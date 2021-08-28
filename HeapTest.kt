import com.korneix.android.flashkard.database.Card
import com.korneix.android.flashkard.utils.Heap
import org.junit.Assert.assertEquals
import org.junit.Test


class HeapTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun heap_isCorrect() {

        // Comparison functions
        val compareFunction: (Int, Int) -> Boolean = { a: Int, b: Int -> a < b }
        // val compareFunctionCards = { a: Card, b: Card -> a.mistakes > b.mistakes }

        // Can the heap work with a primitive type (Integer) ?
        var h: Heap<Int> = Heap(10, compareFunction)

        // is Heap empty at initialization?
        assertEquals(h.isEmpty, true)

        // Is the addition of a value following the Heap logic?
        h.insert(11)
        assertEquals(h.isEmpty, false)
        assertEquals(h.peek(), 11)

        // Does popping off a one-value heap returns that same value? Is the heap empty after?
        assertEquals(h.pop(),11)
        assertEquals(h.isEmpty, true)

        // Is the Heap properly sorting the elements based on the comparison function?
        h.insert(11)
        h.insert(9)
        h.insert(7)
        h.insert(4)
        h.insert(3)
        h.insert(8)
        assertEquals(3, h.pop())
        assertEquals(4, h.pop())
        assertEquals(7, h.pop())
        assertEquals(8, h.pop())
        assertEquals(9, h.pop())
        assertEquals(11, h.pop())

        /** Can the Heap work with a custom Object?
        val heap = Heap<Card>(10, compareFunctionCards)
        heap.insert(Card(0, "a", "b", mistakes = 14))
        heap.insert(Card(0, "a", "b", mistakes = 64))
        heap.insert(Card(0, "a", "b", mistakes = 7))
        heap.insert(Card(0, "a", "b", mistakes = 9))
        heap.insert(Card(0, "a", "b", mistakes = 118))
        heap.insert(Card(0, "a", "b", mistakes = 20))
        heap.insert(Card(0, "a", "b", mistakes = 100))
        assertEquals(118, heap.pop()?.mistakes)
        assertEquals(100, heap.pop()?.mistakes)
        assertEquals(64, heap.pop()?.mistakes)
        assertEquals(20, heap.pop()?.mistakes)
        assertEquals(14, heap.pop()?.mistakes)
        assertEquals(9, heap.pop()?.mistakes)
        assertEquals(7, heap.pop()?.mistakes) **/

        // Does the insert() method return false when we insert more element than its size?
        // Does it raise an exception?
        h = Heap<Int>(5, compareFunction)
        assertEquals(true, h.insert(0))
        assertEquals(true, h.insert(1))
        assertEquals(true, h.insert(2))
        assertEquals(true, h.insert(3))
        assertEquals(true, h.insert(4))
        assertEquals(false, h.insert(5))

        // Does the pop() method return null when we pop more elements than what's stored?
        // Does it raise an exception?
        assertEquals(true, h.pop()?.let { it::class.java == Int::class.java } ?: false )
        assertEquals(true, h.pop()?.let { it::class.java == Int::class.java } ?: false)
        assertEquals(true, h.pop()?.let { it::class.java == Int::class.java } ?: false)
        assertEquals(true, h.pop()?.let { it::class.java == Int::class.java } ?: false)
        assertEquals(true, h.pop()?.let { it::class.java == Int::class.java } ?: false)
        assertEquals(true, h.pop() == null)
        assertEquals(h.isEmpty, true)

        // Heapify testing
        val h2 = Heap.heapify(listOf(9, 8, 7, 6, 5), compareFunction)
        assertEquals(5, h2.size)
        assertEquals(5, h2.pop())
        assertEquals(6, h2.pop())
        assertEquals(7, h2.pop())
        assertEquals(8, h2.pop())
        assertEquals(9, h2.pop())
        assertEquals(true, h2.isEmpty)

        // Take testing
        val h3 = Heap.heapify(listOf(10, 20, 50, 20, 15), compareFunction).take(5)
        assertEquals(5, h3.size)
        assertEquals(10, h3[0])
        assertEquals(15, h3[1])
        assertEquals(20, h3[2])
        assertEquals(20, h3[3])
        assertEquals(50, h3[4])
    }
}
