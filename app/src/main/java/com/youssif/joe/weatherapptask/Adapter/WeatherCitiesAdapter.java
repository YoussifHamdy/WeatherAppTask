package com.youssif.joe.weatherapptask.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.youssif.joe.weatherapptask.Commen.Common;
import com.youssif.joe.weatherapptask.MainActivity;
import com.youssif.joe.weatherapptask.Model.WeatherCitiesResult;
import com.youssif.joe.weatherapptask.R;


public class WeatherCitiesAdapter extends RecyclerView.Adapter<WeatherCitiesAdapter.MyViewHolder> {

    Context context;
    WeatherCitiesResult weatherCitiesResult;

    public WeatherCitiesAdapter(Context context , WeatherCitiesResult weatherCitiesResult){
        this.context = context ;
        this.weatherCitiesResult = weatherCitiesResult;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate( R.layout.item_weather_cities , parent , false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //Load image
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherCitiesResult.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(holder.img_weather);




        //Load data
        holder.txt_city_name.setText(new StringBuilder(weatherCitiesResult.list.get(position).name));

        holder.txt_description.setText(new StringBuilder(weatherCitiesResult.list.get(position).weather.get(0).getDescription()));

        holder.txt_temperature.setText(new StringBuilder(String.valueOf(weatherCitiesResult.list.get(position).main.getTemp())).append("°C"));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.lat = String.valueOf(weatherCitiesResult.list.get(position).coord.getLat());
                Common.lon = String.valueOf(weatherCitiesResult.list.get(position).coord.getLon());

                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);

            }
        });


    }



    @Override
    public int getItemCount() {
        return weatherCitiesResult.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_city_name , txt_description , txt_temperature;
        ImageView img_weather;

        public MyViewHolder(View itemView) {
            super(itemView);


            img_weather = itemView.findViewById(R.id.img_weather);
            txt_city_name = itemView.findViewById(R.id.txt_city_name);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_temperature = itemView.findViewById(R.id.txt_temperature);
        }
    }
}
