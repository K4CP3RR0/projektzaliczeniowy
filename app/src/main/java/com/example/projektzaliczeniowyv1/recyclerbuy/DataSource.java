package com.example.projektzaliczeniowyv1.recyclerbuy;

import android.content.Context;

import com.example.projektzaliczeniowyv1.database.DbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSource {
    Context context;
    DbHelper dbHelper = new DbHelper(context);

    public DataSource() {
    }

    public static Description getAffirmationFromData(int affirmation){
        Description description = new Description();
        description.setStrResourceId(affirmation);
        return description;
    }

    public List<Description> loadDescriptions2(){
        return new ArrayList<>(
                Arrays.asList(
//                        getAffirmationFromData()
                )
        );
    }

}
