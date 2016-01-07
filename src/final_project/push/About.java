package final_project.push;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Author: G. Allen Johnson
 * Final Project - UI
 * Class: CS 17.11 - D. Pearson
 */

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_about, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
