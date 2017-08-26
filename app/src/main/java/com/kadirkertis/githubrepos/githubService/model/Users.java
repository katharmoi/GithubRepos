package com.kadirkertis.githubrepos.githubService.model;

import java.util.List;

/**
 * Created by Kadir Kertis on 11.8.2017.
 */

public class Users {
    private  int totalCount;
    private boolean incompleteResults;
    private List<User> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}
