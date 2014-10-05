package be.appfoundry.android.dynsecprovider;

import com.google.common.collect.Ordering;

import java.security.Provider;
import java.security.Security;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * JCA utils.
 */
public class JcaUtils {

    private static DecimalFormat df = new DecimalFormat("#.##");

    static {
        df.setMinimumFractionDigits(2);
    }

    /**
     * Get a list of the installed providers.
     * The order of the providers is their preference order.
     *
     * @return the installed cryptographic service providers
     */
    public static List<Provider> getSecurityProviders() {
        return Arrays.asList(Security.getProviders());
    }

    /**
     * Get the services supported by a provider.
     *
     * @param provider java security API provider
     * @return unmodifiable Set of supported services
     */
    public static Set<Provider.Service> getProviderServices(final Provider provider) {
        return provider.getServices();
    }

    /**
     * Get the services supported by a provider, sorted by service type.
     *
     * @param provider java security API provider
     * @return sorted List of supported services
     */
    public static List<Provider.Service> getSortedProviderServices(final Provider provider) {
        List<Provider.Service> sorted = Ordering.from(new ServiceTypeComparator())
                .sortedCopy(getProviderServices(provider));
        return sorted;
    }

    private static class ServiceTypeComparator implements Comparator<Provider.Service> {
        @Override
        public int compare(Provider.Service lhs, Provider.Service rhs) {
            return lhs.getType().compareToIgnoreCase(rhs.getType());
        }
    }

    /**
     * Get (debug) Provider info.
     */
    public static String printProviderInfo(final Provider provider) {
        checkNotNull(provider);
        return String.format("%s/%s/%f\n", provider.getName(), provider.getInfo(), provider.getVersion());
    }

    /**
     * Get (debug) Service info.
     */
    public static String printServiceInfo(final Provider.Service service) {
        checkNotNull(service);
        return String.format("--- %s/%s/%s\n", service.getType(), service.getAlgorithm(), service.getClassName());
    }

    /**
     * Get full (debug) Provider info.
     */
    public static String printFullProviderInfo(final Provider provider) {
        checkNotNull(provider);
        StringBuilder sb = new StringBuilder(printProviderInfo(provider));
        for (Provider.Service service : getSortedProviderServices(provider)) {
            sb.append(printServiceInfo(service));
        }
        return sb.toString();
    }

    /**
     * Get a formatted Provider version number.
     */
    public static String formatProviderVersion(final Provider provider) {
        checkNotNull(provider);
        return df.format(provider.getVersion());
    }
}
