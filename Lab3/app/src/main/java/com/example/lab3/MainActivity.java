package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String fileName = "";
    Button chooseButton;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chooseButton = (Button) findViewById(R.id.button);
        saveButton = (Button) findViewById(R.id.button2);
        chooseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnOpenFileClick(v);
                    }
                }
        );
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnSaveFileClick(v);
                    }
                }
        );
    }


    public void OnOpenFileClick(View view) {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // разрешение не предоставлено
            ActivityCompat.requestPermissions(this, new
                    String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else {
            OpenFileDialog();
        }
    }

    public void OnSaveFileClick(View view) {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                    String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            SaveFile();
        }
    }

    public void OpenFileDialog()
    {
        OpenFileDialog fileDialog = new OpenFileDialog(this)
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {

                    @Override
                    public void OnSelectedFile(String selectedFile) {
                        File file = new File(selectedFile);
                        fileName = selectedFile;
                        StringBuilder text = new StringBuilder();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;
                            while ((line = br.readLine()) != null) { //читаем построчно до каонца файла
                                text.append(line);
                                text.append('\n');
                            }
                            br.close(); //закрываем файл
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        TextView tv = findViewById(R.id.textView); //получаем наш textView
                        tv.setText(text.toString()); //устанавливаем полученных из файла текст в textView
                    }
                });
        fileDialog.show(); // показываем диалог выбора файла
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {

                    OpenFileDialog();
                } else {

                }
                return;
            }
            case 2: {
                SaveFile();
                return;
            }
        }
    }

    public void SaveFile()
    {
        if (fileName != "" && fileName != null) { //если мы открыли файл
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                TextView tv = findViewById(R.id.textView); //выбираем наш textView
                fos.write(tv.getText().toString().getBytes());
                fos.close(); //закрываем поток
                Toast.makeText(this, "Файл сохранен!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

