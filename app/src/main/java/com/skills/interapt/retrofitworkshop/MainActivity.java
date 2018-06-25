package com.skills.interapt.retrofitworkshop;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.artist_name_edittext)
    protected TextInputEditText artistName;

    @BindView(R.id.song_edittext)
    protected TextInputEditText songTitle;

    private LyricsFragment lyricsFragment;
    public static final String ARTIST_NAME = "artist_name";
    public static final String SONG_TITLE = "song_title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_button)
    protected void submitClicked(){
        if(artistName.getText().toString().isEmpty()|| songTitle.getText().toString().isEmpty()){
            //handle if there is missing input
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
        } else {
            //handle getting info from editTexts and passing it to the fragment for our API call
            lyricsFragment = LyricsFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, lyricsFragment).commit();
            Bundle bundle = new Bundle();
            bundle.putString(ARTIST_NAME, artistName.getText().toString());
            bundle.putString(SONG_TITLE, songTitle.getText().toString());
            lyricsFragment.setArguments(bundle);


        }
    }

    @Override
    public void onBackPressed() {
        if(lyricsFragment.isVisible()){
            getSupportFragmentManager().beginTransaction().remove(lyricsFragment).commit();
        } else {
            super.onBackPressed();
        }
    }
}
