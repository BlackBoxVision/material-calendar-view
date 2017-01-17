
#Material Calendar View ![Build Status](https://travis-ci.org/BlackBoxVision/material-calendar-view.svg?branch=master) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2715) [![Join the chat at https://gitter.im/jonisaa/material-calendar-view](https://badges.gitter.im/jonisaa/material-calendar-view.svg)](https://gitter.im/jonisaa/material-calendar-view?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

MaterialCalendarView is a prettier and simpler, material design calendar that allows full customization and it's backwards compatible with API 11+.
    
----------
Screenshots
-------------
<img src="https://raw.githubusercontent.com/SAMSistemas/MaterialCalendarView/master/screenshots/calendars.png" height="425" width="100%">

<img src="https://i.imgur.com/ViolZD2.gif" height="550" width="100%" style="align: center;">

##Installation

### For Gradle:
**Step 1:** you must add the JitPack repository to your build file. Type the
on your build.gradle at the end of repositories.

```java
  repositories {
    maven { 
	    url "https://jitpack.io"
	}
  }
```

**Step 2:** Add the dependency in the form

```java
dependencies {
    compile 'com.github.BlackBoxVision:material-calendar-view:v1.2.3'
}
```
###For Maven:

**Step 1:** Add the JitPack repository to your maven file. 
```xml
<repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
</repository>
```
**Step 2:** Add the dependency in the form
```xml
<dependency>
    <groupId>com.github.BlackBoxVision</groupId>
    <artifactId>material-calendar-view</artifactId>
    <version>v1.2.3</version>
</dependency>
```
###For SBT:

**Step 1:** Add it in your build.sbt at the end of resolvers:
```java
resolvers += "jitpack" at "https://jitpack.io"
```
**Step 2:** Add the dependency in the form
```java
libraryDependencies += "com.github.BlackBoxVision" % "material-calendar-view" % "v1.2.3"	
```

##Usage example

Pay attention to the following:

```xml
<io.blackbox_vision.calendarview.widget.CalendarView
	android:id="@+id/calendar_view"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:background="@color/colorPrimary">
</io.blackbox_vision.calendarview.widget.CalendarView>
```

The above code snippet will show the simple Calendar View on the default layout. Now, you can use the following attributes if you want to customize the appearance of calendar:

```xml
<io.blackbox_vision.calendarview.widget.CalendarView
    android:id="@+id/calendar_view"
    android:layout_marginTop="56dp"
    android:layout_marginEnd="2dp"
    android:layout_marginLeft="2dp"
    android:layout_marginRight="2dp"
    android:layout_marginStart="2dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorPrimary"
    app:calendarBackgroundColor="@color/colorPrimary"
    app:calendarTitleTextColor="@color/colorAccent"
    app:currentDayOfMonthColor="@color/white"
    app:dayOfMonthTextColor="@color/white"
    app:dayOfWeekTextColor="@android:color/white"
    app:disabledDayBackgroundColor="@color/colorPrimary"
    app:disabledDayTextColor="@color/colorAccent"
    app:selectedDayBackgroundColor="@color/colorAccent"
    app:titleLayoutBackgroundColor="@color/colorPrimary"
    app:weekLayoutBackgroundColor="@color/colorPrimary"
    app:weekendColor="@color/red"
    app:weekend="saturday|sunday">
</io.blackbox_vision.calendarview.widget.CalendarView>
```
The next step is initialize the Calendar View to change the appearance and behavior of calendar using the following methods:

```java
calendarView = (CalendarView) findViewById(R.id.calendar_view);

calendarView.setFirstDayOfWeek(Calendar.MONDAY);
calendarView.setIsOverflowDateVisible(true);
calendarView.setCurrentDay(new Date(System.currentTimeMillis()));
calendarView.setBackButtonColor(R.color.colorAccent);
calendarView.setNextButtonColor(R.color.colorAccent);
calendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()));
calendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
    @Override
    public void onDateClick(@NonNull Date selectedDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        textView.setText(df.format(selectedDate));
    }
});

calendarView.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
    @Override
    public void onMonthChanged(@NonNull Date monthDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        if (null != actionBar)
            actionBar.setTitle(df.format(monthDate));
    }
});

final DayView dayView = calendarView.findViewByDate(new Date(System.currentTimeMillis()));
if(null != dayView)
    Toast.makeText(getApplicationContext(), "Today is: " + dayView.getText().toString() + "/" + calendarView.getCurrentMonth() + "/" +  calendarView.getCurrentYear(), Toast.LENGTH_SHORT).show();
```

##Issues

If you found a bug, or you have an answer, or whatever. Please, open an [issue](https://github.com/BlackBoxVision/material-calendar-view/issues). I will do the best to fix it, or help you.

##Contributing

Of course, if you see something that you want to upgrade from this library, or a bug that needs to be solved, **PRs are welcome!**

##License

Distributed under the **MIT license**. See [LICENSE](https://github.com/BlackBoxVision/material-calendar-view/blob/master/LICENSE) for more information.

