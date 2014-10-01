package com.kd.coder006.keyboarddynamics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseInstallation;

public class MainActivity extends Activity {

    Constants constant = new Constants();
    Long pTime = 0L;
    Long cTime = 0L;
    Long[] timeDifferences = new Long[6];
    String installationObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "XVZ306n62GIo2SLIcuqbuN92DZPyK21jPssM8nWn",
                "2uGzmMJxRLLQX1cl1J0zkF0Pt7CgNOO4VdHKXxef");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();
        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        Log.v("installation_id: ", installation.getInstallationId());
        installationObjectId = installation.getObjectId();

        final EditText edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.v("KeyPressed: ", String.valueOf(keyCode));
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String textValue = edittext.getText().toString().toLowerCase();
                    if(!textValue.equals(constant.TEST_WORD)){
                        Log.v("editTextValue: ", textValue);
                        CharSequence wrongSequenceToast = "Wrong Word. Please type the correct Word!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), wrongSequenceToast, duration);
                        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 600);
                        toast.show();
                        edittext.setText("");
                    }
                    else{
                        for(int i=0; i<6; i++){
                            Log.v(String.valueOf(i) + ": ", String.valueOf(timeDifferences[i]));

                        }
                        ParseObject userClass = new ParseObject(installationObjectId);
                        userClass.add("wo", timeDifferences[0]);
                        userClass.add("ol", timeDifferences[1]);
                        userClass.add("lf", timeDifferences[2]);
                        userClass.add("fr", timeDifferences[3]);
                        userClass.add("ra", timeDifferences[4]);
                        userClass.add("am", timeDifferences[5]);

                        userClass.saveInBackground();

                        CharSequence wrongSequenceToast = "Trial complete!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), wrongSequenceToast, duration);
                        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 600);
                        toast.show();
                        edittext.setText("");
                    }
                }
                return true;
            }
        });

        edittext.addTextChangedListener(new TextWatcher() {
            int cIndex = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                cIndex = i2;
                Log.v("after: ", String.valueOf(i2));
                Log.v("count: ", String.valueOf(i3));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String etText = edittext.getText().toString().toLowerCase();
                //Log.v("Newtext: ", edittext.getText().toString());
                if(!constant.TEST_WORD.startsWith(etText)){
                    Context context = getApplicationContext();
                    CharSequence wrongSequenceToast = "Wrong Sequence. Please type the correct sequence!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, wrongSequenceToast, duration);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 600);
                    toast.show();

                    edittext.setText("");
                }
                else {
                    if(cIndex == 0){
                        pTime = System.currentTimeMillis();
                    }
                    else if(cIndex >= 1 && cIndex <= 6){
                        cTime = System.currentTimeMillis();
                        timeDifferences[cIndex-1] = cTime - pTime;
                        Log.v("tDiff: ", String.valueOf(timeDifferences[cIndex-1]));
                        pTime = cTime;
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
