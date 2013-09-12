package com.play;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExtensionReceiver extends BroadcastReceiver{
    private String verse ="Error loading Verse";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(PlayExtensionsService.NSD_SERVICE, "onReceive: "+ intent.getAction());

        intent.setClass(context, PlayExtensionsService.class);
        context.startService(intent);
		
	}







}
