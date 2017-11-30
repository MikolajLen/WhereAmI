package pl.mikolajlen.whereami.application;

import javax.inject.Singleton;

import dagger.Component;
import pl.mikolajlen.whereami.mainActivity.TestMainActivityModule;

/**
 * Created by mikolaj on 09.07.2017.
 */
@Singleton
@Component(modules = TestMainActivityModule.class)
public interface TestAppComponent {

    void inject(TestApplication application);
}
