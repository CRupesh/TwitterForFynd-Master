package com.fynd.twitter.twitterforfynd.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fynd.twitter.twitterforfynd.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Auth_Activity extends AppCompatActivity {

    @BindView(R.id.btn_oAuth)
    TwitterLoginButton btn_oAuth;

    TwitterSession twitterSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ButterKnife.bind(this);

        twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (twitterSession != null){
            Intent intent = new Intent(Auth_Activity.this, TwittFeed_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        btn_oAuth.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = twitterSession.getAuthToken();

                //
                Log.d("twitter", twitterSession.getUserName());
                Intent intent = new Intent(Auth_Activity.this, TwittFeed_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(Auth_Activity.this, "Failed to authenticate, exception: "+ exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        btn_oAuth.onActivityResult(requestCode, resultCode, data);
    }
}
