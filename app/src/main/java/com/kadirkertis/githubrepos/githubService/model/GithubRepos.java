package com.kadirkertis.githubrepos.githubService.model;

import java.util.List;

/**
 * Created by Kadir Kertis on 4.8.2017.
 */

public class GithubRepos {
    private List<GithubRepo> githubRepos;

    public List<GithubRepo> getGithubRepos() {
        return githubRepos;
    }

    public void setGithubRepos(List<GithubRepo> githubRepos) {
        this.githubRepos = githubRepos;
    }
}
