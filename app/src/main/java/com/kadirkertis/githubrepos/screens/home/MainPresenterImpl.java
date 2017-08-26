package com.kadirkertis.githubrepos.screens.home;

import com.kadirkertis.githubrepos.app.MyBus;
import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.repository.GithubRepository;
import com.kadirkertis.githubrepos.utility.Constants;
import com.kadirkertis.githubrepos.utility.Utilities;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private static final int ATTEMPTS = 5;
    private static final int USER_PER_PAGE = 30;
    private final MainView view;
    private final GithubRepository githubRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private PublishProcessor<Integer> paginator;
    private boolean requestActive = false;
    private int currentPage;

    public MainPresenterImpl(MainViewImpl view, GithubRepository githubRepository) {
        this.view = view;
        this.githubRepository = githubRepository;
    }

    public void onCreate() {
        disposable.add(observeSearchViewTextChange());
        disposable.add(observeUserClicked());
        disposable.add(observeUserListEndReached());
    }

    public void onDestroy() {
        disposable.clear();
    }

    private Disposable observeSearchViewTextChange() {
        return view.observeTextChange()
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(textChangeEvent -> textChangeEvent.text().toString())
                .filter(Utilities::notNullOrEmpty)
                .filter(username -> username.length() > 3)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> view.displayAutoCompleteSpinner(true))
                .observeOn(Schedulers.io())
                .switchMap(userName -> githubRepository.getUsersWithName(userName, 1, 30))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(s -> view.displayAutoCompleteSpinner(false))
                .retryWhen(failures -> failures
                        .zipWith(Observable.range(1, ATTEMPTS), Utilities::exponentialBackOff)
                        .flatMap(x -> x))
                .subscribe(
                        result -> {
                            view.displayUserSuggestion(result.getItems());
                        },
                        error -> {
                            view.displayMessage("Error Loading Repo");
                            Timber.e(error);
                        });
    }

    private Disposable observeUserClicked() {
        return view.observeUserClicked()
                .map(clicked -> (User) clicked.view().getAdapter().getItem(clicked.position()))
                .doOnNext(user -> {
                    view.displayUserAvatar(user.getAvatarUrl());
                    view.displayUserNameOnToolbar(user.getLogin());
                    view.hideKeyboard();
                })
                .map(User::getLogin)
                .doOnNext(user -> view.displayLodingDialog(true))
                .observeOn(Schedulers.io())
                .switchMap(githubRepository::getRepoForUser)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(userRepo -> view.displayLodingDialog(false))
                .retryWhen(failures -> failures
                        .zipWith(Observable.range(1, ATTEMPTS), Utilities::exponentialBackOff)
                        .flatMap(x -> x))
                .subscribe(view::displayReposList,

                        error -> {
                            view.displayMessage("Error Loading Repo");
                            Timber.e(error);
                        });
    }

    private Disposable observeUserListEndReached() {
        //TODO Add No more page check
        return MyBus.getInstance()
                .asFlowable()
                .filter(event -> event.getEventName().equals(Constants.Events.END_OF_LIST_REACHED_EVENT))
                .filter(event -> !requestActive)
                .doOnNext(event ->{
                    view.displayAutoCompleteSpinner(true);
                    currentPage=(int)event.getEvent()/USER_PER_PAGE +1;
                } )
                .map(event -> view.getUserName())
                .doOnNext(s ->requestActive = true)
                .observeOn(Schedulers.io())
                .switchMap(username -> githubRepository.getUsersWithName(username,currentPage, USER_PER_PAGE).toFlowable(BackpressureStrategy.LATEST))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(s -> view.displayAutoCompleteSpinner(false))
                .subscribe(result -> {
                            view.addToUserSuggestionList(result.getItems());
                            requestActive = false;
                            view.displayMessage("End Reached.Displaying PageNumber " +currentPage);
                        },
                        error -> {
                            view.displayMessage("Error Loading Repo" + "Source:observeUserListEndReached");
                            Timber.e(error);
                        }
                );

    }


}
