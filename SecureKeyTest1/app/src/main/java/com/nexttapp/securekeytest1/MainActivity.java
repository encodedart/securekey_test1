package com.nexttapp.securekeytest1;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private Set<String> dic= new HashSet<>();
    private Map<String, Boolean> wordsFound;
    private EditText inputTxt;
    private TextView outputWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readDictionary();

        inputTxt = (EditText) findViewById(R.id.main_input_string);
        outputWords = (TextView) findViewById(R.id.main_output_result);
        Button btn = (Button) findViewById(R.id.main_parse_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseWords();
            }
        });
    }

    private void readDictionary() {
        InputStream inputStream;
        try {
            inputStream = getResources().openRawResource(R.raw.words_en);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String resultString;
        try {
            resultString = byteArrayOutputStream.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        String[] words = resultString.split("\r\n");
        Collections.addAll(dic,words);
    }

    private void parseWords() {
        Log.e("A","" + dic.size());
        outputWords.setText("");
        String ip = inputTxt.getText().toString();
        if (ip == null || ip.isEmpty()) {
            outputWords.setText("Please input text to parse");
            return;
        }
        String result = "";
        wordsFound = new HashMap<>();
        for (int i=0;i<ip.length()-1;i++) {
            String word = Character.toString(ip.charAt(i));
            for (int j=i+1;j<ip.length();j++) {
                word += Character.toString(ip.charAt(j));
                Log.d("Word: ", word);
                if (!wordsFound.containsKey(word)) {
                    wordsFound.put(word, true);
                    if (isEnglishWord(word)) {
                        result += word + ", ";
                        Log.e("English word: ", word);
                    }

                } else {
                    Log.e("SKIP", word);
                }
            }
        }
        if (result.isEmpty()) {
            outputWords.setText("No English word found in the input string");
        } else {
            outputWords.setText(result.substring(0, result.length()-1));
        }

    }

    private boolean isEnglishWord(String word) {
        if (dic == null || dic.isEmpty()) return false;
        return dic.contains(word.toLowerCase());

    }
}
