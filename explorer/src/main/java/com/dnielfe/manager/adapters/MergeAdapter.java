package com.dnielfe.manager.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MergeAdapter extends BaseAdapter implements SectionIndexer {
  private final ArrayList<ListAdapter> pieces = new ArrayList<>();
  private String noItemsText;

  // stock constructor simply chaining to the superclass
  public MergeAdapter() {
    super();
  }

  // adds a new adapter to the roster of things to appear in the aggregate list
  public void addAdapter(ListAdapter adapter) {
    pieces.add(adapter);
    adapter.registerDataSetObserver(new CascadeDataSetObserver());
  }

  // get the data item associated with the specified position in the data set
  public Object getItem(int position) {
    for (ListAdapter piece : pieces) {
      int size = piece.getCount();
      if (position < size) {
        return piece.getItem(position);
      }
      position -= size;
    }
    return null;
  }

  // get the adapter associated with the specified position in the data set
  public ListAdapter getAdapter(int position) {
    for (ListAdapter piece : pieces) {
      int size = piece.getCount();
      if (position < size) {
        return piece;
      }
      position -= size;
    }
    return null;
  }

  // how many items are in the data set represented by this adapter
  public int getCount() {
    int total = 0;
    for (ListAdapter piece : pieces) {
      total += piece.getCount();
    }
    if (total == 0 && noItemsText != null) {
      total = 1;
    }
    return total;
  }

  // returns the number of types of views that will be created by getView
  @Override
  public int getViewTypeCount() {
    int total = 0;
    for (ListAdapter piece : pieces) {
      total += piece.getViewTypeCount();
    }
    // required for setListAdapter before content add
    return Math.max(total, 1);
  }

  // get the type of view that will be created by getView for the specified item
  @Override
  public int getItemViewType(int position) {
    int typeOffset = 0;
    int result = -1;
    for (ListAdapter piece : pieces) {
      int size = piece.getCount();
      if (position < size) {
        result = typeOffset + piece.getItemViewType(position);
        break;
      }
      position -= size;
      typeOffset += piece.getViewTypeCount();
    }
    return result;
  }

  // if all items in this adapter are enabled it means all items are selectable and clickable
  @Override
  public boolean areAllItemsEnabled() {
    return false;
  }

  // returns true if the item at the specified position is not a separator
  @Override
  public boolean isEnabled(int position) {
    for (ListAdapter piece : pieces) {
      int size = piece.getCount();
      if (position < size) {
        return piece.isEnabled(position);
      }
      position -= size;
    }
    return false;
  }

  // get a view that displays the data at the specified position in the data set
  public View getView(int position, View convertView, ViewGroup parent) {
    for (ListAdapter piece : pieces) {
      int size = piece.getCount();
      if (position < size) {
        return piece.getView(position, convertView, parent);
      }
      position -= size;
    }
    if (noItemsText != null) {
      TextView text = new TextView(parent.getContext());
      text.setText(noItemsText);
      return text;
    }
    return null;
  }

  // get the row id associated with the specified position in the list
  public long getItemId(int position) {
    for (ListAdapter piece : pieces) {
      int size = piece.getCount();
      if (position < size) {
        return piece.getItemId(position);
      }
      position -= size;
    }
    return -1;
  }

  public int getPositionForSection(int section) {
    int position = 0;
    for (ListAdapter piece : pieces) {
      if (piece instanceof SectionIndexer) {
        Object[] sections = ((SectionIndexer) piece).getSections();
        int numSections = 0;
        if (sections != null) {
          numSections = sections.length;
        }
        if (section < numSections) {
          return (position + ((SectionIndexer) piece).getPositionForSection(section));
        } else if (sections != null) {
          section -= numSections;
        }
      }
      position += piece.getCount();
    }
    return 0;
  }

  public int getSectionForPosition(int position) {
    int section = 0;
    for (ListAdapter piece : pieces) {
      int size = piece.getCount();
      if (position < size) {
        if (piece instanceof SectionIndexer) {
          return (section + ((SectionIndexer) piece).getSectionForPosition(position));
        }
        return 0;
      } else {
        if (piece instanceof SectionIndexer) {
          Object[] sections = ((SectionIndexer) piece).getSections();
          if (sections != null) {
            section += sections.length;
          }
        }
      }
      position -= size;
    }
    return 0;
  }

  public Object[] getSections() {
    ArrayList<Object> sections = new ArrayList<>();
    for (ListAdapter piece : pieces) {
      if (piece instanceof SectionIndexer) {
        Object[] curSections = ((SectionIndexer) piece).getSections();
        if (curSections != null) {
          Collections.addAll(sections, curSections);
        }
      }
    }
    if (sections.isEmpty()) {
      return null;
    }
    return sections.toArray(new Object[sections.size()]);
  }

  private class CascadeDataSetObserver extends DataSetObserver {
    @Override
    public void onChanged() {
      notifyDataSetChanged();
    }

    @Override
    public void onInvalidated() {
      notifyDataSetInvalidated();
    }
  }
}