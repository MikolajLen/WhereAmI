package pl.mikolajlen.whereami.application;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.location.Geocoder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.mikolajlen.whereami.location.LocationProvider;
import pl.mikolajlen.whereami.location.LocationViewData;
import pl.mikolajlen.whereami.location.LocationViewModelFactory;

import static org.mockito.Mockito.mock;

/**
 * Created by mikolaj on 10.08.2017.
 */
@Module
public class TestModule {


    @Provides
    @Singleton
    Geocoder provideGeocoder(Application application) {
        return new Geocoder(application);
    }

    @Provides
    @Singleton
    LocationProvider provideLocationProvider() {
        return mock(LocationProvider.class);
    }

    @Provides
    Resources provideResources(Application application) {
        return application.getResources();
    }

    @Provides
    RecyclerView.ItemDecoration provideDecoration(Application application) {
        return new DividerItemDecoration(application, DividerItemDecoration.VERTICAL);
    }

    @Provides
    ViewModelProvider.Factory provideFactory(Resources resources, LocationProvider locationProvider, Geocoder geocoder,
                                             LocationViewData locationViewData) {
        return new LocationViewModelFactory(resources, locationProvider, geocoder, locationViewData);
    }

}
