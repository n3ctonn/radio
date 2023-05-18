package com.example.radiopazza;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RadioManager {
    private Context context;
    private ListView radioListView;
    private List<RadioStation> radioList;
    private RadioListAdapter radioListAdapter;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;

    public RadioManager(Context context, ListView radioListView) {
        this.context = context;
        this.radioListView = radioListView;
        this.radioList = new ArrayList<>();
        this.radioListAdapter = new RadioListAdapter(context, radioList);
        this.radioListView.setAdapter(radioListAdapter);
        this.mediaPlayer = new MediaPlayer();
        this.isPlaying = false;
        setupMediaPlayer();
    }

    public void addRadio(String radioName, String radioUrl) {
        RadioStation radioStation = new RadioStation(radioName, radioUrl);
        radioList.add(radioStation);
        radioListAdapter.notifyDataSetChanged();
    }

    private void setupMediaPlayer() {
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
            }
        });
    }

    private class RadioListAdapter extends ArrayAdapter<RadioStation> {
        private LayoutInflater inflater;

        public RadioListAdapter(Context context, List<RadioStation> radioList) {
            super(context, 0, radioList);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.radioNameTextView = convertView.findViewById(R.id.radioNameTextView);
                viewHolder.playButton = convertView.findViewById(R.id.play);
                viewHolder.deleteButton = convertView.findViewById(R.id.remove);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            RadioStation radioStation = getItem(position);
            viewHolder.radioNameTextView.setText(radioStation.getName());

            viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPlaying) {
                        stopRadio();
                        viewHolder.playButton.setBackgroundResource(R.drawable.play);
                    } else {
                        startRadio(radioStation.getUrl());
                        viewHolder.playButton.setBackgroundResource(R.drawable.stop);}
                }
            });

            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioList.remove(radioStation);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

        private void startRadio(String radioUrl) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(radioUrl);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        isPlaying = true;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void stopRadio() {
            mediaPlayer.stop();
            mediaPlayer.reset();
            isPlaying = false;
        }

        private class ViewHolder {
            TextView radioNameTextView;
            Button playButton;
            Button deleteButton;
        }
    }
}

class RadioStation {
    private String name;
    private String url;

    public RadioStation(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
