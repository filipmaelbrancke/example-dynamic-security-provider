package be.appfoundry.android.dynsecprovider;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.Provider;
import java.security.Security;
import java.util.List;

/**
 * A fragment representing a single Provider detail screen.
 * This fragment is either contained in a {@link ProviderListActivity}
 * in two-pane mode (on tablets) or a {@link ProviderDetailActivity}
 * on handsets.
 */
public class ProviderDetailFragment extends ListFragment {

    private static final String TAG = ProviderDetailFragment.class.getSimpleName();

    /**
     * The fragment argument representing the Provider ID (name) that this fragment
     * represents.
     */
    public static final String ARG_PROVIDER_ID = "provider_id";

    /**
     * The Provider to check for services.
     */
    private Provider mProvider;

    /**
     * Services adapter.
     */
    private ServicesAdapter mAdapter;

    private TextView mProviderName;
    private TextView mProviderInfo;

    /**
     * Create a ProviderDetailFragment instance.
     *
     * @param id Provider id
     */
    public static ProviderDetailFragment newInstance(final String id) {
        final Bundle arguments = new Bundle();
        arguments.putString(ARG_PROVIDER_ID, id);
        final ProviderDetailFragment fragment = new ProviderDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProviderDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_PROVIDER_ID)) {
            mProvider = Security.getProvider(getArguments().getString(ARG_PROVIDER_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_provider_detail, container, false);

        mProviderName = (TextView) rootView.findViewById(R.id.provider_detail_name);
        mProviderInfo = (TextView) rootView.findViewById(R.id.provider_detail_info);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mProvider != null) {
            //Log.d(TAG, JcaUtils.printFullProviderInfo(mProvider));
            mProviderName.setText(mProvider.getName());
            mProviderInfo.setText(mProvider.getInfo());
        }

        if (mProvider != null) {
            List<Provider.Service> services = JcaUtils.getSortedProviderServices(mProvider);
            mAdapter = new ServicesAdapter(getActivity(), services);
            setListAdapter(mAdapter);
        }

        if (mProvider != null) {
            Log.d(TAG, JcaUtils.printProviderInfo(mProvider));
            for (Provider.Service service : mProvider.getServices()) {
                Log.d(TAG, JcaUtils.printServiceInfo(service));
            }
        }
    }
}
