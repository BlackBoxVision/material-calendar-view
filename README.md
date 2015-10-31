# MaterialCalendarView

Custom Calendar view library. The library code is based on this project: https://github.com/npanigrahy/Custom-Calendar-View

Currently it supports the following features:

    Next and previous month navigation
    Allow various customization including background color for day, week and title
    Set custom typeface using setCustomTypeFace() method.
    Show hide next previous month overflow days
    Set custom day options for start day of week. By default it is set to Calendar.SUNDAY
    Unlimited customizations for day of the month using custom Decorators.
    Allow you to handle event when user changes month and day selection.


##How to use

Actually this library has a release version V0.0.1, if you want to integrate to your project, the best way to achieve this is to work with jitpack and gradle files. 

In your project main **build.gradle** file you have to add this: 
   ```  
   repositories {
          // ...
          //This is the line you have to add..
          maven { url "https://jitpack.io" }
   }
   ```
   
In your application **build.gradle** file you have to add this:
  ```
	dependencies {
	       compile 'com.github.SAMSistemas:MaterialCalendarView:v1.0.0'
	}
	 



