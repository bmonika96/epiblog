package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
//Banana
public class Zgodovina extends AppCompatActivity {
    //Naslov
    //List z zgodovino po mesecih
    Shramba shramba;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zgodovina);
        BottomNavigationView navigation = this.findViewById(R.id.app_bar_menu);

        Menu menu = navigation.getMenu();
        menu.findItem(R.id.navigation_zgodovina).setChecked(true);
        shramba=new Shramba(this);

        lv = findViewById(R.id.activity_zgodovina_list);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCas = ((TextView) view.findViewById(R.id.zgodovina_item_holder_cas)).getText().toString();
            String selectedIntenzivnost = ((TextView) view.findViewById(R.id.zgodovina_item_holder_intenzivnost)).getText().toString();
            String selectedTrajanje = ((TextView) view.findViewById(R.id.zgodovina_item_holder_trajanje)).getText().toString();
            String selectedSprozilci = ((TextView) view.findViewById(R.id.zgodovina_item_holder_sprozilci)).getText().toString();
            String sid = ((TextView) view.findViewById(R.id.zgodovina_item_holder_id)).getText().toString();
            Intent i = new Intent(this,OpisDogodka.class);
            ArrayList<String> list = new ArrayList<String>();
            list.add(selectedCas);
            list.add(selectedTrajanje);
            list.add(selectedIntenzivnost);
            list.add(selectedSprozilci);
            list.add(sid);
            i.putExtra(OpisDogodka.PODATKIODOGODKU, list);
            startActivity(i);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();


        NodeList nodezgodovina=shramba.pridobiZgodovino();
        if (nodezgodovina!=null&&nodezgodovina.getLength()>0) {
            ArrayList<HashMap<String, String>> hashZgodovina = nodelistToHashmap(nodezgodovina);
            prikaziPodatke(hashZgodovina);
        }
        else{
            lv.setAdapter(null);
            TextView nizgodovine=findViewById(R.id.activity_zgodovina_nizgodovine);
            nizgodovine.setVisibility(View.VISIBLE);
        }
    }
    private void prikaziPodatke(ArrayList<HashMap<String, String>> seznam){
        SimpleAdapter adapter = new SimpleAdapter(this,
                seznam,
                R.layout.zgodovina_item_holder,
                new String[]{getString(R.string.shramba_tag_cas), getString(R.string.shramba_tag_trajanje), getString(R.string.shramba_tag_intenzivnosst),getString(R.string.shramba_tag_sprozilci),getString(R.string.shramba_tag_id)},
                new int[]{R.id.zgodovina_item_holder_cas, R.id.zgodovina_item_holder_trajanje, R.id.zgodovina_item_holder_intenzivnost,R.id.zgodovina_item_holder_sprozilci,R.id.zgodovina_item_holder_id});
        lv.setAdapter(adapter);
    }

    private ArrayList<HashMap<String, String>> nodelistToHashmap(NodeList nodeList){

        String TAG = this.getClass().getSimpleName();
        ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
            try {

                // looping through All Contacts
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node c =nodeList.item(i);

                   String cas = Shramba.getValue(getString(R.string.shramba_tag_cas),(Element)c);
                    String trajanje = Shramba.getValue(getString(R.string.shramba_tag_trajanje),(Element)c);
                    String intenzivnost = Shramba.getValue(getString(R.string.shramba_tag_intenzivnosst),(Element)c);
                    String sprozilci = Shramba.getValue(getString(R.string.shramba_tag_sprozilci),(Element)c);
                    String id = Shramba.getValue(getString(R.string.shramba_tag_id),(Element)c);

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put(getString(R.string.shramba_tag_cas), cas);
                    contact.put(getString(R.string.shramba_tag_trajanje), trajanje);
                    contact.put(getString(R.string.shramba_tag_intenzivnosst), intenzivnost);
                    contact.put(getString(R.string.shramba_tag_sprozilci), sprozilci);
                    contact.put(getString(R.string.shramba_tag_id),id);

                    // adding contact to contact list
                    contactList.add(contact);
                }
            } catch (Exception e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
            Collections.reverse(contactList);
            return contactList;
        }
    public void clickedZgodovina(MenuItem item){
        Log.d("abc","zgo");
    }
    public void clickedProfil(MenuItem item){
        this.finish();
        Log.d("abc","pro");
    }
    public void clickedNastavitve(MenuItem item){
        Intent i = new Intent(this,Nastavitve.class);
        startActivity(i);
        this.finish();
        Log.d("abc","nov");
    }
    public void reload(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}