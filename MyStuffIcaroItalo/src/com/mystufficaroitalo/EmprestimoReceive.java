package com.mystufficaroitalo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EmprestimoReceive extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, EmprestimoService.class);
		context.startService(service);
	}
	
}
