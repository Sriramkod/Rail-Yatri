package com.example.vbtt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;
import android.widget.Toast;
import android.os.Bundle;
import android.animation.Animator;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener {

    /** Called when the activity is first created. */
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextToSpeech tts;
    private Button buttonSpeak;
    private EditText editText;


String sp=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
 tts = new TextToSpeech(this, this);
 }
    @Override
    public void onDestroy() {
// Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            String text = "Say one for user source location Say three for user destination location";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            //speakOut();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //textChanger.setText("After some delay, it changed to new text");
                    promptSpeechInput();
                }
            }, 5000);


            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }



    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
                    Toast.makeText(this, ""+result.get(0).toString(), Toast.LENGTH_SHORT).show();
                    sp = result.get(0);
                    if(sp!=null) {
                        if (sp.equalsIgnoreCase("1")){
                          //  tts.speak("It is users source location", TextToSpeech.QUEUE_FLUSH, null);
                       /* Intent in = new Intent(MainActivity.this,Assistant.class);
                        in.putExtra("control",sp);
                        startActivity(in);*/
                            Intent in = new Intent(MainActivity.this,Assistant.class);
                            in.putExtra("control",sp);
                            startActivity(in);
                        }
                        else if (sp.equalsIgnoreCase("3")){
                           // tts.speak("It is users destination location", TextToSpeech.QUEUE_FLUSH, null);
                            Intent in = new Intent(MainActivity.this,Assistant.class);
                            in.putExtra("control",sp);
                            startActivity(in);
                            //Intent in = new Intent(MainActivity.this,Retrieve.class);
                            //in.putExtra("control",sp);
                            //startActivity(in);
                        }

                       else{
                            tts.speak("That was an invalid input     Say one for user source location Say three for user destination location", TextToSpeech.QUEUE_FLUSH, null);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    promptSpeechInput();
                                }
                            }, 7000);

                        }

                    }
                }
                break;
            }

        }
    }
    private void speechButton(){
        tts.stop();
        tts.shutdown();
        String text = "Say one for user source location Say three for user destination location";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                promptSpeechInput();
            }
        }, 5000);
    promptSpeechInput();}

    public void Voice(View view) {
        speechButton();
    }
}