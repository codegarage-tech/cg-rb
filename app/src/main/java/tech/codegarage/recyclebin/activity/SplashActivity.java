package tech.codegarage.recyclebin.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.reversecoder.permission.activity.PermissionListActivity;

import io.realm.RealmObject;
import tech.codegarage.recyclebin.R;
import tech.codegarage.recyclebin.model.RealmController;
import tech.codegarage.recyclebin.model.Tag;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class SplashActivity extends AppCompatActivity{

    private String TAG = SplashActivity.class.getSimpleName();
    private RealmController realmController;

    //Count down timer
    SplashCountDownTimer splashCountDownTimer;
    private final long startTime = 4 * 1000;
    private final long interval = 1 * 1000;

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
        // Initialize Splash timer
        splashCountDownTimer = new SplashCountDownTimer(startTime, interval);
        splashCountDownTimer.start();

        // Initialize realm data
        initRealmData();
    }

    private void navigateHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PermissionListActivity.REQUEST_CODE_PERMISSIONS) {
            if (resultCode == RESULT_OK) {
                navigateHomeActivity();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    public class SplashCountDownTimer extends CountDownTimer {
        public SplashCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent(SplashActivity.this, PermissionListActivity.class);
                startActivityForResult(intent, PermissionListActivity.REQUEST_CODE_PERMISSIONS);
            } else {
                navigateHomeActivity();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    }

//    /******************************
//     * Methods for database input *
//     ******************************/
//    private class InputData extends AsyncTask<String, Object, ArrayList<AppDataBuilder>> {
//
//        int mCounter = 0, mProgress = 0;
//
//        private InputData() {
//            mCounter = 0;
//            mProgress = 0;
//            Log.d(TAG, "TAG-8");
//        }
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected ArrayList<AppDataBuilder> doInBackground(String... params) {
//
//            if (!AllSettingsManager.isNullOrEmpty(SessionManager.getStringSetting(QuoteApp.getGlobalContext(), SESSION_DATA_APP_DATA_BUILDER))) {
//                Log.d(TAG, "TAG-9");
//                try {
//                    Thread.sleep(2 * interval);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                Log.d(TAG, "TAG-10");
//
//                if (SessionManager.getBooleanSetting(SplashActivity.this, SESSION_IS_FIRST_TIME, true)) {
//                    Intent intentAppIntro = new Intent(SplashActivity.this, AppIntroActivity.class);
//                    startActivity(intentAppIntro);
//                    Bungee.slideUp(SplashActivity.this);
//                    finish();
//                    Log.d(TAG, "TAG-11");
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        Intent intentPermission = new Intent(SplashActivity.this, PermissionListActivity.class);
//                        startActivityForResult(intentPermission, PermissionListActivity.REQUEST_CODE_PERMISSIONS);
//                        Bungee.slideUp(SplashActivity.this);
//                        Log.d(TAG, "TAG-12");
//                    } else {
//                        navigateHomeActivity();
//                        Log.d(TAG, "TAG-13");
//                    }
//                    Log.d(TAG, "TAG-14");
//                }
//            } else {
//
////                for (int mCounter = 0; mCounter <= 100; mCounter++) {
////                    try {
////                        float progress = ((float) mCounter / (float) 100);
////                        Log.d(TAG, "Progress:(i) = " + mCounter);
////                        Log.d(TAG, "Progress:(i) = " + progress);
////                        publishProgress(progress);
////                        Thread.sleep(30);
////                    } catch (Exception ex) {
////                        ex.printStackTrace();
////                    }
////                }
//                Log.d(TAG, "TAG-15");
//
//                publishProgress(mCounter);
//                ArrayList<AppDataBuilder> appDataBuilders = new ArrayList<>();
//                appDataBuilders = AppDataHandler.initAllQuotes(new DataInputListener<Object>() {
//                    @Override
//                    public void InputListener(Object insertedData) {
//                        if(insertedData !=null){
//                            publishProgress(insertedData);
//                            Log.d(TAG, "TAG-publish progress");
//                        }
//                    }
//                });
//                Log.d(TAG, "TAG-16");
//
//                return appDataBuilders;
//            }
//
//            return null;
//        }
//
//        protected void onProgressUpdate(Object... progress) {
//            Log.d(TAG, "TAG-=========");
//            if (progress[0] != null) {
//                Log.d(TAG, "TAG-17");
//
//                //assigning message
//                String progressMessage = "", progressStatus = "";
//                if (progress[0] instanceof Quote) {
//                    Log.d(TAG, "input(Quote): " + ((Quote) progress[0]).toString());
//                    progressStatus = getString(R.string.txt_setting_quote);
//                    progressMessage = ((Quote) progress[0]).getQuoteDescription();
//                    Log.d(TAG, "TAG-18");
//                } else if (progress[0] instanceof QuoteLanguageAuthorTag) {
//                    Log.d(TAG, "input(QuoteLanguageAuthorTag): " + ((QuoteLanguageAuthorTag) progress[0]).toString());
//                    progressStatus = getString(R.string.txt_setting_tag);
//                    progressMessage = getString(R.string.txt_linking_quote_with_tag);
//                    Log.d(TAG, "TAG-19");
//                } else if (progress[0] instanceof Author) {
//                    Log.d(TAG, "input(Author): " + ((Author) progress[0]).toString());
//                    progressStatus = getString(R.string.txt_setting_author);
//                    progressMessage = ((Author) progress[0]).getAuthorName();
//                    Log.d(TAG, "TAG-20");
//                } else if (progress[0] instanceof Tag) {
//                    Log.d(TAG, "input(Tag): " + ((Tag) progress[0]).toString());
//                    progressStatus = getString(R.string.txt_setting_tag);
//                    progressMessage = ((Tag) progress[0]).getTagName();
//                    Log.d(TAG, "TAG-21");
//                } else if (progress[0] instanceof Language) {
//                    Log.d(TAG, "input(Language): " + ((Language) progress[0]).toString());
//                    progressStatus = getString(R.string.txt_setting_language);
//                    progressMessage = ((Language) progress[0]).getLanguageName();
//                    Log.d(TAG, "TAG-22");
//                }
//                Log.d(TAG, "TAG-23");
//
//                //setting message
//                tvProgressStatus.setText(progressStatus + ",");
//                tvLeftFirstBrace.setText("(");
//                tvProgressMessage.setText(progressMessage);
//                tvRightFirstBrace.setText(")");
//
//                //set progress
//                //As total input is 7885, that's why progress is percentage is 7885/100
//                if (mCounter == 79) {
//                    mProgress++;
//                    float finalProgress = ((float) mProgress / (float) 100);
//                    if (!progressLayout.isPlaying()) {
//                        progressLayout.start();
//                    }
//
//                    progressLayout.setCurrentProgress((int) (finalProgress * 100));
//                    tvMessage.setText(getString(R.string.txt_loading_for_the_first_time) + ",\n" + (int) (finalProgress * 100) + "%");
//                    mCounter = 0;
//                    Log.d(TAG, "TAG-24");
//                }
//                mCounter++;
//                Log.d(TAG, "TAG-25");
//            }
//            Log.d(TAG, "TAG-=========");
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<AppDataBuilder> result) {
//
//            if (result != null && result.size() > 0) {
//                if (SessionManager.getBooleanSetting(SplashActivity.this, SESSION_IS_FIRST_TIME, true)) {
//                    Intent intentAppIntro = new Intent(SplashActivity.this, AppIntroActivity.class);
//                    startActivity(intentAppIntro);
//                    Bungee.slideUp(SplashActivity.this);
//                    finish();
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        Intent intentPermission = new Intent(SplashActivity.this, PermissionListActivity.class);
//                        startActivityForResult(intentPermission, PermissionListActivity.REQUEST_CODE_PERMISSIONS);
//                        Bungee.slideUp(SplashActivity.this);
//                    } else {
//                        navigateHomeActivity();
//                    }
//                }
//            }
//        }
//    }
//
//    private class PerformLottieTitle extends AsyncTask<String, LottieComposition, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            if (!AllSettingsManager.isNullOrEmpty(params[0])) {
//                llTitleAnimationView.removeAllViews();
//
//                String name = params[0];
//
//                for (int i = 0; i < name.length(); i++) {
//                    String fileName = "mobilo/" + name.charAt(i) + ".json";
//                    LottieComposition.Factory.fromAssetFileName(SplashActivity.this, fileName, new OnCompositionLoadedListener() {
//                        @Override
//                        public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                            if (composition != null) {
//                                publishProgress(composition);
//                            }
//                        }
//                    });
//
//                    try {
//                        Thread.sleep(interval);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//            return "Executed";
//        }
//
//        @Override
//        protected void onProgressUpdate(LottieComposition... progress) {
//            if (progress[0] != null) {
//                LottieComposition lottieComposition = progress[0];
//                LottieAnimationView lottieAnimationView = new LottieAnimationView(SplashActivity.this);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
//                lottieAnimationView.setLayoutParams(layoutParams);
//                lottieAnimationView.setComposition(lottieComposition);
//                lottieAnimationView.playAnimation();
//                llTitleAnimationView.addView(lottieAnimationView);
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.d(TAG, "TAG-3");
//            if (inputData == null) {
//                inputData = new InputData();
//            }
//            Log.d(TAG, "TAG-4");
//            inputData.execute();
//            Log.d(TAG, "TAG-5");
//
//            if (progressLayout.isPlaying()) {
//                Log.d(TAG, "TAG-6");
//                progressLayout.stop();
//            }
//            Log.d(TAG, "TAG-7");
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (inputData != null && inputData.getStatus() == AsyncTask.Status.RUNNING) {
//            inputData.cancel(true);
//        }
//
//        if (performLottieTitle != null && performLottieTitle.getStatus() == AsyncTask.Status.RUNNING) {
//            performLottieTitle.cancel(true);
//        }
//
//        if (progressLayout.isPlaying()) {
//            progressLayout.stop();
//        }
//
//        super.onBackPressed();
//        Bungee.slideDown(SplashActivity.this);
//    }
}
