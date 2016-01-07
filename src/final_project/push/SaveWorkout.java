package final_project.push;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SaveWorkout extends Activity implements OnClickListener {
    private static String TAG = " SaveWorkout";
    // Context context = getApplicationContext();
    CharSequence dataSaved = "Workout Was Saved.";
    CharSequence notSaved = "Workout Was Not Saved.";
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_workout);
        // Makes buttons
        View saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        View statsButton = findViewById(R.id.exit_button);
        statsButton.setOnClickListener(this);
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
        getMenuInflater().inflate(R.menu.activity_save_workout, menu);
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
        Integer tempPushTotal = myPrefs.getInt("temp_Total", 0);
        Integer totalPushups = myPrefs.getInt("total_Pushups", 0);

        // Log.d(TAG, "Id of row before Save or Exit click: " + rowTracker);
        switch (v.getId()) {
            case R.id.save_button:
                rowTracker++;
                totalPushups = totalPushups + tempPushTotal;
                editor.putInt("total_Pushups", totalPushups);
                editor.putInt("row_Tracker", rowTracker);
                editor.putInt("temp_Total", 0);
                editor.commit();
                Toast savedToast = Toast.makeText(getApplicationContext(),
                        dataSaved, duration);
                savedToast.show();
                finish();
                break;

            case R.id.exit_button:
                if (rowTracker == 1) {
                    editor.putInt("row_Tracker", rowTracker);
                    editor.putInt("temp_Total", 0);
                    editor.commit();
                } else {
                    editor.putInt("row_Tracker", rowTracker);
                    editor.putInt("temp_Total", 0);
                    editor.commit();

                }
                Toast notSavedToast = Toast.makeText(getApplicationContext(),
                        notSaved, duration);
                notSavedToast.show();
                finish();
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
