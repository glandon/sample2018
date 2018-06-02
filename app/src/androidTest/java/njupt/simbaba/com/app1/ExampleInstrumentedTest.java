package njupt.simbaba.com.app1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import njupt.simbaba.com.applib.A1;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;

import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<A1> vmActivityRule = new ActivityTestRule<>(A1.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("njupt.simbaba.com.app1", appContext.getPackageName());
    }

    /**
     * https://developer.android.google.cn/training/testing/espresso/basics
     */
    @Test
    public void checkActivityIdTest() {
        onView(withId(R.id.name_of_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.name_of_activity)).check(matches(withSubstring("1000")));
    }
}
