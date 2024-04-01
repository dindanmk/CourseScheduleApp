package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(HomeActivity::class.java)
        Intents.init()
    }

    @Test
    fun plusButtonTest(){
        Espresso.onView(withId(R.id.action_add)).perform(ViewActions.click())
        Intents.intended(hasComponent(AddCourseActivity::class.java.name))
        Intents.release()
    }
}