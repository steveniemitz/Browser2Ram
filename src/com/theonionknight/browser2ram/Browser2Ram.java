package com.theonionknight.browser2ram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class Browser2Ram extends BroadcastReceiver {
    
    /* ****** ****** */

    @Override public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            this.onBootCompleted(context);
        }

        Log.d("Browser2Ram", intent.getAction());
    }

    /* ****** ****** */

    public static void MountCache(DataOutputStream os) throws IOException
    {
    	MountCache("/data/data/com.android.browser/cache", "browser_cache", os);
    	//MountCache("/data/data/com.android.chrome/cache", "chrome_cache", os);
    	//MountCache("/data/data/com.chrome.beta/cache", "chrome_beta_cache", os);
    	
        os.close();
    }
    
    private static void MountCache(String directory, String mountName, DataOutputStream os) throws IOException
    {
    	os.writeBytes("rm -rf " + directory + "/*\n");
        os.writeBytes("mount -t tmpfs -o size=50m " + mountName + " " + directory + "\n");
    }
    
    private void onBootCompleted(Context context) {
        try {
            final Process su = Runtime.getRuntime().exec("su");
            final DataOutputStream os = new DataOutputStream(su.getOutputStream());
            MountCache(os);
            
        } catch (Exception e) {
        	Log.d("Browser2Ram", e.toString());
            return;
        }
        
        Log.d("Browser2Ram", "Browser2Ram installed");
    }
}
