package final_project.push;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Author: G. Allen Johnson
 * Final Project - UI
 * Class: CS 17.11 - D. Pearson
 */

public class Start extends Activity implements OnClickListener {
    private final String TAG = "Start Class";
    private static final String CURRENT_REP = "CURRENT_REP";
    private static final String CURRENT_COUNT = "CURRENT_COUNT";
    private static final String CURRENT_TIMER_STATE = "CURRENT_TIMER_STATE";
    private static final String CURRENT_END_TIME = "CURRENT_END_TIME";
    TextView repUpdate;
    TextView currentRep;
    TextView centerHeader;
    TextView weekDate;
    private Button stopB;
    private boolean timerOn = false;
    private int count = 2;
    private final long interval = 1000;
    private Timer timer;
    private long endTime;
    private long timeRemaining;
    // private long resumedTimeRemaining;
    private long resumedEndTime;
    private SetDBAdapter sdbAdapter;
    private Cursor setCursor;
    Intent intent;
    private Integer rowTracker = null;

    // This was needed to use startManagingCursor.
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Deals with the database
        // Gets the SharedPreferences where the rowTracker value is stored
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);

        // This caused me a lot of problems. It turns out the id count starts at
        // 0, not 1
        Integer rowTracker = myPrefs.getInt("row_Tracker", 0);

        // Handle to the Week# Day# at the top of the page.
        weekDate = (TextView) this.findViewById(R.id.date_header);

        // Handle to the text above the center content
        centerHeader = (TextView) this.findViewById(R.id.center_header);

        // Handle to the center content
        currentRep = (TextView) this.findViewById(R.id.center_content);

        // Handle to the reps listed at the bottom of the page
        repUpdate = (TextView) this.findViewById(R.id.reps_list);

        // Creates and Opens Database
        sdbAdapter = new SetDBAdapter(this);
        sdbAdapter.open();

        // Inserts values into Database
        sdbAdapter.saveSet(null, "Week 1 Day 1", "2", "3", "2", "2", "3");
        sdbAdapter.saveSet(null, "Week 1 Day 2", "3", "4", "2", "3", "4");
        sdbAdapter.saveSet(null, "Week 1 Day 3", "4", "5", "4", "4", "5");
        sdbAdapter.saveSet(null, "Week 2 Day 1", "4", "6", "4", "4", "6");
        sdbAdapter.saveSet(null, "Week 2 Day 2", "5", "6", "4", "4", "7");
        sdbAdapter.saveSet(null, "Week 2 Day 3", "5", "7", "5", "5", "8");
        sdbAdapter.saveSet(null, "Week 3 Day 1", "10", "12", "7", "7", "9");
        sdbAdapter.saveSet(null, "Week 3 Day 2", "10", "12", "8", "8", "12");
        sdbAdapter.saveSet(null, "Week 3 Day 3", "11", "13", "9", "9", "13");
        sdbAdapter.saveSet(null, "Week 4 Day 1", "12", "14", "11", "10", "16");
        sdbAdapter.saveSet(null, "Week 4 Day 2", "14", "16", "12", "12", "18");
        sdbAdapter.saveSet(null, "Week 4 Day 3", "16", "18", "13", "13", "20");
        sdbAdapter.saveSet(null, "Week 5 Day 1", "17", "19", "15", "15", "20");
        sdbAdapter.saveSet(null, "Week 5 Day 2", "20", "26", "20", "18", "25");
        sdbAdapter.saveSet(null, "Week 5 Day 3", "26", "30", "24", "20", "30");
        sdbAdapter.saveSet(null, "Week 6 Day 1", "25", "30", "20", "15", "40");
        sdbAdapter.saveSet(null, "Week 6 Day 2", "28", "30", "28", "20", "44");
        sdbAdapter.saveSet(null, "Week 6 Day 3", "26", "34", "32", "28", "50");

        // Retrieves values in database
        setCursor = sdbAdapter.getAllSets();

        startManagingCursor(setCursor); // The reason for
                                        // SuppressWarnings("deprecation")

        // Displays row of sets to be completed along the bottom
        displaySet(rowTracker);

        // Initializes the center content to the current set of reps
        currentRep.setText(setCursor.getString(2));

        // Sets up done button
        View repDoneButton = findViewById(R.id.rep_done);
        repDoneButton.setOnClickListener(this);

        // Handle to the Done button to change it to a Stop button
        stopB = (Button) this.findViewById(R.id.rep_done);
        stopB.setOnClickListener(this);

    }

    // Updated timer. Uses current system time vs a projected time to determine
    // the time to rest
    public void getTimer() {
        endTime = System.currentTimeMillis() + 60000;
        timeRemaining = endTime - System.currentTimeMillis();
        timer = new Timer(timeRemaining, interval);
    }

    public void getTimer(Long finishTime) {
        endTime = finishTime;
        timeRemaining = endTime - System.currentTimeMillis();
        timer = new Timer(timeRemaining, interval);
    }

    // Displays row of sets along the bottom of the page
    public void displaySet(Integer rowTracker) {
        setCursor.moveToPosition(rowTracker);
        String display = setCursor.getString(2) + " | "
                + setCursor.getString(3) + " | " + setCursor.getString(4)
                + " | " + setCursor.getString(5) + " | "
                + setCursor.getString(6);

        weekDate.setText(setCursor.getString(1));
        repUpdate.setText(display);
    }

    // Keeps track of how many sets have been interated over
    public void repCount() {
        if (count < 5) {
            count++;
            repDisplay(count);
        } else if (count < 6) {
            count++;

            repDisplay(count);
        } else {
            count = 2;
            workoutDone();
        }
    }

    public void resumeRepCount() {
        if (count == 6) {
            timerOn = true;
            repDisplay(count);
            count++;
        } else {
            count = 2;
            workoutDone();
        }

    }

    // Displays the big current rep in the center
    public void repDisplay(int rep) {
        currentRep.setText(setCursor.getString(rep));
    }

    public void workoutDone() {
        // If all 6 weeks have been completed
        if (setCursor.getPosition() == 17) {
            Integer id = setCursor.getPosition();
            SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            // Saving the rowTracker pointer to pass to the congratsDone dialog
            editor.putInt("row_Tracker", id);
            editor.commit();
            congratsDone();
        }
        // Tracks cursor across the database. It only has 18 rows of data
        if ((setCursor != null) && (setCursor.getPosition() < 17)) {
            Integer id = setCursor.getPosition();
            SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            // Saving the rowTracker pointer to pass to the saveWorkout dialog
            editor.putInt("row_Tracker", id);
            editor.commit();
            saveWorkout();

        } else {
            Integer id = 0;
            SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            setCursor.moveToPosition(id);
            editor.putInt("row_Tracker", id);
            editor.commit();
        }
    }

    public void congratsDone() {
        Intent intent = new Intent(this, Congrats.class);
        startActivity(intent);
        finish();
    }

    public void saveWorkout() {
        Integer tempTotal = 0;
        // Gets the pushups done for the current workout
        tempTotal = (Integer.parseInt(setCursor.getString(2))
                + Integer.parseInt(setCursor.getString(3))
                + Integer.parseInt(setCursor.getString(4))
                + Integer.parseInt(setCursor.getString(5)) + Integer
                .parseInt(setCursor.getString(6)));

        // Storing the pushups done for the current workout to be used by the
        // Stats page
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putInt("temp_Total", tempTotal);
        editor.commit();

        // Launches the SaveWorkout dialog
        Intent intent = new Intent(this, SaveWorkout.class);
        startActivity(intent);
        finish();
    }

    public void resumeTimer(Long timerUpdate, boolean state) {
        timerOn = state;
        if (count > 5) {
            stopB.setText("Done");
            repCount();

        } else {
            if (!timerOn) {
                timerOn = true;
                getTimer(timerUpdate);
                centerHeader.setTextColor(-65536);
                centerHeader.setText("Time to Rest:");
                currentRep.setTextColor(-65536);
                currentRep.setText(currentRep.getText()
                        + String.valueOf(timeRemaining));
                timer.start();
                stopB.setText("Stop");
                return;
            } else {
                timer.cancel();
                timerOn = false;
                centerHeader.setTextColor(-16777216);
                currentRep.setTextColor(-16777216);
                stopB.setText("Done");
                timer.onFinish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start, menu);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rep_done:
                if (count > 5) {
                    stopB.setText("Done");
                    repCount();

                } else {
                    if (!timerOn) {
                        timerOn = true;
                        getTimer();
                        centerHeader.setTextColor(-65536);
                        centerHeader.setText("Time to Rest:");
                        currentRep.setTextColor(-65536); // Tacked in to alter
                                                         // text color to red
                        currentRep.setText(currentRep.getText()
                                + String.valueOf(timeRemaining));
                        timer.start();
                        stopB.setText("Stop");
                        break;
                    } else {
                        timer.cancel();
                        timerOn = false;
                        centerHeader.setTextColor(-16777216);
                        currentRep.setTextColor(-16777216);
                        stopB.setText("Done");
                        timer.onFinish();
                    }
                }
        }

    }

    // Timer class
    public class Timer extends CountDownTimer {
        public Timer(Long timeRemaining, Long interval) {
            super(timeRemaining, interval);
        }

        @Override
        public void onFinish() {
            centerHeader.setText("Current Set:");
            timerOn = false;
            centerHeader.setTextColor(-16777216);
            currentRep.setTextColor(-16777216);
            stopB.setText("Done");
            repCount();

        }

        @Override
        public void onTick(long millisUntilFinished) {
            currentRep.setText("" + (millisUntilFinished / 1000));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        rowTracker = myPrefs.getInt("row_Tracker", setCursor.getPosition());
        displaySet(rowTracker);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putString(CURRENT_REP, currentRep.getText()
                .toString());

        saveInstanceState.putInt(CURRENT_COUNT, count);
        saveInstanceState.putBoolean(CURRENT_TIMER_STATE, timerOn);
        saveInstanceState.putLong(CURRENT_END_TIME, endTime);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        String text = "";
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(CURRENT_REP)) {
            text = savedInstanceState.getString(CURRENT_REP);
        }
        currentRep.setText(text);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(CURRENT_COUNT)) {
            count = savedInstanceState.getInt(CURRENT_COUNT);
        }

        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(CURRENT_TIMER_STATE)) {
            timerOn = savedInstanceState.getBoolean(CURRENT_TIMER_STATE);
        }
        if (timerOn == true) {
            if ((savedInstanceState != null)
                    && savedInstanceState.containsKey(CURRENT_END_TIME)) {
                resumedEndTime = savedInstanceState.getLong(CURRENT_END_TIME);
                resumeTimer(resumedEndTime, false);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        rowTracker = setCursor.getPosition();
        editor.putInt("row_Tracker", rowTracker);
        editor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        sdbAdapter.close();

    }
}
