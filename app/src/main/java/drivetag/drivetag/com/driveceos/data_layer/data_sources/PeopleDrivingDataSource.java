package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.PeopleDrivingRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.Constants;
import drivetag.drivetag.com.driveceos.utils.ListUtils;

/**
 * Created by artem on 4/26/17.
 */

public class PeopleDrivingDataSource extends DataSource {
    public Integer pageSize;

    public Integer startTime;

    public Integer count;

    public String thoughtsType;

    public Integer sustainableCounter;

    public Integer greedyCounter;

    public Integer leaderCounter;

    public Integer goodJobCounter;

    public Integer harmfulCounter;

    public Integer wastefulCounter;

    public Integer drivesCount;

    public Constants.PeopleDrivingType peopleDrivingType;

    private Post post;

    private Tag tag;

    private PeopleDrivingRequest peopleDrivingRequest;

    public PeopleDrivingDataSource(Post post) {
        this.post = post;
        pageSize = 10;
    }

    public PeopleDrivingDataSource(Tag tag) {
        this.tag = tag;
        pageSize = 10;
    }

    public void reloadData() {
        if (loading) {
            return;
        }

        lastPage = false;
        loading = true;
        page = 1;
        startTime = 0;

        removeAllSections();
        notifyListenersWillLoadItems();

        if (peopleDrivingType.equals(Constants.PeopleDrivingType.PEOPLE_DRIVING_TYPE_POST)) {
            peopleDrivingRequest = new PeopleDrivingRequest(post, startTime, thoughtsType);
        } else {
            peopleDrivingRequest = new PeopleDrivingRequest(tag, startTime, thoughtsType);
        }

        peopleDrivingRequest.peopleDrivingType = peopleDrivingType;

        peopleDrivingRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest serverRequest) {
                PeopleDrivingRequest request = (PeopleDrivingRequest) serverRequest;

                drivesCount = request.drivesCount;
                sustainableCounter = request.sustainableCounter;
                greedyCounter = request.greedyCounter;
                leaderCounter = request.leaderCounter;
                goodJobCounter = request.goodJobCounter;
                harmfulCounter = request.harmfulCounter;
                wastefulCounter = request.wastefulCounter;

                loading = false;

                if (request.error != null) {
                    notifyListenersDidLoadItemsWithError(request.error);
                    return;
                }

                sections.add((List) request.serverResponse);
                notifyListenersDidLoadItems();
            }

            @Override
            public void completionHandlerWithError(String error) {
            }
        });
    }

    public void loadNextPage() {
        if (loading) {
            return;
        }

        loading = true;
        page++;

        notifyListenersWillLoadItems();
        startTime = peopleDrivingRequest.nextTime;

        peopleDrivingRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest serverRequest) {
                PeopleDrivingRequest request = (PeopleDrivingRequest) serverRequest;
                loading = false;

                if (request.error != null) {
                    page--;
                    notifyListenersDidLoadItemsWithError(request.error);
                } else {
                    lastPage = request.nextTime.equals(-1);

                    ListUtils.addObjects((List) request.serverResponse, sections);

                    notifyListenersDidLoadItems();
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
            }
        });
    }

    @Override
    public Boolean isInitialPage() {
        return page == 1;
    }
}
