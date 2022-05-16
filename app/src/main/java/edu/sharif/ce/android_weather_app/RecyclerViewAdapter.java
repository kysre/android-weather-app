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

import edu.sharif.ce.android_weather_app.Model.MainWeather;

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
        // TODO: set ImageView

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
        private String dayName;
        private String minTemp;
        private String maxTemp;
        private MainWeather weatherCondition;

        public ListItem(String dayName, String minTemp, String maxTemp, MainWeather weatherCondition) {
            this.dayName = dayName;
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            this.weatherCondition = weatherCondition;
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

        public MainWeather getWeatherCondition() {
            return weatherCondition;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public void setMinTemp(String minTemp) {
            this.minTemp = minTemp;
        }

        public void setMaxTemp(String maxTemp) {
            this.maxTemp = maxTemp;
        }

        public void setWeatherCondition(MainWeather weatherCondition) {
            this.weatherCondition = weatherCondition;
        }
    }

    public interface SelectListener {
        void onItemClicked(ListItem listItem);
    }
}
