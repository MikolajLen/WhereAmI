package pl.mikolajlen.whereami.mainActivity;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by mikolaj on 15.06.2017.
 */
@Subcomponent(modules = {MainActivityMockedModule.class, TestMainActivitySubcomponent.ActivityModule.class})
public interface TestMainActivitySubcomponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }

    @Module
    class ActivityModule {

        @Provides
        Activity provideActivity(MainActivity activity) {
            return activity;
        }
    }
}
