package be.appfoundry.android.dynsecprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.security.Provider;
import java.util.List;

/**
 * Adapter between the providers and the providers listview.
 */
public class ProviderAdapter extends ArrayAdapter<Provider> {

    private final LayoutInflater mLayoutInflater;

    public ProviderAdapter(final Context context, final List<Provider> providers) {
        super(context, R.layout.listview_provider_row, providers);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = mLayoutInflater.inflate(R.layout.listview_provider_row, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Provider provider = getItem(position);
        viewHolder.providerName.setText(provider.getName());
        viewHolder.providerVersion.setText(JcaUtils.formatProviderVersion(provider));
        viewHolder.providerInfo.setText(provider.getInfo());

        return view;
    }

    static class ViewHolder {
        TextView providerName;
        TextView providerVersion;
        TextView providerInfo;

        ViewHolder(View view) {
            this.providerName = (TextView) view.findViewById(R.id.provider_row_name);
            this.providerVersion = (TextView) view.findViewById(R.id.provider_row_version);
            this.providerInfo = (TextView) view.findViewById(R.id.provider_row_info);
        }
    }
}
