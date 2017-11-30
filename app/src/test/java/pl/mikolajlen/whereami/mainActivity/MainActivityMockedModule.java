package pl.mikolajlen.whereami.mainActivity;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import pl.mikolajlen.whereami.location.LocationViewModel;
import pl.mikolajlen.whereami.utils.PermissionUtils;

import static org.mockito.Mockito.mock;

/**
 * Created by mikolaj on 22.07.2017.
 */
@Module
public class MainActivityMockedModule {

    @Provides
    PermissionUtils providePermissionUtils() {
        return mock(PermissionUtils.class);
    }

    @Provides
    ViewModelProvider.Factory provideMockingFacotry() {
        return new MockingFactory();
    }

    class MockingFactory implements ViewModelProvider.Factory {

        @Override
        public LocationViewModel create(Class modelClass) {
            return mock(LocationViewModel.class);
        }
    }
}
