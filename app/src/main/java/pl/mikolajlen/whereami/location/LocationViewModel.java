package pl.mikolajlen.whereami.location;

import android.arch.lifecycle.ViewModel;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.util.List;

import io.reactivex.internal.functions.Functions;
import pl.mikolajlen.whereami.R;

import static pl.mikolajlen.whereami.mainActivity.MainActivity.MAX_ADDRESSESS_RESULTS;

/**
 * Created by mikolaj on 12.07.2017.
 */

public class LocationViewModel extends ViewModel {

    private Resources resources;
    private LocationProvider locationProvider;
    private Geocoder geocoder;
    private LocationViewData locationViewData;

    public LocationViewModel(Resources resources, LocationProvider locationProvider, Geocoder geocoder,
                             LocationViewData locationViewData) {
        this.resources = resources;
        this.locationProvider = locationProvider;
        this.geocoder = geocoder;
        this.locationViewData = locationViewData;
        init();
    }

    private void init() {
        locationViewData.setTitle(resources.getString(R.string.location_not_fetched));
    }

    public void fetchLocation() {
        locationProvider.getLocationObservable()
                .doOnNext(this::setLocation)
                .doOnError(throwable -> {
                    locationViewData.setLocationError(true);
                    locationViewData.setErrorMessage(resources.getString(R.string.location_fetch_error));
                })
                .map(location ->
                        geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), MAX_ADDRESSESS_RESULTS))
                .subscribe(this::setSuccessData, this::setErrorMessage, Functions.EMPTY_ACTION, disposable -> showProgress());
    }

    private void setErrorMessage(Throwable throwable) {
        hideProgress();
        throwable.printStackTrace();
        if (!locationViewData.getLocationError().get()) {
            locationViewData.setAddressesError(true);
            locationViewData.setErrorMessage(resources.getString(R.string.address_fetch_error));
        }
    }

    private void hideProgress() {
        locationViewData.setProgressVisible(false);
    }

    private void showProgress() {
        locationViewData.setProgressVisible(true);
    }

    public void setLocation(Location location) {
        locationViewData.setTitle(resources.getString(R.string.your_current_location_is));
        locationViewData.setLocation(location);
        locationViewData.setLocationError(false);
    }

    public void setSuccessData(List<Address> addresses) {
        hideProgress();
        if (addresses.isEmpty()) {
            return;
        }
        setMainAddress(addresses);
        setOtherAddresses(addresses);
    }

    private void setMainAddress(List<Address> addresses) {
        locationViewData.setMainAddress(addresses.get(0));
    }

    private void setOtherAddresses(List<Address> addresses) {
        if (addresses.size() > 1) {
            locationViewData.setAddressess(addresses.subList(1, addresses.size()));
            locationViewData.setAddressesError(false);
        }
    }

    public LocationViewData getLocationViewData() {
        return locationViewData;
    }
}
