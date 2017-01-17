
#Material Calendar View 
[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2715) [![](https://jitpack.io/v/BlackBoxVision/material-calendar-view.svg)](https://jitpack.io/#BlackBoxVision/material-calendar-view) ![Build Status](https://travis-ci.org/BlackBoxVision/material-calendar-view.svg?branch=master) 

MaterialCalendarView is a prettier and simpler, material design calendar that allows full customization and it's backwards compatible with API 11+.

##Screenshots

<img src="https://raw.githubusercontent.com/BlackBoxVision/material-calendar-view/master/art/calendars.png" height="475" width="100%">

<img src="https://i.imgur.com/ViolZD2.gif" height="550" width="100%" style="align: center;">

##Installation

**Gradle**

- Add it in your root build.gradle at the end of repositories:

```java
  repositories {
    maven { 
	    url "https://jitpack.io"
	}
  }
```

- Add the dependency:

```java
dependencies {
    compile 'com.github.BlackBoxVision:material-calendar-view:v1.3.0'
}
```
**Maven**

- Add the JitPack repository to your maven file. 

```xml
<repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
</repository>
```
- Add the dependency in the form

```xml
<dependency>
    <groupId>com.github.BlackBoxVision</groupId>
    <artifactId>material-calendar-view</artifactId>
    <version>v1.3.0</version>
</dependency>
```
**SBT**

- Add it in your build.sbt at the end of resolvers:

```java
resolvers += "jitpack" at "https://jitpack.io"
```

- Add the dependency in the form:

```java
libraryDependencies += "com.github.BlackBoxVision" % "material-calendar-view" % "v1.3.0"	
```

##Usage example

In your layout.xml file:

```xml
<io.blackbox_vision.materialcalendarview.view.CalendarView
	android:id="@+id/calendar_view"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:background="@color/colorPrimary">
</io.blackbox_vision.materialcalendarview.view.CalendarView>
```

This example shows all the possible customization around Material Calendar View:

```xml
<io.blackbox_vision.materialcalendarview.view.CalendarView
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
</io.blackbox_vision.materialcalendarview.view.CalendarView>
```
Then, in your Activity.java or Fragment.java initialize the calendar: 

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

