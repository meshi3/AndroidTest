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


public class MainActivity extends AppCompatActivity {

    private ArrayList<String[]> namesAndPrices = new ArrayList<>();
    private ListView listView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ll);
        final EditText input1 =  (EditText) findViewById(R.id.bill);
        final EditText input2 =  (EditText) findViewById(R.id.name);

        mAdapter = new MyAdapter(namesAndPrices);

        View header = getLayoutInflater().inflate(R.layout.list_item, null);
        ((TextView)header.findViewById(R.id.name)).setText(R.string.name);
        ((TextView)header.findViewById(R.id.ammount)).setText(R.string.ammount);
        listView.addHeaderView(header);
        listView.setAdapter(mAdapter);

        final MainActivity that = this;
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int c = 0; c < ( namesAndPrices.size() - 1 ); c++) {
                    for (int d = 0; d < namesAndPrices.size() - c - 1; d++) {
                        if (Integer.parseInt(namesAndPrices.get(d)[0]) > Integer.parseInt(namesAndPrices.get(d + 1)[0])) /* For descending order use < */
                        {
                            String[] swap       = namesAndPrices.get(d);
                            namesAndPrices.set(d, namesAndPrices.get(d + 1));
                            namesAndPrices.set(d + 1, swap);
                        }
                    }
                }
                listView.setAdapter(mAdapter);
            }
        });

        findViewById(R.id.addToList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = input1.getText().toString();
                String n = input2.getText().toString();

                if (b.matches("")) {
                    Toast.makeText(that, "You did not enter a bill amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (b.matches("")) {
                    Toast.makeText(that, "You did not enter a bill amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] str = new String[2];
                str[0]  = b;
                str[1] = n;
                namesAndPrices.add(str);
                listView.setAdapter(mAdapter);

            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayList<Integer>result = new ArrayList<Integer>();

                for (int a = 0; a < namesAndPrices.size(); a++){
                    for (int b = a; b < namesAndPrices.size(); b++){
                        if (a != b && Integer.parseInt(namesAndPrices.get(a)[0]) == Integer.parseInt(namesAndPrices.get(b)[0])){
                            result.add(Integer.parseInt(namesAndPrices.get(a)[0]));
                            break;
                        }
                    }
                }

                Toast.makeText(that, result.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
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

    public void calculateTip(View view){

        RadioButton low = (RadioButton) findViewById(R.id.tip_radio_low);
        RadioButton mid = (RadioButton) findViewById(R.id.tip_radio_mid);

        String tipOutput = new String("");
        for (int i = 0; i < namesAndPrices.size(); i++){
            float bill = Float.parseFloat(namesAndPrices.get(i)[0]);
            float tip;
            if (low.isChecked())
                tip = bill * 13 / 100;
            else if (mid.isChecked())
                tip = bill * 15 / 100;
            else
                tip = bill * 18 / 100;
            tipOutput += namesAndPrices.get(i)[1] + " need to pay " +tip +"$,\n";
        }

        TextView tipView = (TextView) findViewById(R.id.display_tip);
        tipView.setText(tipOutput);
    }
}
