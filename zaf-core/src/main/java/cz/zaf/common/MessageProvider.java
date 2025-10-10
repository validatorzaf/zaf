package cz.zaf.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageProvider {
    private static final String BUNDLE_NAME = "messages";
    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    private final ResourceBundle mainBundle;
    private final ResourceBundle fallbackBundle;

    /**
     * cached first created message provider
     */
    private static MessageProvider messageProvider = null;

    public MessageProvider(Locale locale) {
        Locale effectiveLocale = (locale != null) ? locale : DEFAULT_LOCALE;
        this.mainBundle = ResourceBundle.getBundle(BUNDLE_NAME, effectiveLocale);
        this.fallbackBundle = ResourceBundle.getBundle(BUNDLE_NAME, DEFAULT_LOCALE);
    }

    /**
     * Get translated message by key.
     * @param key message key
     * @param args optional formatting arguments
     * @return translated message or !key! if missing in all bundles
     */
    public String get(String key, Object... args) {
        String pattern = findPattern(key, null);
        return MessageFormat.format(pattern, args);
    }

    /**
     * Get translated message by key with explicit fallback text.
     * @param key message key
     * @param defaultText fallback text if missing in bundles
     * @param args optional formatting arguments
     * @return translated message
     */
    public String getOrDefault(String key, String defaultText, Object... args) {
        String pattern = findPattern(key, defaultText);
        return MessageFormat.format(pattern, args);
    }

    private String findPattern(String key, String defaultText) {
        try {
            if (mainBundle.containsKey(key))
                return mainBundle.getString(key);
            if (fallbackBundle.containsKey(key))
                return fallbackBundle.getString(key);
            return (defaultText != null) ? defaultText : "!" + key + "!";
        } catch (MissingResourceException e) {
            return (defaultText != null) ? defaultText : "!" + key + "!";
        }
    }

    public static Locale detectLocale() {
        Locale systemLocale = Locale.getDefault();
        if ("cs".equalsIgnoreCase(systemLocale.getLanguage())) {
        	return new Locale.Builder().setLanguage("cs").build();
        }
        return DEFAULT_LOCALE;
    }

	public static MessageProvider getProvider() {
		if(messageProvider==null) {
			var locale = detectLocale();
			messageProvider = new MessageProvider(locale);			
		}
		return messageProvider;
	}
}
