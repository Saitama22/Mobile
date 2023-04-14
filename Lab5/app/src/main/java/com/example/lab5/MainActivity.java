package com.example.lab5;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    VideoView videoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPlayer =  (VideoView)findViewById(R.id.videoView);
        Uri myVideoUri= Uri.parse( "android.resource://" + getPackageName() + "/" + R.raw.sample_video);
        videoPlayer.setVideoURI(myVideoUri); //Устанавливаем ссылку на файл для проигрывателя
        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);
    }

    public void play(View view){
        videoPlayer.start(); //Начинает проигрование видео
    }
    public void pause(View view){
        videoPlayer.pause(); //Приостанавливает проигрование
    }
    public void stop(View view){
        videoPlayer.stopPlayback(); //Полностью останавлиевает видео
        videoPlayer.resume(); //Позволяет снова воспроизвести видео после остановки
    }
}