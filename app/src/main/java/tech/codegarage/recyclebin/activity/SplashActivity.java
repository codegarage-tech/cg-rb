package tech.codegarage.recyclebin.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.bumptech.glide.Glide;
import com.reversecoder.library.util.AllSettingsManager;
import com.reversecoder.permission.activity.PermissionListActivity;
import com.rodolfonavalon.shaperipplelibrary.ShapeRipple;
import com.rodolfonavalon.shaperipplelibrary.model.Circle;

import io.realm.RealmObject;
import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.recyclebin.R;
import tech.codegarage.recyclebin.model.realm.RealmController;
import tech.codegarage.recyclebin.model.realm.Tag;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class SplashActivity extends AppCompatActivity {

    private RealmController realmController;
    private final long interval = 1 * 1000;
    ShapeRipple ripple;
    TextView tvAppVersion;
    LinearLayout llTitleAnimationView;
    ImageView ivLogo;

    PerformLottieTitle performLottieTitle = null;
    private String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initSplashUI();
    }

    private void initRealmData() {
        realmController = RealmController.with(this);
        realmController.setOnRealmDataChangeListener(new RealmController.onRealmDataChangeListener() {
            @Override
            public void onInsert(RealmObject realmObject) {
                Log.d(TAG, "Inserted data: " + ((Tag) realmObject).toString());
            }

            @Override
            public void onUpdate(RealmObject realmObject) {

            }

            @Override
            public void onDelete(RealmObject realmObject) {

            }
        });
        realmController.setTags();
    }

    private void initSplashUI() {
        initRealmData();

        ivLogo = (ImageView) findViewById(R.id.iv_logo);
        Glide
                .with(SplashActivity.this)
                .load(R.drawable.ic_trash)
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(ivLogo);

        llTitleAnimationView = (LinearLayout) findViewById(R.id.ll_title_animation_view);
        llTitleAnimationView.removeAllViews();

        tvAppVersion = (TextView) findViewById(R.id.application_version);
        tvAppVersion.setText(getString(R.string.app_version_text) + " " + getString(R.string.app_version_name));

        //shape ripple
        ripple = (ShapeRipple) findViewById(R.id.background_ripple);
        ripple.setRippleShape(new Circle());
        ripple.setEnableColorTransition(true);
        ripple.setEnableSingleRipple(false);
        ripple.setEnableRandomPosition(true);
        ripple.setEnableRandomColor(true);
        ripple.setEnableStrokeStyle(false);
        ripple.setRippleDuration(2500);
        ripple.setRippleCount(10);
        ripple.setRippleMaximumRadius(184);

        //Call Lottie view
        performLottieTitle = new PerformLottieTitle();
        performLottieTitle.execute(getString(R.string.app_name_capital));
    }

    private void navigateHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        Bungee.slideLeft(SplashActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PermissionListActivity.REQUEST_CODE_PERMISSIONS) {
            if (resultCode == RESULT_OK) {
                navigateHomeActivity();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
                Bungee.slideLeft(SplashActivity.this);
            }
        }
    }

    private class PerformLottieTitle extends AsyncTask<String, LottieComposition, String> {

        @Override
        protected String doInBackground(String... params) {

            if (!AllSettingsManager.isNullOrEmpty(params[0])) {
                llTitleAnimationView.removeAllViews();

                String name = params[0];

                for (int i = 0; i < name.length(); i++) {
                    String fileName = "mobilo/" + name.charAt(i) + ".json";
                    LottieComposition.Factory.fromAssetFileName(SplashActivity.this, fileName, new OnCompositionLoadedListener() {
                        @Override
                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
                            if (composition != null) {
                                publishProgress(composition);
                            }
                        }
                    });

                    try {
                        Thread.sleep(interval);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return "Executed";
        }

        @Override
        protected void onProgressUpdate(LottieComposition... progress) {
            if (progress[0] != null) {
                LottieComposition lottieComposition = progress[0];
                LottieAnimationView lottieAnimationView = new LottieAnimationView(SplashActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                lottieAnimationView.setLayoutParams(layoutParams);
                lottieAnimationView.setComposition(lottieComposition);
                lottieAnimationView.playAnimation();
                llTitleAnimationView.addView(lottieAnimationView);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intentPermission = new Intent(SplashActivity.this, PermissionListActivity.class);
                startActivityForResult(intentPermission, PermissionListActivity.REQUEST_CODE_PERMISSIONS);
                Bungee.slideRight(SplashActivity.this);
            } else {
                navigateHomeActivity();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (performLottieTitle != null && performLottieTitle.getStatus() == AsyncTask.Status.RUNNING) {
            performLottieTitle.cancel(true);
        }

        super.onBackPressed();
        Bungee.slideLeft(SplashActivity.this);
    }
}