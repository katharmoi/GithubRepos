package com.kadirkertis.githubrepos.screens.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.kadirkertis.githubrepos.R;
import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.utility.EmptyRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */
@SuppressLint("ViewConstructor")
public class MainViewImpl extends FrameLayout implements MainView {

    private final Picasso picasso;

    private AutoCompleteTextView userNameSearchView;
    private Toolbar toolbar;
    private EmptyRecyclerView repoRecycler;
    private ReposAdapter adapter;
    private UsersAdapter userAdapter;
    private CircleImageView avatarView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView emptyTextView;
    private Activity activity;

    private final ProgressDialog progressDialog = new ProgressDialog(getContext());
    private final ProgressBar progressAutoComplete;


    public MainViewImpl(Activity activity,Picasso picasso) {
        super(activity);
        inflate(getContext(), R.layout.activity_main, this);
        this.activity = activity;
        this.picasso = picasso;
        userNameSearchView = (AutoCompleteTextView) findViewById(R.id.txt_search_box);
        userAdapter = new UsersAdapter(getContext(), R.layout.list_item_user, new ArrayList<>(0));
        userNameSearchView.setAdapter(userAdapter);
        userNameSearchView.setThreshold(3);
        emptyTextView = (TextView) findViewById(R.id.emptyView);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
        avatarView = (CircleImageView) findViewById(R.id.circleImageView);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        adapter = new ReposAdapter(new ArrayList<>(0));
        repoRecycler = (EmptyRecyclerView) findViewById(R.id.reycler_main_repos);
        repoRecycler.setAdapter(adapter);
        repoRecycler.setEmptyView(emptyTextView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        repoRecycler.setLayoutManager(layoutManager);
        progressDialog.setMessage("Please wait while getting user info");
        progressAutoComplete = (ProgressBar)findViewById(R.id.progress_auto_complete);
    }

    public void displayMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLodingDialog(boolean loading) {
        if (loading) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }

    }

    @Override
    public void displayUserSuggestion(List<User> userList) {
        userAdapter.clear();
        userAdapter.addAll(userList);
    }

    @Override
    public void displayReposList(List<GithubRepo> repos) {
        adapter.clear();
        adapter.addAll(repos);
    }

    @Override
    public void displayAutoCompleteSpinner(boolean loading) {
        if (loading) {
            progressAutoComplete.setVisibility(VISIBLE);
        } else {
            progressAutoComplete.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void displayUserAvatar(String avatarUrl) {
        picasso.load(avatarUrl).into(avatarView);

    }

    @Override
    public void displayUserNameOnToolbar(String userName) {
        collapsingToolbarLayout.setTitle(userName);
    }
    @Override
    public String getUserName() {
        return userNameSearchView.getText().toString();
    }

    @Override
    public void displayResponseAsToast(String result) {
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
    }


    @Override
    public Observable<TextViewTextChangeEvent> observeTextChange() {
        return RxTextView.textChangeEvents(userNameSearchView);
    }

    @Override
    public Observable<AdapterViewItemClickEvent> observeUserClicked() {
        return RxAutoCompleteTextView.itemClickEvents(userNameSearchView);
    }

    @Override
    public void hideKeyboard() {

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
