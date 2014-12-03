package com.example.miguel.placestore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Window;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;


public class viewPlaces extends Activity {

    private ArrayList<listPlaces> placelist =new ArrayList<listPlaces>();
    private String toDelete = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_places);
        getActionBar().setTitle("Mis Lugares");

        ListView listView = (ListView) findViewById(R.id.list_place);

        UpdateAdp();

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String coor;
                Double lon, lat;
                coor = placelist.get(i).getCoord();
                lat = Double.parseDouble(coor.substring(0,coor.indexOf("/")-1));
                lon = Double.parseDouble(coor.substring(coor.indexOf("/")+1,coor.length()));
                System.out.println("LAT "+lat);
                System.out.println("Lon "+lon);
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f(%s)", lat, lon,placelist.get(i).getPlace());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                Toast.makeText(getBaseContext()," Obteniendo: "+placelist.get(i).getPlace(), Toast.LENGTH_SHORT).show();
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                           long arg3) {
                toDelete = placelist.get(arg2).getPlace();
                System.out.println("TODEL "+toDelete);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.add(0, v.getId(), 0, "Eliminar");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        SharedPreferences sharedPref = this.getSharedPreferences("PLACES",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(toDelete);
        editor.commit();

        UpdateAdp();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        return true;
    }

    public void UpdateAdp(){
        ListView listView = (ListView) findViewById(R.id.list_place);

        Intent intent = getIntent();
        SharedPreferences sharedPref = this.getSharedPreferences("PLACES", Context.MODE_PRIVATE);
        Map<String,?> keys = sharedPref.getAll();

        placelist = new ArrayList<listPlaces>();
        for (Map.Entry<String, ?> entry: keys.entrySet()) {
            listPlaces cmpList = new listPlaces();
            cmpList.setPlace(entry.getKey());
            cmpList.setCoord((String)entry.getValue());
            placelist.add(cmpList);
        }

        final listAdapter listItemAdapter = new listAdapter(getBaseContext(),placelist);
        listView.setAdapter(listItemAdapter);

        listView.invalidateViews();
        listItemAdapter.notifyDataSetChanged();
    }
}
