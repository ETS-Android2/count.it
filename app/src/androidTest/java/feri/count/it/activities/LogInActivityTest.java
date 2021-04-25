package feri.count.it.activities;


import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Root;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import feri.count.it.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LogInActivityTest {

    @Rule
    public ActivityTestRule<LogInActivity> mActivityTestRule = new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void logInActivityTestFail()
    {
        ViewInteraction appCompatEditText = onView( allOf(withId(R.id.edtEmailLog), childAtPosition( childAtPosition( withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2),
                4), isDisplayed()));
        appCompatEditText.perform(replaceText("goran@gmail.com"), closeSoftKeyboard());
        ViewInteraction appCompatEditText2 = onView( allOf(withId(R.id.edtPassword), childAtPosition(childAtPosition( withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2),
                5), isDisplayed()));
        appCompatEditText2.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction materialButton = onView( allOf(withId(R.id.buttonLoginUser), withText("GO"), childAtPosition( childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                2), 6), isDisplayed()));
        materialButton.perform(click());
        //onView(withText("User does not exist!")).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        //ViewInteraction toastMessageDisplayed = onView(withText("User does not exist!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
        //toastMessageDisplayed.noActivity();
        //return;
    }

    @Test
    public void logInActivityTestEmptyEmail()
    {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.edtPassword),childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2),
                5), isDisplayed()));
        appCompatEditText.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.buttonLoginUser), withText("GO"), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                2), 6), isDisplayed()));
        materialButton.perform(click());
    }

    @Test
    public void logInActivityTestEmptyPassword()
    {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.edtEmailLog), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2),
                4), isDisplayed()));
        appCompatEditText.perform(replaceText("viki@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(allOf(withId(R.id.buttonLoginUser), withText("GO"), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                2), 6), isDisplayed()));
        materialButton.perform(click());
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
