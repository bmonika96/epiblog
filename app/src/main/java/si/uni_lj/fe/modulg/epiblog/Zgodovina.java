package si.uni_lj.fe.modulg.epiblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class Zgodovina extends AppCompatActivity {
    //Naslov
    //List z zgodovino po mesecih
    Shramba shramba;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zgodovina);
        shramba=new Shramba(this);
        lv = findViewById(R.id.activity_zgodovina_list);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCas = ((TextView) view.findViewById(R.id.zgodovina_item_holder_cas)).getText().toString();
            String selectedIntenzivnost = ((TextView) view.findViewById(R.id.zgodovina_item_holder_intenzivnost)).getText().toString();
            String selectedTrajanje = ((TextView) view.findViewById(R.id.zgodovina_item_holder_trajanje)).getText().toString();
            String selectedSprozilci = ((TextView) view.findViewById(R.id.zgodovina_item_holder_sprozilci)).getText().toString();
            Intent i = new Intent(this,OpisDogodka.class);
            ArrayList<String> list = new ArrayList<String>();
            list.add(selectedCas);
            list.add(selectedTrajanje);
            list.add(selectedIntenzivnost);
            list.add(selectedSprozilci);
            i.putExtra(OpisDogodka.PODATKIODOGODKU, list);
            startActivity(i);});
    }
    protected void onStart() {
        super.onStart();
        /*new AsyncTaskExecutor().execute(new PrenosPodatkov(urlNaslov,this),
                rezultat -> Toast.makeText(this,rezultat,Toast.LENGTH_LONG).show())
        ;*/
        NodeList nodezgodovina=shramba.pridobiZgodovino();
        ArrayList<HashMap<String, String>> hashZgodovina=nodelistToHashmap(nodezgodovina);
        prikaziPodatke(hashZgodovina);
    }
    private void prikaziPodatke(ArrayList<HashMap<String, String>> seznam){
        SimpleAdapter adapter = new SimpleAdapter(this,
                seznam,
                R.layout.zgodovina_item_holder,
                new String[]{"Cas", "Trajanje", "Intenzivnost","Sprozilci"},
                new int[]{R.id.zgodovina_item_holder_cas, R.id.zgodovina_item_holder_trajanje, R.id.zgodovina_item_holder_intenzivnost,R.id.zgodovina_item_holder_sprozilci});
        lv.setAdapter(adapter);
    }

    private ArrayList<HashMap<String, String>> nodelistToHashmap(NodeList nodeList){

        String TAG = this.getClass().getSimpleName();
        ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
            try {

                // looping through All Contacts
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node c =nodeList.item(i);

                   String cas = Shramba.getValue("Cas",(Element)c);
                    String trajanje = Shramba.getValue("Trajanje",(Element)c);
                    String intenzivnost = Shramba.getValue("Intenzivnost",(Element)c);
                    String sprozilci = Shramba.getValue("Sprozilci",(Element)c);

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("Cas", cas);
                    contact.put("Trajanje", trajanje);
                    contact.put("Intenzivnost", intenzivnost);
                    contact.put("Sprozilci", sprozilci);

                    // adding contact to contact list
                    contactList.add(contact);
                }
            } catch (Exception e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
            return contactList;
        }
}