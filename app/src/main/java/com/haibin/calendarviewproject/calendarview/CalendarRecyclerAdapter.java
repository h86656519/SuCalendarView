package com.haibin.calendarviewproject.calendarview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.haibin.calendarviewproject.R;
import java.util.ArrayList;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.ViewHolder> {
    public CalendarRecyclerAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
    }

    public Context context;
    public List<String> data = new ArrayList<>();
    @NonNull
    @Override
    public CalendarRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CalendarRecyclerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_month, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.simple_month_view.setDate(2018, 3);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleMonthView3 simple_month_view;
        ViewHolder(final View itemView) {
            super(itemView);
            simple_month_view = itemView.findViewById(R.id.simple_month_view);
        }

    }
}
