package com.wunder.test.wunderapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wunder.test.wunderapp.entity.Car;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String API_URL = "https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json";

    private ArrayList<Car> carsList;
    private CarsArrayAdapter mCarsArrayAdapter;
    private ListView mCarsListView;
    private TextView mNoCarsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carsList = new ArrayList<>();

        mNoCarsTextView = (TextView) findViewById(R.id.no_cars_text);

        mCarsArrayAdapter = new CarsArrayAdapter(this, R.layout.list_item_car, carsList);
        mCarsListView = (ListView) findViewById(R.id.cars_listview);
        mCarsListView.setAdapter(mCarsArrayAdapter);
        mCarsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                intent.putParcelableArrayListExtra("carsList", (ArrayList) carsList);
                intent.putExtra("car", carsList.get(position));
                startActivity(intent);
            }
        });

        new MyAsyncTask().execute();
    }


    class MyAsyncTask extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            try {


                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    bReader.close();

                    result = sBuilder.toString();

                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {

            carsList.clear();

            //parse JSON data
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray jArray = obj.getJSONArray("placemarks");

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    String name = jObject.getString("name");

                    String coordinates  = jObject.getString("coordinates");
                    coordinates = coordinates.replace("[","");
                    coordinates = coordinates.replace("]","");
                    coordinates = coordinates.replace(","," ");
                    String [] loc = coordinates.split(" ");
                    float longitude = Float.parseFloat(loc[0]);
                    float latitude = Float.parseFloat(loc[1]);

                    String address  = jObject.getString("address");

                    // TODO load other car attributes when used

                    Car car = new Car(name, latitude, longitude, address);
                    carsList.add(car);

                    //System.out.println(name +", "+latitude+", "+longitude);
                }

                this.progressDialog.dismiss();

            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }

            Log.i(MainActivity.class.getCanonicalName(), getString(R.string.data_loaded));

            // update list data
            mCarsArrayAdapter.notifyDataSetChanged();
            if(mCarsArrayAdapter.getCount() == 0){
                mNoCarsTextView.setVisibility(View.VISIBLE);
                mCarsListView.setVisibility(View.GONE);
            }else{
                mNoCarsTextView.setVisibility(View.GONE);
                mCarsListView.setVisibility(View.VISIBLE);
            }
        }
    }


    private class ViewHolder{
        ImageView carIcon;
        TextView carName;
        TextView carAddress;

        public ViewHolder(View convertView) {
            this.carIcon = (ImageView) convertView.findViewById(R.id.car_imageView);
            this.carName = (TextView) convertView.findViewById(R.id.car_name_textview);
            this.carAddress = (TextView) convertView.findViewById(R.id.car_address_textView);
        }
    }

    public class CarsArrayAdapter extends ArrayAdapter<Car> {
        Context mContext;
        public CarsArrayAdapter(Context context, int resource, ArrayList<Car> list) {
            super(context, resource, list);
            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            Car car = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_car, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.carIcon.setImageResource(R.drawable.car);
            holder.carName.setText(car.getName());

            int MAXLEN = 100;
            if(car.getAddress().length() < MAXLEN) {
                holder.carAddress.setText(car.getAddress());
            }else {
                holder.carAddress.setText(car.getAddress().substring(0, MAXLEN) + "  ...");
            }

            return convertView;
        }
    }
}



