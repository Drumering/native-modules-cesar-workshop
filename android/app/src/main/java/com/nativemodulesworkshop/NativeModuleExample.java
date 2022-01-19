package com.nativemodulesworkshop;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class NativeModuleExample extends ReactContextBaseJavaModule {

    NativeModuleExample(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return NativeModuleExample.class.getSimpleName();
    }

    @ReactMethod
    public void sum(Float a, Float b, Promise promise) {
        promise.resolve(a + b);
    }

    @ReactMethod
    public void openCalendar(String eventTitle, String eventLocation, Promise promise) {
        if (MainActivity.openCalendar(eventTitle, eventLocation) != null) {
            promise.resolve(String.valueOf(MainActivity.openCalendar(eventTitle, eventLocation)));
        }
    }
}
