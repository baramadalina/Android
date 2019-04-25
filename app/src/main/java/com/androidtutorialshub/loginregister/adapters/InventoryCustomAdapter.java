package com.androidtutorialshub.loginregister.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.model.Equipment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Madalina Bara on 2019-Apr-19.
 */
public class InventoryCustomAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Equipment> inventoryList;
    private List<Equipment> filteredList;
    private EquipmentFilter filter;


    public InventoryCustomAdapter(Context context, List<Equipment> userModelArrayList) {
        this.context = context;
        this.inventoryList = userModelArrayList;
        this.filteredList = inventoryList;
        getFilter();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
                holder.tvDescription = (TextView) convertView.findViewById(R.id.description);
                convertView.setTag(holder);
            }
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvname.setText(String.format("Name: %s", filteredList.get(position).getName()));
        holder.tvroom.setText(String.format("Room: %s", filteredList.get(position).getRoom()));
        if (filteredList.get(position).getDescription() != null) {
            holder.tvDescription.setText(String.format("Description: %s", filteredList.get(position).getDescription()));
        }
        return convertView;
    }

    /**
     * Get custom filter
     *
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new EquipmentFilter();
        }
        return filter;
    }

    private class ViewHolder {
        TextView tvname, tvroom, tvDescription;
    }

    /**
     * Custom filter for equipments list
     * Filter content in equipments list according to the search text
     */
    private class EquipmentFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<Equipment> tempList = new ArrayList<>();

                // search content in inventory list
                for (Equipment equipment : inventoryList) {
                    if (equipment.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(equipment);
                    }
                }
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = inventoryList.size();
                filterResults.values = inventoryList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Equipment>) results.values;
            notifyDataSetChanged();
        }
    }

}