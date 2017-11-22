package piyushchavan.contactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class ContactAdaptor extends BaseAdapter{

    //list of contacts
    ArrayList<Contact> list;
    Context context;
    int i;

    public ContactAdaptor(Context context,ArrayList<Contact> l){
        //initialize
        this.context = context;
        list = l;
    }

    @Override
    public int getCount() {
        //number of items in the adapter
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        //object or value in the position or index
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Contact contact = list.get(i);
        //convert xml layout to view java object
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.list_item,viewGroup,false);
        TextView textViewName = contactView.findViewById(R.id.textViewName);
        TextView textViewNumber = contactView.findViewById(R.id.textViewNumber);

        textViewName.setText(contact.getName());
        textViewNumber.setText(contact.getNumber()+"");

        return contactView;
    }

    public void addContact(Contact contact){
        list.add(contact);
    }

    public void removeContact(int pos){
        list.remove(pos);
    }

    public void viewContact(int position){

    }
    //array adapter - fixed length - default implementation - storage array
    //BaseAdapter - add, remove - own implementation - storage ArrayList
}
