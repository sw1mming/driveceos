package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.IndexPath;

/**
 * Created by sergeymelnik on 2017-03-24.
 */

public class DataSource {
    public Boolean loading = false;

    public Boolean lastPage = false;

    public int page;

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

    public Boolean isInitialPage() {
        return page == 0;
    }

    public Boolean hasIdentifier(String identifier, IndexPath indexPath) {
        if (identifier == null) {
            return false;
        }

        if (sections.size() > 0) {
            List<Object> section = sections.get(0);
            Integer index = section.indexOf(identifier);
            return index == indexPath.item;
        } else {
            return false;
        }
    }


    /** - Listeners - */

    public void addListener(DataSourceListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(DataSourceListener listener) {
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

    public void removeAllSections() {
        sections.clear();
    }

    public interface DataSourceListener {
        void notifyWillLoadItems();
        void notifyDidLoadItems(List<List> sections);
        void notifyDidLoadItemsWithError(String error);
    }
}
