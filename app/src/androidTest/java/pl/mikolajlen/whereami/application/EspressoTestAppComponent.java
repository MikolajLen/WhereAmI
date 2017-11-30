package pl.mikolajlen.whereami.application;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import pl.mikolajlen.whereami.mainActivity.MainActivityTest;
import pl.mikolajlen.whereami.mainActivity.TestMainActivityModule;

/**
 * Created by mikolaj on 09.07.2017.
 */
@Singleton
@Component(modules = {TestMainActivityModule.class, TestModule.class})
public interface EspressoTestAppComponent {

    void inject(EspressoTestApplication application);
    void inject(MainActivityTest test);

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application app);
        EspressoTestAppComponent build();
    }
}
