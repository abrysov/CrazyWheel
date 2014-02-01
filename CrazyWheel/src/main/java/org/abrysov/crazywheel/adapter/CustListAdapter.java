package org.abrysov.crazywheel.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.abrysov.crazywheel.R;
import org.abrysov.crazywheel.model.ModelContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abrysov on 30.01.14.
 */
public class CustListAdapter extends ArrayAdapter {

    private final Activity mContext;
    private ArrayList<ModelContent> mContent;


    static class ViewHolder {
        public TextView title;
        public TextView text;

    }

    public CustListAdapter(Activity context, List<ModelContent> modelContent) {
        super(context, R.layout.rowlayout, modelContent);
        this.mContext = context;
        this.mContent = new ArrayList<ModelContent>(modelContent);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder holder;

        if (rowView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.row_tv_text);
            viewHolder.title = (TextView) rowView.findViewById(R.id.row_tv_title);

            rowView.setTag(viewHolder);
        }

        final ModelContent modelContent = mContent.get(position);

        if (modelContent == null) {
            return rowView;

        }else{
        }

        holder = (ViewHolder) rowView.getTag();

        holder.title.setText(mContent.get(position).getTitle());
        holder.text.setText(mContent.get(position).getText());


        return rowView;
    }
}
