package edu.sharif.ce.android_weather_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import edu.sharif.ce.android_weather_app.Model.MainWeather;
import edu.sharif.ce.android_weather_app.Model.Weather;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ListItem> list;
    private SelectListener listener;

    public RecyclerViewAdapter(Context context, List<ListItem> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dayNameTextView.setText(list.get(position).getDayName());
        holder.minTempTextView.setText(list.get(position).getMinTemp());
        holder.maxTempTextView.setText(list.get(position).getMaxTemp());
        Context context = holder.iconImageView.getContext();
        int iconId = context.getResources().getIdentifier(
                list.get(position).getWeatherCondition().toString().toLowerCase(Locale.ROOT),
                "drawable", context.getPackageName());
        holder.iconImageView.setImageResource(iconId);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayNameTextView, minTempTextView, maxTempTextView;
        ImageView iconImageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayNameTextView = itemView.findViewById(R.id.recyclerviewDayNameTextView);
            minTempTextView = itemView.findViewById(R.id.recyclerviewMinTempTextView);
            maxTempTextView = itemView.findViewById(R.id.recyclerviewMaxTempTextView);
            iconImageView = itemView.findViewById(R.id.recyclerviewIconImageView);
            cardView = itemView.findViewById(R.id.mainContainerCardView);
        }
    }

    public static class ListItem {
        private final Weather weather;
        private MainWeather weatherCondition;
        private String dayName;
        private String minTemp;
        private String maxTemp;
        private String moonPhase;
        private String feelsLike;
        private String windSpeed;
        private String humidity;

        @SuppressLint("DefaultLocale")
        public ListItem(Weather weather) {
            this.weather = weather;
            this.dayName = weather.getDay();
            this.weatherCondition = weather.getWeather();
            this.minTemp = String.format("%.0f", weather.getLowTemperature()) + "\u00B0";
            this.maxTemp = String.format("%.0f", weather.getHighTemperature()) + "\u00B0";
            this.feelsLike = String.format("%.0f", weather.getFeelsLike()) + "\u00B0";
            this.windSpeed = String.format("%.1f", weather.getWindSpeed()) + " km/s";
            this.moonPhase = String.format("%.0f", weather.getMoonPhase() * 100) + "%";
            this.humidity = String.format("%.0f", weather.getHumidity()) + "%";
        }

        public MainWeather getWeatherCondition() {
            return weatherCondition;
        }

        public String getDayName() {
            return dayName;
        }

        public String getMinTemp() {
            return minTemp;
        }

        public String getMaxTemp() {
            return maxTemp;
        }

        public String getMoonPhase() {
            return moonPhase;
        }

        public String getFeelsLike() {
            return feelsLike;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public String getHumidity() {
            return humidity;
        }
    }

    public interface SelectListener {
        void onItemClicked(ListItem listItem);
    }
}
