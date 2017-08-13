package com.kadirkertis.githubrepos.repository;

import android.app.Activity;

import com.kadirkertis.githubrepos.githubService.GithubApi;
import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.githubService.model.GithubRepos;
import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.githubService.model.Users;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Kadir Kertis on 4.8.2017.
 */

public class GithubRepositoryImpl implements GithubRepository {
    private final GithubApi githubApi;

    public GithubRepositoryImpl(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    @Override
    public Observable<List<GithubRepo>> getRepoForUser(String username) {
        return githubApi.getRepos(username);
    }

    @Override
    public Observable<Users> getUsersWithName(String name) {
        return githubApi.getUsers(name);
    }
}
