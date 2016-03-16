package com.materialcalendarview.sample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.android.support.calendar.util.ScreenUtility;
import com.android.support.calendar.wrapper.ViewHolderWrapper;
import com.android.support.calendar.model.DayTime;
import com.android.support.calendar.model.Event;
import com.android.support.calendar.util.CalendarUtility;
import com.android.support.calendar.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jonatan.salas
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    CalendarView calendarView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        animate(fab);
        animate(textView);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        final SimpleDateFormat formatter = new SimpleDateFormat(DATE_TEMPLATE, Locale.getDefault());

        calendarView.shouldAnimateOnEnter(true);
        calendarView.setOnDayViewClickListener(new CalendarView.OnDayViewClickListener() {
            @Override
            public void onDayViewClick(@NonNull View view, int year, int month, int dayOfMonth, @Nullable List<Event> eventList) {
                final Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                final String dateString = formatter.format(calendar.getTime());
                Snackbar.make(view, getString(R.string.selected_date) + " " + dateString, Snackbar.LENGTH_SHORT).show();
            }
        });

        final String todayDate = getString(R.string.today) + " " + formatter.format(new Date(System.currentTimeMillis()));
        textView.setText(todayDate);

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChanged(@NonNull View view, int year, int month) {
                final Calendar calender = new GregorianCalendar(year, month - 1, month);
                final Calendar calendar = Calendar.getInstance(Locale.getDefault());
                final String message = getString(R.string.month_display) + " " + month + " " + getString(R.string.year_is) + " " + year;
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

                if (!CalendarUtility.isSameMonth(calender, calendar)) {
                    textView.setText(getString(R.string.not_actual_month));
                } else {
                    textView.setText(todayDate);
                }
            }
        });

        calendarView.setOnDayViewStyleChangeListener(new CalendarView.OnDayViewStyleChangeListener() {
            @Override
            public void onDayViewStyleChange(ViewHolderWrapper wrapper, DayTime dayTime) {
                if (dayTime.isCurrentMonth()) {
                    wrapper.getView().setTextColor(Color.CYAN);
                }

                if (dayTime.isWeekend() || (!dayTime.isCurrentMonth() && dayTime.isWeekend())) {
                    wrapper.getView().setTextColor(Color.GREEN);
                }

                if (dayTime.getDay() == 7 && dayTime.getMonth() == 2 && dayTime.getYear() == 2016 && dayTime.isCurrentMonth()) {
                    wrapper.getView().setTextColor(Color.BLACK);
                }
            }
        });
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void animate(View v) {
        ViewCompat.setTranslationY(v, ScreenUtility.getScreenHeight(getApplicationContext()));
        ViewCompat.setAlpha(v, 0f);
        ViewCompat.animate(v)
                .translationY(0f)
                .setDuration(1500)
                .alpha(1f)
                .setInterpolator(new DecelerateInterpolator(3.0f))
                .start();
    }
}
