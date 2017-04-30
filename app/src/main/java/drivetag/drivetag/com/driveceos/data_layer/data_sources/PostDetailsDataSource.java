package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.IndexPath;
import drivetag.drivetag.com.driveceos.data_layer.models.Comment;
import drivetag.drivetag.com.driveceos.data_layer.models.Post;
import drivetag.drivetag.com.driveceos.data_layer.requests.post.LoadPostRequest;


/**
 * Created by artem.
 */

public class PostDetailsDataSource extends DataSource {

    private static final String COMMENTS_HEADER_CELL = "CommentsHeaderCell";

    private Post post;

    private LoadPostRequest loadPostRequest;


    /** Interface. */

    public PostDetailsDataSource(Post post) {
        this.post = post;
    }

    public void reloadData() {
        removeAllSections();
        notifyListenersWillLoadItems();

        List<Object> section = new ArrayList<>();

        section.add(post);
        section.add(COMMENTS_HEADER_CELL);
        section.add(post.comments);

        sections.add(section);

        notifyListenersDidLoadItems();
    }

    public void refreshData() {
        loadPostWithCompletionHandler(new PostDetailsCompletionHandler() {
            @Override
            public void completionHandler() {
                reloadData();
            }

            @Override
            public void completionHandlerWithError(String error) {
                notifyListenersDidLoadItemsWithError(error);
            }
        });
    }

    public void loadPostWithCompletionHandler(PostDetailsCompletionHandler handler) {
        final PostDetailsCompletionHandler completionHandler = handler;

        loadPostRequest = new LoadPostRequest(post);
        loadPostRequest.resumeWithCompletionHandler(new LoadPostRequest.LoadPostCompletionHandler() {
            @Override
            public void completionHandler(LoadPostRequest request, Post postFromRequest) {

                post.comments = postFromRequest.comments;
                post.postStats = postFromRequest.postStats;

                completionHandler.completionHandler();
            }

            @Override
            public void completionHandlerWithError(String error) {
                completionHandler.completionHandlerWithError(error);
            }
        });

    }

    public Boolean isFeedCell (IndexPath indexPath) {
        if (indexPath.item == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isPostCommentCell (IndexPath indexPath) {
        List<Object> section = sections.get(0);
        Object item = section.get(indexPath.item);

        if (item.getClass().isInstance(Comment.class)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isCommentsHeaderCell (IndexPath indexPath) {
        return hasIdentifier(COMMENTS_HEADER_CELL, indexPath);
    }


    /** Private. */

    private interface PostDetailsCompletionHandler {
        void completionHandler();
        void completionHandlerWithError(String error);
    }
}
