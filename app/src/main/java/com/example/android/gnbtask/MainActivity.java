package com.example.android.gnbtask;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements ExploreSightAdapter.ListItemClickListener{

    //Base API Url of the GNB API
    final static String API_BASE_URL = "http://grapes-n-berries.getsandbox.com/sightsofegypt/";

    final static String TAG = MainActivity.class.getSimpleName();

    final int COUNT = 10;

    final private int FROM = 0;

    final static int SPAN_COUNT = 2;


    private ExploreSightAdapter.ListItemClickListener mClickListener = this;

    //Create an ExploreSightAdapter and a RecyclerView variable
    private ExploreSightAdapter mSightAdapter;
    private RecyclerView mSightList;

    TextView mEmptyStateTextView;
    View mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create a reference from the RecyclerView and store our member variable
        mSightList = (RecyclerView) findViewById(R.id.rv_explore_sight);

        //Create LayoutManager and set our RecyclerView to mentioned LayoutManager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mSightList.setLayoutManager(layoutManager);
        mSightList.setHasFixedSize(true);

        mEmptyStateTextView = (TextView) findViewById(R.id.tv_empty_view);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);


        //mSightAdapter = new ExploreSightAdapter(list);
        //mSightList.setAdapter(mSightAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);

        //Create Retrofit settings and set our endpoint String Url
        OkHttpClient httpClient = new OkHttpClient.Builder().cache(cache).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(
                        GsonConverterFactory.create()
                );

        Retrofit retrofit = builder.build();

        //Get REST adapter to point at our GNP API endpoint
        GNBApi client = retrofit.create(GNBApi.class);

        // Get the list of ExploreSights
        Call<List<ExploreSight>> call = client.getExploreSights(COUNT, FROM);

        //Execute the call and get a positive or negative callback, if positive use the returned list in UI
        call.enqueue(new Callback<List<ExploreSight>>() {
            @Override
            public void onResponse(Call<List<ExploreSight>> call, Response<List<ExploreSight>> response) {
                //network call was a success use the list to display in ui
                List<ExploreSight> list = response.body();

                if (response.body() != null) {
                    Log.v(TAG, response.body().toString());
                } else {Log.v(TAG, "response.body is null"); }

                if (list != null) {
                    mLoadingIndicator.setVisibility(View.GONE);
                    //Initialize Adapter with the returned list and set our RecyclerView to that Adapter
                    mSightAdapter = new ExploreSightAdapter(list, mClickListener);
                    mSightList.setAdapter(mSightAdapter);
                } else {
                    Log.v(TAG, "List is null");
                }
            }

            @Override
            public void onFailure(Call<List<ExploreSight>> call, Throwable t) {
                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    mLoadingIndicator.setVisibility(View.GONE);

                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
                //network call was a failure
                Log.e(TAG, "Network call failed", t);

                Log.d("fail", t.getMessage());

            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex, String description, int price, String imageUrl) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("description",description);
        intent.putExtra("price", String.valueOf(price));
        intent.putExtra("imageUrl",imageUrl);

        startActivity(intent);
    }
}
