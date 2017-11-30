package pl.mikolajlen.whereami.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import pl.mikolajlen.whereami.BuildConfig;
import pl.mikolajlen.whereami.application.TestApplication;

import static android.Manifest.permission.ACCESS_CHECKIN_PROPERTIES;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.ADD_VOICEMAIL;
import static android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE;
import static android.Manifest.permission.BIND_APPWIDGET;
import static android.Manifest.permission.BIND_INCALL_SERVICE;
import static android.Manifest.permission.BIND_MIDI_DEVICE_SERVICE;
import static android.Manifest.permission.BROADCAST_PACKAGE_REMOVED;
import static android.Manifest.permission.BROADCAST_SMS;
import static android.Manifest.permission.CAPTURE_AUDIO_OUTPUT;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.GET_PACKAGE_SIZE;
import static android.Manifest.permission.INSTALL_LOCATION_PROVIDER;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.RECORD_AUDIO;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.mikolajlen.whereami.mainActivity.MainActivity.PERMISSIONS_REQUEST_CODE;

/**
 * Created by mikolaj on 10.07.2017.
 */
@RunWith(ParameterizedRobolectricTestRunner.class)
@Config(constants = BuildConfig.class, minSdk = Build.VERSION_CODES.M, application = TestApplication.class)
public class PermissionUtilsTest {

    @ParameterizedRobolectricTestRunner.Parameters(name = "Test requesting permissions {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, ACCESS_CHECKIN_PROPERTIES, ACCESS_LOCATION_EXTRA_COMMANDS),
                        Arrays.asList(ACCESS_NETWORK_STATE, ACCESS_WIFI_STATE, ADD_VOICEMAIL)},
                {Arrays.asList(ACCESS_NETWORK_STATE),
                        Arrays.asList(BIND_ACCESSIBILITY_SERVICE, BROADCAST_PACKAGE_REMOVED, CHANGE_WIFI_STATE)},
                {Arrays.asList(BROADCAST_SMS, CAPTURE_AUDIO_OUTPUT),
                        Arrays.asList(BIND_ACCESSIBILITY_SERVICE)},
                {Arrays.asList(RECORD_AUDIO, BIND_INCALL_SERVICE),
                        Arrays.asList(INSTALL_LOCATION_PROVIDER, READ_CONTACTS)},
                {Arrays.asList(RECORD_AUDIO, GET_ACCOUNTS),
                        Arrays.asList(GET_PACKAGE_SIZE)},
                {Arrays.asList(BIND_MIDI_DEVICE_SERVICE),
                        Arrays.asList(BIND_APPWIDGET)},
                {Arrays.asList(ACCESS_NETWORK_STATE, ACCESS_WIFI_STATE, BIND_ACCESSIBILITY_SERVICE, BROADCAST_PACKAGE_REMOVED, CHANGE_WIFI_STATE),
                        Arrays.asList(ACCESS_COARSE_LOCATION)},
                {Arrays.asList(ACCESS_NETWORK_STATE),
                        Arrays.asList(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, INSTALL_LOCATION_PROVIDER, READ_CONTACTS, GET_PACKAGE_SIZE, CAPTURE_AUDIO_OUTPUT)}
        });
    }

    private Activity activity;
    private List<String> grantedPermissions;
    private List<String> deniedPermissions;

    public PermissionUtilsTest(List<String> grantedPermissions, List<String> deniedPermissions) {
        this.grantedPermissions = grantedPermissions;
        this.deniedPermissions = deniedPermissions;
    }

    @Before
    public void setUp() {
        activity = Mockito.spy(Robolectric.setupActivity(Activity.class));
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void shouldAskForNotGrantedPermission() {
        //given
        mockResponseForPermissions(grantedPermissions, PackageManager.PERMISSION_GRANTED);
        mockResponseForPermissions(deniedPermissions, PackageManager.PERMISSION_DENIED);
        PermissionUtils permissionUtils = new PermissionUtils(activity);
        permissionUtils.arePemissionsGranted(concatLists(grantedPermissions, deniedPermissions));

        //when
        permissionUtils.requestNotGrantedPermissions(PERMISSIONS_REQUEST_CODE);

        //then
        verify(activity).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), PERMISSIONS_REQUEST_CODE);
    }

    private String[] concatLists(List<String> list1, List<String> list2) {
        List<String> concatedList = new ArrayList<>(list1);
        concatedList.addAll(list2);
        return concatedList.toArray(new String[concatedList.size()]);
    }

    private void mockResponseForPermissions(List<String> permissions, int response) {
        for (String permission : permissions) {
            when(activity.checkPermission(eq(permission), anyInt(), anyInt())).thenReturn(response);
        }
    }
}