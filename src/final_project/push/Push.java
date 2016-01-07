package final_project.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Author: G. Allen Johnson
 * Final Project - UI
 * Class: CS 17.11 - D. Pearson
 * Version: 3.9.4
 */

public class Push extends Activity implements OnClickListener {

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_button:
                Intent i = new Intent(this, About.class);
                startActivity(i);
                break;
            case R.id.start_button:
                pushStart();
                break;
            case R.id.stats_button:
                pushStats();
                break;

            case R.id.exit_button:
                finish();
                break;
        // More buttons follow here
        }
    }

    public void pushStart() {
        Intent intent = new Intent(this, Start.class);
        startActivity(intent);

    }

    public void pushStats() {
        Intent intent = new Intent(this, Stats.class);
        startActivity(intent);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        // Sets up click listeners
        View startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        View statsButton = findViewById(R.id.stats_button);
        statsButton.setOnClickListener(this);

        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_push, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.erase_button: {
                clearAllData();
                return true;
            }
            case R.id.exit_button: {
                finish();
                return true;
            }
        }
        return false;

    }

    public void clearAllData() {
        Intent intent = new Intent(this, ClearData.class);
        startActivity(intent);
        // finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
