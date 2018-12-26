package com.skripsi.gis.bekasipatriot.activity.MainActivity.FragmentNews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skripsi.gis.bekasipatriot.R;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by madeinsap on 3/23/2017.
 */

public class FragmentNews extends Fragment{

    RecyclerView recycler;
    private static final String TWITTER_KEY = "i2f1CXwMPmveUrghTMJPhCXiy";
    private static final String TWITTER_SECRET = "InE8nyajXMwzxDTkhz0JoKXuAvrhyBk3zqYOE6SBYvJ1zDR2Cy";

    public FragmentNews() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        TwitterConfig config = new TwitterConfig.Builder(getActivity())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET))//pass Twitter API Key and Secret
                .debug(true)
                .build();
        Twitter.initialize(config);

        UserTimeline userTimeline = new UserTimeline.Builder().screenName("pemkotbekasi").build();
        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(getActivity())
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightStyle)
                        .build();
        recycler.setAdapter(adapter);

        return view;
    }

}