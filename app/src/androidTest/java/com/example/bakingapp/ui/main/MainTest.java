package com.example.bakingapp.ui.main;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainTest() {
        ViewInteraction textView = onView(
                allOf(withText("Baking App"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Baking App")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipes_rv),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_recipe_iv),
                        childAtPosition(
                                allOf(withId(R.id.recipe_cl),
                                        childAtPosition(
                                                withId(R.id.recipe_cv),
                                                0)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.title_recipe_tv), withText("Nutella Pie"),
                        childAtPosition(
                                allOf(withId(R.id.recipe_cl),
                                        childAtPosition(
                                                withId(R.id.recipe_cv),
                                                0)),
                                2),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.recipe_cl),
                        childAtPosition(
                                allOf(withId(R.id.recipe_cv),
                                        childAtPosition(
                                                withId(R.id.recipes_rv),
                                                0)),
                                0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.ingredients_recipe_li_tv), withText("9 ingredients"),
                        childAtPosition(
                                allOf(withId(R.id.recipe_cl),
                                        childAtPosition(
                                                withId(R.id.recipe_cv),
                                                0)),
                                3),
                        isDisplayed()));
        textView3.check(matches(withText("9 ingredients")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.servings_recipe_li_tv), withText("8 servings"),
                        childAtPosition(
                                allOf(withId(R.id.recipe_cl),
                                        childAtPosition(
                                                withId(R.id.recipe_cv),
                                                0)),
                                4),
                        isDisplayed()));
        textView4.check(matches(withText("8 servings")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.steps_recipe_li_tv), withText("7 steps"),
                        childAtPosition(
                                allOf(withId(R.id.recipe_cl),
                                        childAtPosition(
                                                withId(R.id.recipe_cv),
                                                0)),
                                5),
                        isDisplayed()));
        textView5.check(matches(withText("7 steps")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
