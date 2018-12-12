package com.example.capar.eventreminder

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import android.support.test.rule.ActivityTestRule
import com.example.capar.eventreminder.CustomAssertions.Companion.hasItemCount
import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITests{

    @Rule
    @JvmField
    public val rule = ActivityTestRule(MainActivity::class.java)

    private val event_header_totype = "Test Event #1"
    private val event_description_totype = "Test\n" +
            "Description"
    private val event_priority_tochoose = "Medium-High Priority"
    private val event_priority_tochange = "High Priority"
    private val event_priority_color = R.color.colorMidHighPriority

    private val event_header_updated_totype = "Test Event #1 Edited"
    private val event_description_updated_totype = "Edited\n" +
            "Test\n" +
            "Description"

    @Before
    fun addEvent() {
        Log.e("@Test", "Performing add event test")
        Espresso.onView((withId(R.id.fab)))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.event_header_edittext))
            .perform(ViewActions.typeText(event_header_totype))
        Espresso.onView(withId(R.id.event_description_edittext))
            .perform(ViewActions.typeText(event_description_totype))
        Espresso.onView(withId(R.id.priorityPicker))
            .perform(ViewActions.click())
        onData(allOf(`is`(instanceOf(String::class.java)),
            `is`(event_priority_tochoose))).perform(click())
        Espresso.onView(withId(R.id.save_event_button))
            .perform(ViewActions.click())

        Log.e("@Test", "Checking for successful event creation")
        Espresso.onView(withId(R.id.recycler_view))
            .check(hasItemCount(1))
    }

    @Test
    fun canEditEvent() {
        Log.e("@Test", "Performing edit event test")
        Espresso.onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<EventAdapter.ViewHolder>(1))
        Espresso.onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<EventAdapter.ViewHolder>(0,ViewActions.click()))
        Espresso.onView(withId(R.id.fab_edit_details))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.event_header_edittext))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.event_header_edittext))
            .perform(ViewActions.typeText(event_header_updated_totype))
        Espresso.onView(withId(R.id.event_description_edittext))
            .perform(ViewActions.typeText(event_description_updated_totype))
        Espresso.onView(withId(R.id.priorityPicker))
            .perform(ViewActions.click())
        onData(allOf(`is`(instanceOf(String::class.java)),
            `is`(event_priority_tochange))).perform(click())

        ViewActions.closeSoftKeyboard()
        Espresso.onView(withId(R.id.save_event_button))
            .perform(ViewActions.click())
        onView(withId(R.id.event_activity_header))
            .check(matches(isDisplayed()))
        onView(withId(R.id.event_activity_description))
            .check(matches(isDisplayed()))
    }

    @Test
    fun eventHasCorrectDetails() {
        Log.e("@Test", "Performing event detail checker test")
        Espresso.onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<EventAdapter.ViewHolder>(1))
        Espresso.onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<EventAdapter.ViewHolder>(0,ViewActions.click()))
        Espresso.onView(withId(R.id.event_activity_header))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.event_activity_description))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.event_activity_priority))
            .check(matches(isDisplayed()))
    }

}
