package drivetag.drivetag.com.driveceos.presentation_layer.user_profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import drivetag.drivetag.com.driveceos.R;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View root) {
        root.findViewById(R.id.coverFrame).setOnClickListener(this);
        root.findViewById(R.id.addPageFrame).setOnClickListener(this);
        root.findViewById(R.id.profileImageView).setOnClickListener(this);
        ((EditText)root.findViewById(R.id.drivesEditText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getActivity(), "add api call.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coverFrame:
                Toast.makeText(getActivity(), "cover click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addPageFrame:
                Toast.makeText(getActivity(), "page click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profileImageView:
                Toast.makeText(getActivity(), "photo click", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}