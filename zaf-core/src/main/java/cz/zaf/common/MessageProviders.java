package cz.zaf.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.Locale.Builder;
import java.util.Map;

/**
 * Collection of message providers.
 * 
 * This class serves as a collection of prepared message providers
 * for given locale.
 */
public class MessageProviders {
	
	/**
	 * Message providers by locale
	 */
	static private class MultiLocaleProvider {
		
		Map<Locale, MessageProvider> providersByLocale = new HashMap<>();
		
		final String bundleName;
		
		public MultiLocaleProvider(final String bundleName) {
			this.bundleName = bundleName;
		}

		public MessageProvider getProvider(Locale locale) {
			return providersByLocale.computeIfAbsent(locale, l-> new MessageProvider(l, bundleName));
		}
		
	}
	
	static private Map<String, MultiLocaleProvider> providers = new HashMap<>();

	public static MessageProvider getProvider(String bundleName, Locale locale) {
		synchronized (providers) {
			var multiProvider = providers.computeIfAbsent(bundleName, bn -> new MultiLocaleProvider(bn));
			return multiProvider.getProvider(locale);
		}		
	}

	/** 
	 * Detect system locale
	 * @return
	 */
	public static Locale detectLocale() {
	    Locale systemLocale = Locale.getDefault();
	    if ("cs".equalsIgnoreCase(systemLocale.getLanguage())) {
	    	return new Locale.Builder().setLanguage("cs").build();
	    }
	    return MessageProvider.DEFAULT_LOCALE;
	}
	
}
