package pl.mikolajlen.whereami.location;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.location.Geocoder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mikolaj on 13.06.2017.
 */
@Module
public class LocationModule {

    @Provides
    FusedLocationProviderClient provideFusedLocationProviderClient(Activity activity) {
        return LocationServices.getFusedLocationProviderClient(activity);
    }

    @Provides
    Geocoder provideGeocoder(Activity activity) {
        return new Geocoder(activity, Locale.getDefault());
    }

    @Provides
    Resources provideResources(Activity activity) {
        return activity.getResources();
    }

    @Provides
    RecyclerView.ItemDecoration provideDecoration(Activity activity) {
        return new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
    }

    @Provides
    ViewModelProvider.Factory provideFactory(Resources resources, LocationProvider locationProvider, Geocoder geocoder,
                                             LocationViewData locationViewData){
        return new LocationViewModelFactory(resources, locationProvider, geocoder, locationViewData);
    }
}
