# MaterialCalendarView ![Build Status](https://travis-ci.org/SAMSistemas/MaterialCalendarView.svg?branch=master)

Material Calendar view library. This library code is based on this project: ![Custom Calendar View](https://github.com/npanigrahy/Custom-Calendar-View)

Currently it supports the following features:

    - Compatible with API 7+ 
    - Next and previous month navigation
    - Allow various customization including background color for day, week and title from xml and setters
    - Set custom typeface using setTypeFace() method.
    - Show hide next previous month overflow days
    - Set custom day options for start day of week. By default it is set to Calendar.SUNDAY
    - Unlimited customizations for day of the month using custom Decorators.
    - Allow you to handle event when user changes month and day selection.
		
##Features in process: 
    
    - Support swipe gestures
    - Binding events from Google Calendar ContentProvider

##How to use

Actually this library has a release version V1.0.0, if you want to integrate to your project, the best way to achieve this is to work with jitpack and gradle files. 

In your project main **build.gradle** file you have to add this: 
   ```  
   repositories {
          // ...
          mavenCentral()
          //This is the line you have to add..
          maven { 
          	url "https://jitpack.io" 
          }
   }
   ```
   
In your application **build.gradle** file you have to add this:
  ```
  dependencies {
	 compile 'com.github.SAMSistemas:MaterialCalendarView:v1.0.0'
  } 
  ```
	 
## Using MaterialCalendarView Library

The GitHub project source includes a sample application, that is used for demonstrating the various features currently supported by this library. Once the library is added to your project, you can include the MaterialCalendarView into your activity/fragment layout using the following code snippets.

```xml
<com.samsistemas.calendarview.widget.CalendarView
	android:id="@+id/calendar_view"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:background="#ffffff">
</com.samsistemas.calendarview.widget.CalendarView>
```
The above code snippet will show the simple Calendar View with default design. Now, you can use the following attributes, to customize the appearance of calendar.

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


