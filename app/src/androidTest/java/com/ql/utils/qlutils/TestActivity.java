package com.ql.utils.qlutils;

import android.os.SystemClock;
import android.preference.DialogPreference;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.object.HasToString.hasToString;

/**
 * 创建时间:2017/12/14
 * 描述:
 *
 * @author ql
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestActivity {


    @Rule
    public ActivityTestRule<TestaActivity> mActivityRule = new ActivityTestRule<>(
            TestaActivity.class);

    @Test
    public void sayHello() {
        onView(withId(R.id.activity_test_et_account)).perform(typeText("account")); //line 1
        onView(withId(R.id.activity_test_et_pw)).perform(typeText("123456"), clearText(), typeText("654321"), pressKey(KeyEvent.KEYCODE_A));

        //onView(withId(R.id.activity_test_btn)).perform(click());
        onData(StringStartsWith.startsWith("item")).inAdapterView(withId(R.id.activity_test_lv)).atPosition(0).perform(click());
        SystemClock.sleep(1000);
        onView(withText("cancel")).perform(click());
        SystemClock.sleep(1000);
        onData(StringStartsWith.startsWith("item")).inAdapterView(withId(R.id.activity_test_lv)).atPosition(3).perform(click());
        SystemClock.sleep(1000);
        onView(withText("cancel")).perform(click());
        SystemClock.sleep(1000);
        onData(StringStartsWith.startsWith("item")).inAdapterView(withId(R.id.activity_test_lv)).atPosition(4).perform(click());
        SystemClock.sleep(1000);
        onView(withText("cancel")).perform(click());
        SystemClock.sleep(1000);
        onData(StringStartsWith.startsWith("item")).inAdapterView(withId(R.id.activity_test_lv)).atPosition(5).perform(click());
        onView(withText("cancel")).perform(click());
        SystemClock.sleep(5000);

    }


}
