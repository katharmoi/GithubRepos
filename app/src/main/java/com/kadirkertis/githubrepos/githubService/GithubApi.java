package com.kadirkertis.githubrepos.githubService;



import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.githubService.model.GithubRepos;
import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.githubService.model.Users;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */


public interface GithubApi {

    @GET("users/{username}/repos")
    public Observable<List<GithubRepo>> getRepos(@Path("username") String username);

    @GET("search/users")
    public Observable<Users> getUsers(
            @Query("q") String username
    );
}
