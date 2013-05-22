package com.bonch.cvcreator;
import org.acra.*;
import org.acra.annotation.*;

@ReportsCrashes(formKey = "dEo3UndNc2NZNXdpM1F6LUlmeUt5UWc6MQ",
mode = ReportingInteractionMode.TOAST,
forceCloseDialogAfterToast = false, // optional, default false
resToastText = R.string.crash_toast_text) 
public class MyApplication extends android.app.Application{
	@Override
	  public void onCreate() {
	      super.onCreate();

	      // The following line triggers the initialization of ACRA
	      ACRA.init(this);
	  }
	
}
