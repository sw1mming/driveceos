package drivetag.drivetag.com.driveceos.presentation_layer.user_profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;

import drivetag.drivetag.com.driveceos.BaseActivity;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.SuggectionsAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.UserProfileAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.alert_dialog.AlertDialogFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.user_profile.suggestions.SuggestionsActivity;

/**
 * Created by sergeymelnik on 2017-03-27.
 */

public class UserProfileActivity extends BaseActivity {

    private final static int COVER_PHOTO_INDEX = 0;

    private final static int PROFILE_PHOTO_INDEX = 1;

    private final static int MY_PAGE_PHOTO_INDEX = 3;

    private final int REQUEST_IMAGE_GALLERY = 0;

    private final int REQUEST_CAMERA = 1;

    private final int REQUEST_SELECTED_SUGGESTION = 2;

    private AlertDialogFragment alertDialogFragment;

    private UserProfileAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setupRecyclerView();
        setupActionBar();
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(setupUserProfileAdapter());
        recyclerView.setLayoutManager(manager);
    }

    private UserProfileAdapter setupUserProfileAdapter() {
        adapter = new UserProfileAdapter();

        adapter.handler = new UserProfileAdapter.UserProfileAdapterHandler() {
            @Override
            public void didSelectCoverPhotoCompletionHandler(UserProfileAdapter adapter) {
                showAlertDialogForIndex(COVER_PHOTO_INDEX);
            }

            @Override
            public void didSelectProfilePhotoCompletionHandler(UserProfileAdapter adapter) {
                showAlertDialogForIndex(PROFILE_PHOTO_INDEX);
            }

            @Override
            public void didSelectWhatDrivesYouCompletionHandler(UserProfileAdapter adapter) {
                Intent intent = new Intent(UserProfileActivity.this, SuggestionsActivity.class);
                startActivityForResult(intent, REQUEST_SELECTED_SUGGESTION);
            }

            @Override
            public void didSelectMyPageCompletionHandler(UserProfileAdapter adapter) {
                showAlertDialogForIndex(MY_PAGE_PHOTO_INDEX);
            }
        };

        return adapter;
    }

    private void showAlertDialogForIndex(int index) {
        alertDialogFragment = new AlertDialogFragment();

        alertDialogFragment.currentAlertDialogIndex = index;

        alertDialogFragment.handler = new AlertDialogFragment.AlertDialogFragmentHandler() {
            @Override
            public void didSelectCameraButton() {
                showCamera();
            }

            @Override
            public void didSelectSavedPhotoButton() {
                showGallery();
            }

            @Override
            public void didSelectDeletePhotoButton() {
                deletePhoto();
            }
        };

        alertDialogFragment.show(UserProfileActivity.this.getFragmentManager(), "");
    }

    private void showCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    private void showGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
        }
    }

    private void deletePhoto() {
        deleteRowImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            updateRowWithImageBitmap(bitmap);
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = getBitmapFromUri(uri);
                updateRowWithImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_SELECTED_SUGGESTION && resultCode == RESULT_OK) {
            adapter.suggestionText = data.getStringExtra(SuggestionsActivity.SUGGESTION_SELECTED_KEY);;
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteRowImage() {
        updateRowWithImageBitmap(null);
    }

    private void updateRowWithImageBitmap(Bitmap imageBitmap) {
        if (alertDialogFragment.currentAlertDialogIndex == COVER_PHOTO_INDEX) {
            adapter.coverPhotoBitmap = imageBitmap;
        } else if (alertDialogFragment.currentAlertDialogIndex == PROFILE_PHOTO_INDEX) {
            adapter.userProfileBitmap = imageBitmap;
        } else if (alertDialogFragment.currentAlertDialogIndex == MY_PAGE_PHOTO_INDEX) {
            adapter.myPagePhotoBitmap = imageBitmap;
        }

        adapter.notifyDataSetChanged();
        alertDialogFragment = null;
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void setupActionBar() {
        TextView titleActionBarTextView = (TextView) findViewById(R.id.action_bar_title_text_view);
        titleActionBarTextView.setText("User Profile");
    }
}
