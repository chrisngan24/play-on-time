package com.play;

import android.content.ContentValues;
import android.content.Context;

import com.sonyericsson.extras.liveware.aef.registration.Registration;
import com.sonyericsson.extras.liveware.aef.sensor.Sensor;
import com.ngan.play.R;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.registration.HostApplicationInfo;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;
import com.sonyericsson.extras.liveware.extension.util.sensor.AccessorySensor;

public class PlayRegistrationInformation extends RegistrationInformation {

	final Context mContext;

    /**
     * Create sensor registration object
     *
     * @param context The context
     */
    protected PlayRegistrationInformation(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        mContext = context;
    }

    @Override
    public int getRequiredControlApiVersion() {
        return 1;
    }

    @Override
    public int getRequiredSensorApiVersion() {
        return 1;
        //return RegistrationInformation.API_NOT_REQUIRED;
    }

    @Override
    public int getRequiredNotificationApiVersion() {
        return RegistrationInformation.API_NOT_REQUIRED;
    }

    @Override
    public int getRequiredWidgetApiVersion() {
        return RegistrationInformation.API_NOT_REQUIRED;
    }



    /**
     * Get the extension registration information.
     *
     * @return The registration configuration.
     */
    @Override
    public ContentValues getExtensionRegistrationConfiguration() {
        String iconHostapp = ExtensionUtils.getUriString(mContext, R.drawable.ic_launcher);
        String iconExtension = ExtensionUtils.getUriString(mContext, R.drawable.icon_extension);

        ContentValues values = new ContentValues();

        values.put(Registration.ExtensionColumns.CONFIGURATION_ACTIVITY,
                PlayPreferenceActivity.class.getName());
        values.put(Registration.ExtensionColumns.CONFIGURATION_TEXT,
                mContext.getString(R.string.configuration_text));
        values.put(Registration.ExtensionColumns.NAME, mContext.getString(R.string.extension_name));
        values.put(Registration.ExtensionColumns.EXTENSION_KEY,
                PlayExtensionsService.EXTENSION_KEY);
        values.put(Registration.ExtensionColumns.HOST_APP_ICON_URI, iconHostapp);
        values.put(Registration.ExtensionColumns.EXTENSION_ICON_URI, iconExtension);
        values.put(Registration.ExtensionColumns.NOTIFICATION_API_VERSION,
                getRequiredNotificationApiVersion());
        values.put(Registration.ExtensionColumns.PACKAGE_NAME, mContext.getPackageName());

        return values;
    }

    @Override
    public boolean isDisplaySizeSupported(int width, int height) {
        return (width == PlaySensorControl.WIDTH && height == PlaySensorControl.HEIGHT);
    }

    @Override
    public boolean isSensorSupported(AccessorySensor sensor) {
       return Sensor.SENSOR_TYPE_ACCELEROMETER.equals(sensor.getType().getName());

    }

    @Override
    public boolean isSupportedSensorAvailable(Context context, HostApplicationInfo hostApplication) {
        // Both control and sensor needs to be supported to register as sensor
        return super.isSupportedSensorAvailable(context, hostApplication)
                && super.isSupportedControlAvailable(context, hostApplication);

    }

    @Override
    public boolean isSupportedControlAvailable(Context context, HostApplicationInfo hostApplication) {
        // Both control and sensor needs to be supported to register as control.
        return super.isSupportedSensorAvailable(context, hostApplication)
                && super.isSupportedControlAvailable(context, hostApplication);
    }

}
