Material Calendar View ![Build Status](https://travis-ci.org/SAMSistemas/MaterialCalendarView.svg?branch=master)
===================
Material calendar view is a Library based on this GitHub project: [Custom Calendar View](https://github.com/npanigrahy/Custom-Calendar-View)

I found [Custom Calendar View](https://github.com/npanigrahy/Custom-Calendar-View) as a really interesting project. But when I started first of all using Custom Calendar View I find that this project need some modifications in order to work as expected. 

So I decided to fork Custom Calendar View to make the modifications I needed. Thanks to [Npanigrahy](https://github.com/npanigrahy)! 

Actually my project supports the next features: 
> - Compatibility with Android API 7+
> - Swipe gesture based navigation
> - Next and Previous month based navigation
> - Full customization of calendar from XML file, or setters. 
> - Allow you to handle events when user changes month and day selection using two listeners. 
> - Show hide next previous month overflow days.
> - Allows you to modify Typeface of the Calendar View using setTypeface() method.
> - Unlimited customizations for Day of the Month using custom Decorators.

----------
Features in Process
-------------
I have some features that I want to add in order to make the Calendar more customizable and Interactive:
> - Binding Events with Google Calendar ContentProvider
> - Add Roboto Fonts files and a Util class to make this fonts accessible. (This probably increase library size) 
> - Add Ripple effects when a user touches a day.   
> - Making two layouts for CalendarView. One that supports only Swipe navigation without buttons; and the other one that supports swipe, and back/next button navigation (Actually the default view).
 
----------
Screenshots
-------------
<img src="https://raw.githubusercontent.com/SAMSistemas/MaterialCalendarView/master/screenshots/calendars.png" height="425" width="100%">

----------
Adding to your Project 
-------------
Actually the Project has a release version 1.1.0. 

### For Gradle:
**Step 1:** Add the JitPack repository to your build file. Add it in your build.gradle at the end of repositories.

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
    compile 'com.github.SAMSistemas:MaterialCalendarView:v1.1.0'
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
     <groupId>com.github.SAMSistemas</groupId>
     <artifactId>MaterialCalendarView</artifactId>
     <version>v1.1.0</version>
</dependency>
```
###For SBT:

**Step 1:** Add it in your build.sbt at the end of resolvers:
```java
resolvers += "jitpack" at "https://jitpack.io"
```
**Step 2:** Add the dependency in the form
```java
libraryDependencies += "com.github.SAMSistemas" % "MaterialCalendarView" % "v1.1.0"	
```
----------
Using Material Calendar View library
-------------
This project includes a Sample App that shows you the power of this library. If you want to know more, clone, deploy and play with it. Else I will give you a little code snippets! 

Here it goes! 
```xml
<com.samsistemas.calendarview.widget.CalendarView
	android:id="@+id/calendar_view"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:background="#ffffff">
</com.samsistemas.calendarview.widget.CalendarView>
```
The above code snippet will show the simple Calendar View with default design. Now, you can use the following attributes, to customize the appearance of calendar:
```xml
<com.samsistemas.calendarview.widget.CalendarView
    android:id="@+id/calendar_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/off_white"
    app:calendarBackgroundColor="@color/off_white"
    app:calendarTitleTextColor="@color/black"
    app:currentDayOfMonthColor="@color/blue"
    app:dayOfMonthTextColor="@color/black"
    app:dayOfWeekTextColor="@color/black"
    app:disabledDayBackgroundColor="@color/off_white"
    app:disabledDayTextColor="@color/grey"
    app:selectedDayBackgroundColor="@color/blue"
    app:titleLayoutBackgroundColor="@color/white"
    app:weekLayoutBackgroundColor="@color/white">
</com.samsistemas.calendarview.widget.CalendarView>
```
Let us now, initialize the calendar view to control the various other appearance and behavior of calendar using the following methods.

```java
calendarView = (CalendarView) findViewById(R.id.calendar_view);

calendarView.setFirstDayOfWeek(Calendar.MONDAY);
calendarView.setIsOverflowDateVisible(true);
calendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()));
calendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
    @Override
    public void onDateSelected(@NonNull Date selectedDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Toast.makeText(MainActivity.this, df.format(selectedDate), Toast.LENGTH_SHORT).show();
    }
});

calendarView.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
    @Override
    public void onMonthChanged(@NonNull Date monthDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        if (null != actionBar)
            actionBar.setTitle(df.format(monthDate));
        Toast.makeText(MainActivity.this, df.format(monthDate), Toast.LENGTH_SHORT).show();
    }
});
```

----------
License
-------------
This library is distributed under APACHE license, feel free to do any modification.

```
/*
 * Copyright (C) 2015 ImanoWeb {link: http://imanoweb.com}.
 * Copyright (C) 2015 Jonisaa  {link: http://the-android-developer.blogspot.com.ar}.
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
```
