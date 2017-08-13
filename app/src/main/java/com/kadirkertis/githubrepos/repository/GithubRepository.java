package com.kadirkertis.githubrepos.repository;

import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.githubService.model.GithubRepos;
import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.githubService.model.Users;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Kadir Kertis on 4.8.2017.
 */

public interface GithubRepository {

    Observable<List<GithubRepo>> getRepoForUser(String username);

    Observable<Users> getUsersWithName(String name);
}
