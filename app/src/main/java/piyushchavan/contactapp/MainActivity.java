package piyushchavan.contactapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ContactAdaptor contactAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView =findViewById(R.id.listview);
/*        Contact piyush=new Contact();
        piyush.setName("Piyush");
        piyush.setNumber("8097222245");

        Contact rohit=new Contact();
        rohit.setName("Rohit");
        rohit.setNumber("8169046980");

        ArrayList <Contact> contacts=new ArrayList<>();
        contacts.add(piyush);
        contacts.add(rohit);
        */

        DatabaseHandler databaseHandler = new DatabaseHandler(MainActivity.this);
        ArrayList <Contact> contacts = databaseHandler.getAllContacts();

        contactAdaptor=new ContactAdaptor(MainActivity.this,contacts);
        listView.setAdapter(contactAdaptor);


        //register the context menu for the view
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopupMenu(view,i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menuinflater converts menu xml file into menu java object
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selctedItemId = item.getItemId();
        if(selctedItemId == R.id.actionAdd){
            //do the add operation
            addContact();
            Toast.makeText(this, "Add Item Clicked", Toast.LENGTH_SHORT).show();
        }
        else if(selctedItemId == R.id.actionSettings){
            Toast.makeText(this, "Settings Item Clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
        menu.setHeaderTitle("Options");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = adapterContextMenuInfo.position;


        int selectedItemId = item.getItemId();
        if(selectedItemId == R.id.actionDelete){
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }


    public void showPopupMenu(final View view, final int pos){
        //context, view
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int selectedItemId = item.getItemId();
                if(selectedItemId == R.id.actionView){
                    Toast.makeText(MainActivity.this, "View", Toast.LENGTH_SHORT).show();
                    Contact contact= (Contact) contactAdaptor.getItem(pos);
                    viewContact(contact);
                }
                else if(selectedItemId == R.id.actionCall){
                    Toast.makeText(MainActivity.this, "Call", Toast.LENGTH_SHORT).show();
                    Contact contact= (Contact) contactAdaptor.getItem(pos);
                    call(contact);
                }
                else if(selectedItemId == R.id.actionEdit){
                    Toast.makeText(MainActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        popupMenu.show();
    }


    public void addContact(){
        Intent intent = new Intent(MainActivity.this,NewContactActivity.class);
        //startActivity(intent);
        startActivityForResult(intent,1);
    }

    public void viewContact(Contact contact){
        String name=contact.getName();
        String number=contact.getNumber();
        Intent viewintent = new Intent(MainActivity.this,ViewActivity.class);
        viewintent.putExtra("Name",name);
        viewintent.putExtra("Number",number);
        startActivity(viewintent);

    }
    public void call(Contact contact){
        String number=contact.getNumber();
        Intent intent =new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
        String permissions[]={Manifest.permission.CALL_PHONE};
        ActivityCompat.requestPermissions(MainActivity.this,permissions,123);}
        startActivity(intent);
    }

    public void deleteContact(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){

                String name = data.getStringExtra("name");
                String number = data.getStringExtra("number");

                Contact contact = new Contact();
                contact.setName(name);
                contact.setNumber(number);

                contactAdaptor.addContact(contact);
                contactAdaptor.notifyDataSetChanged();

                DatabaseHandler databaseHandler = new DatabaseHandler(MainActivity.this);
                databaseHandler.insertContact(contact);
            }
        }
    }
}