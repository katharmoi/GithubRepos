package com.kadirkertis.githubrepos.screens;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kadirkertis.githubrepos.R;
import com.kadirkertis.githubrepos.githubService.model.User;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.EasyMock2Matchers.equalTo;

/**
 * Created by Kadir Kertis on 14.8.2017.
 */
public class GithubReposMatchers {

    public static Matcher<View> withUserName(final String expected){
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if(item != null && item.findViewById(R.id.txt_user_name) != null) {
                    TextView userNameTextView = (TextView) item.findViewById(R.id.txt_user_name);
                    return !TextUtils.isEmpty(userNameTextView.getText()) && userNameTextView.getText().equals(expected);
                 }else{
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with user name " +expected);
            }
        };
    }

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }


    public static Matcher<User> withUserName(Matcher<? super String> matcher){
        return new FeatureMatcher<User,String>( matcher,"stringThat","stringThis") {

            @Override
            protected String featureValueOf(User actual) {
                return actual.getLogin();
            }
        };
    }


    public static Matcher<User> withUserId(final int id){
        return new BaseMatcher<User>() {
            @Override
            public boolean matches(Object item) {
                final User user = (User) item;
                return user.getId()== id ;
            }

            @Override
            public void describeTo(Description description) {
                    description.appendText("with user id :" + id);
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendText("was").appendText(String.valueOf(((User)item).getId()));
            }
        };
    }

    public static Matcher<User> withUserAvatar(final String userAvatarUrl){
        return new TypeSafeMatcher<User>() {
            @Override
            protected boolean matchesSafely(User item) {
                return item.getAvatarUrl().equals(userAvatarUrl);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }


}
