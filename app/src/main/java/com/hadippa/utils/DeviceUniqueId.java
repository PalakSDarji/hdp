package com.hadippa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

public class DeviceUniqueId {

	private static final String TAG = "DeviceUniqueId";
	
	protected static final String PREFS = "device_id";
	
    protected static final String PREFS_DEVICE_ID = "device_id";
    
    protected volatile static String uniqueId;
    
    protected volatile static UUID uniqueIdUUID;

    public DeviceUniqueId() {
    	// Empty
    }
    
    public DeviceUniqueId(Context context, boolean useAndroidID) {
    	if (useAndroidID) {
    		uniqueId = generateUniqueIdAndroid(context);
    	} else {
    		uniqueId = generateUniqueId(context);
    	}
    }
    
    private String generateUniqueId(Context context) {
        if (uniqueId == null || TextUtils.isEmpty(uniqueId)) {
            synchronized (DeviceUniqueId.class) {
                if (uniqueId == null || TextUtils.isEmpty(uniqueId)) {
                    final SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        // Use the ID previously stored in the preferences file
                    	uniqueId = id;
                    } else {
                        try {
                        	// OPTION 1 - DEVICE ID (IMEI)
                            final String  deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                            
                            // FAIL OVER 1 - RANDOM ID                            
                            Random tmpRand = new Random();
                            Long tmpRandId = tmpRand.nextLong();
                            String randomId = String.valueOf(tmpRandId < 0 ? tmpRandId * -1 : tmpRandId);
                            randomId = randomId.length() > 13 ? "98" + randomId.substring(0, 13) : randomId;
                            uniqueId = deviceId != null && !deviceId.equals("000000000000000") ? deviceId : randomId;
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                        // Write the value out to the preferences file
                        setDeviceUniqueId(context, uniqueId);
                    }
                    return uniqueId;
                } else {
                	return uniqueId;
                }
            }
        } else {
        	return uniqueId;
        }
    }
    
    private String generateUniqueIdAndroid(Context context) {
        if (uniqueIdUUID == null) {
            synchronized (DeviceUniqueId.class) {
                if (uniqueIdUUID == null) {
                    final SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        // Use the ID previously stored in the preferences file
                    	uniqueIdUUID = UUID.fromString(id);
                    } else {
                    	// OPTION 1 - ANDROID ID
                        final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                        try {
                            if (androidId != null && !TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
                            	uniqueIdUUID = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                            	// FAIL OVER 1 - DEVICE ID (IMEI)
                                final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                // FAIL OVER 2 - RANDOM UUID
                                uniqueIdUUID = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        // Write the value out to the preferences file
                        setDeviceUniqueId(context, uniqueIdUUID.toString());
                    }
                    return uniqueIdUUID.toString();
                } else {
                	return uniqueIdUUID.toString();
                }
            }
        } else {
        	return uniqueIdUUID.toString();
        }
    }
    
    public String getAndroidId(Context context) {
    	final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    	try {
            if (androidId != null && !TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
            	return androidId;
            }
        } catch (Exception e) {
        	 Log.e(TAG, e.toString());
        }
    	return "";
    }
    
    public void setDeviceUniqueId(Context context, String id) {
	    final SharedPreferences prefs = context.getSharedPreferences(PREFS, 0);
	    prefs.edit().putString(PREFS_DEVICE_ID, id).apply();
    }
    
    public String getDeviceUniqueId() {
        return uniqueId;
    }
}
