package space.dorzhu.store

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test

class CartTest{

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.cart)).perform(click())
        onView(withId(R.id.btn_buy)).perform(click())
    }

    @Test
    fun buyForm_inView(){
        onView(withId(R.id.buy_form_wrapper)).check(matches(isDisplayed()))
    }

    @Test
    fun successfullyDialog_inView(){
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.succeccfull_wrapper)).check(matches(isDisplayed()))
    }

    @Test
    fun btnContinueShoppingChecking(){
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.backToShop)).perform(click())
        onView(withId(R.id.main_wrapper)).check(matches(isDisplayed()))
    }
}