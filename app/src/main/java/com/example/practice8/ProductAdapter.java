package com.example.practice8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Product> productList;

    ProductAdapter(Context context, int resource, ArrayList<Product> products) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Product product = productList.get(position);

        viewHolder.nameView.setText(product.getName());
        viewHolder.countView.setText(product.getCount() + " " + product.getUnit());

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int count = product.getCount()-1;
                if (count < 0) count = 0;
                product.setCount(count);
                viewHolder.countView.setText(count + " " + product.getUnit());
            }
        });
        viewHolder.addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int count = product.getCount()+1;
                product.setCount(count);
                viewHolder.countView.setText(count + " " + product.getUnit());
            }
        });
        return convertView;
    }

    private class ViewHolder {
        final Button addButton, removeButton;
        final TextView nameView, countView;
        ViewHolder(View view){
            addButton = view.findViewById(R.id.addButton);
            removeButton = view.findViewById(R.id.removeButton);
            nameView = view.findViewById(R.id.nameView);
            countView = view.findViewById(R.id.countView);
        }

    }
}

