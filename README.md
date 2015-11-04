Material Calendar View ![Build Status](https://travis-ci.org/SAMSistemas/MaterialCalendarView.svg?branch=master)
===================
Material Calendar View is a Library based on the GitHub project: [Custom Calendar View](https://github.com/npanigrahy/Custom-Calendar-View)

I found [Custom Calendar View](https://github.com/npanigrahy/Custom-Calendar-View) as a really interesting project. But when I started using Custom Calendar View I’ve found that it needed some modifications in order to work as I expected. 

So I decided to get involved onto Custom Calendar View to get the modifications I
needed. *(Thanks to [Npanigrahy](https://github.com/npanigrahy))* 

Nowadays my project supports this features: 
> - Compatibility with Android API 8+
> - Swipe gesture-based navigation
> - 'Next' and 'Previous' month-based navigation
> - Full customization of calendar from XML file, or setters. 
> - User-allowed to handle events when changes month and day selection
using two listeners.
> - Show/hide next/previous month overflow days.
> - User-allowed modify Typeface of the Calendar View by using setTypeface()
method.
> - Unlimited customizations for Day of the Month using custom Decorators.

----------
Features in Process
-------------
In order to make the Calendar more customizable and Interactive, the next is
a list of some features that I want to add:
> - Provide two CalendarViews, one build in Java Calendar API, and the other in JODA Time API
> - Synchonize Events with Google Calendar ContentProvider
> - Add Roboto Fonts files and a Util class to make this fonts accessible. *(This probably increase library size)* 
> - Add Ripple Effects when the user touches a day.  
> - Add two layouts for CalendarView: first one that supports only Swipe navigation without buttons; the second one will support Swipe, and back/next button navigation (actually the default view).
 
----------
Screenshots
-------------
<img src="https://raw.githubusercontent.com/SAMSistemas/MaterialCalendarView/master/screenshots/calendars.png" height="425" width="100%">

----------
Screencast
-------------
<img src="https://i.imgur.com/ViolZD2.gif" height="550" width="100%" style="align: center;">

----------
Adding to your Project 
-------------
The last version of the Project is the 1.1.1.

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
    compile 'com.github.SAMSistemas:MaterialCalendarView:v1.1.1'
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
     <version>v1.1.1</version>
</dependency>
```
###For SBT:

**Step 1:** Add it in your build.sbt at the end of resolvers:
```java
resolvers += "jitpack" at "https://jitpack.io"
```
**Step 2:** Add the dependency in the form
```java
libraryDependencies += "com.github.SAMSistemas" % "MaterialCalendarView" % "v1.1.1"	
```
----------
Using Material Calendar View library
-------------
This project includes a Sample App that shows you the true power of this library. If you want to know more, feel free to clone, deploy and play with it. Else I’ll give you a little code snippets.

Pay attention to the following:
```xml
<com.samsistemas.calendarview.widget.CalendarView
	android:id="@+id/calendar_view"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:background="#ffffff">
</com.samsistemas.calendarview.widget.CalendarView>
```
The above code snippet will show the simple Calendar View on the default layout. Now, you can use the following attributes if you want to customize the appearance of calendar:
```xml
<com.samsistemas.calendarview.widget.CalendarView
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
    app:weekLayoutBackgroundColor="@color/colorPrimary">
</com.samsistemas.calendarview.widget.CalendarView>
```
The next step is initialize the Calendar View to change the appearance and behavior of calendar using the following methods:

```java
calendarView = (CalendarView) findViewById(R.id.calendar_view);

calendarView.setFirstDayOfWeek(Calendar.MONDAY);
calendarView.setIsOverflowDateVisible(true);
calendarView.setBackButtonColor(R.color.colorAccent);
calendarView.setNextButtonColor(R.color.colorAccent);
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
Known issues
-------------

I’ve made a lot of testand I’ve found no issues. This library I’ve built againts travis and run the sample app in the supported API emulators. But maybe you can find an Issue that i can't detect or whatever.

If you do so, please, let me know. I will be happy to solve it in order to help you.

----------
Contact Me
-------------
If you like this library, and want me to add some features in order to make it better just mail me to jonatansalas@live.com.ar or jonatan.salas.js@gmail.com. I’ll make it count.

----------
License
-------------
This library is distributed under **Apache License 2**, feel free to do any modification.

```
/*
 * Copyright (C) 2015 ImanoWeb 	   {link: http://imanoweb.com}.
 * Copyright (C) 2015 SAMSistemas  {link: http://www.samsistemas.com.ar}
 * Copyright (C) 2015 Jonisaa  	   {link: http://the-android-developer.blogspot.com.ar}.
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
