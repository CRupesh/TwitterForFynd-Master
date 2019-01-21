package com.fynd.twitter.twitterforfynd.Network;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

public class TwitterClientAPI extends TwitterApiClient {

    public TwitterClientAPI (TwitterSession session){
        super(session);
    }

    public ServiceApi getCustomService(){
        TwitterApiClient twitterApiClient = new TwitterApiClient();
        return getService(ServiceApi.class);

    }
}
