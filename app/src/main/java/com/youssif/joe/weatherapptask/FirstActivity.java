package com.youssif.joe.weatherapptask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.youssif.joe.weatherapptask.Adapter.WeatherCitiesAdapter;
import com.youssif.joe.weatherapptask.Commen.Common;
import com.youssif.joe.weatherapptask.Model.WeatherCitiesResult;
import com.youssif.joe.weatherapptask.Retrofit.IOpenWeatherMap;
import com.youssif.joe.weatherapptask.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

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


        recyclerView = findViewById(R.id.citiesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL , false));
        
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);


        getCitiesWeatherInformation();
        
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
