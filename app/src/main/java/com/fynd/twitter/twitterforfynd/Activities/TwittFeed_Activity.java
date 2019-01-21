package com.fynd.twitter.twitterforfynd.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fynd.twitter.twitterforfynd.AdapterClasses.Feed_RecyclerAdapter;
import com.fynd.twitter.twitterforfynd.ModelClasses.FeedOfflin_Model;
import com.fynd.twitter.twitterforfynd.Network.TwitterClientAPI;
import com.fynd.twitter.twitterforfynd.R;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwittFeed_Activity extends AppCompatActivity {

    @BindView(R.id.txt_UserName)
    TextView txt_UserName;

    @BindView(R.id.rc_FeedList)
    RecyclerView rc_FeedList;

    @BindView(R.id.swipe_refreshFeed)
    SwipeRefreshLayout swipe_refreshFeed;

    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;

    Realm mRealm;
    private boolean doubleBackToExitPressedOnce = false;

    Feed_RecyclerAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitt_feed);
        ButterKnife.bind(this);
        Fresco.initialize(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        swipe_refreshFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTwitterFeed(200);
            }
        });
    }

    public void getTwitterFeed(int count){

        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (twitterSession != null) {

            TwitterClientAPI clientAPI = new TwitterClientAPI(twitterSession);
            Call<List<Tweet>> userFeeds = clientAPI.getCustomService().getUserTimeline(count);//apiClient.getServiceApi().getUserTimeline(100);//

            userFeeds.enqueue(new Callback<List<Tweet>>() {
                @Override
                public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {

                    progress_bar.setVisibility(View.GONE);
                    swipe_refreshFeed.setVisibility(View.VISIBLE);


                    if (response.isSuccessful()) {
                        swipe_refreshFeed.setEnabled(true);
                        swipe_refreshFeed.setRefreshing(false);
                        init_RealM();
                        mRealm.beginTransaction();
                        for (int i=0; i<response.body().size(); i++){
                            Tweet tweet = response.body().get(i);
                            FeedOfflin_Model feedOfflinModel = mRealm.createObject(FeedOfflin_Model.class);
                            feedOfflinModel.setUserName(tweet.user.name);
                            feedOfflinModel.setUserFeed(tweet.text);
                            feedOfflinModel.setImageUrl(tweet.user.profileImageUrl);
                            feedOfflinModel.setNoOfRetweeted(""+tweet.retweetCount);
                            feedOfflinModel.setNoOfLikes(""+tweet.favoriteCount);
                        }
                        mRealm.commitTransaction();
                        List<FeedOfflin_Model> list = mRealm.where(FeedOfflin_Model.class).findAll();
                        show_TwitFeeds(list);
                    }
                    else {
                        onFeedLoadFail();
                    }
                }

                @Override
                public void onFailure(Call<List<Tweet>> call, Throwable t) {
                    onFeedLoadFail();
                }
            });
        }
    }

    public void show_TwitFeeds(List<FeedOfflin_Model> tweets){
        try {
            recycleAdapter = new Feed_RecyclerAdapter(tweets);
            rc_FeedList.setAdapter(recycleAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onFeedLoadFail(){
        swipe_refreshFeed.setEnabled(true);
        swipe_refreshFeed.setRefreshing(false);
        progress_bar.setVisibility(View.GONE);
        swipe_refreshFeed.setVisibility(View.VISIBLE);

        List<FeedOfflin_Model> list = mRealm.where(FeedOfflin_Model.class).findAll();
        if (list != null && list.size()!=0)
            show_TwitFeeds(list);//response.body());

        Toast.makeText(TwittFeed_Activity.this, "Fail to refresh user feeds\nSwipe screen down to refresh feeds", Toast.LENGTH_LONG).show();
    }

    private void init(){

        mRealm = Realm.getDefaultInstance();

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        txt_UserName.setText(String.format("%s, %s", "Welcome", session.getUserName()));

        progress_bar.setVisibility(View.VISIBLE);
        swipe_refreshFeed.setVisibility(View.GONE);
        getTwitterFeed(200);

        swipe_refreshFeed.setEnabled(false);
        swipe_refreshFeed.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

    }

    private void init_RealM(){
        mRealm.beginTransaction();
        mRealm.delete(FeedOfflin_Model.class);
        mRealm.commitTransaction();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            System.exit(0);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
