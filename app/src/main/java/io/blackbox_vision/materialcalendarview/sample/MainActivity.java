package io.blackbox_vision.materialcalendarview.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;

import butterknife.ButterKnife;
import io.blackbox_vision.materialcalendarview.view.CalendarView;
import io.blackbox_vision.materialcalendarview.view.DayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public final class MainActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView textView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        fab.setOnClickListener(this::onClick);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.shouldAnimateOnEnter(true);
        calendarView.setIsOverflowDateVisible(true);
        calendarView.setCurrentDay(new Date(System.currentTimeMillis()));
        calendarView.setBackButtonColor(R.color.colorAccent);
        calendarView.setNextButtonColor(R.color.colorAccent);
        calendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()));
        calendarView.setOnDateLongClickListener(selectedDate -> {
            final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            textView.setText(df.format(selectedDate));
        });

        calendarView.setOnMonthChangeListener(monthDate -> {
            final SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

            if (null != actionBar) {
                actionBar.setTitle(df.format(monthDate));
            }
        });

        calendarView.setOnMonthTitleClickListener(monthDate -> {});

        final DayView dayView = calendarView.findViewByDate(new Date(System.currentTimeMillis()));

        if (null != dayView) {
            final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            final Date date = dayView.getDay().toDate();

            Toast.makeText(getApplicationContext(), "Today is: " + df.format(date), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
