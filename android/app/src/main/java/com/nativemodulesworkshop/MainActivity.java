package com.nativemodulesworkshop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.ReactActivity;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class MainActivity extends ReactActivity {

  public static ContentResolver contentResolver;
  public static  Activity context;

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "NativeModulesWorkshop";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    contentResolver = getContentResolver();
    context = this;
  }

  public static Long openCalendar(String eventTitle, String eventLocation) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CALENDAR)) {
      new AlertDialog.Builder(context)
              .setTitle("Permissões")
              .setMessage("Considere conceder as permissões para criar um evento")
              .setPositiveButton("Conceder", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  ActivityCompat.requestPermissions(context, new String[] { Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 1);
                }
              })
              .setCancelable(true)
              .create()
              .show();
    } else if (checkPermissions(context) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(context, new String[] { Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 1);
    } else {

      Random id = new Random(100);

      long startMillis = 0;
      long endMillis = 0;
      Calendar beginTime = Calendar.getInstance();
      beginTime.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
      startMillis = beginTime.getTimeInMillis();
      Calendar endTime = Calendar.getInstance();
      endTime.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE) + 5);
      endMillis = endTime.getTimeInMillis();

      ContentValues contentValues = new ContentValues();
      contentValues.put(CalendarContract.Events.TITLE, eventTitle);
      contentValues.put(CalendarContract.Events.EVENT_LOCATION, eventLocation);
      contentValues.put(CalendarContract.Events.CALENDAR_ID, id.nextInt());
      contentValues.put(CalendarContract.Events.DTSTART, startMillis);
      contentValues.put(CalendarContract.Events.DTEND, endMillis);
      contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());

      Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, contentValues);

      return Long.parseLong(uri.getLastPathSegment());
    }

    return null;
  }

  public static Integer checkPermissions(Context context) {

     if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
       return 1;
     }

    return 0;
  }
}
