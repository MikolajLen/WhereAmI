package pl.mikolajlen.whereami.application;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by mikolaj on 09.07.2017.
 */

public class EspressoTestApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private EspressoTestAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerEspressoTestAppComponent.builder()
                .application(this)
                .build();
        appComponent.inject(this);
    }

    public EspressoTestAppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
