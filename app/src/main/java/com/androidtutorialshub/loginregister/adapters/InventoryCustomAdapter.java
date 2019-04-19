package com.androidtutorialshub.loginregister.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.model.Equipment;

import java.util.List;


/**
 * Created by Madalina Bara on 2619-Apr-19.
 */
public class InventoryCustomAdapter extends BaseAdapter {

    private Context context;
    private List<Equipment> inventoryList;

    public InventoryCustomAdapter(Context context, List<Equipment> userModelArrayList) {
        this.context = context;
        this.inventoryList = userModelArrayList;
    }


    @Override
    public int getCount() {
        return inventoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return inventoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.lv_inventory, null, true);

                holder.tvname = (TextView) convertView.findViewById(R.id.name);
                holder.tvroom = (TextView) convertView.findViewById(R.id.room);
                convertView.setTag(holder);
            }
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvname.setText(String.format("Name: %s", inventoryList.get(position).getName()));
        holder.tvroom.setText(String.format("Room: %s", inventoryList.get(position).getRoom()));
        //holder.tvcity.setText("City: "+inventoryList.get(position).getCity());
        return convertView;
    }

    private class ViewHolder {
        TextView tvname, tvroom;
    }

}