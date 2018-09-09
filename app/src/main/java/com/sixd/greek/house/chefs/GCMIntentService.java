package com.sixd.greek.house.chefs;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.sixd.greek.house.chefs.utils.Constant;


public class GCMIntentService extends GCMBaseIntentService {

	final static String MY_ACTION = "MY_ACTION";
	Intent notificationIntent;
	Intent dintent;
	String title = "", dataReceived = "", type = "", data = "", msg = "",
			invId = "";

	public GCMIntentService() {
		super(Constant.GCMSenderId);
	}

	@Override
	protected void onError(Context context, String regId) {
		// TODO Auto-generated method stub

		//Logger.i(context, "error registration id : " + regId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//Logger.i(context, "messages in onMessage()" + "msg      ");
		reactToNotification(context, intent);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		// TODO Auto-generated method stub
		handleRegistration(context, regId);
		//Logger.i(context, "Registration ID = " + regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		// TODO Auto-generated method stub
	}

	private void reactToNotification(Context context, Intent intent) {
		dataReceived = "";
		dataReceived = intent.getStringExtra("message");
		String dataReceived1 = intent.getStringExtra("message");
		//Logger.i(context, " dataReceivede202: " + dataReceived);
		//Logger.i(context, " dataReceived303: " + dataReceived1);
		new showNotification(context).execute(dataReceived, dataReceived1);
	}

	private void handleRegistration(Context context, String regId) {
		// TODO Auto-generated method stub
		Constant.notificationId = regId;
		//Logger.i(context, "Registration ID = " + regId);
	}

	private class showNotification extends AsyncTask<String, Void, Bitmap> {

		Context context;
		String message;
		private String dataReceived1;

		public showNotification(Context context) {
			super();
			this.context = context;
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			InputStream in;
			message = params[0];
			dataReceived1 = params[1];
			//Logger.i("dataReceived1 N: 101", dataReceived1);

			try {
				Bitmap myBitmap = null;

				if (dataReceived1.contains("<br>")) {
					URL url = new URL(/*
									 * com.sixdegrees.utils.URL.
									 * BASE_URL_NOTIFICATION +
									 */dataReceived1.substring(0,
							dataReceived1.indexOf("<br>")));
					//Logger.i("image URL", url.toString());
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setDoInput(true);
					connection.connect();
					in = connection.getInputStream();
					myBitmap = BitmapFactory.decodeStream(in);
				}
				return myBitmap;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {

			super.onPostExecute(result);
			try {
				// AnimationSound aSound=new AnimationSound(context,
				// R.raw.phonering);

				PowerManager pm = (PowerManager) context
						.getSystemService(Context.POWER_SERVICE);
				WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
				wakeLock.acquire();

				KeyguardManager km = (KeyguardManager) context
						.getSystemService(Context.KEYGUARD_SERVICE);
				final KeyguardManager.KeyguardLock kl = km
						.newKeyguardLock("MyKeyguardLock");
				/* kl.disableKeyguard(); */

				int icon = R.drawable.app_icon_new;
				CharSequence tickerText = "Barcel";
				long when = System.currentTimeMillis();
				// CharSequence contentTitle = "Meeting coming up soon!";
				CharSequence contentText = dataReceived;
				NotificationManager notificationManager = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				// Notification notification = new Notification(icon,
				// tickerText, when);
				notificationIntent = null;
				Uri alarmSound = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

				NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
						GCMIntentService.this);
				// NotificationCompat.BigPictureStyle notifStyle=new
				// NotificationCompat.BigPictureStyle();
				if (null != result) {
					notificationBuilder
							.setLargeIcon(
									BitmapFactory.decodeResource(
											getResources(), icon))
							.setContentTitle(tickerText)
							.setStyle(
									new NotificationCompat.BigPictureStyle()
											.bigPicture(result).setSummaryText(
													dataReceived1
													.substring(dataReceived1.indexOf("<br>") + 4,
															dataReceived1.length()))).setWhen(when)
							.setAutoCancel(true)
							.setVibrate(new long[] { 100L, 100L, 200L, 500L })
							.setSound(alarmSound)
							.setPriority(NotificationCompat.PRIORITY_DEFAULT);
				} else {
					//Logger.i("message N: 102", message);
					notificationBuilder
							.setLargeIcon(
									BitmapFactory.decodeResource(
											getResources(), icon))
							.setContentTitle(tickerText)
							.setContentText(message)
							.setStyle(new NotificationCompat.BigTextStyle())
							.setWhen(when).setAutoCancel(true)
							.setVibrate(new long[] { 100L, 100L, 200L, 500L })
							.setSound(alarmSound)
							.setPriority(NotificationCompat.PRIORITY_DEFAULT);
				}

				if (Build.VERSION.SDK_INT >= 21) {
					notificationBuilder.setSmallIcon(R.drawable.app_icon_new);
				} else {
					notificationBuilder.setSmallIcon(R.drawable.app_icon_new);
				}

				//notificationIntent = new Intent(context, PushNotifyFull.class);

				/* String dataReceived = null; */

				// Intent intent = new Intent(activity2.this, activity1.class);
//				if (result != null) {
//					notificationIntent.putExtra("STRING_I_NEED",  dataReceived1
//							.substring(dataReceived1.indexOf("<br>") + 4,
//									dataReceived1.length()));
//
//					notificationIntent.putExtra("message", dataReceived1
//							.substring(dataReceived1.indexOf("<br>") + 4,
//									dataReceived1.length()));
//				} else {
					notificationIntent.putExtra("STRING_I_NEED", dataReceived1);
					notificationIntent.putExtra("message", dataReceived1);
//				}

				PendingIntent pendingIntent = null;
				// PendingIntent contentIntent =
				// PendingIntent.getActivity(context, UNIQUE_INT_PER_CALL,
				// notificationIntent, 0);
				// PendingIntent contentIntent =
				// PendingIntent.getActivity(context, 0, notificationIntent,
				// PendingIntent.FLAG_UPDATE_CURRENT);
				pendingIntent = PendingIntent.getActivity(context, 0,
						notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

				// pendingIntent = PendingIntent.getActivity(context,
				// UNIQUE_INT_PER_CALL,notificationIntent, 0);
				/*
				 * pendingIntent = PendingIntent.getActivity(context,
				 * 0,notificationIntent, 0);
				 */
				notificationBuilder.setContentIntent(pendingIntent);
				notificationManager.notify(50, notificationBuilder.build());

				PowerManager pm1 = (PowerManager) context
						.getSystemService(Context.POWER_SERVICE);
				WakeLock wl = pm1.newWakeLock(PowerManager.FULL_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
				wl.acquire();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
