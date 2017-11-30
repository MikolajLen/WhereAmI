package pl.mikolajlen.whereami.mainActivity;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.annotations.NonNull;
import pl.mikolajlen.whereami.R;
import pl.mikolajlen.whereami.databinding.ActivityMainBinding;
import pl.mikolajlen.whereami.location.LocationViewModel;
import pl.mikolajlen.whereami.utils.PermissionUtils;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    public static final String[] PERMISSIONS = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
    public static final int PERMISSIONS_REQUEST_CODE = 123;
    public static final int MAX_ADDRESSESS_RESULTS = 5;

    @Inject
    PermissionUtils permissionUtils;

    @Inject
    ViewModelProvider.Factory factory;

    @VisibleForTesting
    LocationViewModel viewModel;

    private ActivityMainBinding viewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        AndroidInjection.inject(this);
        viewModel = ViewModelProviders.of(this, factory)
                .get(LocationViewModel.class);
        viewDataBinding.setLocationViewData(viewModel.getLocationViewData());
        viewDataBinding.fetchLocationButton.setOnClickListener(v -> getLastKnownLocation());
    }

    public void getLastKnownLocation() {
        if (permissionUtils.arePemissionsGranted(PERMISSIONS)) {
            viewModel.fetchLocation();
        } else {
            permissionUtils.requestNotGrantedPermissions(PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            permissionUtils.checkPermissionGranted(grantResults, () -> viewModel.fetchLocation(),
                    () -> showPermissionsRequiredDialog());
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showPermissionsRequiredDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permissions_not_granted)
                .setTitle(R.string.alert_dialog_title)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
