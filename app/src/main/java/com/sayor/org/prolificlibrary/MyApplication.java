package com.sayor.org.prolificlibrary;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Sayor on 2/25/16.
 */
public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build()
    );
  }
}
