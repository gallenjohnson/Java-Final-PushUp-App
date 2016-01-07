package final_project.push;

public class SetReps {

    // private variables
    int _id;
    String _weekDay;
    String _set1;
    String _set2;
    String _set3;
    String _set4;
    String _set5;

    // Empty Constructor
    public SetReps() {
    }

    // Constructor
    public SetReps(int id, String weekDay, String set1, String set2,
            String set3, String set4, String set5) {
        this._id = id;
        this._weekDay = weekDay;
        this._set1 = set1;
        this._set2 = set2;
        this._set3 = set3;
        this._set4 = set4;
        this._set5 = set5;
    }

    // Constructor
    public SetReps(String weekDay, String set1, String set2, String set3,
            String set4, String set5) {
        this._weekDay = weekDay;
        this._set1 = set1;
        this._set2 = set2;
        this._set3 = set3;
        this._set4 = set4;
        this._set5 = set5;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public String get_weekDay() {
        return _weekDay;
    }

    public void set_weekDay(String weekDay) {
        this._weekDay = weekDay;
    }

    public String get_set1() {
        return _set1;
    }

    public void set_set1(String set1) {
        this._set1 = set1;
    }

    public String get_set2() {
        return _set2;
    }

    public void set_set2(String set2) {
        this._set2 = set2;
    }

    public String get_set3() {
        return _set3;
    }

    public void set_set3(String set3) {
        this._set3 = set3;
    }

    public String get_set4() {
        return _set4;
    }

    public void set_set4(String set4) {
        this._set4 = set4;
    }

    public String get_set5() {
        return _set5;
    }

    public void set_set5(String set5) {
        this._set5 = set5;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (_weekDay != null) {
            builder.append(_weekDay).append(" ");
        }
        if (_set1 != null) {
            builder.append(_set1);
        }
        if (_set2 != null) {
            builder.append(_set2);
        }
        if (_set3 != null) {
            builder.append(_set3);
        }
        if (_set4 != null) {
            builder.append(_set4);
        }
        if (_set5 != null) {
            builder.append(_set5);
        }
        return builder.toString();
    }

}
