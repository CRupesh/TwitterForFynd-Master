package com.fynd.twitter.twitterforfynd.AdapterClasses;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fynd.twitter.twitterforfynd.ModelClasses.FeedOfflin_Model;
import com.fynd.twitter.twitterforfynd.HolderClasses.Feed_ViewHolder;
import com.fynd.twitter.twitterforfynd.R;

import java.util.List;

public class Feed_RecyclerAdapter  extends RecyclerView.Adapter<Feed_ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    List<FeedOfflin_Model> tweets;
    Context context;
    public Feed_RecyclerAdapter(List<FeedOfflin_Model> tweets) {
        this.tweets = tweets;
    }

    @Override
    public Feed_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context  =  parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.feed_layout,parent,false);
        Feed_ViewHolder timeLineTweetViewHolder = new Feed_ViewHolder(view);

        return timeLineTweetViewHolder;
    }

    @Override
    public void onBindViewHolder(Feed_ViewHolder holder, int position) {

        populateItemRows(holder,position);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    private void populateItemRows(Feed_ViewHolder holder, int position) {

        /*Tweet tweet = tweets.get(position);

        holder.txt_UserTweet.setText(tweet.text);
        holder.txt_UserName.setText(tweet.user.name);
        Uri uri =  Uri.parse(tweet.user.profileImageUrlHttps);*/

        holder.txt_UserTweet.setText(tweets.get(position).getUserFeed());
        holder.txt_UserName.setText(tweets.get(position).getUserName());
        holder.tv_likes.setText(tweets.get(position).getNoOfLikes());
        holder.tv_retweet.setText(tweets.get(position).getNoOfRetweeted());

        Uri uri =  Uri.parse(tweets.get(position).getImageUrl());


        holder.img_UserImage.setImageURI(uri);

    }
}
