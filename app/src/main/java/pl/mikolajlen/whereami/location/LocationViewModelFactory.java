package pl.mikolajlen.whereami.location;

import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.location.Geocoder;

import javax.inject.Inject;

/**
 * Created by mikolaj on 20.07.2017.
 */

public class LocationViewModelFactory implements ViewModelProvider.Factory {

    private Resources resources;
    private LocationProvider locationProvider;
    private Geocoder geocoder;
    private LocationViewData locationViewData;

    @Inject
    public LocationViewModelFactory(Resources resources, LocationProvider locationProvider, Geocoder geocoder,
                                    LocationViewData locationViewData) {
        this.resources = resources;
        this.locationProvider = locationProvider;
        this.geocoder = geocoder;
        this.locationViewData = locationViewData;
    }

    @Override
    public LocationViewModel create(Class modelClass) {
        return new LocationViewModel(resources, locationProvider, geocoder, locationViewData);
    }
}
