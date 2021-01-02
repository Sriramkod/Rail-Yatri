package com.example.vbtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Assistant extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    int des_count=0;
    String control;
    String res=null;
    private final int REQ_CODE_SPEECH_INPUT = 101;
    private TextToSpeech textToSpeech;
    private TextToSpeech tts;
    ListView listView;
    int location=0;
    ArrayAdapter<String> adapter;
    DatabaseHelper mDatabaseHelper;int co=0;
    //String[] listValue = new String[] {"Welcome to web based train timetable"};
    ArrayList<String> listValue=new ArrayList<String>();
    String[] des ;
    String[] src;
    String[] arrival;
    String[] departure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        mDatabaseHelper = new DatabaseHelper(this);
        Cursor data = mDatabaseHelper.getData();
        Cursor data1 = mDatabaseHelper.getData();
        while (data.moveToNext()) {
            co++;
        }

         des = new String[co];src=new String[co];arrival=new String[co];departure=new String[co];
        int ind1=0,ind2=0,ind3=0,ind4=0;
        while (data1.moveToNext()) {
            String srct= data1.getString(1);
            String dest = data1.getString(2);
            String arrt=data1.getString(3);
            String dept = data1.getString(4);
            src[ind1++]=srct;
            des[ind2++]=dest;
            arrival[ind3++]=arrt;
            departure[ind4++]=dept;
        }
        tts = new TextToSpeech(this, this);
        listView = (ListView)findViewById(R.id.listView);
    listValue.add("Welcome to Voice based train timetable");
         adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, android.R.id.text1, listValue);

        listView.setAdapter(adapter);
        Intent intent = getIntent();
        control = intent.getStringExtra("control");

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
                    Toast.makeText(this, "" + result.get(0).toString(), Toast.LENGTH_SHORT).show();
                    res = result.get(0);
                    if (res != null) {
                        ///adapter.add(res);
                        listValue.add(res);
                        adapter.notifyDataSetChanged();

                        //tts.speak("You have given the destination as :" + res, TextToSpeech.QUEUE_FLUSH, null);
                    }
                        if(location==1){
                            int index_found = -1;
                            for (int i = 0; i < src.length; i++) {
                                if (src[i].trim().equalsIgnoreCase(res)) {
                                    des_count++;
                                    index_found = i;
                                }
                            }
                            if (index_found == -1) {
                                listValue.add("There are no trains starting to your specified source location...");
                                adapter.notifyDataSetChanged();
                                tts.speak("There are no trains starting to your specified source location...", TextToSpeech.QUEUE_FLUSH, null);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        tts.speak("Now again tell a new Source location or Say 4 to exit", TextToSpeech.QUEUE_FLUSH, null);

                                    }
                                }, 8000);
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        promptSpeechInput();
                                    }
                                }, 13000);
                            }
                            else {
                                listValue.add("The train will start from " + src[index_found] +" at"+arrival[index_found]+ " and will reach " + des[index_found] + "at " + departure[index_found]);
                                adapter.notifyDataSetChanged();
                                ;
                                tts.speak("The train will start from " + src[index_found] +" at"+arrival[index_found]+ " and will reach " + des[index_found] + "at " + departure[index_found], TextToSpeech.QUEUE_FLUSH, null);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        tts.speak("Now just talk to search a new Source location or Say 4 to exit", TextToSpeech.QUEUE_FLUSH, null);

                                    }
                                }, 8000);
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        promptSpeechInput();
                                    }
                                }, 12000);
                            }

                        }
                        if (location == 3) {
                            int index_found = -1;
                            for (int i = 0; i < des.length; i++) {
                                if (des[i].trim().equalsIgnoreCase(res)) {
                                    des_count++;
                                    index_found = i;
                                }
                            }
                            if (index_found == -1) {
                                listValue.add("There are no trains arriving to your specified destination location...");
                                adapter.notifyDataSetChanged();
                                tts.speak("There are no trains arriving to your specified destination location...", TextToSpeech.QUEUE_FLUSH, null);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        tts.speak("Now just talk to search a new destination location or Say 4 to exit", TextToSpeech.QUEUE_FLUSH, null);

                                    }
                                }, 8000);
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        promptSpeechInput();
                                    }
                                }, 13000);
                            } else {
                                listValue.add("There is a train starting from " + src[index_found] + " and will reach " + des[index_found] + "at " + departure[index_found]);
                                adapter.notifyDataSetChanged();
                                ;
                                tts.speak("There is a train starting from " + src[index_found] + " and will reach " + des[index_found] + "at " + departure[index_found], TextToSpeech.QUEUE_FLUSH, null);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        tts.speak("Now just talk to search a new destination location or Say 4 to exit", TextToSpeech.QUEUE_FLUSH, null);

                                    }
                                }, 8000);
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //textChanger.setText("After some delay, it changed to new text");
                                        promptSpeechInput();
                                    }
                                }, 12000);
                            }

                        }
                    if (res.equalsIgnoreCase("4")) {
                        /*tts.speak("You have sucessfully exited out", TextToSpeech.QUEUE_FLUSH, null);
                        Intent intent = new Intent(Assistant.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/
                        tts.stop();
                        Intent intent = new Intent(Assistant.this,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            }

        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            String text = control;
            if(control.equalsIgnoreCase("1")){
                location=1;
            tts.speak("You have selected the Source location option", TextToSpeech.QUEUE_FLUSH, null);
        //adapter.add("You have selected the Source location option");
                listValue.add("You have selected the Source location option");
                adapter.notifyDataSetChanged();
                tts.speak("Please say the source location", TextToSpeech.QUEUE_FLUSH, null);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //textChanger.setText("After some delay, it changed to new text");
                        promptSpeechInput();
                    }
                }, 4000);
            }
            else if(control.equalsIgnoreCase("3")){
                location=3;
                //tts.speak("You have selected the Destination location option", TextToSpeech.QUEUE_FLUSH, null);
                //adapter.add("You have selected the Source location option");
               // listValue.add("You have selected the Destination location option");
                adapter.notifyDataSetChanged();
                tts.speak("Please say the destination location", TextToSpeech.QUEUE_FLUSH, null);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //textChanger.setText("After some delay, it changed to new text");
                        promptSpeechInput();
                    }
                }, 4000);
            }
            //speakOut();

        }
    }
}