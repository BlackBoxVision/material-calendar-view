/*
 * Copyright (C) 2015 Jonatan E. Salas { link: http://the-android-developer.blogspot.com.ar }
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package materialcalendarview2.sample.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;

import materialcalendarview2.data.model.DayTime;
import materialcalendarview2.data.model.Event;
import materialcalendarview2.sample.activity.base.BaseActivity;
import materialcalendarview2.sample.presenter.MainPresenter;
import materialcalendarview2.sample.view.MainView;
import materialcalendarview2.sample.R;
import materialcalendarview2.view.MaterialCalendarView;
import materialcalendarview2.view.DayView;

import static materialcalendarview2.util.CalendarUtils.isSameMonth;
import static materialcalendarview2.sample.util.AnimationUtil.animate;

/**
 * @author jonatan.salas
 */
public class MainActivity extends BaseActivity implements MainView {
    private static final String DATE_TEMPLATE = "dd/MM/yyyy";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.textview)
    TextView textView;

    @Bind(R.id.calendar_view)
    MaterialCalendarView calendarView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @NonNull
    private final SimpleDateFormat formatter = new SimpleDateFormat(DATE_TEMPLATE, Locale.getDefault());

    @NonNull
    private String todayDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        getPresenter().addNavigationDrawer();
        getPresenter().addCalendarView();
        getPresenter().addTextView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().animate();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void prepareNavigationDrawer() {
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public void prepareTextView() {
        todayDate = getString(R.string.today) + " " + formatter.format(new Date(System.currentTimeMillis()));
        textView.setText(todayDate);
    }

    @Override
    public void prepareCalendarView() {
        calendarView.shouldAnimateOnEnter(true);
        calendarView.setOnDayViewClickListener(this::onDayViewClick);
        calendarView.setOnMonthChangeListener(this::onMonthChanged);
        calendarView.setOnDayViewStyleChangeListener(this::onDayViewStyleChange);
    }

    @Override
    public void animateViews() {
        animate(fab, getApplicationContext());
        animate(textView, getApplicationContext());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onDayViewClick(@NonNull View view, int year, int month, int dayOfMonth, @Nullable List<Event> eventList) {
        final Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        final String dateString = formatter.format(calendar.getTime());
        Snackbar.make(view, getString(R.string.selected_date) + " " + dateString, Snackbar.LENGTH_SHORT).show();
    }

    public void onDayViewStyleChange(@NonNull DayView dayView, @NonNull DayTime dayTime) {
    }

    public void onMonthChanged(@NonNull View view, int year, int month) {
        final Calendar calender = new GregorianCalendar(year, month - 1, month);
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final String message = getString(R.string.month_display) + " " + month + " " + getString(R.string.year_is) + " " + year;
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

        if (!isSameMonth(calender, calendar)) {
            textView.setText(getString(R.string.not_actual_month));
        } else {
            textView.setText(todayDate);
        }
    }
}
