package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.IndexPath;

/**
 * Created by sergeymelnik on 2017-03-24.
 */

public class DataSource {
    public Boolean loading = false;

    public List<List> sections = new ArrayList<>();

    public String userFacebookToken;

    private List<DataSourceListener> listeners = new ArrayList<>();

    public int numberOfSections() {
        return sections.size();
    }

    public int numberOfItemsInSection(int section) {
        return sections.get(section).size();
    }

    public Object itemAtIndexPath(IndexPath indexPath) {
        return sections.get(indexPath.section).get(indexPath.item);
    }

    public IndexPath indexPathForItem(Object item) {
        int sectionIndex = 0;

        for (List section : sections) {
            int positionIndex = 0;
            for (Object currentItem : section) {
                if (currentItem.equals(item)) {
                    return new IndexPath(sectionIndex, positionIndex);
                }

                positionIndex++;
            }

            sectionIndex++;
        }

        return null;
    }


    /** - Listeners - */

    public void addListener(Object listener) {
        if (!listeners.contains(listener)) {
            listeners.add((DataSourceListener) listener);
        }
    }

    public void removeListener(Object listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void notifyListenersDidLoadItems() {
        for (DataSourceListener listener : listeners) {
            listener.notifyDidLoadItems(sections);
        }
    }

    public void notifyListenersWillLoadItems() {
        for (DataSourceListener listener : listeners) {
            listener.notifyWillLoadItems();
        }
    }

    public void notifyListenersDidLoadItemsWithError(String error) {
        for (DataSourceListener listener : listeners) {
            listener.notifyDidLoadItemsWithError(error);
        }
    }

    public interface DataSourceListener {
        void notifyWillLoadItems();
        void notifyDidLoadItems(List<List> sections);
        void notifyDidLoadItemsWithError(String error);
    }
}
