package org.openas2.util;

import java.util.HashMap;
import java.util.Map;

public class Properties
{
	public static String APP_VERSION_PROP = "app.version";
	public static String APP_TITLE_PROP = "app.title";
	public static String APP_BASE_DIR_PROP = "app.base.dir";
	public static String HTTP_USER_AGENT_PROP = "http.user.agent";
	
	public static String AS2_MESSAGE_ID_FORMAT = "as2_message_id_format";
	public static String AS2_MDN_MESSAGE_ID_FORMAT = "as2_mdn_message_id_format";
	public static String AS2_MESSAGE_ID_ENCLOSE_IN_BRACKETS = "as2_message_id_enclose_in_brackets";
	
	public static String AS2_MDN_RESP_MAX_WAIT_SECS = "as2_mdn_response_max_wait_seconds";

	private static Map<String, String> _properties = new HashMap<String, String>();
	
	public static void setProperties(Map<String, String> map)
	{
		_properties.putAll(map);
	}

	public static void setProperty(String prop, String val)
	{
		_properties.put(prop, val);
	}

	public static Map<String, String> getProperties()
	{
		return _properties;
	}

	public static String getProperty(String key, String fallback)
	{
		String val = _properties.get(key);
		if (val == null)
		{
			return fallback;
		}
		return val;
	}


}
