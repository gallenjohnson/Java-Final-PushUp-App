package final_project.push;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

/**
 * Author: G. Allen Johnson
 * Final Project - UI
 * Class: CS 17.11 - D. Pearson
 */

public class Stats extends Activity {
    private TextView pushupsDone;
    private TextView percentDone;
    private String pushTotal = "Total number of pushups \n" + "completed:  ";
    private String percentTotal = "% of 6 week program \n " + "completed:  ";
    private Integer rowTracker = null;
    private final double totalWorkouts = 18D; // This almost works as a double.
                                              // I need to fix the formatting.
    private Integer percentOfWorkout;
    private Integer totalPushups;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        pushupsDone = (TextView) this.findViewById(R.id.total_pushups);
        percentDone = (TextView) this.findViewById(R.id.percent_complete);

        pushupsDone.setText(pushTotal);
        percentDone.setText(percentTotal);

        // Gets stored data
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        rowTracker = myPrefs.getInt("row_Tracker", 0);
        totalPushups = myPrefs.getInt("total_Pushups", 0);

        // Filters for new user
        if ((rowTracker == null) || (rowTracker == 0)) {
            // I needed to put this in to handle an edge case bug at the end of
            // 6 weeks
            editor.putInt("total_Pushups", 0);
            editor.commit();

            pushupsDone.setText("You haven't done anything yet, slacker.");
            percentDone
                    .setText("Check again after you have done some pushups.");

            // Displays current stats
        } else {
            percentOfWorkout = (int) ((rowTracker / totalWorkouts) * 100);
            pushupsDone.setText("" + pushTotal + totalPushups);
            percentDone.setText("" + percentTotal + percentOfWorkout + "%");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stats, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
