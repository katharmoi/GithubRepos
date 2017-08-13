package com.kadirkertis.githubrepos.screens.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kadirkertis.githubrepos.R;
import com.kadirkertis.githubrepos.githubService.model.GithubRepo;
import com.kadirkertis.githubrepos.repository.GithubRepository;
import com.kadirkertis.githubrepos.utility.EmptyRecyclerView;

import java.util.List;

/**
 * Created by Kadir Kertis on 6.8.2017.
 */

public class ReposAdapter extends EmptyRecyclerView.Adapter<ReposAdapter.ViewHolder> {
    private List<GithubRepo> reposList;

    public ReposAdapter(List<GithubRepo> reposList) {
        this.reposList = reposList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GithubRepo item = reposList.get(position);
        holder.repoNameTxt.setText(item.getName());
        holder.repoDescText.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }

    public void addItem(GithubRepo item){
        reposList.add(item);
        notifyItemInserted(reposList.size() -1);
    }

    public void addAll(List<GithubRepo> items){
        reposList.addAll(items);
        notifyDataSetChanged();
    }

    public void clear(){
        int size = reposList.size();
        reposList.clear();
        notifyItemRangeRemoved(0,size);

    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView repoNameTxt;
        private TextView repoDescText;
        public ViewHolder(View itemView) {
            super(itemView);
            repoNameTxt = (TextView) itemView.findViewById(R.id.txt_repo_header);
            repoDescText = (TextView) itemView.findViewById(R.id.txt_repo_desc);
        }
    }
}
