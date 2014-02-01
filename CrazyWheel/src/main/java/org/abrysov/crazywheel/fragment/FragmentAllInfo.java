package org.abrysov.crazywheel.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.abrysov.crazywheel.R;
import org.abrysov.crazywheel.model.ModelContent;

/**
 * Created by abrysov on 31.01.14.
 */
public class FragmentAllInfo extends Fragment {

    private TextView title;
    private TextView text;

    private ModelContent mModelContent;

    public FragmentAllInfo (ModelContent modelContent) {
        this.mModelContent = modelContent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_info, container, false);

        title = (TextView)rootView.findViewById(R.id.all_tv_title_content);
        title.setText(mModelContent.getTitle());

        text = (TextView)rootView.findViewById(R.id.all_tv_text_content);
        text.setText(mModelContent.getText());


        return rootView;
    }


}
