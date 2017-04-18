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
import android.view.View;
import android.widget.LinearLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.business_layer.LoginFlow;
import drivetag.drivetag.com.driveceos.business_layer.SignUpFlow;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.presentation_layer.BaseActivity;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.SignUpAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.alert_dialog.AlertDialogFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.EditTextRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.PickerRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.SignUpFooterRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.SignUpHeaderRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.VerificationRow;
import drivetag.drivetag.com.driveceos.presentation_layer.picker_fragment.PickerFragment;

/**
 * Created by artem on 3/24/17.
 */

public class SignUpActivity extends BaseActivity {

    private List<TableRow> rows;

    private static final int REQUEST_IMAGE_GALLERY = 0;

    private static final int REQUEST_CAMERA = 1;

    private static final int TITLES_ROW_INDEX = 7;

    private static final int VERIFICATION_CODE_ROW_INDEX = 5;

    private SignUpHeaderRow headerRow;

    private PickerRow driveTagRow;

    private EditTextRow emailRow;

    private PickerRow titlesRow;

    private EditTextRow passwordRow;

    private VerificationRow verificationRow;

    private SignUpAdapter adapter;

    private SignUpFlow signUpFlow;

    private User user;

    private LinearLayout pickerContainer;

    private PickerFragment pickerFragment;

//    private List<String> freeDriveTags;

//    private List<String> titles;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpFlow = new SignUpFlow((DTApplication) getApplication());
        user = signUpFlow.user;

        setupRows();
        setupRecyclerView();
        setupPickerFragmentContainer();
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

        driveTagRow = setupDriveTagRow(null);
        verificationRow = setupVerificationRow();
        titlesRow = setupEmployeeTitlesRow();

        rows.add(setupHeaderRow());
        rows.add(setupFirstNameRow());
        rows.add(setupMidNameRow());
        rows.add(setupLastNameRow());
        rows.add(setupEmailRow());
        rows.add(driveTagRow);
        rows.add(setupPasswordRow());
        rows.add(setupSignUpFooterRow());
    }

    private SignUpHeaderRow setupHeaderRow() {
        headerRow = new SignUpHeaderRow();
        headerRow.title = "Upload/Take Profile pic";
//        headerRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                showAlertDialog();
//            }
//        };

        return headerRow;
    }

    private EditTextRow setupFirstNameRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "First Name";

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                user.firstName = text;
                loadAvailableDriveTags();
            }
        };

        return row;
    }

    private EditTextRow setupMidNameRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "Mid Name";

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                user.middleName = text;
            }
        };

        return row;
    }

    private EditTextRow setupLastNameRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "Last Name";

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                user.lastName = text;
                loadAvailableDriveTags();
                System.out.println();
            }
        };

        return row;
    }

    private PickerRow setupDriveTagRow(final List<String> freeDriveTags) {
        PickerRow row = new PickerRow();
        row.placeholder = ">FirstMidLast@company";

        if (freeDriveTags != null) {
            row.textFieldEnabled = freeDriveTags.size() > 0;
        } else {
            row.textFieldEnabled = false;
        }

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                user.driveTag = text;
            }
        };

        row.didSelectRowHandler = new TableRow.TableRowHandler() {
            @Override
            public void didSelectRow() {
                if (freeDriveTags != null && freeDriveTags.size() > 0) {
                    showPickerWithContentArray(freeDriveTags);
                }
            }
        };

        return row;
    }

    private PickerRow setupEmployeeTitlesRow() {
        PickerRow row = new PickerRow();
        row.placeholder = "Title";

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                System.out.println();
            }
        };

        row.didSelectRowHandler = new TableRow.TableRowHandler() {
            @Override
            public void didSelectRow() {
                if (titlesRow.pickerTitles != null && titlesRow.pickerTitles.size() > 0) {
                    showPickerWithContentArray(titlesRow.pickerTitles);
                }
            }
        };

        return row;
    }

    private EditTextRow setupPasswordRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "password(combine 6 letters or numbers)";

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                user.password = text;
                System.out.println();
            }
        };

        return row;
    }

    private VerificationRow setupVerificationRow() {
        VerificationRow row = new VerificationRow();
        row.placeholder = "enter verification code";

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                user.verification = text;
            }
        };

        row.verificationResendButtonHandler = new VerificationRow.VerificationResendButtonHandler() {
            @Override
            public void didSelectResendButton() {
                sendVerifyCodeToEmail(user.email);
            }
        };

        return row;
    }

    private EditTextRow setupEmailRow() {
        EditTextRow row = new EditTextRow();
        row.placeholder = "Your email";

        row.didEnterTextHandler = new EditTextRow.EditTextRowHandler() {
            @Override
            public void didEnterText(String text) {
                user.email = text;
                loadAvailableDriveTags();
                System.out.println();
            }
        };

        row.didEndChangingHandler = new EditTextRow.EditTextRowDidEndChangingHandler() {
            @Override
            public void didEndChangingTextView() {
                if (signUpFlow.isEmailValidationSentAutomatically == null || !signUpFlow.isEmailValidationSentAutomatically) {
                    sendVerifyCodeToEmail(user.email);
                }
            }
        };

        return row;
    }

    private void sendVerifyCodeToEmail(final String email) {
        if (Email_Validate(email)) {
            signUpFlow.isEmailValidationSentAutomatically = true;

            // show activity indicator
            signUpFlow.verifyEmail(email, new SignUpFlow.CompletionErrorHandler() {
                @Override
                public void completionHandlerWithError(final String error) {
                    if (!rows.contains(verificationRow)) {
                        rows.add(VERIFICATION_CODE_ROW_INDEX, verificationRow);
                        adapter.notifyItemInserted(VERIFICATION_CODE_ROW_INDEX);
                    }

                    loadTitlesForEmail(email, new EmptyCompletionHandler() {
                        @Override
                        public void completionHandler() {
                            // hide activity indicator
                            if (error != null) {
                                //showDefaultAlertWithViewController
                            }
                        }
                    });
                }
            });
        } else {
            //        [UIAlertController showAlertWithMessage: NSLocalizedString(@"Email is not valid", nil) viewController: self];
        }
    }


    private void loadTitlesForEmail(final String email, final EmptyCompletionHandler handler) {
        signUpFlow.isPersonalEmail(email, new LoginFlow.CompletionHandler<Boolean>() {
            @Override
            public void completionHandler(Boolean completionObject, String error) {
                Boolean isPersonal = completionObject;

                if (isPersonal) {
                    if (rows.contains(titlesRow)) {
                        rows.remove(titlesRow);
                        adapter.notifyItemRemoved(TITLES_ROW_INDEX);
                    }

                    if (handler != null) {
                        handler.completionHandler();
                    }
                } else {
                    signUpFlow.employeeTitlesForEmail(email, new LoginFlow.CompletionHandler<List<String>>() {
                        @Override
                        public void completionHandler(List<String> completionObject, String error) {
                            if (!rows.contains(titlesRow)) {
                                rows.add(TITLES_ROW_INDEX, titlesRow);
                                adapter.notifyItemInserted(TITLES_ROW_INDEX);
                            }

                            if (completionObject != null) {
                                titlesRow.pickerTitles = completionObject;
                                titlesRow.textFieldEnabled = completionObject.size() > 0;
                            }
                        }
                    });

                    if (handler != null) {
                        handler.completionHandler();
                    }
                }
            }
        });
    }

    private SignUpFooterRow setupSignUpFooterRow() {
        SignUpFooterRow row = new SignUpFooterRow();

        row.handler = new SignUpFooterRow.SignUpFooterRowHandler() {
            @Override
            public void didSelectSignUpButton() {

                if (user.firstName == null ||
                    user.lastName == null ||
                    user.email == null ||
                    user.driveTag == null ||
                    user.password == null ||
                    user.verification == null) {

                    //show alert with error
                    return;
                }

                signUpFlow.resumeWithCompletionHandler(new LoginFlow.CompletionHandler<User>() {
                    @Override
                    public void completionHandler(User completionObject, String error) {
                        User signedUpUser = completionObject;

                        if (signedUpUser == null || error != null) {
                            //show alert with error
                        } else {
                            // show alert with text "You successfully climed account."
                        }
                    }
                });
            }

            @Override
            public void didSelectTermsButton() {

            }
        };

        return row;
    }

    private void setupPickerFragmentContainer() {
        pickerContainer = (LinearLayout) findViewById(R.id.picker_container);
    }

    private void showPickerWithContentArray(final List<String> contentArray) {
        pickerFragment = (PickerFragment) getFragmentManager().findFragmentById(R.id.picker_fragment);
        pickerFragment.handler = new PickerFragment.PickerFragmentHandler() {
            @Override
            public void didSelectItemString(String itemString) {
                driveTagRow.title = itemString;
                adapter.notifyDataSetChanged();
            }
        };

        pickerContainer.setVisibility(View.VISIBLE);
        pickerFragment.setContentArray(contentArray);
    }

    private void hidePicker() {
        pickerContainer.setVisibility(View.INVISIBLE);
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

    private void loadAvailableDriveTags() {
        if (user.firstName == null || user.lastName == null || user.email == null) {
            return;
        }

        if (user.firstName.length() < 1 ||
            user.lastName.length() < 1 ||
            !Email_Validate(user.email)) {
            System.out.println();
            return;
        }

        signUpFlow.freeDriveTagsForEmail(user.email, user.firstName, user.lastName, new LoginFlow.CompletionHandler<List<String>>() {
            @Override
            public void completionHandler(List<String> completionObject, String error) {
//                if (error == null) { есть текст undefine error/
                if (completionObject.size() > 0) {
                    driveTagRow.pickerTitles = completionObject;
                    driveTagRow.textFieldEnabled = true;
                    adapter.notifyDataSetChanged();
                }
//                }
            }
        });
    }

    private boolean Email_Validate(String email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            if(bitmap == null)
                return;
//            persistImage(bitmap, "123");

            try {
                File file = new File("path");
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }

            headerRow.userImageBitmap = bitmap;
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = getBitmapFromUri(uri);
//                File f = bitmap.compress(Bitmap.CompressFormat.PNG, 100, null);


                File file = new File("path");
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.close();
                System.out.println();



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

    private interface EmptyCompletionHandler {
        void completionHandler();
    }
}
