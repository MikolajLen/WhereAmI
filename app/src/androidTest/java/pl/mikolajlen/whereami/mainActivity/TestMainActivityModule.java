package pl.mikolajlen.whereami.mainActivity;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by mikolaj on 09.07.2017.
 */
@Module(subcomponents = TestMainActivitySubcomponent.class)
public abstract class TestMainActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindYourActivityInjectorFactory(TestMainActivitySubcomponent.Builder builder);
}
