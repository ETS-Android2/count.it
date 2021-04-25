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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivitySuccess {

    @Rule
    public ActivityTestRule<LogInActivity> mActivityTestRule = new ActivityTestRule<>(LogInActivity.class);

    @Test
    public void registerActivitySuccess()
    {
        ViewInteraction materialButton = onView(allOf(withId(R.id.buttonRegister), withText("+"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 5), isDisplayed()));
        materialButton.perform(click());

        ViewInteraction supportVectorDrawablesButton = onView(allOf(withId(R.id.email_button), withText("Sign in with email"), childAtPosition(allOf(withId(R.id.btn_holder), childAtPosition(withId(R.id.container),
                0)), 0)));
        supportVectorDrawablesButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(allOf(withId(R.id.email), childAtPosition(childAtPosition(withId(R.id.email_layout), 0), 0)));
        textInputEditText.perform(scrollTo(), replaceText("jana.jankovic.feri@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(allOf(withId(R.id.button_next), withText("Next"), childAtPosition(allOf(withId(R.id.email_top_layout), childAtPosition(withClassName(is("android.widget.ScrollView")),
                0)), 2)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(allOf(withId(R.id.password), childAtPosition(childAtPosition(withId(R.id.password_layout), 0), 0)));
        textInputEditText2.perform(scrollTo(), replaceText("jebemTiGoogle2020"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(allOf(withId(R.id.button_done), withText("Sign in"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 4)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.edtEmail), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2), 4),
                isDisplayed()));
        appCompatEditText.perform(replaceText("email@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.edtEmailLog), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2),
                5), isDisplayed()));
        appCompatEditText2.perform(replaceText("email"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.edtPassword), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2), 6),
                isDisplayed()));
        appCompatEditText3.perform(replaceText("1111aAAA#"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(allOf(withId(R.id.buttonRegisterUser), withText("GO"), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                2), 7), isDisplayed()));
        materialButton4.perform(click());
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
