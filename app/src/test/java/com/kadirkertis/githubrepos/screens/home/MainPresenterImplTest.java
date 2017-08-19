package com.kadirkertis.githubrepos.screens.home;

import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.kadirkertis.githubrepos.RxImmediateSchedulerRule;
import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.githubService.model.Users;
import com.kadirkertis.githubrepos.repository.GithubRepository;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kadir Kertis on 7.8.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterImplTest {


    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();
    private static final String ERROR_MSG = "Error Loading Repo";

    MainPresenterImpl mainPresenter;

    @Mock
    GithubRepository mockrepository;

    @Mock
    MainViewImpl mockView;

    @Mock
    TextView tv;

    @Mock
    ListView mockListView;

    @Mock
    UsersAdapter mockAdapter;

    @Mock
    User mockUser;

    List<GithubRepo> mockRepolist = Collections.emptyList();
    Users mockUsers = new Users();
    @Before
    public void setUp() throws Exception {
        mainPresenter = new MainPresenterImpl(mockView,mockrepository);
        when(mockView.observeTextChange()).thenReturn(Observable.never());
        when(mockView.observeUserClicked()).thenReturn(Observable.never());
    }

    @Test
    public void shouldNotSendNetworkRequestWhenUserEmptyOrLessThan4Character(){
        mainPresenter.onCreate();
        verify(mockView,times(1)).observeTextChange();
        verify(mockrepository,never()).getRepoForUser(anyString());
    }

    @Test
    public void testObserveTextViewTextChange(){
        when(mockView.observeTextChange()).thenReturn(Observable.just(TextViewTextChangeEvent.create(tv,"kath",0
        ,3,4)));
        when(mockrepository.getUsersWithName("kath")).thenReturn(Observable.just(mockUsers));
        mainPresenter.onCreate();
        verify(mockView,times(1)).displayAutoCompleteSpinner(true);
        verify(mockrepository,times(1)).getUsersWithName(anyString());
        verify(mockView,times(2)).displayAutoCompleteSpinner(false);
    }

    @Test
    public void testObserveUserclicked(){
        when(mockListView.getAdapter()).thenReturn(mockAdapter);
        when(mockAdapter.getItem(0)).thenReturn(mockUser);
        when(mockUser.getLogin()).thenReturn("katharmoi");
        when(mockUser.getAvatarUrl()).thenReturn("some_url");
        when(mockView.observeUserClicked()).thenReturn(Observable.just(AdapterViewItemClickEvent.create(mockListView,tv,0,0)));
        when(mockrepository.getRepoForUser("katharmoi")).thenReturn(Observable.just(mockRepolist));

        mainPresenter.onCreate();

        verify(mockView,times(1)).displayUserAvatar(anyString());
        verify(mockView,times(1)).displayUserNameOnToolbar(anyString());
        verify(mockView,times(1)).hideKeyboard();
        verify(mockView,times(1)).displayLodingDialog(true);
        verify(mockrepository,times(1)).getRepoForUser(anyString());
        verify(mockView,times(2)).displayLodingDialog(false); // 2
        verify(mockView,times(1)).displayReposList(mockRepolist);

    }

    @Test
    public void shouldTextChangeRetryInvokeFiveTimesInCaseOfFailure(){

        when(mockView.observeTextChange()).thenReturn(Observable.just(TextViewTextChangeEvent.create(tv,"kath",0
                ,3,4)));
        when(mockrepository.getUsersWithName("kath")).thenReturn(Observable.error(new RuntimeException("Test Exception")));
        mainPresenter.onCreate();
        verify(mockView,times(5)).displayAutoCompleteSpinner(true);
        verify(mockrepository,times(5)).getUsersWithName(anyString());
        verify(mockView,times(5)).displayAutoCompleteSpinner(false);
        verify(mockView,times(1)).displayMessage(ERROR_MSG);

    }

    @Test
    public void shouldObserveUserClickedInvokeFiveTimesInCaseOfFailure(){
        when(mockListView.getAdapter()).thenReturn(mockAdapter);
        when(mockAdapter.getItem(0)).thenReturn(mockUser);
        when(mockUser.getLogin()).thenReturn("katharmoi");
        when(mockUser.getAvatarUrl()).thenReturn("some_url");
        when(mockView.observeUserClicked()).thenReturn(Observable.just(AdapterViewItemClickEvent.create(mockListView,tv,0,0)));
        when(mockrepository.getRepoForUser("katharmoi")).thenReturn(Observable.error(new RuntimeException("Test Exception")));

        mainPresenter.onCreate();

        verify(mockView,times(5)).displayUserAvatar(anyString());
        verify(mockView,times(5)).displayUserNameOnToolbar(anyString());
        verify(mockView,times(5)).hideKeyboard();
        verify(mockView,times(5)).displayLodingDialog(true);
        verify(mockrepository,times(5)).getRepoForUser(anyString());
        verify(mockView,times(5)).displayLodingDialog(false);
        verify(mockView,times(0)).displayReposList(mockRepolist);
        verify(mockView,times(1)).displayMessage(ERROR_MSG);

    }


}