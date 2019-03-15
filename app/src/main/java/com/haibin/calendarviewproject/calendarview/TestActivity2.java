package com.haibin.calendarviewproject.calendarview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;


import com.haibin.calendarviewproject.R;
import com.haibin.calendarviewproject.base.activity.BaseActivity;
import com.haibin.calendarviewproject.calendarview.CalendarRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity2 extends AppCompatActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    RecyclerView calendarRecyclerView;
    CalendarRecyclerAdapter calendarRecyclerAdapter;
    Context mcontent;

    public TestActivity2() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_test2);
        initView(this);
    }
//    public static void show(Context context) {
//        context.startActivity(new Intent(context, TestActivity2.class));
//
//    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {

    }

    //    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_test;
//    }
//
//    @Override
    protected void initView(Context context) {
//        SimpleMonthView3 simpleMonthView3 = new SimpleMonthView3(context);
//        simpleMonthView3.setDate(2018, 3);

        List<String> data = new ArrayList();
        data.add("SSSS");
        calendarRecyclerAdapter = new CalendarRecyclerAdapter(this, data);
        calendarRecyclerView = (RecyclerView) findViewById(R.id.calendarRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        calendarRecyclerView.setLayoutManager(linearLayoutManager);
        calendarRecyclerView.setAdapter(calendarRecyclerAdapter);
        calendarRecyclerAdapter.notifyDataSetChanged();
    }

//    @Override
//    protected void initData() {
//
//    }
}
