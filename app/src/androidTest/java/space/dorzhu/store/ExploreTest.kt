package space.dorzhu.store

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class ExploreTest{
    @Test
    fun tvNotFound_inView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.explore)).perform(click())

        onView(withId(R.id.et_search)).perform(typeText("*/-*/+*-/"))

        onView(withId(R.id.btn_search)).perform(click())

        onView(withId(R.id.tvNotFound)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun tvNotFound_notInView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.explore)).perform(click())

        onView(withId(R.id.et_search)).perform(typeText("hyperx"))

        onView(withId(R.id.btn_search)).perform(click())

        onView(withId(R.id.tvNotFound)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}