package pl.mikolajlen.whereami.location;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.location.Address;
import android.location.Location;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import pl.mikolajlen.whereami.location.list.AddressesListAdapter;

/**
 * Created by mikolaj on 22.07.2017.
 */

public class LocationViewData {

    private ObservableBoolean locationError = new ObservableBoolean(true);
    private ObservableBoolean addressesError = new ObservableBoolean(true);
    private ObservableField<String> errorMessage = new ObservableField<>();
    private ObservableField<String> title = new ObservableField<>();
    private ObservableField<Address> mainAddress = new ObservableField<>();
    private ObservableField<Location> location = new ObservableField<>();
    private ObservableBoolean progressVisible = new ObservableBoolean(false);

    private RecyclerView.ItemDecoration decoration;
    private AddressesListAdapter addressesListAdapter;

    @Inject
    public LocationViewData(RecyclerView.ItemDecoration decoration, AddressesListAdapter addressesListAdapter) {
        this.decoration = decoration;
        this.addressesListAdapter = addressesListAdapter;
    }

    public ObservableBoolean getLocationError() {
        return locationError;
    }

    public void setLocationError(boolean locationError) {
        this.locationError.set(locationError);
    }

    public ObservableBoolean getAddressesError() {
        return addressesError;
    }

    public void setAddressesError(boolean addressesError) {
        this.addressesError.set(addressesError);
    }

    public ObservableField<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.set(errorMessage);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObservableField<Address> getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Address mainAddress) {
        this.mainAddress.set(mainAddress);
    }

    public ObservableField<Location> getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location.set(location);
    }

    public RecyclerView.ItemDecoration getDecoration() {
        return decoration;
    }

    public ObservableBoolean getProgressVisible() {
        return progressVisible;
    }

    public void setProgressVisible(boolean progressVisible) {
        this.progressVisible.set(progressVisible);
    }

    public AddressesListAdapter getAddressesListAdapter() {
        return addressesListAdapter;
    }

    public void setAddressess(List<Address> addressess) {
        addressesListAdapter.setItems(addressess);
    }
}
