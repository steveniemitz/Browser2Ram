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
    	os.writeBytes("rm -rf /data/data/com.android.browser/cache/*\n");
        os.writeBytes("mount -t tmpfs -o size=100m browser_cache /data/data/com.android.browser/cache\n");
        
        os.writeBytes("rm -rf /data/data/com.android.chrome/cache/*\n");
        os.writeBytes("mount -t tmpfs -o size=100m chrome_cache /data/data/com.android.chrome/cache\n");
        
        os.close();
    }
    
    private void onBootCompleted(Context context) {
        try {
            final Process su = Runtime.getRuntime().exec("su");
            final DataOutputStream os = new DataOutputStream(su.getOutputStream());
            //final DataInputStream is = new DataInputStream(su.getInputStream());
            	
            MountCache(os);
            
            //Log.d("Browser2Ram", is.readUTF());
        } catch (Exception e) {
        	Log.d("Browser2Ram", e.toString());
            return;
        }
        
        Log.d("Browser2Ram", "Browser2Ram installed");
    }
}
