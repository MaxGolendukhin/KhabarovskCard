package com.golendukhin.khabarovskcard;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private int photoPosition = 0;

    /**
     * @param savedInstanceState if parameter is not null, get photo position to show same photo after rotation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getting photo position if device was rotated
        if (savedInstanceState != null) {
            photoPosition = savedInstanceState.getInt("photoPosition");
            if (photoPosition > 0) photoPosition--;//to show same photo that was before rotation
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int [] imageList = makeImageList();
        final int imageViewDuration = 3000;//time, picture is visible on screen
        final int animationDuration = 500;// of both fade in and fade out, total animation duration is two times longer

        final Animation fadeOutAnimation = new AlphaAnimation(1, 0);
        fadeOutAnimation.setStartOffset(imageViewDuration - animationDuration);

        final Animation fadeInAnimation = new AlphaAnimation(0, 1);

        final AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(fadeInAnimation);
        animationSet.addAnimation(fadeOutAnimation);
        animationSet.setDuration(animationDuration);
        animationSet.setInterpolator(new AccelerateInterpolator(1f));

        final ImageView imageView = findViewById(R.id.image_view);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                imageView.setImageResource(imageList[photoPosition]);
                imageView.startAnimation(animationSet);
                photoPosition++;
                if (photoPosition > imageList.length - 1) photoPosition = 0;
                handler.postDelayed(this, imageViewDuration);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    /**
     * Helper method, creates list depending of phone orientation
     * @return list of ints with number of every image resource
     */
    private int [] makeImageList() {
        int orientation = getResources().getConfiguration().orientation;
        int [] imageList = new int [12];
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageList[0] =  R.drawable.image_1_land;
            imageList[1] =  R.drawable.image_2_land;
            imageList[2] =  R.drawable.image_3_land;
            imageList[3] =  R.drawable.image_4_land;
            imageList[4] =  R.drawable.image_5_land;
            imageList[5] =  R.drawable.image_6_land;
            imageList[6] =  R.drawable.image_7_land;
            imageList[7] =  R.drawable.image_8_land;
            imageList[8] =  R.drawable.image_9_land;
            imageList[9] =  R.drawable.image_10_land;
            imageList[10] =  R.drawable.image_11_land;
            imageList[11] =  R.drawable.image_12_land;
        } else {
            imageList[0] =  R.drawable.image_1_port;
            imageList[1] =  R.drawable.image_2_port;
            imageList[2] =  R.drawable.image_3_port;
            imageList[3] =  R.drawable.image_4_port;
            imageList[4] =  R.drawable.image_5_port;
            imageList[5] =  R.drawable.image_6_port;
            imageList[6] =  R.drawable.image_7_port;
            imageList[7] =  R.drawable.image_8_port;
            imageList[8] =  R.drawable.image_9_port;
            imageList[9] =  R.drawable.image_10_port;
            imageList[10] =  R.drawable.image_11_port;
            imageList[11] =  R.drawable.image_12_port;
        }
        return imageList;
    }

    /**
     * Launches web browser with Khabarovsk wiki page
     * @param view textView with suggestion browse additional info in Wikipedia
     */
    public void openWiki(View view) {
        String url = getResources().getString(R.string.wiki_link);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Launches Google map to observe Khabarovsk on the map
     * @param view textView, suggests to open Google maps
     */
    public void openMaps(View view) {
        Uri gmmIntentUri = Uri.parse(getResources().getString(R.string.maps_uri));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    /**
     * Saves photo position in list of images while device is rotated
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("photoPosition", photoPosition);
        super.onSaveInstanceState(outState);
    }
}