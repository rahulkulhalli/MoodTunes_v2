package com.moodtunes.moodtunes_v2.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moodtunes.moodtunes_v2.R;

import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> headers;
    private Map<String, String> info;
    private Context mContext;
    private List<Integer> icons;

    private final String strTAG = getClass().getSimpleName();

    public ExpandableListAdapter(List<String> headers, Map<String, String> info,
                                 List<Integer> icons, Context context) {

        this.headers = headers;
        this.info = info;
        this.icons = icons;
        mContext = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view,
                             ViewGroup viewGroup) {

        View groupView = view;

        if (groupView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupView = inflater
                    .inflate(R.layout.list_header_layout, null);
        }

        TextView header = (TextView) groupView.findViewById(R.id.header_text);
        header.setText(headers.get(groupPosition));

        return groupView;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headers.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b,
                             View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView = inflater
                .inflate(R.layout.list_item_layout, null);

        TextView bodyText = (TextView) convertView.findViewById(R.id.list_item);
        String text = info.get(headers.get(groupPosition));
        bodyText.setText(text);

        ImageView buttonView = (ImageView) convertView
                .findViewById(R.id.list_button);

        buttonView.setImageDrawable(ContextCompat.getDrawable(mContext,
                icons.get(groupPosition)));

        switch (icons.get(groupPosition)) {

            case R.drawable.ic_sync_black_36dp:
                buttonView.setId(R.id.btn_scan);
                break;

            case R.drawable.ic_filter_list_black_36dp:
                buttonView.setId(R.id.btn_metadata);
                break;

            case R.drawable.ic_cloud_upload_black_36dp:
                buttonView.setId(R.id.btn_gracenote);
                break;

            case R.drawable.ic_sd_storage_black_36dp:
                buttonView.setId(R.id.btn_db);
                break;

            case R.drawable.ic_camera_alt_black_36dp:
                buttonView.setId(R.id.btn_camera);
                break;

            case R.drawable.ic_question_answer_black_36dp:
                buttonView.setId(R.id.btn_faq);
                break;

            default:
                break;
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return headers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // Only one child!
        return 1;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return info.get(headers.get(groupPosition));
    }
}
