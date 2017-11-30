package pl.mikolajlen.whereami.mainActivity;

import android.app.AlertDialog;
import android.content.pm.PackageManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import pl.mikolajlen.whereami.BuildConfig;
import pl.mikolajlen.whereami.R;
import pl.mikolajlen.whereami.application.TestApplication;
import pl.mikolajlen.whereami.location.LocationViewModel;
import pl.mikolajlen.whereami.utils.PermissionUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by mikolaj on 22.07.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, minSdk = 23, application = TestApplication.class)
public class MainActivityTest {

    private PermissionUtils permissionUtils;
    private LocationViewModel viewModel;
    private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MainActivity.class);
        permissionUtils = activity.permissionUtils;
        viewModel = activity.viewModel;
    }

    @Test
    public void shouldAskForPermissionsWhenNotGranted() {
        //given
        given(permissionUtils.arePemissionsGranted(any(String[].class))).willReturn(false);

        //when
        activity.getLastKnownLocation();

        //then
        verify(permissionUtils).requestNotGrantedPermissions(MainActivity.PERMISSIONS_REQUEST_CODE);
    }

    @Test
    public void shouldAskForLocationWhenPermissionsGranted() {
        //given
        given(permissionUtils.arePemissionsGranted(any(String[].class))).willReturn(true);

        //when
        activity.getLastKnownLocation();

        //then
        verify(viewModel).fetchLocation();
    }

    @Test
    public void shouldDisplayErrorMessageWhenPermissionsNotGranted() {
        //given
        doCallRealMethod().when(permissionUtils).checkPermissionGranted(any(int[].class), any(Runnable.class), any(Runnable.class));

        //when
        activity.onRequestPermissionsResult(MainActivity.PERMISSIONS_REQUEST_CODE,
                MainActivity.PERMISSIONS, new int[]{1, 1});

        //then
        verifyPermissionsAlert();
    }

    @Test
    public void shouldAskForLocationAfterGrantingPermissions() {
        //given
        doCallRealMethod().when(permissionUtils).checkPermissionGranted(any(int[].class), any(Runnable.class), any(Runnable.class));

        //when
        activity.onRequestPermissionsResult(MainActivity.PERMISSIONS_REQUEST_CODE,
                MainActivity.PERMISSIONS, new int[]{PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED});

        //then
        verify(viewModel).fetchLocation();
    }

    @Test
    public void shouldDisplayAlertAfterDenyingPermissions() {
        //given
        doCallRealMethod().when(permissionUtils).checkPermissionGranted(any(int[].class), any(Runnable.class), any(Runnable.class));

        //when
        activity.onRequestPermissionsResult(MainActivity.PERMISSIONS_REQUEST_CODE,
                MainActivity.PERMISSIONS, new int[]{PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_DENIED});

        //then
        verifyPermissionsAlert();
    }

    private void verifyPermissionsAlert() {
        AlertDialog latestAlertDialog = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowAlertDialog = shadowOf(latestAlertDialog);
        assertThat(shadowAlertDialog.getMessage()).isEqualTo(activity.getString(R.string.permissions_not_granted));
    }
}