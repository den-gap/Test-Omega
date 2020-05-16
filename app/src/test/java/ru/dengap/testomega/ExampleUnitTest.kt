package ru.dengap.testomega

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.dengap.testomega.pojo.Result

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_replace() {
        val test =
            Result(artworkUrl100 = "https://is2-ssl.mzstatic.com/image/thumb/Music118/v4/24/46/97/24469731-f56f-29f6-67bd-53438f59ebcb/source/100x100bb.jpg")
        val test1 =
            Result(artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Music118/v4/87/00/83/8700835d-d21f-d862-d816-62966095521e/source/100x100bb.jpg")
        val lt = mutableListOf(test, test1)
        lt.map {
            var newString = it.artworkUrl100
            newString = newString?.replace("https".toRegex(), "500x500bb")
            println(newString)
        }
    }
}
