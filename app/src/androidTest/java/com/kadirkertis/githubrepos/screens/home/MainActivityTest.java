package com.kadirkertis.githubrepos.screens.home;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ProgressBar;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.kadirkertis.githubrepos.R;
import com.kadirkertis.githubrepos.app.NetworkModule_ProvideOkHttpClientFactory;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Kadir Kertis on 7.8.2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private OkHttpClient client = new OkHttpClient.Builder().build();
    private IdlingResource idlingResource ;
    @Before
    public void setUp(){
        idlingResource = OkHttp3IdlingResource.create("okhttp",client);
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void clickingLookUpBtnShouldNotStartProgressBarIfUserTextEmpty(){
        onView(withId(R.id.btn_lookup)).perform(click());
        onView(withText("Please wait while getting user info")).check(matches(not(isDisplayed())));
    }

    @Test
    public void clickingLookUpBtnShouldNotStartProgressBarIfUserTextLessThan4Chars(){
        onView(withId(R.id.text_username)).perform(typeText("kat"));
        onView(withId(R.id.btn_lookup)).perform(click());
        onView(withText("Please wait while getting user info")).check(matches(not(isDisplayed())));
    }

    @Test
    public void successfulSearchShouldPopulateRecyclerView(){
        onView(withId(R.id.text_username)).perform(typeText("katharmoi"));
        onView(withId(R.id.btn_lookup)).perform(click());
        onView(withText("Orfo")).check(matches(isDisplayed()));
    }

    @After
    public void clear(){
        if(idlingResource !=null){
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }



}