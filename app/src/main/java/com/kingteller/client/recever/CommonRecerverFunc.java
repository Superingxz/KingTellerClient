package com.kingteller.client.recever;

import android.content.Context;
import android.content.Intent;

public class CommonRecerverFunc {
	
	public void wUpdata(Context context) {
		Intent intent = new Intent("android.intent.action.W_UPDATA");
		context.sendBroadcast(intent); 
	}

}
