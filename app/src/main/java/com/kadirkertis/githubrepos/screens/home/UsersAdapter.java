package com.kadirkertis.githubrepos.screens.home;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.kadirkertis.githubrepos.R;
import com.kadirkertis.githubrepos.app.Event;
import com.kadirkertis.githubrepos.app.MyBus;
import com.kadirkertis.githubrepos.githubService.model.User;
import com.kadirkertis.githubrepos.utility.Constants;

import java.util.Collection;
import java.util.List;

/**
 * Created by Kadir Kertis on 11.8.2017.
 */

public class UsersAdapter extends ArrayAdapter<User> implements Filterable {
    private List<User> userList;


    public UsersAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> userList) {
        super(context, resource, userList);
        this.userList = userList;
    }

    private static class ViewHolder{
        TextView userView;
        ImageView userAvatar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_user,parent,false);

            viewHolder.userView = (TextView) convertView.findViewById(R.id.txt_user_name);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Send Sıgnal if the end of the list reached
        boolean endReached = position == userList.size() -1;
        if(endReached){
            MyBus.getInstance().post(Constants.Events.END_OF_LIST_REACHED_EVENT,userList.size());
        }
        User user = getItem(position);

        assert user != null;
        viewHolder.userView.setText(user.getLogin());

        convertView.setTag(viewHolder);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null){

                    filterResults.values = userList;
                    filterResults.count = userList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                if(filterResults != null && filterResults.count > 0){
                    notifyDataSetChanged();
                }else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    static class PageEndReached{
        private int listSize;

        public int getListSize() {
            return listSize;
        }
    }
}
