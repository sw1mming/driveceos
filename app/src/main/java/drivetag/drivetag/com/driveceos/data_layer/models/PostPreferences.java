package drivetag.drivetag.com.driveceos.data_layer.models;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import drivetag.drivetag.com.driveceos.helpers.Constants;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;

/**
 * Created by artem on 4/14/17.
 */

public class PostPreferences {

    public String thoughtsFlag;

    public Boolean isSharedPost;

    public Boolean isCommentedByUser;

    public Integer thoughtsGoodJob;

    public Integer thoughtsGreedy;

    public Integer thoughtsHarmful;

    public Integer thoughtsLeader;

    public Integer thoughtsSustainable;

    public Integer thoughtsWasteful;

    private List<Integer> thoughtsCounters = new ArrayList<>();


    public PostPreferences(JsonObject jsonObject) {

        JsonObject prefsDictionary = null;
        if (JsonObjectHelper.hasValueFromKey("prefs", jsonObject)) {
            prefsDictionary = jsonObject.getAsJsonObject("prefs");
        }

        if (JsonObjectHelper.hasValueFromKey("share_flag", prefsDictionary)) {
            isSharedPost = prefsDictionary.get("share_flag").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("discussed_flag", prefsDictionary)) {
            isCommentedByUser = prefsDictionary.get("discussed_flag").getAsBoolean();
        }

        if (JsonObjectHelper.hasValueFromKey("thought_flag", prefsDictionary)) {
            thoughtsFlag = prefsDictionary.get("thought_flag").getAsString();
        }

        JsonObject statsDictionary = null;
        if (JsonObjectHelper.hasValueFromKey("stats", jsonObject)) {
            statsDictionary = jsonObject.getAsJsonObject("stats");
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_good_job_count", statsDictionary)) {
            thoughtsGoodJob = statsDictionary.get("thoughts_good_job_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_greedy_count", statsDictionary)) {
            thoughtsGreedy = statsDictionary.get("thoughts_greedy_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_harmful_count", statsDictionary)) {
            thoughtsHarmful = statsDictionary.get("thoughts_harmful_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_leader_count", statsDictionary)) {
            thoughtsLeader = statsDictionary.get("thoughts_leader_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_sustainable_count", statsDictionary)) {
            thoughtsSustainable = statsDictionary.get("thoughts_sustainable_count").getAsInt();
        }

        if (JsonObjectHelper.hasValueFromKey("thoughts_wasteful_count", statsDictionary)) {
            thoughtsWasteful = statsDictionary.get("thoughts_wasteful_count").getAsInt();
        }

        updateCounterArray();
    }

    static public List<String> thoughtsNameImages() {
        List<String> obj = null;
        obj.add("goodJobIcon");
        obj.add("greedyIcon");
        obj.add("harmfulIcon");
        obj.add("leaderIcon");
        obj.add("noticesIcon");
        obj.add("wastefulIcon");

        return obj;
    }

    static public List<String> thoughtsStates() {
        List<String> obj = null;
        obj.add("good_job");
        obj.add("greedy");
        obj.add("harmful");
        obj.add("leader");
        obj.add("sustainable");
        obj.add("wasteful");

        return obj;
    }

    static public List<String> thoughtsDisplayName () {
        List<String> obj = null;
        obj.add("Good Job!");
        obj.add("Greedy");
        obj.add("Harmful");
        obj.add("Leader");
        obj.add("Sustainable");
        obj.add("Wasteful");

        return obj;
    }

    public Boolean isDrivedByMe() {
        Boolean result;

        if (thoughtsFlag != null && thoughtsFlag.equals(Constants.KEY.THOUGHT_TYPE_NONE)) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    static public Boolean isThoughtSelected(String thought) {
        Boolean result;

        if (thought.equals("good_job")) {
            result = true;
        } else if (thought.equals("greedy")) {
            result = true;
        } else if (thought.equals("harmful")) {
            result = true;
        } else if (thought.equals("leader")) {
            result = true;
        } else if (thought.equals("sustainable")) {
            result = true;
        } else if (thought.equals("wasteful")) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    public Boolean isSameThoughtSelected (String thought) {
        Boolean result;

        if (thoughtsFlag.equals(thought)) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    public void setThoughtsFlag (String thoughtsFlag) {
        if ((this.thoughtsFlag != null) && (isDrivedByMe())) {
            Integer index = indexOfThougthsFlag(this.thoughtsFlag);
            decrementCounterForIndex(index);

            if (this.thoughtsFlag.equals(thoughtsFlag)) {
                this.thoughtsFlag = Constants.KEY.THOUGHT_TYPE_NONE;
                return;
            }
        }

        Integer index = indexOfThougthsFlag(thoughtsFlag);
        incrementCounterForIndex(index);
        this.thoughtsFlag = thoughtsFlag;
    }

    private void incrementCounterForIndex (Integer index) {
        updateCounterWithValue(1, index);
    }

    private void decrementCounterForIndex (Integer index) {
        updateCounterWithValue(-1, index);
    }

    private void updateCounterArray() {
        thoughtsCounters.add(thoughtsGoodJob);
        thoughtsCounters.add(thoughtsGreedy);
        thoughtsCounters.add(thoughtsHarmful);
        thoughtsCounters.add(thoughtsLeader);
        thoughtsCounters.add(thoughtsSustainable);
        thoughtsCounters.add(thoughtsWasteful);
    }

    private void updateCounterWithValue (int value, Integer index) {
        if (index.equals(0)) {
            thoughtsGoodJob += value;
        } else if (index.equals(1)) {
            thoughtsGreedy += value;
        } else if (index.equals(2)) {
            thoughtsHarmful += value;
        } else if (index.equals(3)) {
            thoughtsLeader += value;
        } else if (index.equals(4)) {
            thoughtsSustainable += value;
        } else if (index.equals(5)) {
            thoughtsWasteful += value;
        }

        updateCounterArray();
    }

    private int indexOfThougthsFlag (String flag) {
        List<String> array = thoughtsStates();
        int index = array.indexOf(flag);

        return index;
    }

    private String imageNameForThougthsIndex (int index) {
        List<String> imagesArray = thoughtsNameImages();
        String imageName = imagesArray.get(index);

        return imageName;
    }

    public List<String> topTwoThoughtsDisplayName() {
        List<String> result = new ArrayList<>();
        List<IndexValue> indexValueList = topTwoThoughtsIndexesAndValues();

        List<String> thoughtsNames = thoughtsDisplayName();
        String firstName = thoughtsNames.get(indexValueList.get(0).index);
        String secondName = thoughtsNames.get(indexValueList.get(1).index);

        IndexValue firstIndexValue = indexValueList.get(0);
        IndexValue secondIndexValue = indexValueList.get(1);

        if (firstIndexValue.value > 0) {
            result.add(firstName);
        }

        if (secondIndexValue.value > 0) {
            result.add(secondName);
        }

        return result;
    }

    public List<String> topTwoThougthsImages() {
        List<String> result = new ArrayList<>();
        List<IndexValue> indexValueList = topTwoThoughtsIndexesAndValues();

        String firstImageName = imageNameForThougthsIndex(indexValueList.get(0).index);

        String secondImageName = imageNameForThougthsIndex(indexValueList.get(1).index);

        IndexValue firstIndexValue = indexValueList.get(0);
        IndexValue secondIndexValue = indexValueList.get(1);

        if (firstIndexValue.value > 0) {
            result.add(firstImageName);
        }

        if (secondIndexValue.value > 0) {
            result.add(secondImageName);
        }

        return result;
    }

    private List<IndexValue> topTwoThoughtsIndexesAndValues() {

        Integer firstThoughtsValue = Collections.max(thoughtsCounters);
        int firstThoughtsIndex = thoughtsCounters.indexOf(firstThoughtsValue);

        Integer secondThoughtsValue = 0;
        int secondThoughtsIndex = 0;
        int index = 0;

        for (Integer number: thoughtsCounters) {
            if (number >= firstThoughtsValue) {
                if (firstThoughtsIndex != index) {
                    if (secondThoughtsValue < number) {
                        secondThoughtsValue = number;
                        secondThoughtsIndex = index;
                    }
                }
            }

            index++;
        }

        IndexValue firstIndexValue = new IndexValue(firstThoughtsIndex, firstThoughtsValue);
        IndexValue secondIndexValue = new IndexValue(secondThoughtsIndex, secondThoughtsValue);

        List<IndexValue> indexValues = new LinkedList<>();
        indexValues.add(firstIndexValue);
        indexValues.add(secondIndexValue);

        return indexValues;
    }


    private class IndexValue {
        int index;
        Integer value;

        private IndexValue(int index, Integer value) {
            this.index = index;
            this.value = value;
        }
    }
}
