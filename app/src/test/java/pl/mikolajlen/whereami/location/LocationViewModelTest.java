package pl.mikolajlen.whereami.location;

import android.content.res.Resources;
import android.databinding.ObservableBoolean;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by mikolaj on 22.07.2017.
 */
public class LocationViewModelTest {

    @Mock
    LocationProvider locationProvider;
    @Mock
    Geocoder geocoder;
    @Mock
    LocationViewData locationViewData;
    @Mock
    Resources resources;

    private LocationViewModel locationViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        locationViewModel = new LocationViewModel(resources, locationProvider, geocoder, locationViewData);
    }

    @Test
    public void shouldSetLocationAfterFetching() {
        //given
        Location mockedLocation = mock(Location.class);
        given(locationProvider.getLocationObservable()).willReturn(Observable.just(mockedLocation));

        //when
        locationViewModel.fetchLocation();

        //then
        verify(locationViewData).setLocation(mockedLocation);
    }

    @Test
    public void shouldShowErrorWhenFetchingLocationFailed() {
        //given
        given(resources.getString(anyInt())).willReturn("");
        given(locationProvider.getLocationObservable()).willReturn(Observable.error(new RuntimeException()));

        //when
        locationViewModel.fetchLocation();

        //then
        verify(locationViewData).setLocationError(true);
        verify(locationViewData).setErrorMessage("");
    }

    @Test
    public void shouldSetAddressAfterFetching() throws IOException {
        //given
        Address mockedAddress = mock(Address.class);
        given(locationProvider.getLocationObservable()).willReturn(Observable.just(mock(Location.class)));
        given(geocoder.getFromLocation(anyDouble(), anyDouble(), anyInt())).willReturn(Arrays.asList(mockedAddress));

        //when
        locationViewModel.fetchLocation();

        //then
        verify(locationViewData).setMainAddress(mockedAddress);
    }

    @Test
    public void shouldShowOtherAddressess() throws IOException {
        //given
        Address mockedAddress = mock(Address.class);
        Address secondAddress = mock(Address.class);
        given(locationProvider.getLocationObservable()).willReturn(Observable.just(mock(Location.class)));
        given(geocoder.getFromLocation(anyDouble(), anyDouble(), anyInt()))
                .willReturn(Arrays.asList(mockedAddress, secondAddress));

        //when
        locationViewModel.fetchLocation();

        //then
        ArgumentCaptor captor = ArgumentCaptor.forClass(List.class);
        verify(locationViewData).setAddressess((List<Address>) captor.capture());
        List<Address> otherAddresses = (List<Address>) captor.getValue();
        assertThat(otherAddresses.get(0)).isEqualTo(secondAddress);
    }

    @Test
    public void shouldShowErrorWhenFetchingAddressesFailed() throws IOException {
        //given
        given(resources.getString(anyInt())).willReturn("");
        given(locationViewData.getLocationError()).willReturn(new ObservableBoolean(false));
        given(locationProvider.getLocationObservable()).willReturn(Observable.just(mock(Location.class)));
        given(geocoder.getFromLocation(anyDouble(), anyDouble(), anyInt())).willThrow(new IOException());

        //when
        locationViewModel.fetchLocation();

        //then
        verify(locationViewData).setAddressesError(true);
        verify(locationViewData).setErrorMessage("");
    }

    @Test
    public void shouldShowAndHideProgressWhenSuccess() {
        //given
        Location mockedLocation = mock(Location.class);
        given(locationProvider.getLocationObservable()).willReturn(Observable.just(mockedLocation));

        //when
        locationViewModel.fetchLocation();

        //then
        verify(locationViewData).setProgressVisible(true);
        verify(locationViewData).setProgressVisible(false);
    }

    @Test
    public void shouldShowAndHideProgressWhenError() {
        //given
        given(locationProvider.getLocationObservable()).willReturn(Observable.error(new RuntimeException()));

        //when
        locationViewModel.fetchLocation();

        //then
        verify(locationViewData).setProgressVisible(true);
        verify(locationViewData).setProgressVisible(false);
    }
}