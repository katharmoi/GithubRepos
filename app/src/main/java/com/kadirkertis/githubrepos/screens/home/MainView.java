package com.kadirkertis.githubrepos.screens.home;


import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent;
import com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.githubService.model.User;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

public interface MainView {

    Observable<TextViewTextChangeEvent> observeTextChange();

    Observable<AdapterViewItemClickEvent> observeUserClicked();

    Observable<Object> observeSuggestionListEnd();

    void displayMessage(String msg);

    void displayLodingDialog(boolean loading);

    void displayUserSuggestion(List<User> userList);

    void addToUserSuggestionList(List<User> userList);

    void displayReposList(List<GithubRepo> repos);

    void displayAutoCompleteSpinner(boolean loading);

    void displayUserAvatar(String avatarUrl);

    void displayUserNameOnToolbar(String userName);

    void hideKeyboard();

    String getUserName();

    void displayResponseAsToast(String result);
}
