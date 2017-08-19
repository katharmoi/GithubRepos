package com.kadirkertis.githubrepos.screens.home;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.kadirkertis.githubrepos.R;
import com.kadirkertis.githubrepos.githubService.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.kadirkertis.githubrepos.screens.GithubReposMatchers.withUserName;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Kadir Kertis on 7.8.2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() {

    }

    @Test
    public void enteringLessThan4CharactersShouldNotStartSuggestionsList() {
        onView(withId(R.id.txt_search_box))
                .perform(typeText("kat"));
        onData(allOf(is(instanceOf(User.class))))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void entering4OrMoreCharactersShouldStartSuggestionsList() throws InterruptedException {
        onView(withId(R.id.txt_search_box))
                .perform(typeText("kath"));
        onData(allOf(isA(User.class),withUserName(is("kathincolour"))))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void successfulSearchShouldPopulateRecyclerView() {
        onView(withId(R.id.txt_search_box)).perform(typeText("kath"));
        onData(allOf(isA(User.class),withUserName(is("kathincolour"))))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());

        onView(withText("kathincolour")).check(matches(isDisplayed()));

    }

    @Test
    public void successfulSearchShouldLoadUserAvatar() {
        onView(withId(R.id.txt_search_box)).perform(typeText("kath"));

        onData(allOf(withUserName(is("kathincolour"))))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());

        onView(withText("kathincolour")).check(matches(isDisplayed()));

    }

    @Test
    public void clickingShouldCloseAutoCompleteWindow(){
        onView(withId(R.id.txt_search_box)).perform(typeText("kath"));
        onData(allOf(withUserName(is("kathincolour"))))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
//        onView(withText("kath"))
//                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
//                .check(matches(not(isDisplayed())));
    }

    @After
    public void tearDown() {

    }


}