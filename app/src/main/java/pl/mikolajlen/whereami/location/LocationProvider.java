package pl.mikolajlen.whereami.location;

import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mikolaj on 10.07.2017.
 */

public class LocationProvider {

    private FusedLocationProviderClient client;
    private PublishSubject<Location> locationSubject;

    @Inject
    public LocationProvider(FusedLocationProviderClient client) {
        this.client = client;
    }

    public Observable<Location> getLocationObservable() {
        locationSubject = PublishSubject.create();
        try {
            Task<Location> lastLocation = client.getLastLocation();
            lastLocation.addOnSuccessListener(locationSubject::onNext);
            lastLocation.addOnFailureListener(locationSubject::onError);
        } catch (SecurityException e) {
            locationSubject.onError(e);
        } finally {
            return locationSubject;
        }
    }
}
