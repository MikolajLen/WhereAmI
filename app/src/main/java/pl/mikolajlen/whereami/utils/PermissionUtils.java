package pl.mikolajlen.whereami.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.annimon.stream.Collectors;
import com.annimon.stream.IntStream;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mikolaj on 19.12.2016.
 */

public class PermissionUtils {

    private Activity activity;

    private List<String> notGrantedPermissions = new ArrayList<>();

    @Inject
    public PermissionUtils(Activity activity) {
        this.activity = activity;
    }

    public void checkPermissionGranted(@NonNull int[] grantResults, Runnable grantedAction, Runnable deniedAction) {
        if (areAllPermissionsGranterd(grantResults)) {
            grantedAction.run();
        } else {
            deniedAction.run();
        }
    }

    private boolean areAllPermissionsGranterd(@NonNull int[] grantResults) {
        return IntStream.of(grantResults)
                .allMatch(value -> value == PackageManager.PERMISSION_GRANTED);
    }

    public boolean arePemissionsGranted(String[] permissions) {
        notGrantedPermissions = Stream.of(permissions)
                .filter(value -> ActivityCompat.checkSelfPermission(activity, value)
                        != PackageManager.PERMISSION_GRANTED)
                .collect(Collectors.toList());
        return notGrantedPermissions.size() == 0;
    }

    public void requestNotGrantedPermissions(int requestCode) {
        ActivityCompat.requestPermissions(activity, notGrantedPermissions.toArray(new String[notGrantedPermissions.size()]), requestCode);
    }
}
