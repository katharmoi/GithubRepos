package com.kadirkertis.githubrepos.screens.home;

import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.repository.GithubRepository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private static final int ATTEMPTS = 5;
    private final MainView view;
    private final GithubRepository githubRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MainPresenterImpl(MainViewImpl view, GithubRepository githubRepository) {
        this.view = view;
        this.githubRepository = githubRepository;
    }

    public void onCreate() {
        disposable.add(observeSearchViewTextChange());
        disposable.add(observeUserClicked());
    }

    public void onDestroy() {
        disposable.clear();
    }

    private Disposable observeSearchViewTextChange() {
        return view.observeTextChange()
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(textChangeEvent -> textChangeEvent.text().toString())
                .filter(this::notNullOrEmpty)
                .filter(username -> username.length() > 3)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s-> view.displayAutoCompleteSpinner(true))
                .observeOn(Schedulers.io())
                .switchMap(githubRepository::getUsersWithName)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(s-> view.displayAutoCompleteSpinner(false))
                .retryWhen(failures -> failures
                        .zipWith(Observable.range(1, ATTEMPTS), this::exponentialBackOff)
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
                .map(clicked ->(User) clicked.view().getAdapter().getItem(clicked.position()))
                .doOnNext(user -> view.displayUserAvatar(user.getAvatarUrl()))
                .doOnNext(user -> view.displayUserNameOnToolbar(user.getLogin()))
                .map(User::getLogin)
                .doOnNext(user -> view.displayLodingDialog(true))
                .observeOn(Schedulers.io())
                .switchMap(githubRepository::getRepoForUser)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(userRepo -> view.displayLodingDialog(false))
                .retryWhen(failures -> failures
                        .zipWith(Observable.range(1, ATTEMPTS), this::exponentialBackOff)
                        .flatMap(x -> x))
                .subscribe(view::displayReposList,

                        error -> {
                            view.displayMessage("Error Loading Repo");
                            Timber.e(error);
                        });
    }


    private boolean notNullOrEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    private Observable<Long> exponentialBackOff(Throwable error, int attempt) {
        switch (attempt) {
            case 1:
                return Observable.just(1L);
            case ATTEMPTS:
                return Observable.error(error);
            default:
                long expDelay = (long) Math.pow(2, attempt - 2);
                return Observable.timer(expDelay, TimeUnit.SECONDS);
        }
    }


}
