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
 * Adapter between the Provider Services and the services listview.
 */
public class ServicesAdapter extends ArrayAdapter<Provider.Service> {

    private final LayoutInflater mLayoutInflater;

    public ServicesAdapter(Context context, final List<Provider.Service> services) {
        super(context, R.layout.listview_provider_detail_row, services);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = mLayoutInflater.inflate(R.layout.listview_provider_detail_row, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Provider.Service service = getItem(position);
        viewHolder.serviceType.setText(service.getType());
        viewHolder.serviceAlgorithm.setText(service.getAlgorithm());
        viewHolder.serviceClassName.setText(service.getClassName());

        return view;
    }

    static class ViewHolder {
        TextView serviceType;
        TextView serviceAlgorithm;
        TextView serviceClassName;

        ViewHolder(View view) {
            this.serviceType = (TextView) view.findViewById(R.id.provider_detail_row_type);
            this.serviceAlgorithm = (TextView) view.findViewById(R.id.provider_detail_row_algorithm);
            this.serviceClassName = (TextView) view.findViewById(R.id.provider_detail_row_classname);
        }
    }
}
