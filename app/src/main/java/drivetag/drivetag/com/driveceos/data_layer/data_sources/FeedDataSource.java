package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import android.webkit.WebView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import drivetag.drivetag.com.driveceos.data_layer.IndexPath;
import drivetag.drivetag.com.driveceos.data_layer.controllers.WebViewHeightConfigurator;
import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.models.Subscription;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.LoadCurrentUserRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.LoadSubscriptionRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.WelcomeMessageRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.post.LoadPostsRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.tag.LoadTagMetadata;
import drivetag.drivetag.com.driveceos.data_layer.requests.users.LoadTagTeamRequest;
import drivetag.drivetag.com.driveceos.helpers.Constants;
import drivetag.drivetag.com.driveceos.utils.ListUtils;

/**
 * Created by artem on 4/27/17.
 */

public class FeedDataSource extends DataSource {

    private static final String POST_BOX_CELL = "PostBoxCell";
    private static final String CURRENT_USER_FILTER_CELL = "UserFiltersCell";
    private static final String GLOBAL_TAG_FEED_FILTERS_CELL = "GlobalTagFeedFiltersView";
    private static final String USER_FEED_FILTERS_CELL = "UserFeedFiltersCell";
    private static final String CEO_FEED_FILTERS_CELL = "CeoFeedFiltersCell";
    private static final String COMPANY_FEED_FILTERS_CELL = "CompanyFeedFiltersCell";

    public Tag tag;

    public User currentUser;

    public Post post;

    public Integer peopleDrivenCounter;

    public String postsFilter;

    public Integer updatedPostsCount;

    public Integer myPostsCount;

    public Integer myTeamPostsCount;

    public Integer publicPostsCount;

    public Integer teammatesCount;

    public Integer pageSize;

    public Integer startTime;

    public String tagTeamFilter;

    public Tag callOutTag;

    public WebView hiddenWebView;

    public Boolean isCompanyFeedFiltersExtended;

    public FeedDataSourceDelegate delegate;

    private LoadPostsRequest loadPostsRequest;

    private WelcomeMessageRequest welcomeMessageRequest;

    private LoadTagTeamRequest loadTagTeamRequest;

    private LoadTagMetadata loadTagMetadata;

    private LoadCurrentUserRequest loadCurrentUserRequest;

    private LoadSubscriptionRequest loadSubscriptionRequest;

    private WebViewHeightConfigurator webViewHeightConfigurator;


    public FeedDataSource() {
        pageSize = 10;
    }

    public void reloadData() {
        if(loading) {
            return;
        }

        lastPage = false;
        loading = true;
        page = 1;
        startTime = 0;

        removeAllSections();
        notifyListenersWillLoadItems();

        loadSubscriptionWithCompletionHandler(new RequestCompletionHandler() {
            @Override
            public void completionHandler() {

                welcomeMessageWithCompletionHandler(new RequestCompletionHandler() {
                    @Override
                    public void completionHandler() {
                        loadTagTeamWithCompletionHandler(new RequestCompletionHandler() {
                            @Override
                            public void completionHandler() {
                                loadPostsWithCompletionHandler(new RequestCompletionHandler() {
                                    @Override
                                    public void completionHandler() {
                                        loadCurrentUserWithCompletionHandler(new RequestCompletionHandler() {
                                            @Override
                                            public void completionHandler() {
                                                loadTagMetadataWithCompletionHandler(new RequestCompletionHandler() {
                                                    @Override
                                                    public void completionHandler() {
                                                        notifyListenersDidLoadItems();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void loadNextPage() {
        if(loading){
            return;
        }

        notifyListenersWillLoadItems();

        loading = true;
        page++;

        startTime = loadPostsRequest.nextTime;

        loadPostsWithCompletionHandler(new RequestCompletionHandler() {
            @Override
            public void completionHandler() {
                notifyListenersDidLoadItems();
            }
        });
    }

    public Boolean isInitialPage() {
        if(page == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void removeWelcomeMessageAtIndexPath(IndexPath indexPath) {
        removeItemAtIndexPath(indexPath);

        List<Object> result = new ArrayList<>(welcomeMessageRequest.serverResponse);
        result.remove(indexPath.item);
        welcomeMessageRequest.serverResponse = result;
    }

    public Boolean isTagSubscriptionCellIndexPath(IndexPath indexPath) {
        if(loadSubscriptionRequest.serverResponse.size() > 0) {
            if(indexPath.item == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean isWelcomeMessageIndexPath (IndexPath indexPath) {
        if(welcomeMessageRequest.serverResponse.size() > 0) {
            Integer numberOfCellsAbove = loadSubscriptionRequest.serverResponse.size();
            if (numberOfCellsAbove > 0) {
                if (numberOfCellsAbove > indexPath.item) {
                    return false;
                } else if (welcomeMessageRequest.serverResponse.size() + numberOfCellsAbove > indexPath.item) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (welcomeMessageRequest.serverResponse.size() > indexPath.item) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public Boolean isTagTeamIndexPath(IndexPath indexPath) {
      Integer index = tagTeamIndex();

        if(index == indexPath.item) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isPostBoxIndexPath(IndexPath indexPath) {
        return hasIdentifier(POST_BOX_CELL,indexPath);
    }

    private Boolean isCurrentUserFiltersIndexPath(IndexPath indexPath) {
        return hasIdentifier(CURRENT_USER_FILTER_CELL,indexPath);
    }

    private Boolean isGlobalTagFeedFiltersView(IndexPath indexPath) {
        return hasIdentifier(GLOBAL_TAG_FEED_FILTERS_CELL,indexPath);
    }

    private Boolean isUserFeedFiltersCell(IndexPath indexPath) {
        return hasIdentifier(USER_FEED_FILTERS_CELL,indexPath);
    }

    private Boolean isCeoFeedFiltersCell(IndexPath indexPath) {
        return hasIdentifier(CEO_FEED_FILTERS_CELL,indexPath);
    }

    private Boolean isCompanyFeedFiltersCell(IndexPath indexPath) {
        return hasIdentifier(COMPANY_FEED_FILTERS_CELL,indexPath);
    }

    public Boolean isPostIndexPath(IndexPath indexPath) {
        List<Object> section = sections.get(0);
        Object item = section.get(indexPath.item);
        if(item.getClass().isInstance(Post.class)) {
            return true;
        } else {
            return false;
        }
    }

    private Integer tagTeamIndex() {
        if (loadTagTeamRequest.serverResponse.size() > 0) {
            List<Object> section = sections.get(0);
            Integer index = section.indexOf(POST_BOX_CELL);

            if (index == -1) {
                index = welcomeMessageRequest.serverResponse.size();
            } else {
                index++;
            }

            return index;
        } else {
            return -1;
        }
    }

    public Boolean hasIdentifier(String identifier, IndexPath indexPath) {
        if (identifier == null) {
            return false;
        }

        if (sections.size() > 0) {
            List<Object> section = sections.get(0);
            Integer index = section.indexOf(identifier);

            if (index == indexPath.item) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void addFiltersItemForSection(List<Object> section, Boolean feedStalled) {
        if (tag.currentType().equals(User.USER_TYPE)) {
            if (delegate.shouldShowCurrentUserFiltersForDriveTag(this,tag.name)) {
                section.add(CURRENT_USER_FILTER_CELL);
            } else if (tag.user.title.equals("CEO")) {
                section.add(CEO_FEED_FILTERS_CELL);
            } else {
                section.add(USER_FEED_FILTERS_CELL);
            }
        } else if (delegate.shouldShowGlobalTagFeedFiltersForDriveTag(this,tag.name)) {
            section.add(GLOBAL_TAG_FEED_FILTERS_CELL);
        } else if (tag.currentType().equals(tag.COMPANY_TYPE)) {
            if(feedStalled) {
                section.add(COMPANY_FEED_FILTERS_CELL);
            }
        }
    }

    private void configureForTag(Tag tag) {
        this.tag = tag;

        if (tag.currentType().equals(User.USER_TYPE)) {
            if (currentUser.driveTag.equals(tag.user.driveTag)) {
                postsFilter = Constants.KEY.POSTS_FILTER_CURRENT_USER_PUBLIC;
            } else {
                postsFilter = Constants.KEY.POST_FILTER_USER_PUBLIC;
            }
            tagTeamFilter = null;
        } else if (tag.currentType().equals(tag.DRIVE_TAG_TYPE)) {
            postsFilter = Constants.KEY.POST_FILTER_GLOBAL_HOME_TEAM_FEED;
            tagTeamFilter = Constants.KEY.TAG_TEAM_ORDER_MARKET;
        } else if (tag.currentType().equals(tag.COMPANY_TYPE)) {
            postsFilter = null;
            tagTeamFilter = null;
        }
    }

    private List<Object> section() {
        List<Object> section = new ArrayList<>();

        if (sections.size() > 0) {
            section = sections.get(0);
        } else {
            sections.add(section);
        }

        return section;
    }


    /* Requests */

    private void loadPostsWithCompletionHandler(final RequestCompletionHandler handler) {
        loadPostsRequest = new LoadPostsRequest(tag,startTime);
        loadPostsRequest.count = pageSize;
        loadPostsRequest.postsFilter = postsFilter;

        if (postsFilter.equals(Constants.KEY.POSTS_FILTER_CURRENT_USER_MY) ||
                postsFilter.equals(Constants.KEY.POSTS_FILTER_CURRENT_USER_MY_TEAM) ||
                postsFilter.equals(Constants.KEY.POSTS_FILTER_CURRENT_USER_PUBLIC)) {

             Integer userDriveId = delegate.getCurrentUserDriveId(this);
             loadPostsRequest.userDriveID = userDriveId;
        }

        if (postsFilter.equals(Constants.KEY.POST_FILTER_USER_PUBLIC)) {
            loadPostsRequest.userDriveID = tag.user.driveID;
        }

        loadPostsRequest.resumeWithCompletionhandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                if(request.error != null) {
                    notifyListenersDidLoadItemsWithError(request.error);
                } else {
                    startTime = loadPostsRequest.nextTime;

                    if(loadPostsRequest.nextTime.equals(-1)) {
                        lastPage = true;
                    } else {
                        lastPage = false;
                    }

                    if(isInitialPage()) {
                        addFiltersItemForSection(section(),loadPostsRequest.feedStalled);
                    }

                    page++;
                    section().add(loadPostsRequest.serverResponse);
                }

                calculateWebViewHeightForPostsWithCompletionHandler(null,new RequestCompletionHandler() {
                    @Override
                    public void completionHandler() {
                        if (handler != null) {
                            loading = false;
                            handler.completionHandler();
                        }
                    }
                });
            }

            @Override
            public void completionHandlerWithError(String error) {

            }
        });
    }

    private void welcomeMessageWithCompletionHandler(final RequestCompletionHandler handler) {
        if(delegate.shouldShowWelcomeMessage(this)) {

            welcomeMessageRequest = new WelcomeMessageRequest();
            welcomeMessageRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<Object>>() {
                @Override
                public void completionHandler(ServerRequest<List<Object>> request) {
                    if (request.serverResponse.size() > 0) {
                        ListUtils.addObjects(request.serverResponse, section());
                    }
                    section().add(POST_BOX_CELL);

                    if (handler != null) {
                        handler.completionHandler();
                    }
                }

                @Override
                public void completionHandlerWithError(String error) {

                }
            });
        } else {
            welcomeMessageRequest = null;
            section().add(POST_BOX_CELL);

            if (handler != null) {
                handler.completionHandler();
            }
        }
    }

    private void loadSubscriptionWithCompletionHandler(final RequestCompletionHandler handler) {
        if(tag.type.equals(tag.COMPANY_TYPE)) {
            loadSubscriptionRequest = new LoadSubscriptionRequest(tag);
            loadSubscriptionRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
                @Override
                public void completionHandler(ServerRequest serverRequest) {
                    LoadSubscriptionRequest request = (LoadSubscriptionRequest) serverRequest;

                    if (request.serverResponse.size() > 0) {
                        section().add(request.serverResponse.get(0));
                        tag.subscription = request.serverResponse.get(0);
                    }

                    if (handler != null) {
                        handler.completionHandler();
                    }
                }

                @Override
                public void completionHandlerWithError(String error) {
                }
            });
        } else {
            loadSubscriptionRequest = null;

            if (handler != null) {
                handler.completionHandler();
            }
        }
    }

    private void loadTagMetadataWithCompletionHandler(final RequestCompletionHandler handler) {
        loadTagMetadata = new LoadTagMetadata(tag);
        loadTagMetadata.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {

                if(request.error != null) {
                    lastPage = true;
                    notifyListenersDidLoadItemsWithError(request.error);
                } else {
                    peopleDrivenCounter = loadTagMetadata.peopleDrivenCounter;
                }

                if (handler != null) {
                    handler.completionHandler();
                }
            }

            @Override
            public void completionHandlerWithError(String error) {

            }
        });
    }

    private void loadTagTeamWithCompletionHandler(final RequestCompletionHandler handler) {
        loadTagTeamRequest = new LoadTagTeamRequest(tag, tagTeamFilter, startTime);
        loadTagTeamRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest serverRequest) {
                LoadTagTeamRequest request = (LoadTagTeamRequest) serverRequest;
                callOutTag = request.callOutTag;
                teammatesCount = request.teammatesCount;

                List<Tag> objects = request.serverResponse;

                if (objects.size() > 0) {
                    section().add(objects);
                }

                if (handler != null) {
                    handler.completionHandler();
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
            }
        });
    }

    private void loadCurrentUserWithCompletionHandler(final RequestCompletionHandler handler) {
        if(delegate.shouldShowCurrentUserFiltersForDriveTag(this, tag.name)) {
            loadCurrentUserRequest = new LoadCurrentUserRequest();
            loadCurrentUserRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
                @Override
                public void completionHandler(ServerRequest serverRequest) {
                    LoadCurrentUserRequest request = (LoadCurrentUserRequest) serverRequest;
                    updatedPostsCount = request.updatedPostsCount;
                    myPostsCount = request.myPostsCount;
                    myTeamPostsCount = request.myTeamPostsCount;
                    publicPostsCount = request.publicPostsCount;

                    if (request.error != null) {
                        lastPage = true;
                        notifyListenersDidLoadItemsWithError(request.error);
                    }

                    if (handler != null) {
                        handler.completionHandler();
                    }
                }

                @Override
                public void completionHandlerWithError(String error) {
                }
            });
        } else {
            if (handler != null) {
                handler.completionHandler();
            }
        }
    }

    private void calculateWebViewHeightForPostsWithCompletionHandler(List<Post> posts, final RequestCompletionHandler handler) {
        if (posts.size() > 0) {
              webViewHeightConfigurator = new WebViewHeightConfigurator(posts);

//            self.webViewHeightConfigurator.loadWebViewContentBlock = ^(NSString *discription) {
//                FeedDataSource *strongSelf = weakSelf;
//            [strongSelf.hiddenWebView loadHTMLString: discription baseURL: nil];
//            };

//            self.webViewHeightConfigurator.didLoadHeightsBlock = ^{
//                if (completionBlock) {
//                    completionBlock();
//                }
//            };
//            webViewHeightConfigurator.configureHeights();
        } else {
            if (handler != null) {
                handler.completionHandler();
            }
        }
    }

        private void forTesting() {

        }

    public ShouldShowWelcomeMessageHandler shouldShowWelcomeMessageHandler;

    public interface FeedDataSourceCompletionHandler {
        void completionHandler();
    }

    public interface ShouldShowWelcomeMessageHandler {
        Boolean completionHandler(FeedDataSource dataSource);
    }

    public interface FeedDataSourceDelegate {
        Boolean shouldShowWelcomeMessage(FeedDataSource dataSource);
        Boolean shouldShowCurrentUserFiltersForDriveTag(FeedDataSource dataSource, String driveTag);
        Boolean shouldShowGlobalTagFeedFiltersForDriveTag(FeedDataSource dataSource, String driveTag);

        Integer getCurrentUserDriveId(FeedDataSource dataSource);
    }

    /**
     *  Completion handler interface for request.
     */

    public interface RequestCompletionHandler<T> {
        void completionHandler();
    }
}