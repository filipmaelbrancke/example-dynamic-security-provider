package be.appfoundry.android.dynsecprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;


/**
 * An activity representing a list of Providers.
 */
public class ProviderListActivity extends Activity
        implements ProviderListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);

        if (findViewById(R.id.provider_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ProviderListFragment) getFragmentManager()
                    .findFragmentById(R.id.provider_list))
                    .setActivateOnItemClick(true);
        }

        //ProviderInstaller.installIfNeededAsync(getApplicationContext(), providerInstallListener);
    }

    /**
     * Callback method from {@link ProviderListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onProviderSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            ProviderDetailFragment fragment = ProviderDetailFragment.newInstance(id);
            getFragmentManager().beginTransaction()
                    .replace(R.id.provider_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ProviderDetailActivity.class);
            detailIntent.putExtra(ProviderDetailFragment.ARG_PROVIDER_ID, id);
            startActivity(detailIntent);
        }
    }

    private ProviderInstaller.ProviderInstallListener providerInstallListener = new ProviderInstaller.ProviderInstallListener() {
        @Override
        public void onProviderInstalled() {
            // Provider installed, do stuff...
        }

        @Override
        public void onProviderInstallFailed(int errorCode, Intent recoveryIntent) {
            // Provider installation failed
            if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
                // Show an error dialog
                GooglePlayServicesUtil.getErrorDialog(errorCode, ProviderListActivity.this, 0).show();
            }
        }
    };
}
