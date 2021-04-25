package feri.count.it.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import feri.count.it.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CalorieInputTest {

    @Rule
    public ActivityTestRule<LogInActivity> mActivityTestRule = new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void calorieInputTest()
    {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.edtEmailLog), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2), 4),
                isDisplayed()));
        appCompatEditText.perform(replaceText("viki@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.edtPassword), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2), 5),
                isDisplayed()));
        appCompatEditText2.perform(replaceText("1111aAAA#"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.buttonLoginUser), withText("GO"), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                2), 6), isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(allOf(withId(R.id.buttonRegister2), withText("+"), childAtPosition(childAtPosition(withId(R.id.nav_host_fragment), 0), 0), isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView( allOf(withId(R.id.buttonAdd), childAtPosition(allOf(withId(R.id.item_entry_layout), childAtPosition(withId(R.id.recyclerViewFood), 0)), 6), isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(allOf(withId(R.id.buttonRegister3), withText("+"), childAtPosition(childAtPosition(withId(R.id.nav_host_fragment), 0), 1), isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(allOf(withId(R.id.buttonAdd), childAtPosition(allOf(withId(R.id.item_entry_layout), childAtPosition(withId(R.id.recyclerViewFood), 1)), 6), isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(allOf(withId(R.id.buttonRegister4), withText("+"), childAtPosition(childAtPosition(withId(R.id.nav_host_fragment), 0), 2), isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(allOf(withId(R.id.buttonAdd), childAtPosition(allOf(withId(R.id.item_entry_layout), childAtPosition(withId(R.id.recyclerViewFood), 2)), 6),
                isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction materialButton8 = onView(allOf(withId(R.id.buttonRegister5), withText("+"), childAtPosition(childAtPosition(withId(R.id.nav_host_fragment), 0), 3), isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction materialButton9 = onView(allOf(withId(R.id.buttonAdd), childAtPosition(allOf(withId(R.id.item_entry_layout), childAtPosition(withId(R.id.recyclerViewFood), 2)), 6), isDisplayed()));
        materialButton9.perform(click());
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
