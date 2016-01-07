package final_project.push;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Author: G. Allen Johnson
 * Final Project - UI
 * Class: CS 17.11 - D. Pearson
 */

public class Settings extends Activity implements OnClickListener {

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.erase_button:
                pushClear();
                break;
            case R.id.exit_button:
                finish();
                break;
        }

    }

    public void pushClear() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Sets up click listeners
        View startButton = findViewById(R.id.erase_button);
        startButton.setOnClickListener(this);
        View statsButton = findViewById(R.id.exit_button);
        statsButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
    }

}
