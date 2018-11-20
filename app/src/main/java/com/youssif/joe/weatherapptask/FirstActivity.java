package com.youssif.joe.weatherapptask;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.youssif.joe.weatherapptask.Adapter.WeatherCitiesAdapter;
import com.youssif.joe.weatherapptask.Commen.Common;
import com.youssif.joe.weatherapptask.Model.WeatherCitiesResult;
import com.youssif.joe.weatherapptask.Retrofit.IOpenWeatherMap;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class FirstActivity extends AppCompatActivity {

    private Toolbar toolbar;


    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,ContactActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.citiesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL , false));

        setupRetrofitAndOkHttp();
        getCitiesWeatherInformation();
        
    }

    private void setupRetrofitAndOkHttp() {

        Cache cache = new Cache(getCacheDir(), Common.cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain)
                            throws IOException {
                        Request request = chain.request();
                        if (!isNetworkAvailable()) {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
                            request = request
                                    .newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());


        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = builder.build();
        mService = retrofit.create(IOpenWeatherMap.class);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    private void getCitiesWeatherInformation() {




        compositeDisposable.add(mService.getCitiesWeatherByID(
                "524901,703448,2643743,4396915,6155070" ,
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherCitiesResult>() {
                    @Override
                    public void accept(WeatherCitiesResult weatherCitiesResult) throws Exception {

                        WeatherCitiesAdapter adapter = new WeatherCitiesAdapter(FirstActivity.this,weatherCitiesResult);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR22" ,"" +throwable.getMessage());

                    }
                })

        );
    }
}
