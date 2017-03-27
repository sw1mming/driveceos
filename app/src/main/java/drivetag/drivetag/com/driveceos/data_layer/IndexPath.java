package drivetag.drivetag.com.driveceos.data_layer;

/**
 * Created by sergeymelnik on 2017-03-24.
 */

public class IndexPath {
    /** Section in path. */
    public int section;

    /** Item in path. */
    public int item;


    /** Interface */

    public IndexPath(int section, int item) {
        this.section = section;
        this.item = item;
    }
}
