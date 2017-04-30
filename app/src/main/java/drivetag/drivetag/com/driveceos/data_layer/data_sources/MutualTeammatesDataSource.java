package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.ArrayList;

import drivetag.drivetag.com.driveceos.data_layer.requests.MutualTeammatesRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;

/**
 * Created by yuriy on 4/26/17.
 */

public class MutualTeammatesDataSource extends DataSource {

    private MutualTeammatesRequest mutualTeammatesRequest;


    /** Interface. */

    public void reloadData() {

        notifyListenersWillLoadItems();

        mutualTeammatesRequest = new MutualTeammatesRequest();
        mutualTeammatesRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {

                if (request.error != null) {
                    notifyListenersDidLoadItemsWithError(request.error);
                    return;
                }

                ArrayList array = (ArrayList) request.serverResponse;

                sections.add(array);
                notifyListenersDidLoadItems();
            }

            @Override
            public void completionHandlerWithError(String error) {
                notifyListenersDidLoadItemsWithError(error);
            }
        });
    }
}
