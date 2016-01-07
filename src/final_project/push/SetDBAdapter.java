package final_project.push;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class SetDBAdapter {
    // All Static Variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "setsManager.db";

    // SetReps table name
    private static final String TABLE_SETREPS = "sets";

    private SQLiteDatabase db;

    private Context context;

    private SetsDatabaseOpenHelper dbHelper;

    public SetDBAdapter(Context context) {
        this.context = context;
        dbHelper = new SetsDatabaseOpenHelper(context);
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException se) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public boolean saveSet(SetReps setRep) {
        return saveSet(setRep.get_id(), setRep.get_weekDay(),
                setRep.get_set1(), setRep.get_set2(), setRep.get_set3(),
                setRep.get_set4(), setRep.get_set5());
    }

    public boolean saveSet(Integer _id, String _weekDay, String _set1,
            String _set2, String _set3, String _set4, String _set5) {
        boolean state = false;

        if (_id == null) {
            // Creates new row
            ContentValues newSetValues = new ContentValues();

            // Assigns sets
            newSetValues.put("_weekDay", _weekDay);
            newSetValues.put("_set1", _set1);
            newSetValues.put("_set2", _set2);
            newSetValues.put("_set3", _set3);
            newSetValues.put("_set4", _set4);
            newSetValues.put("_set5", _set5);

            long newId = db.insert("sets", null, newSetValues);
            if (newId != -1) {
                state = true;
            }
        } else {
            // Creates update row
            ContentValues updateSetValues = new ContentValues();

            // Assign sets
            updateSetValues.put("_weekDay", _weekDay);
            updateSetValues.put("_set1", _set1);
            updateSetValues.put("_set2", _set2);
            updateSetValues.put("_set3", _set3);
            updateSetValues.put("_set4", _set4);
            updateSetValues.put("_set5", _set5);

            // Updates the row of sets
            db.update("sets", updateSetValues, "_id = " + _id, null);
            state = true;
        }
        return state;
    }

    public SetReps getSetReps(Integer id) {
        /*
         * query( boolean distinct - true or false - true if you want each row
         * to be unique, false otherwise. String table - The table name to
         * compile the query against. String[] columns - and array of column
         * names to return String selection - the where clause String[]
         * selectionArgs - arguments to the where clause - advanced topic, just
         * use null String groupBy - advanced topic, use null String having -
         * advanced topic, use null String orderBy - sort the results - null for
         * however the database feels like returning the data String limit -
         * limit the number of row to return )
         */

        Cursor cursor = db.query(false, "sets", new String[] { "_id",
                "_weekDay", "_set1", "_set2", "_set3", "_set4", "_set5" },
                "_id = " + id, null, null, null, null, null);

        Integer scrubbedId = cursor.getInt(0);
        String weekDay = cursor.getString(1);
        String set1 = cursor.getString(2);
        String set2 = cursor.getString(3);
        String set3 = cursor.getString(4);
        String set4 = cursor.getString(5);
        String set5 = cursor.getString(6);

        SetReps setReps = new SetReps(scrubbedId, weekDay, set1, set2, set3,
                set4, set5);
        return setReps;

    }

    public Cursor getAllSets() {
        return db.query("sets", new String[] { "_id", "_weekDay", "_set1",
                "_set2", "_set3", "_set4", "_set5" }, null, null, null, null,
                "_id", null);
    }

    // Gets Sets by weekDay

    public Cursor findSetByWeekDay(String weekDay) {
        return db.query("sets", new String[] { "_id", "_weekDay", "_set1",
                "_set2", "_set3", "_set4", "_set5" }, "_weekDay = ?",
                new String[] { weekDay }, null, null, "_id", null);
    }

    // The Database Engine

    private static class SetsDatabaseOpenHelper extends SQLiteOpenHelper {

        private static final String CREATE_TABLE = "CREATE TABLE sets "
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "_weekDay varchar(15), " + "_set1 varchar(10), "
                + "_set2 varchar(10), " + "_set3 varchar(10), "
                + "_set4 varchar(10), " + "_set5 varchar(10) )";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS sets";

        public SetsDatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(DROP_TABLE);

            onCreate(db);

        }

    }

}
