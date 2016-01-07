package final_project.push;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Congrats extends Activity implements OnClickListener {
    // Context context = getApplicationContext();
    CharSequence dataCleared = "Workout Data Was Reset.";
    CharSequence setWeek4 = "Workout Set To Week 4.";
    int duration = Toast.LENGTH_SHORT;
    private final Integer resetValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);
        // Makes buttons
        View resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        View week4Button = findViewById(R.id.week4_button);
        week4Button.setOnClickListener(this);
        // Gets saved prefs
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        Integer rowTracker = myPrefs.getInt("row_Tracker", 0);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_congrats, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        Integer rowTracker = myPrefs.getInt("row_Tracker", 0);
        editor.putInt("row_Tracker", rowTracker);
        editor.commit();
    }

    public void onClick(View v) {
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        Integer rowTracker = myPrefs.getInt("row_Tracker", 0);
        switch (v.getId()) {
            case R.id.reset_button:
                // Resets all stored values in MY_PREFS
                editor.putInt("row_Tracker", resetValue);
                editor.putInt("temp_Total", resetValue);
                editor.putInt("total_Pushups", resetValue);
                Toast resetToast = Toast.makeText(getApplicationContext(),
                        dataCleared, duration);
                resetToast.show();
                finish();
                break;

            case R.id.week4_button:
                // Week 4 is 9 rows down
                rowTracker = 9;
                // Total number of pushups up to Week 4 Day 1 is 280
                Integer week4Total = 280;
                editor.putInt("row_Tracker", rowTracker);
                editor.putInt("temp_Total", resetValue);
                editor.putInt("total_Pushups", week4Total);
                editor.commit();
                Toast week4Toast = Toast.makeText(getApplicationContext(),
                        setWeek4, duration);
                week4Toast.show();
                finish();
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
