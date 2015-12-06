package com.at.androbot;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Random;

public class Control extends Activity  {

	//public GFXView mView;
	static int x;
	DataLoop mData;
	WakeLock mWakeLock;
	static String DATA;
	String str = "-";
	BluetoothService mBlueService = MainActivity.mBluetoothService;
	Button bhit,bDisconnect;
	ImageButton ib1, ib2, ib3, ib4, ib5, ib6;

    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    public static final String TOAST = "toast";


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// OnCreate
		super.onCreate(savedInstanceState);
		mData = new DataLoop(mBlueService);
		setContentView(R.layout.control);
		ib1 = (ImageButton) findViewById(R.id.ib1);
		ib2 = (ImageButton) findViewById(R.id.ib2);
		ib3 = (ImageButton) findViewById(R.id.ib3);
		ib4 = (ImageButton) findViewById(R.id.ib4);
		ib5 = (ImageButton) findViewById(R.id.ib5);
		ib6 = (ImageButton) findViewById(R.id.ib6);
		bhit = (Button) findViewById(R.id.bhit);
        bDisconnect = (Button) findViewById(R.id.disconnect);

        ButtonInti();
		x = 48;
		// PowerManger Wakelock
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Wake Lock");

		Random rgen = new Random(); // Random number generator
		int[] seq = new int[6];

		// Initialize the array to the ints
		for (int i = 1; i - 1 < seq.length; i++) {
			seq[i - 1] = i;
		}

		// Shuffle by exchanging each element randomly
		for (int i = 0; i < seq.length; i++) {
			int randomPosition = rgen.nextInt(seq.length);
			int temp = seq[i];
			seq[i] = seq[randomPosition];
			seq[randomPosition] = temp;
		}
		// Create the String
		for (int a : seq)
			str += Integer.toString(a) + "-";
		

	}

	void ButtonInti() {
        bDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlueService.stop();
                MainActivity.tv1.setText("You tried to disconnect.");
                finish();
            }
        });
		
		ib1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					x = 48;
				}else
				x = 49;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
				return true;

			}
		});
		
		ib2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					x = 48;
				}else
				x = 50;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
				return true;

			}
		});
		ib3.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					x = 48;
				}else
				x = 51;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
				return true;

			}
		});
		ib4.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					x = 48;
				}else
				x = 52;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
				return true;

			}
		});
		ib5.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					x = 48;
				}else
				x = 53;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
				return true;

			}
		});
		ib6.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					x = 48;
				}else
				x = 54;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
				return true;

			}
		});
		bhit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					x = 48;
				}else
				x = 55;
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    arg0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                }
				return true;

			}
		});

	}


    @Override
	protected void onPause() {
		// OnPause
		super.onPause();
		mWakeLock.release();
		mData.pause();
	}

	@Override
	protected void onResume() {
		// OnResume
		super.onResume();
		mWakeLock.acquire();
		mData.resume();
	}

	@Override
	protected void onStop() {
		// onStop
		super.onStop();
		if (mBlueService != null) {
			mBlueService.stop();
		}
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	
	public class DataLoop implements Runnable {

		BluetoothService mBtService;
		boolean connected;
		float a, b;
		Thread mDataLoop;

		public DataLoop(BluetoothService mService) {
			mBtService = mService;
			connected = (mBtService.getState() == BluetoothService.STATE_CONNECTED);
		}

		public void pause() {
			connected = false;
			// To Stop the Data Thread
			while (true) {
				try {
					mDataLoop.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			mDataLoop = null;
		}

		public void resume() {

			connected = (mBtService.getState() == BluetoothService.STATE_CONNECTED);
			mDataLoop = new Thread(this);
			mDataLoop.start();

		}

		public byte[] sendMessage(float u) {

			byte[] data = { 0 };
			byte c;
			c = (byte) ((int) (u));
			data[0] = c;
			return data;

		}

		@Override
		public void run() {
			// run() for Data thread
			while (connected) {
				try {
					mBtService.write(sendMessage(x));
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
