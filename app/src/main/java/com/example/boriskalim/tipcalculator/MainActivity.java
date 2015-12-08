package com.example.boriskalim.tipcalculator;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String[]> namesAndPrices = new ArrayList<>();
    private ListView listView;
    private MyAdapter mAdapter;
    private EditText input1;
    private EditText input2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        setupListView();

    }

    private void setupListView() {
        mAdapter = new MyAdapter(namesAndPrices);

        View header = getLayoutInflater().inflate(R.layout.list_item, null);
        ((TextView)header.findViewById(R.id.name)).setText(R.string.name);
        ((TextView)header.findViewById(R.id.ammount)).setText(R.string.ammount);
        listView.addHeaderView(header);
        listView.setAdapter(mAdapter);
    }

    private void initUI() {
        listView = (ListView) findViewById(R.id.ll);
        input1 =  (EditText) findViewById(R.id.bill);
        input2 =  (EditText) findViewById(R.id.name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculateTip(View view) {
        RadioButton low = (RadioButton) findViewById(R.id.tip_radio_low);
        RadioButton mid = (RadioButton) findViewById(R.id.tip_radio_mid);

        String tipOutput = new String("");
        for (int i = 0; i < namesAndPrices.size(); i++) {
            float bill = Float.parseFloat(namesAndPrices.get(i)[0]);
            float tip;
            if (low.isChecked())
                tip = bill * 13 / 100;
            else if (mid.isChecked())
                tip = bill * 15 / 100;
            else
                tip = bill * 18 / 100;
            tipOutput += namesAndPrices.get(i)[1] + " need to pay " + tip + "$,\n";
        }

        TextView tipView = (TextView) findViewById(R.id.display_tip);
        tipView.setText(tipOutput);
    }

    public void sortByPrice(View v){
        Collections.sort(namesAndPrices, new Comparator<String[]>() {

            int result;
            public int compare(String[] s1, String[] s2) {
               Double d1 = Double.parseDouble(s1[0]);
               Double d2 = Double.parseDouble(s2[0]);

                if(d1.equals(d2)) result = -1;
                if(d1 < d2) result = -1;
                if(d1 > d2) result = 0;

                return result;
            }

        });

        mAdapter.notifyDataSetChanged();
    }

    public void addToList(View v){
        String b = input1.getText().toString();
        String n = input2.getText().toString();

        if (n.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_name, Toast.LENGTH_SHORT).show();
            return;
        }

        if (b.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_amount, Toast.LENGTH_SHORT).show();
            return;
        }

        namesAndPrices.add(new String[]{b, n});
        mAdapter.notifyDataSetChanged();
    }

    public void showDuplicateAmount(View v){
        Set<String> set = new HashSet<>();
        ArrayList<String>result = new ArrayList<>();

        for(String[]item : namesAndPrices){
            if(!set.add(item[0])){
                result.add(item[0]);
            }
        }

        Toast.makeText(this, result.toString(),
                Toast.LENGTH_LONG).show();
    }
}
