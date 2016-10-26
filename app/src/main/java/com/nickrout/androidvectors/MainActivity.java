package com.nickrout.androidvectors;

import android.os.Build;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ANIM_FADE_SCALE = "Alpha and scale";
    private static final String ANIM_ROTATE = "Rotate";
    private static final String ANIM_TRANSLATE = "Translate";
    private static final String ANIM_COLOR = "Color";
    private static final String ANIM_TRIM_PATH = "Trim path";
    private static final String ANIM_MORPH = "Morph";

    private AppCompatImageView mImage;
    private AppCompatButton mButton;
    private AppCompatSpinner mSpinner;

    private String mCurrentType;
    private boolean mReverse;

    private AnimatedVectorDrawableCompat mAvdAlphaScale, mAvdAlphaScaleReverse;
    private AnimatedVectorDrawableCompat mAvdRotate;
    private AnimatedVectorDrawableCompat mAvdTranslate;
    private AnimatedVectorDrawableCompat mAvdColor, mAvdColorReverse;
    // TODO: Fix - trim path anim does not work on search icon 'handle'
    private AnimatedVectorDrawableCompat mAvdTrimPath, mAvdTrimPathReverse;
    private AnimatedVectorDrawableCompat mAvdMorph, mAvdMorphReverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = (AppCompatImageView) findViewById(R.id.image);
        mButton = (AppCompatButton) findViewById(R.id.button);
        mSpinner = (AppCompatSpinner) findViewById(R.id.spinner);
        setLightStatusBar(findViewById(R.id.activity_main));
        mCurrentType = ANIM_FADE_SCALE;
        setupButton();
        setupSpinner();
        instantiateAnimatedVectorDrawables();
    }

    private void setLightStatusBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    private void setupButton() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
                mReverse = !mReverse;
            }
        });
    }

    private void animate() {
        AnimatedVectorDrawableCompat avd = null;
        switch (mCurrentType) {
            case ANIM_FADE_SCALE:
                if (!mReverse) {
                    avd = mAvdAlphaScale;
                    break;
                }
                avd = mAvdAlphaScaleReverse;
                break;
            case ANIM_ROTATE:
                avd = mAvdRotate;
                break;
            case ANIM_TRANSLATE:
                avd = mAvdTranslate;
                break;
            case ANIM_COLOR:
                if (!mReverse) {
                    avd = mAvdColorReverse;
                    break;
                }
                avd = mAvdColor;
                break;
            case ANIM_TRIM_PATH:
                if (!mReverse) {
                    avd = mAvdTrimPath;
                    break;
                }
                avd = mAvdTrimPathReverse;
                break;
            case ANIM_MORPH:
                if (!mReverse) {
                    avd = mAvdMorph;
                    break;
                }
                avd = mAvdMorphReverse;
                break;
            }
        if (avd != null) {
            mImage.setImageDrawable(avd);
            avd.start();
        }
    }

    private void setupSpinner() {
        List<String> animList = new ArrayList<>();
        animList.add(ANIM_FADE_SCALE);
        animList.add(ANIM_ROTATE);
        animList.add(ANIM_TRANSLATE);
        animList.add(ANIM_COLOR);
        animList.add(ANIM_TRIM_PATH);
        animList.add(ANIM_MORPH);
        ArrayAdapter<String> animAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, animList);
        animAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(animAdapter);
        mSpinner.setOnItemSelectedListener(new OnAnimItemSelectedListener());
    }

    private class OnAnimItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mReverse = false;
            mCurrentType = adapterView.getItemAtPosition(i).toString();
            resetInitialVector();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    private void resetInitialVector() {
        mImage.setImageResource(R.drawable.ic_search);
    }

    private void instantiateAnimatedVectorDrawables() {
        mAvdAlphaScale = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_alpha_scale);
        mAvdAlphaScaleReverse = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_alpha_scale_reverse);
        mAvdRotate = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_rotate);
        mAvdTranslate = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_translate);
        mAvdColor = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_color);
        mAvdColorReverse = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_color_reverse);
        mAvdTrimPath = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_trim_path);
        mAvdTrimPathReverse = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_trim_path_reverse);
        mAvdMorph = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_morph);
        mAvdMorphReverse = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_morph_reverse);
    }
}
