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

public class TestApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private TestAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerTestAppComponent.create();
        appComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
