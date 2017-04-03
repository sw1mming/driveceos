package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding;

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

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.BaseActivity;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.SignUpAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.UserProfileAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.alert_dialog.AlertDialogFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.EditTextRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.SignUpFooterRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.SignUpHeaderRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;
import drivetag.drivetag.com.driveceos.presentation_layer.user_profile.UserProfileActivity;
import drivetag.drivetag.com.driveceos.presentation_layer.user_profile.suggestions.SuggestionsActivity;

/**
 * Created by artem on 3/24/17.
 */

public class SignUpActivity extends BaseActivity {

    private List<TableRow> rows;

    private final int REQUEST_IMAGE_GALLERY = 0;

    private final int REQUEST_CAMERA = 1;

    SignUpHeaderRow headerRow;

    SignUpAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupRows();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new SignUpAdapter();
        adapter.rows = rows;

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sign_up_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    private void setupRows() {
        rows = new ArrayList<>();

        rows.add(setupHeaderRow());
        rows.add(setupFirstNameRow());
        rows.add(setupMidNameRow());
        rows.add(setupLastNameRow());
        rows.add(setupEmailRow());
        rows.add(setupDriveTagRow());
        rows.add(setupPasswordRow());
        rows.add(setupSignUpFooterRow());
    }

    private SignUpHeaderRow setupHeaderRow() {

        headerRow = new SignUpHeaderRow();
        headerRow.title = "Upload/Take Profile pic";
        headerRow.handler = new TableRow.TableRowHandler() {
            @Override
            public void didSelectRow() {
                showAlertDialog();
            }
        };

        return headerRow;
    }

    private EditTextRow setupFirstNameRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "First Name";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private EditTextRow setupMidNameRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "Mid Name";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private EditTextRow setupLastNameRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "Last Name";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private EditTextRow setupDriveTagRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = ">FirstMidLast@company";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private EditTextRow setupEmployeeTitlesRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "Title";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private EditTextRow setupPasswordRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "password(combine 6 letters or numbers)";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private EditTextRow setupVerificationRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "enter verification code";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private EditTextRow setupEmailRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "Your email";

        row.handler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        return row;
    }

    private SignUpFooterRow setupSignUpFooterRow() {
        SignUpFooterRow row = new SignUpFooterRow();

        row.handler = new SignUpFooterRow.SignUpFooterRowHandler() {
            @Override
            public void didSelectSignUpButton() {

            }

            @Override
            public void didSelectTermsButton() {

            }
        };

        return row;
    }

    private void showAlertDialog() {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();

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

        alertDialogFragment.show(SignUpActivity.this.getFragmentManager(), "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            headerRow.userImageBitmap = bitmap;
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = getBitmapFromUri(uri);
                headerRow.userImageBitmap = bitmap;
                adapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
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
        headerRow.userImageBitmap = null;
        adapter.notifyDataSetChanged();
    }
}
