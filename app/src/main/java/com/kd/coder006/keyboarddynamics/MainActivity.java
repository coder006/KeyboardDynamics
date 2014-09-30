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


public class MainActivity extends Activity {

    Constants constant = new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.v("KeyPressed: ", String.valueOf(keyCode));
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String textValue = edittext.getText().toString();
                    Log.v("EditTextValue: ", textValue);
                }
                return true;
            }
        });

        edittext.addTextChangedListener(new TextWatcher() {
            int cIndex = 0;
            Long pTime = 0L;
            Long cTime = 0L;

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
                    else if(cIndex >= 1){
                        cTime = System.currentTimeMillis();
                        Long timeDiff = cTime - pTime;
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
