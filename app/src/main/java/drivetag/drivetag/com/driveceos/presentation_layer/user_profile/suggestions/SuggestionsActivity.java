package drivetag.drivetag.com.driveceos.presentation_layer.user_profile.suggestions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;
import java.util.List;

import drivetag.drivetag.com.driveceos.BaseActivity;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.user_profile.suggestions.LoadSuggestionsListRequest;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.SuggectionsAdapter;

/**
 * Created by sergeymelnik on 2017-03-28.
 */

public class SuggestionsActivity extends BaseActivity {

    public final static String SUGGESTION_SELECTED_KEY = "SUGGESTION_SELECTED_KEY";

    private LoadSuggestionsListRequest suggestionsListRequest;

    private SuggectionsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        setupRecyclerView();
        loadSuggestions();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.suggestion_recycler_view);
        setupAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void setupAdapter() {
        adapter = new SuggectionsAdapter();

        adapter.handler = new SuggectionsAdapter.SuggestionsAdapterHandler() {
            @Override
            public void didSelectSuggestion(String suggestion) {
                Intent intent = new Intent();
                intent.putExtra(SUGGESTION_SELECTED_KEY, suggestion);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    private void loadSuggestions() {
        suggestionsListRequest = new LoadSuggestionsListRequest();
        suggestionsListRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<String>>() {
            @Override
            public void completionHandler(ServerRequest<List<String>> request) {
                if (request.serverResponse != null) {
                    adapter.setSuggestionsArray(request.serverResponse);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void completionHandlerWithError(String error) {

            }
        });
    }
}
