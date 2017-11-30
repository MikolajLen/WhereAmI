package pl.mikolajlen.whereami.application;

import dagger.Component;
import pl.mikolajlen.whereami.mainActivity.MainActivityModule;

/**
 * Created by mikolaj on 15.06.2017.
 */
@Component(modules = {MainActivityModule.class})
public interface ApplicationComponent {

    void inject(WhereAmIApplication application);
}
