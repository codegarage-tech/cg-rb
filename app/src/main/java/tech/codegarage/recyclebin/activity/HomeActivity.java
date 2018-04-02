package tech.codegarage.recyclebin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import spencerstudios.com.bungeelib.Bungee;
import tech.codegarage.recyclebin.R;
import tech.codegarage.recyclebin.service.RecoveryService;

import static tech.codegarage.recyclebin.util.AllConstants.EXTRA_ACTION_START;
import static tech.codegarage.recyclebin.util.AllConstants.KEY_INTENT_EXTRA_ACTION;
import static tech.codegarage.recyclebin.util.AllConstants.KEY_INTENT_EXTRA_DIR_PATH;
import static tech.codegarage.recyclebin.util.AllConstants.KEY_INTENT_EXTRA_MASK;
import static tech.codegarage.recyclebin.util.FileManager.isServiceRunning;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitle;
    private ImageView ivBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

        initAction();
    }

    private void initUI() {
        initToolBar();

        initFileObserver();
    }

    private void initAction() {
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.title_activity_home));
        ivBack = (ImageView) findViewById(R.id.iv_back);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Bungee.slideLeft(HomeActivity.this);
            }
        });
    }

    /*****************
     * File observer *
     *****************/
    private void initFileObserver() {
        if (!isServiceRunning(HomeActivity.this, RecoveryService.class)) {
            Intent intentOserverService = new Intent(getBaseContext(), RecoveryService.class);
            intentOserverService.putExtra(KEY_INTENT_EXTRA_ACTION, EXTRA_ACTION_START);
            intentOserverService.putExtra(KEY_INTENT_EXTRA_DIR_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
            intentOserverService.putExtra(KEY_INTENT_EXTRA_MASK, FileObserver.ALL_EVENTS);
            startService(intentOserverService);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bungee.slideLeft(HomeActivity.this);
    }
}