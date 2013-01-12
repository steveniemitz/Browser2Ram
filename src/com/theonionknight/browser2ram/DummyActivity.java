package com.theonionknight.browser2ram;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DummyActivity extends Activity
{
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button b = (Button)this.findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) 
        	{
        		CleanCache();
        	}
        });
    }
    
    private void CleanCache()
    {
    	Log.d("Browser2Ram", "Installing...");
    	try
    	{
	    	final Process su = Runtime.getRuntime().exec("su");
	        final DataOutputStream os = new DataOutputStream(su.getOutputStream());
	        
	        os.writeBytes("killall com.android.browser\n");
	        os.writeBytes("umount /data/data/com.android.browser/cache\n");
	        
	        //os.writeBytes("killall com.android.chrome\n");
	        //os.writeBytes("umount /data/data/com.android.chrome/cache\n");

	        //os.writeBytes("killall com.chrome.beta\n");
	        //os.writeBytes("umount /data/data/com.chrome.beta/cache\n");
	        
	        Browser2Ram.MountCache(os);
    	} 
    	catch (IOException ex)
    	{
    		Log.e("Browser2Ram", ex.toString());
    	}
    }
}
