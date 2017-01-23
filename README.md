<img src="https://raw.githubusercontent.com/BlackBoxVision/material-calendar-view/master/art/logo.png" width="720px" height="125px">
> Prettier and simpler Material Design CalendarView

[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2715) [![](https://jitpack.io/v/BlackBoxVision/material-calendar-view.svg)](https://jitpack.io/#BlackBoxVision/material-calendar-view) ![Build Status](https://travis-ci.org/BlackBoxVision/material-calendar-view.svg?branch=master) 

**MaterialCalendarView** is a **prettier** and **simpler**, **material design calendar** that allows full customization and it's backwards compatible with API 11+.

##Screenshots

<div style="align:center; display:inline-block; width:100%;">
	<img src="https://raw.githubusercontent.com/BlackBoxVision/material-calendar-view/master/art/newer.png" height="800" width="50%">
	<img src="https://raw.githubusercontent.com/BlackBoxVision/material-calendar-view/master/art/other.png" height="800" width="50%">
</div>

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
    compile 'com.github.BlackBoxVision:material-calendar-view:v1.5.1'
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
    <version>v1.5.1</version>
</dependency>
```
**SBT**

- Add it in your build.sbt at the end of resolvers:

```java
resolvers += "jitpack" at "https://jitpack.io"
```

- Add the dependency in the form:

```java
libraryDependencies += "com.github.BlackBoxVision" % "material-calendar-view" % "v1.5.1"	
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
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	app:calendarIsMultiSelectDayEnabled="false"
	app:calendarIsOverflowDatesVisible="true"
	app:calendarBackgroundColor="@color/colorPrimary"
	app:calendarTitleTextColor="@color/colorAccent"
	app:calendarCurrentDayTextColor="@color/white"
	app:calendarDayOfWeekTextColor="@android:color/white"
	app:calendarDisabledDayBackgroundColor="@color/colorPrimary"
	app:calendarDisabledDayTextColor="@android:color/darker_gray"
	app:calendarSelectedDayBackgroundColor="@color/colorAccent"
	app:calendarTitleBackgroundColor="@color/colorPrimary"
	app:calendarWeekBackgroundColor="@color/colorPrimary"
	app:calendarCurrentDayBackgroundColor="@color/teal500"
	app:calendarWeekendTextColor="@color/colorAccent"
	app:calendarButtonBackgroundColor="@color/colorAccent"
	app:calendarWeekendDays="saturday|sunday">
</io.blackbox_vision.materialcalendarview.view.CalendarView>
```
Then, in your Activity.java or Fragment.java initialize the calendar: 

```java
calendarView = (CalendarView) findViewById(R.id.calendar_view);

calendarView.shouldAnimateOnEnter(true)
	.setFirstDayOfWeek(Calendar.MONDAY)	
	.setOnDateClickListener(this::onDateClick)
	.setOnMonthChangeListener(this::onMonthChange)
	.setOnDateLongClickListener(this::onDateLongClick)
	.setOnMonthTitleClickListener(this::onMonthTitleClick);

if (calendarView.isMultiSelectDayEnabled()) {
	calendarView.setOnMultipleDaySelectedListener(this::onMultipleDaySelected);
}

calendarView.update(Calendar.getInstance(Locale.getDefault()));
```

##Issues

If you found a bug, or you have an answer, or whatever. Please, open an [issue](https://github.com/BlackBoxVision/material-calendar-view/issues). I will do the best to fix it, or help you.

##Contributing

Of course, if you see something that you want to upgrade from this library, or a bug that needs to be solved, **PRs are welcome!**

##License

Distributed under the **MIT license**. See [LICENSE](https://github.com/BlackBoxVision/material-calendar-view/blob/master/LICENSE) for more information.

