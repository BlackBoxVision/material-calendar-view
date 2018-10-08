<img src="https://raw.githubusercontent.com/BlackBoxVision/material-calendar-view/master/art/logo.png" width="720px" height="125px">
> Prettier and simpler Material Design CalendarView

[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Calendar%20View-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2715) [![](https://jitpack.io/v/BlackBoxVision/material-calendar-view.svg)](https://jitpack.io/#BlackBoxVision/material-calendar-view) ![Build Status](https://travis-ci.org/BlackBoxVision/material-calendar-view.svg?branch=master) [![OpenCollective](https://opencollective.com/material-calendar-view/backers/badge.svg)](#backers) 
[![OpenCollective](https://opencollective.com/material-calendar-view/sponsors/badge.svg)](#sponsors)
 

**MaterialCalendarView** is a **prettier** and **simpler**, **material design calendar** that allows full customization and it's backwards compatible with API 11+.

## Screenshots

<div style="align:center; display:inline-block; width:100%;">
	<img src="https://raw.githubusercontent.com/BlackBoxVision/material-calendar-view/master/art/newer.png" height="775" width="49%">
	<img src="https://raw.githubusercontent.com/BlackBoxVision/material-calendar-view/master/art/other.png" height="775" width="49%">
</div>

<img src="https://i.imgur.com/ViolZD2.gif" height="550" width="100%" style="align: center;">

## Installation

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
    compile 'com.github.BlackBoxVision:material-calendar-view:v1.5.8'
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
    <version>v1.5.8</version>
</dependency>
```
**SBT**

- Add it in your build.sbt at the end of resolvers:

```java
resolvers += "jitpack" at "https://jitpack.io"
```

- Add the dependency in the form:

```java
libraryDependencies += "com.github.BlackBoxVision" % "material-calendar-view" % "v1.5.8"
```

## Usage example

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
	app:calendarDayOfWeekTextColor="@color/grey"
	app:calendarDayOfMonthTextColor="@android:color/white"
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

## Issues

If you found a bug, or you have an answer, or whatever. Please, open an [issue](https://github.com/BlackBoxVision/material-calendar-view/issues). I will do the best to fix it, or help you.

## Contributing

Of course, if you see something that you want to upgrade from this library, or a bug that needs to be solved, **PRs are welcome!**


## Backers
Support us with a monthly donation and help us continue our activities. [[Become a backer](https://opencollective.com/material-calendar-view#backer)]

<a href="https://opencollective.com/material-calendar-view/backer/0/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/0/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/1/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/1/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/2/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/2/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/3/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/3/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/4/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/4/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/5/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/5/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/6/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/6/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/7/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/7/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/8/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/8/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/9/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/9/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/10/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/10/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/11/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/11/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/12/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/12/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/13/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/13/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/14/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/14/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/15/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/15/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/16/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/16/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/17/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/17/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/18/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/18/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/19/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/19/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/20/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/20/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/21/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/21/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/22/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/22/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/23/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/23/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/24/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/24/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/25/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/25/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/26/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/26/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/27/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/27/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/28/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/28/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/backer/29/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/backer/29/avatar.svg"></a>

## Sponsors
Become a sponsor and get your logo on our README on Github with a link to your site. [[Become a sponsor](https://opencollective.com/material-calendar-view#sponsor)]

<a href="https://opencollective.com/material-calendar-view/sponsor/0/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/0/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/1/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/1/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/2/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/2/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/3/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/3/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/4/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/4/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/5/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/5/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/6/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/6/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/7/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/7/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/8/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/8/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/9/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/9/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/10/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/10/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/11/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/11/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/12/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/12/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/13/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/13/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/14/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/14/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/15/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/15/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/16/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/16/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/17/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/17/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/18/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/18/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/19/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/19/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/20/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/20/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/21/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/21/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/22/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/22/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/23/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/23/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/24/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/24/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/25/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/25/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/26/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/26/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/27/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/27/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/28/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/28/avatar.svg"></a>
<a href="https://opencollective.com/material-calendar-view/sponsor/29/website" target="_blank"><img src="https://opencollective.com/material-calendar-view/sponsor/29/avatar.svg"></a>

## License

Distributed under the **MIT license**. See [LICENSE](https://github.com/BlackBoxVision/material-calendar-view/blob/master/LICENSE) for more information.

