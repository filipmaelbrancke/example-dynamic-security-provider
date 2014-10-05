package be.appfoundry.android.dynsecprovider;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

/**
 * Dynamic Security Provider application.
 */
public class DspApplication extends Application {

    private static final String TAG = DspApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            ProviderInstaller.installIfNeeded(getBaseContext());
        } catch (GooglePlayServicesRepairableException e) {
            Log.d(TAG, "Google Play Services should be installed. " + e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d(TAG, "Google Play Services is not available. " + e.getMessage());
        }
    }


}
