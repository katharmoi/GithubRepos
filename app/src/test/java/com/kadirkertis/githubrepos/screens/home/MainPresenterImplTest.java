package com.kadirkertis.githubrepos.screens.home;

import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.repository.GithubRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Kadir Kertis on 7.8.2017.
 */
public class MainPresenterImplTest {
    MainPresenterImpl mainPresenter;
    GithubRepository mockrepository;
    MainViewImpl mockView;

    List<GithubRepo> mockRepolist = Collections.emptyList();
    @Before
    public void setUp() throws Exception {
        mockrepository = mock(GithubRepository.class);
        mockView = mock(MainViewImpl.class);
        mainPresenter = new MainPresenterImpl(mockView,mockrepository);

        when(mockView.getUserName()).thenReturn("Duffy Duck");
        when(mockView.observeBtn()).thenReturn(Observable.never());

        when(mockrepository.getRepoForUser(anyString())).thenReturn(Observable.just(mockRepolist));

    }

    @Test
    public void shouldNotSendANetworkRequestWhenUserEmpty(){

    }

    @Test
    public void shouldDisplayRepoWhenUservalid(){

    }


}