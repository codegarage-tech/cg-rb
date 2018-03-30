package tech.codegarage.recyclebin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tech.codegarage.recyclebin.R;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

        initAction();
    }

    private void initUI() {

    }

    private void initAction() {
    }
}
