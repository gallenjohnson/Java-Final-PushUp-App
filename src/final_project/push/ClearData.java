package final_project.push;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ClearData extends Activity implements OnClickListener {
    Integer rowTracker = null;
    CharSequence dataCleared = "Workout Data Was Reset.";
    int duration = Toast.LENGTH_SHORT;
    private final Integer resetValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_data);
        // Makes buttons
        View resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        // Gets saved prefs
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        if (rowTracker != null) {
            rowTracker = myPrefs.getInt("row_Tracker", 0);
        }
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_clear_data, menu);
        return true;
    }

    public void onClick(View v) {
        SharedPreferences myPrefs = getSharedPreferences("MY_PREFS",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        rowTracker = myPrefs.getInt("row_Tracker", 0);
        switch (v.getId()) {
            case R.id.reset_button:
                // Resets all stored values in MY_PREFS
                editor.putInt("row_Tracker", resetValue);
                editor.putInt("temp_Total", resetValue);
                editor.putInt("total_Pushups", resetValue);
                editor.commit();
                // I have no idea why I had to use getApplicationContext here,
                // but declaring a context variable at the top caused a Null
                // Point Exception Error and doing this fixed it.
                Toast resetToast = Toast.makeText(getApplicationContext(),
                        dataCleared, duration);
                resetToast.show();
                finish();
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
