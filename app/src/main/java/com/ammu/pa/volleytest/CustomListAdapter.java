package com.ammu.pa.volleytest;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by aneesh pa on 27/3/18.
 */

class CustomListAdapter extends ArrayAdapter {

    private final Activity context;
    private final Integer [] imageIDArray;
    private final String[] keyArray;
    private final String[] valArray;

    public CustomListAdapter(Activity context, String[] keyarray, String[] valarray, Integer[] imageidarray) {
        super(context, R.layout.layout_row_g5, keyarray);

        this.context = context;
        this.keyArray = keyarray;
        this.valArray = valarray;
        //Log.e("CustomListAdapter constructor", );
        this.imageIDArray = imageidarray;
    }

    public void printVal() {
        Log.e("printVal", "keyArray len " + keyArray.length);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.layout_row_g5, parent, false);
        TextView keyTextView1 = rowView.findViewById(R.id.keyTextViewG5);
        TextView valTextView2 = rowView.findViewById(R.id.valTextViewG5);
        //ImageView imageView = findViewById(R.id.imageView);

        //set the values
        keyTextView1.setText(keyArray[position]);
        valTextView2.setText(valArray[position]);
        //imageView.setImageResource(imageIDArray[position]);
        return(rowView);
    }
} // End of Class CustomListAdapter
