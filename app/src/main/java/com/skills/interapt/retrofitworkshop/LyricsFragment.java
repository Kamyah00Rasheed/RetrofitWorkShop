package com.skills.interapt.retrofitworkshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.skills.interapt.retrofitworkshop.MainActivity.ARTIST_NAME;
import static com.skills.interapt.retrofitworkshop.MainActivity.SONG_TITLE;

public class LyricsFragment extends Fragment {

    private String baseUrl = "https://api.lyrics.ovh/v1/";
    private Retrofit retrofit;
    private RetrofitMusicApiCalls retrofitMusicApiCalls;
    @BindView(R.id.lyrics_textview)
    protected TextView lyricsTextview;

    //onCreate view used for fragments. take out the return.super. insert view. attachToRoot is always false.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lyrics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static LyricsFragment newInstance() {

        Bundle args = new Bundle();

        LyricsFragment fragment = new LyricsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Retrofit Converter method. Tells Retrofit how to convert the Json it gets into something
    //We can use in our app
    private void buildRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitMusicApiCalls = retrofit.create(RetrofitMusicApiCalls.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        //TODO Get artist and title from bundle here
        String artistName = getArguments().getString(ARTIST_NAME);
        String songTitle = getArguments().getString(SONG_TITLE);
        buildRetrofit();
        //TODO make our API call
        makeApiCall(artistName, songTitle);
    }

    private void makeApiCall(String artistName, String songTitle){
        retrofitMusicApiCalls.getSongLyrics(artistName, songTitle).enqueue(new Callback<RetrofitMusicApiCalls.SongLyrics>() {
            @Override
            public void onResponse(Call<RetrofitMusicApiCalls.SongLyrics> call, Response<RetrofitMusicApiCalls.SongLyrics> response) {

                if (response.isSuccessful()){
                    //Get info from the call, set the textview equal to the lyrics
                    lyricsTextview.setText(response.body().getLyrics());

                } else{
                    Toast.makeText(getActivity(), "Error, try again!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RetrofitMusicApiCalls.SongLyrics> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(getActivity(), "Hit onFailure, check API info and network Connection" , Toast.LENGTH_SHORT).show();

            }
        });


    }
}
