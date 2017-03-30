package drivetag.drivetag.com.driveceos.presentation_layer.alert_dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-03-29.
 */

public class AlertDialogFragment extends android.app.DialogFragment {

    public int currentAlertDialogIndex;

    public AlertDialogFragmentHandler handler;

    private final int DeletePhotoButtonIndex = 2;

    private final int SavedPhotoButtonIndex = 1;

    private final int CameraButtonIndex = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(new CharSequence[]{getString(R.string.camera), getString(R.string.saved_photos), getString(R.string.delete_photo)},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println();
                        switch (which) {
                            case CameraButtonIndex:
                                if (handler != null) {
                                    handler.didSelectCameraButton();
                                }

                                break;

                            case SavedPhotoButtonIndex:
                                if (handler != null) {
                                    handler.didSelectSavedPhotoButton();
                                }

                                break;

                            case DeletePhotoButtonIndex:
                                if (handler != null) {
                                    handler.didSelectDeletePhotoButton();
                                }

                                break;

                            default:
                                break;
                        }
                    }
                });

        return builder.create();
    }

    public interface AlertDialogFragmentHandler {
        void didSelectCameraButton();
        void didSelectSavedPhotoButton();
        void didSelectDeletePhotoButton();
    }
}
