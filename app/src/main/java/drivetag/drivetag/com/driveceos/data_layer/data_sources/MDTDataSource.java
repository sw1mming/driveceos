package drivetag.drivetag.com.driveceos.data_layer.data_sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import drivetag.drivetag.com.driveceos.data_layer.IndexPath;
import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import drivetag.drivetag.com.driveceos.data_layer.models.Tag;
import drivetag.drivetag.com.driveceos.data_layer.requests.MdtRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.helpers.Constants;
import drivetag.drivetag.com.driveceos.utils.ListUtils;

/**
 * Created by artem on 4/26/17.
 */

public class MDTDataSource extends DataSource {

    public static final String MDT_FILTER_TITLE = "MdtFilterTitle";
    public static final String MDT_FILTER_VALUE = "MdtFilterValue";

    public static final String FILTER_DRIVEN_LEADERS = "driven_leaders";

    private static final String FILTER_DRIVEN_ORGANIZATIONS = "driven_organizations";
    private static final String FILTER_CALLED_LEADERS = "called_leaders";
    private static final String FILTER_DRIVEN_PEOPLE = "driven_people";
    private static final String FILTER_MY_TEAMMATES = "my_teammates";

    private static final String FILTER_DRIVEN_CEOS = "driven_ceos";
    private static final String FILTER_DRIVEN_EXECUTIVES = "driven_executives";
    private static final String FILTER_DRIVEN_COMPANIES = "driven_companies";
    private static final String FILTER_CALLED_CEOS = "called_ceos";
    private static final String FILTER_CALLED_EXECUTIVES = "called_executives";

    private static final String FILTER_DRIVEN_HEADS = "driven_heads";
    private static final String FILTER_DRIVEN_POLITICIANS = "driven_politicians";
    private static final String FILTER_DRIVEN_COUNTRIES = "driven_countries";
    private static final String FILTER_CALLED_HEADS = "called_heads";
    private static final String FILTER_CALLED_POLITICIANS = "called_politicians";

    public String dataTypeString;

    public String thoughtsType;

    public String filterCategory;

    public UserStorage userStorage;

    public Integer selectedFilterIndex;

    private MdtRequest mdtRequest;

    private Integer startIndex;

    private Integer pageSize;

    private List<String> specialFilters;


    /** Interface. */

    public MDTDataSource() {
        pageSize = 10;
        startIndex = 0;
    }

    public void reloadData() {
        specialFilters = getSpecialFilters();
        lastPage = false;
        loading = true;
        page = 1;
        startIndex = 0;

        removeAllSections();

        sections.add(setupFilterTypeSection());

        if (isSpecialFilter()) {
            sections.add(setupThoughtsFilterSection());
        }

        List<String> filterSectionList = new ArrayList<>(setupFilterSection().values());
        sections.add(filterSectionList); // todo: check it!

        notifyListenersWillLoadItems();

        mdtRequest = new <List<Tag>>MdtRequest(thoughtsType, dataTypeString, startIndex);
        mdtRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<Tag>>() {
            @Override
            public void completionHandler(ServerRequest serverRequest) {
                MdtRequest request = (MdtRequest) serverRequest;
                if (!thoughtsType.equals(request.thoughtType)) {
                    return;
                }

                loading = false;

                if (request.error != null) {
                    notifyListenersDidLoadItemsWithError(request.error);
                    return;
                } else {
                    lastPage = request.nextIndex.equals(-1);
                }

                startIndex = request.startIndex;

                List<Tag> array = request.serverResponse;

                if (array.size() > 0) {
                    ListUtils.addObjects(array, sections);
                }

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

        mdtRequest = new MdtRequest(thoughtsType, dataTypeString, startIndex);
        mdtRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<Tag>>() {
            @Override
            public void completionHandler(ServerRequest serverRequest) {
                MdtRequest request = (MdtRequest) serverRequest;
                loading = false;

                if (request.error != null) {
                    notifyListenersDidLoadItemsWithError(request.error);
                    return;
                } else {
                    if (request.nextIndex.equals(-1)) {
                        lastPage = true;
                    } else {
                        lastPage = false;
                    }
                }

                startIndex = request.startIndex;

                List<Tag> array = request.serverResponse;

                if (array.size() > 0) {
                    ListUtils.addObjects(array, sections);
                }

                notifyListenersDidLoadItems();
            }

            @Override
            public void completionHandlerWithError(String error) {
            }
        });
    }

    public Boolean isFiltersTypeSectionByIndexPath(IndexPath indexPath) {
        if (indexPath.section == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isThoughtsFiltersSectionByIndexPath(IndexPath indexPath) {
        if (isSpecialFilter()) {
            return false;
        } else {
            if (indexPath.section == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Boolean isFilterSectionByIndexPath(IndexPath indexPath) {
        if (isSpecialFilter()) {
            if (indexPath.section == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            if (indexPath.section == 2) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Boolean isSpecialFilterForIndexPath(IndexPath indexPath) {
        HashMap <String, String> item = (HashMap<String, String>) itemAtIndexPath(indexPath); // TODO: not sure it work
        if (specialFilters.contains(item.get(MDT_FILTER_VALUE))) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean hasItems() {
        if (isSpecialFilter()) {
            if (sections.size() > 2) {
                return true;
            } else {
                return false;
            }
        } else {
            if (sections.size() > 3) {
                return true;
            } else {
                return false;
            }
        }
    }


    /** Private */

    private Boolean isSpecialFilter() {
        if (specialFilters.contains(dataTypeString)) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getSpecialFilters() {
        if (specialFilters == null) {
            specialFilters = new ArrayList<>();
            specialFilters.add("called_ceos");
            specialFilters.add("called_leaders");
            specialFilters.add("called_heads");
            specialFilters.add("called_executives");
            specialFilters.add("called_politicians");
        }

        return specialFilters;
    }

    private List<Object> setupThoughtsFilterSection() {
        List<Object> section = new ArrayList<>();
        section.add("thought");
        return section;
    }

    private List<Object> setupFilterTypeSection() {
        List<Object> section = new ArrayList<>();
        section.add("filter_type");
        return section;
    }

    private HashMap<String, String> setupFilterSection() {
        HashMap<String, String> section = new HashMap<>();

        if (filterCategory.equals(Constants.KEY.FILTER_CATEGORY_ALL)) {
            section.put(FILTER_DRIVEN_LEADERS, "Most Driven >Leaders");
            section.put(FILTER_DRIVEN_ORGANIZATIONS ,"Most Driven >Organizations");
            section.put(FILTER_CALLED_LEADERS, "Most Called out >Leaders (in total, not today)");
            section.put(FILTER_DRIVEN_PEOPLE, "Most Driven >People");
            section.put(FILTER_MY_TEAMMATES, "Most Driven >My Teammates");
        } else if (filterCategory.equals(Constants.KEY.FILTER_CATEGORY_CEO)) {
            section.put(FILTER_DRIVEN_CEOS, "Most Driven >CEOs");
            section.put(FILTER_DRIVEN_EXECUTIVES, "Most Driven >Executives");
            section.put(FILTER_DRIVEN_COMPANIES, "Most Driven >Companies");
            section.put(FILTER_CALLED_CEOS, "Most Called out >CEOs (in total, not today)");
            section.put(FILTER_CALLED_EXECUTIVES, "Most Called out >Executives (in total, not today)");
        } else if (filterCategory.equals(Constants.KEY.FILTER_CATEGORY_POLITICIAN)) {
            section.put(FILTER_DRIVEN_HEADS, "Most Driven >HeadsOfState");
            section.put(FILTER_DRIVEN_POLITICIANS, "Most Driven >Politicians");
            section.put(FILTER_DRIVEN_COUNTRIES, "Most Driven >Countries");
            section.put(FILTER_CALLED_HEADS, "Most Called out >HeadOfState (in total, not today)");
            section.put(FILTER_CALLED_POLITICIANS, "Most Called out >Politicians (in total, not today)");
        }

        return section;
    }
}