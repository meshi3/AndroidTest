package com.example.boriskalim.tipcalculator;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public ArrayList<String[]> namesAndPrices = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ll);
        final EditText input1 =  (EditText) findViewById(R.id.bill);
        final EditText input2 =  (EditText) findViewById(R.id.name);

        final myAdapter adapter = new myAdapter();
        listView.setAdapter(adapter);
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
                listView.setAdapter(adapter);
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
                listView.setAdapter(adapter);

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

    public class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return namesAndPrices.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(position == 0){
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, null);
                ((TextView) convertView.findViewById(R.id.name)).setText("Name");
                ((TextView) convertView.findViewById(R.id.ammount)).setText("Amuont");
                return convertView;
            } else {
                LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, null);
                ((TextView) convertView.findViewById(R.id.name)).setText(namesAndPrices.get(position - 1)[1]);
                ((TextView) convertView.findViewById(R.id.ammount)).setText(namesAndPrices.get(position - 1)[0]);
                return convertView;
            }

        }
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
