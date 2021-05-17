package Some_objects

import org.junit.Assert.*
import org.junit.Test

class DiscountCalculatorTest{

    @Test
    fun inSunday50Percent() {
        val discCalculator = DiscountCalculator()
        val curDayDiscount = discCalculator.calculate("Sun")
        assertTrue( curDayDiscount == 50)
    }

    @Test
    fun inFriday30Percent() {
        val discCalculator = DiscountCalculator()
        val curDayDiscount = discCalculator.calculate("Fri")
        assertTrue( curDayDiscount == 30)
    }
}