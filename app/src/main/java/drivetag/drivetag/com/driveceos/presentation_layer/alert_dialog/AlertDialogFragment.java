package drivetag.drivetag.com.driveceos.presentation_layer.alert_dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by sergeymelnik on 2017-03-29.
 */

public class AlertDialogFragment extends android.app.DialogFragment implements DialogInterface.OnClickListener {

    public int currentAlertDialogIndex;

    public AlertDialogFragmentHandler handler;

    private final int DeletePhotoButtonIndex = -3;

    private final int SavedPhotoButtonIndex = -2;

    private final int CameraButtonIndex = -1;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Camera", this)
                .setNegativeButton("Saved photos", this)
                .setNeutralButton("Delete photo", this);

        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
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
        }
    }

    public interface AlertDialogFragmentHandler {
        void didSelectCameraButton();
        void didSelectSavedPhotoButton();
        void didSelectDeletePhotoButton();
    }
}
