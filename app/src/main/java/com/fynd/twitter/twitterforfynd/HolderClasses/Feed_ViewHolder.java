package com.fynd.twitter.twitterforfynd.HolderClasses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fynd.twitter.twitterforfynd.R;

public class Feed_ViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView img_UserImage;
    public TextView txt_UserName, txt_UserTweet,tv_retweet,tv_likes;


    public Feed_ViewHolder(@NonNull View itemView) {
        super(itemView);

        img_UserImage = itemView.findViewById(R.id.iv_user_image);

        txt_UserName = itemView.findViewById(R.id.tv_user_name);

        txt_UserTweet = itemView.findViewById(R.id.tv_user_tweet);

        tv_retweet = itemView.findViewById(R.id.tv_retweet);

        tv_likes = itemView.findViewById(R.id.tv_likes);
    }
}
