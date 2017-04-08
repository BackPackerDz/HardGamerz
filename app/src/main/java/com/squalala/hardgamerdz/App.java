package com.squalala.hardgamerdz;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.squalala.hardgamerdz.utils.EmailUtils;
import com.squareup.leakcanary.LeakCanary;

import hugo.weaving.DebugLog;
import io.fabric.sdk.android.Fabric;

public class App extends MultiDexApplication {

    private AppComponent component;


    @DebugLog
    @Override public void onCreate() {
        super.onCreate();

        setupGraph();

        Fabric.with(this, new Crashlytics());

        LeakCanary.install(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent component() {
        return component;
    }

    private void setupGraph() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.inject(this);
    }

}
