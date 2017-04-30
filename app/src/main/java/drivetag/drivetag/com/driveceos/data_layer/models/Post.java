package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.IndexPath;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by artem on 4/18/17.
 */

public class Post {

    public PostPreferences postPreferences;

    public PostStats postStats;

    public String identifier;

    public String type;

    public String title;

    public String postDescription;

    public String text;

    public Date timestamp;

    public String companyImageUrlString;

    public String postImageUrlString;

    public String postTagName;

    public List<String> imagesURLs;

    public List<String> videoURLs;

    public boolean sharedOnDriveTag;

    public boolean sharedOnFacebook;

    public boolean sharedOnLinkedIn;

    public boolean sharedOnTwitter;

    public User user;

    public Tag writerTag;

    public Tag coWriterTag;

    public Tag recipientTag;

    public float webViewHeight;

    public IndexPath indexPath;

    public List<Comment> comments;

    public String noticeStatus;

    public Post(JsonObject jsonObject, List<Tag> tags) {

        if (JsonObjectHelper.hasValueFromKey("uid", jsonObject)) {
            identifier = jsonObject.get("uid").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("created_at", jsonObject)) {
            long timeInMiliseconds = jsonObject.get("created_at").getAsLong();

            if (timeInMiliseconds > 0) {
                timestamp = new Date(timeInMiliseconds);
            }
        }

        if (JsonObjectHelper.hasValueFromKey("post_type", jsonObject)) {
            type = jsonObject.get("post_type").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("content", jsonObject)) {
            text = jsonObject.get("content").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("title", jsonObject)) {
            title = jsonObject.get("title").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("images", jsonObject)) {
            JsonArray array = jsonObject.get("images").getAsJsonArray();

            if (array != null) {
                for (int i = 0; i < array.size() ; i++){
                    imagesURLs.add(String.valueOf(array.get(i)));
                }
            }
        }

        if (JsonObjectHelper.hasValueFromKey("icon", jsonObject)) {
            companyImageUrlString = jsonObject.get("icon").getAsString();
        }

        JsonObject statusDictionary = null;
        if (JsonObjectHelper.hasValueFromKey("stats", jsonObject)) {
            statusDictionary = jsonObject.get("stats").getAsJsonObject();
        }

        if (JsonObjectHelper.hasValueFromKey("stats", statusDictionary)) {
            postStats = new PostStats(statusDictionary);
        }

        if (JsonObjectHelper.hasValueFromKey("tag", jsonObject)) {
            postTagName = jsonObject.get("tag").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("on_gp", jsonObject)) {
            sharedOnDriveTag = jsonObject.get("on_gp").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("on_fb", jsonObject)) {
            sharedOnFacebook = jsonObject.get("on_fb").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("on_li", jsonObject)) {
            sharedOnLinkedIn = jsonObject.get("on_li").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("on_tw", jsonObject)) {
            sharedOnTwitter = jsonObject.get("on_tw").getAsBoolean();
        }

        JsonObject prefs = null;
        if (JsonObjectHelper.hasValueFromKey("prefs", jsonObject)) {
            prefs = jsonObject.get("prefs").getAsJsonObject();
        }

        if (JsonObjectHelper.hasValueFromKey("notice_status", prefs)) {
            noticeStatus = prefs.get("notice_status").getAsString();
        }

        if (JsonObjectHelper.hasValueFromKey("user", jsonObject)) {
            JsonObject userDictionary = jsonObject.get("user").getAsJsonObject();
            user = new User(userDictionary);
        }

        postPreferences = new PostPreferences(jsonObject);

        URL url = null; // TODO: method with current url (vimeo or youtube).
// TODO: what?
        if (url != null) {
            videoURLs.add(url.toString());
        }

        JsonObject commentDictionary = null;
        if (JsonObjectHelper.hasValueFromKey("comments", jsonObject)) {
            commentDictionary = jsonObject.getAsJsonObject("comments");
        }

        // TODO: correct initialisation, check null pointer
        List<JsonArray> commentsValue = new ArrayList<>();
        if (JsonObjectHelper.hasValueFromKey("comments", commentDictionary)) {
            JsonArray array = commentDictionary.get("comments").getAsJsonArray();
            commentsValue.set(0, array);// <- todo: check, is it right?
        }

        if (commentsValue.size() > 0) {
            for (int index = commentsValue.size() - 1; index > -1; index--) {
                JsonArray commentArray = commentsValue.get(index);
                JsonObject commentInfo = commentArray.get(1).getAsJsonObject();

                Comment comment = new Comment(commentInfo, tags);
                if (!comment.isVisible) {
                    comments.add(comment);
                }
            }
        }

        if (JsonObjectHelper.hasValueFromKey("co_writer_tag_uid", jsonObject)) {
            String coWriterTagId = jsonObject.get("writer_tag_uid").getAsString();
            coWriterTag = Tag.tagById(Integer.parseInt(coWriterTagId), tags);
        }

        String writerTagId = null;
        if (JsonObjectHelper.hasValueFromKey("writer_tag_uid", jsonObject)) {
            writerTagId = jsonObject.get("writer_tag_uid").getAsString();
        }

        int currentWriterTagId = Integer.parseInt(writerTagId);
        writerTag = Tag.tagById(currentWriterTagId, tags);

        String tagId = null;
        if (JsonObjectHelper.hasValueFromKey("tag_uid", jsonObject)) {
            tagId = jsonObject.get("tag_uid").getAsString();
        }

        int currentTagId = Integer.parseInt(tagId);
        writerTag = Tag.tagById(currentTagId, tags);
    }


    public static Post postById (Number postId, List<Post> posts) {
        for (Post post : posts) {
            if (post.identifier.equals(postId.toString())) {
                return post;
            }
        }
        return null;
    }

    public static boolean isHiddenPost (JsonObject postInfo) {
        JsonObject prefs = postInfo.get("prefs").getAsJsonObject();
        boolean hide = prefs.get("hide_flag").getAsBoolean();
        boolean remove = prefs.get("remove_flag").getAsBoolean();
        return hide || remove;
    }
}
