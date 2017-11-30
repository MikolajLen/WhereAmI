package pl.mikolajlen.whereami.mainActivity;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import pl.mikolajlen.whereami.location.LocationModule;

/**
 * Created by mikolaj on 15.06.2017.
 */
@Subcomponent(modules = {MainActivitySubcomponent.ActivityModule.class, LocationModule.class})
public interface MainActivitySubcomponent extends AndroidInjector<MainActivity> {

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
