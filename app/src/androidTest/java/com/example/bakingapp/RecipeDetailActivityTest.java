package com.example.bakingapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.bakingapp.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBottomNavButtons() {
        onView(withId(R.id.recipes_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.steps_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.next_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.previous_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.finish_btn)).check(matches(not(isDisplayed())));
        onView(withId(R.id.ingredients_btn)).check(matches(not(isDisplayed())));
    }
}
