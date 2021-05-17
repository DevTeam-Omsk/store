package Some_objects

class DiscountCalculator {

    fun calculate(dayOfWeek: String): Int {
        return when (dayOfWeek) {
            "Fri" -> 30
            "Sun" -> 50
            else -> 0
        }
    }
}