package com.example.lucarino.whattowatch.movies;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.lucarino.whattowatch.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MoviesActivityTest {

    @Rule
    public ActivityTestRule<MoviesActivity> mActivityTestRule = new ActivityTestRule<>(MoviesActivity.class);

    @Test
    public void moviesActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.my_recycler_view), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.iv_favorite), isDisplayed()));
        toggleButton.perform(click());

        ViewInteraction toggleButton2 = onView(
                allOf(withId(R.id.iv_favorite), isDisplayed()));
        toggleButton2.perform(click());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"), isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Most popular"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"), isDisplayed()));
        overflowMenuButton2.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Highest rated"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction overflowMenuButton3 = onView(
                allOf(withContentDescription("More options"), isDisplayed()));
        overflowMenuButton3.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.title), withText("Favorites"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction overflowMenuButton4 = onView(
                allOf(withContentDescription("More options"), isDisplayed()));
        overflowMenuButton4.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.my_recycler_view), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.my_recycler_view), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textViewTitle), withText("Guardians of the Galaxy"), isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.my_recycler_view), isDisplayed()));
        recyclerView4.check(matches(isDisplayed()));

    }

}
