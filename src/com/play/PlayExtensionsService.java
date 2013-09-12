package com.play;

import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PlayExtensionsService extends ExtensionService{
	public static final String EXTENSION_KEY = "com.play.key";
	public static final String LOG_TAG = "Hello Smart Watch Extension";
	public static final String NSD_SERVICE = "Service";

	public PlayExtensionsService(String extensionKey) {
		super(extensionKey);
		// TODO Auto-generated constructor stub
	}


	
	public PlayExtensionsService(){
		super(EXTENSION_KEY);
	}

	@Override
	protected RegistrationInformation getRegistrationInformation() {
		// TODO Auto-generated method stub
		return new PlayRegistrationInformation(this);
	}

	@Override
	protected boolean keepRunningWhenConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ControlExtension createControlExtension(String hostAppPackageName) {
		// TODO Auto-generated method stub
//        String verse = getVerse();
//        String verse = "Matthew";
        //String verse = getVerse();
		return new PlaySensorControl(this, hostAppPackageName);
	}


	

}
