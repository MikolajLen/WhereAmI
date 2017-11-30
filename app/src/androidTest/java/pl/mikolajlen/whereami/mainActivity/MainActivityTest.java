package pl.mikolajlen.whereami.mainActivity;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import pl.mikolajlen.whereami.R;
import pl.mikolajlen.whereami.application.EspressoTestApplication;
import pl.mikolajlen.whereami.location.LocationProvider;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(MainActivity.PERMISSIONS);

    @Inject
    LocationProvider provider;

    @Inject
    Geocoder geocoder;

    @Before
    public void setUp() {
        EspressoTestApplication application =
                (EspressoTestApplication) activityActivityTestRule.getActivity().getApplication();
        application.getAppComponent().inject(this);
    }

    @Test
    public void testMainActivity() throws IOException {
        Location location = new Location("");
        location.setLatitude(51.5006902);
        location.setLongitude(-0.1244725);
        Address address = new Address(Locale.getDefault());
        address.setAddressLine(0, "67 Bridge St");
        address.setAddressLine(1, "Westminster");
        address.setAddressLine(2, "6London SW1A 2PW");
        given(provider.getLocationObservable()).willReturn(Observable.just(location));
        given(geocoder.getFromLocation(anyDouble(), anyDouble(), anyInt()))
                .willReturn(Arrays.asList(address));
        onView(withId(R.id.fetch_location_button)).perform(click());
        assert true;
    }
}
