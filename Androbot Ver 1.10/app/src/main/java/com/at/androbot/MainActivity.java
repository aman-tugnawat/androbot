package com.at.androbot;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <h1>MainActivity</h1>
 * This is the main activity for the app which has the Bluetooth handler and the listView for the Serial terminal over bluetooth.
 *
 * @author AmanTugnawat
 * @since 12-10-2014
 */

public class MainActivity extends ActionBarActivity {

    // Debugging
    private static final String TAG = "AndroBot";

    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = null;

    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;

    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;

    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;
    private LinearLayout mEditView;


    // Requests
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BT_CONNECTION = 2;

    static TextView tv1, tv2;
    Button mConnectButton,mControlButton,mConsoleButton;
    CheckBox mDebugMode;
    //Thread mDataLoop;
    BluetoothAdapter mBluetoothAdapter;
    static BluetoothService mBluetoothService;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "+++ ON CREATE +++");
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mDebugMode = (CheckBox) findViewById(R.id.checkBox1);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv);
        mDebugMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    mControlButton.setEnabled(true);
                else
                    mControlButton.setEnabled(false);

            }
        });
        //mConnectButton.setHapticFeedbackEnabled(true);
        //mControlButton.setHapticFeedbackEnabled(true);

        // Initialize the send button with a listener that for click events
        mConnectButton = (Button) findViewById(R.id.b1);
        mConnectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // OnClick Connect button
                //sendMessage("#XY");
                //arg0.performHapticFeedback(HapticFeedbackConstants.);
                if(mConnectButton.getText().equals("Disconnect")){
                    mBluetoothService.stop();
                    tv1.setText("You tried to disconnect.");

                }else
                {if ((mBluetoothService.getState() == BluetoothService.STATE_CONNECTED)) {
                    Toast.makeText(
                            MainActivity.this,
                            "You are already connected to a device!! Disconnect First...",
                            Toast.LENGTH_LONG).show();
                } else {

                    Intent listActivity = new Intent(getApplicationContext(), DeviceListActivity.class);
                    startActivityForResult(listActivity, REQUEST_BT_CONNECTION);
                }
                }


            }
        });

        // Initialize the send button with a listener that for click events
        mControlButton = (Button) findViewById(R.id.b2);
        mControlButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // OnClick Connect button
                if (!mDebugMode.isChecked()) {
                    if ((mBluetoothService.getState() == BluetoothService.STATE_CONNECTED)) {
                        Intent controlActivity = new Intent(getBaseContext(), NewControl.class);
                        startActivity(controlActivity);
                    } else {
                        Toast.makeText(
                                MainActivity.this,
                                "You are not connected to any device!! Connect First...",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent controlActivity = new Intent(getBaseContext(), NewControl.class);
                    startActivity(controlActivity);
                }

            }
        });



        if (mBluetoothAdapter == null) {
            Toast.makeText(MainActivity.this,
                    "Your Device Doesn't Support Bluetooth", Toast.LENGTH_SHORT)
                    .show();
            this.finish();
        }else setupChat();

    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mOutEditText.setOnEditorActionListener(mWriteListener);
        mEditView = (LinearLayout) findViewById(R.id.console);

        // Initialize the send button with a listener that for click events
        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                //TextView view = (TextView) findViewById(R.id.edit_text_out);
                String message = mOutEditText.getText().toString();
                sendMessage(message);
                mOutEditText.setText("");
            }
        });

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // If the action is a key-up event on the return key, send the message
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        sendMessage(message);
                    }
                    Log.i(TAG, "END onEditorAction");
                    return true;
                }
            };




    @Override
    protected void onResume() {
        //
        super.onResume();
        Log.e(TAG, "+++ ON Resume +++");

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Log.e(TAG, Integer.toString(REQUEST_ENABLE_BT));
        }

        if (mBluetoothService == null && mBluetoothAdapter.isEnabled()) {

            // Initialize the BluetoothChatService to perform bluetooth
            // connections
            mBluetoothService = new BluetoothService(this, mHandler);

        }
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity
        // returns.
        if (mBluetoothService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't
            // started already
            if (mBluetoothService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth services
                mBluetoothService.start();
            }
        }
    }

    @Override
    protected void onStart() {
        //
        super.onStart();
        Log.e(TAG, "+++ ON Start +++");
    }

    @Override
    protected void onStop() {
        //
        super.onStop();
        Log.e(TAG, "+++ ON stop +++");
    }

    @Override
    protected void onDestroy() {
        //
        super.onDestroy();
        Log.e(TAG, "+++ ON Destroy +++");
        if(mBluetoothService!=null) {mBluetoothService.stop();mBluetoothService=null;}
        //finish();

    }

    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mBluetoothService.write(send);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                if ((mBluetoothService.getState() == BluetoothService.STATE_CONNECTED)) {
                    Toast.makeText(
                            MainActivity.this,
                            "You are already connected to a device!! Disconnect First...",
                            Toast.LENGTH_LONG).show();
                } else {

                    Intent listActivity = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(listActivity, REQUEST_BT_CONNECTION);
                }
                break;
            case R.id.help:
                Intent helpActivity = new Intent(this, Help.class);
                startActivity(helpActivity);

                break;
            case R.id.disconnect:
                mBluetoothService.stop();
                tv1.setText("You tried to disconnect.");
                //mBluetoothService.start();
                //Intent i = getIntent();
                //finish();
                //startActivity(i);
                break;
            case R.id.about:
                Intent aboutActivity = new Intent(this, About.class);
                startActivity(aboutActivity);
                break;
            case R.id.control:
                if (!mDebugMode.isChecked()) {
                    if ((mBluetoothService.getState() == BluetoothService.STATE_CONNECTED)) {
                        Intent controlActivity = new Intent(this, Control.class);
                        startActivity(controlActivity);
                    } else {
                        Toast.makeText(
                                MainActivity.this,
                                "You are not connected to any device!! Connect First...",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent controlActivity = new Intent(this, Control.class);
                    startActivity(controlActivity);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // The Handler that gets information back from the BluetoothService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            tv2.setText("Connected to: " + mConnectedDeviceName);
                            //MainActivity.this.sendMessage("Connected");
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            tv2.setText("Connecting...");
                            break;
                        case BluetoothService.STATE_LISTEN:
                            //mBluetoothService.stop();
                            //break;
                        case BluetoothService.STATE_NONE:
                            tv2.setText("");
                            tv2.setHint("Not Connected.");
                            if(mControlButton!=null)
                                mControlButton.setEnabled(false);
                            if(mConsoleButton!=null)
                                mConsoleButton.setEnabled(false);
                            if(mOutEditText!=null)
                                mOutEditText.setEnabled(false);
                            if(mDebugMode!=null)
                                mDebugMode.setEnabled(true);
                            //mEditView.setVisibility(View.VISIBLE);
                            if(mSendButton!=null)
                                mSendButton.setEnabled(false);
                            mConnectButton.setText("Connect");
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                     byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                     String writeMessage = new String(writeBuf);
                     mConversationArrayAdapter.add("You: " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    if(mControlButton!=null)
                    mControlButton.setEnabled(true);
                    if(mConsoleButton!=null)
                    mConsoleButton.setEnabled(true);
                    if(mOutEditText!=null)
                    mOutEditText.setEnabled(true);
                    mEditView.setVisibility(View.VISIBLE);
                    if(mSendButton!=null)
                    mSendButton.setEnabled(true);
                    mDebugMode.setEnabled(false);
                    tv2.setText("Connected to:" + mConnectedDeviceName);
                    mConnectButton.setText("Disconnect");
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled
                    Toast.makeText(this, "Bluetooth now Enabled ",
                            Toast.LENGTH_SHORT).show();

                    // Initialize the BluetoothChatService to perform bluetooth
                    // connections
                    mBluetoothService = new BluetoothService(this, mHandler);

                    // Initialize the buffer for outgoing messages
                    // mOutStringBuffer = new StringBuffer("");

                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this,
                            "Bluetooth was not enabled leaving app...",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case REQUEST_BT_CONNECTION:
                // When a Bluetooth Device is selected
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    String name = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_NAME);

                    tv1.setText("You tried to connect to:" + name);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter
                            .getRemoteDevice(address);
                    // Attempt to connect to the device
                    mBluetoothService.connect(device);


                } else {
                    // User did not selected a Bluetooth device or an error occured
                    Log.d(TAG, "Device not selected");

                }
                break;
        }

    }


}
