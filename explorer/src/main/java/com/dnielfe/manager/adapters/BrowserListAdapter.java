package com.dnielfe.manager.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnielfe.manager.R;
import com.dnielfe.manager.preview.IconPreview;
import com.dnielfe.manager.utils.AppPreferences;
import com.dnielfe.manager.utils.SimpleUtils;
import com.dnielfe.manager.utils.SortUtils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BrowserListAdapter extends BaseAdapter {

  private final LayoutInflater mInflater;
  private final Resources mResources;
  private ArrayList<String> mDataSource;
  private final Context mContext;

  public BrowserListAdapter(Context context, LayoutInflater inflater) {
    mInflater = inflater;
    mContext = context;
    mDataSource = new ArrayList<>();
    mResources = context.getResources();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder mViewHolder;
    int numItems = 0;
    final File file = new File(getItem(position));
    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
        DateFormat.SHORT, Locale.getDefault());

    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.item_browserlist, parent, false);
      mViewHolder = new ViewHolder(convertView);
      convertView.setTag(mViewHolder);
    } else {
      mViewHolder = (ViewHolder) convertView.getTag();
    }
    if (AppPreferences.getViewMode() > 0) {
      mViewHolder.dateView.setVisibility(TextView.VISIBLE);
    } else {
      mViewHolder.dateView.setVisibility(TextView.GONE);
    }

    // get icon
    IconPreview.getFileIcon(file, mViewHolder.icon);

    if (file.isFile()) {
      // shows the size of file
      mViewHolder.bottomView.setText(SimpleUtils.formatCalculatedSize(file.length()));
    } else {
      String[] list = file.list();
      if (list != null) {
        numItems = list.length;
      }
      // show the number of files in folder
      mViewHolder.bottomView.setText(numItems + mResources.getString(R.string.files));
    }
    mViewHolder.topView.setText(file.getName());
    mViewHolder.dateView.setText(df.format(file.lastModified()));
    return convertView;
  }

  private static class ViewHolder {
    final TextView topView;
    final TextView bottomView;
    final TextView dateView;
    final ImageView icon;

    ViewHolder(View view) {
      topView = (TextView) view.findViewById(R.id.top_view);
      bottomView = (TextView) view.findViewById(R.id.bottom_view);
      dateView = (TextView) view.findViewById(R.id.date_view);
      icon = (ImageView) view.findViewById(R.id.row_image);
    }
  }

  public void addFiles(String path) {
    if (!mDataSource.isEmpty()) {
      mDataSource.clear();
    }
    mDataSource = SimpleUtils.listFiles(path, mContext);

    // sort files with a comparator if not empty
    if (!mDataSource.isEmpty()) {
      SortUtils.sortList(mDataSource, path);
    }
    notifyDataSetChanged();
  }

  public void addContent(ArrayList<String> files) {
    if (!mDataSource.isEmpty()) {
      mDataSource.clear();
    }
    mDataSource = files;
    notifyDataSetChanged();
  }

  public int getPosition(String path) {
    return mDataSource.indexOf(path);
  }

  public ArrayList<String> getContent() {
    return mDataSource;
  }

  @Override
  public String getItem(int pos) {
    return mDataSource.get(pos);
  }

  @Override
  public int getCount() {
    return mDataSource.size();
  }

  @Override
  public long getItemId(int position) {
    return position;
  }
}