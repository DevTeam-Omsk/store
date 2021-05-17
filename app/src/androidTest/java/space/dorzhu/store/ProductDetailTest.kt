package space.dorzhu.store

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class ProductDetailTest{

    @get:Rule
    val activityScenario = ActivityScenarioRule(ProductDetail::class.java)

    // видная ли кнопка возврата пользователю?
    @Test
    fun bottomNavigation_inView() {
        onView(withId(R.id.btnReturn)).check(matches(ViewMatchers.isDisplayed()))

        onView(withId(R.id.btnReturn))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))) // method 2
    }

}