package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-04-05.
 */

public class VerificationRow extends EditTextRow {

    public VerificationResendButtonHandler verificationResendButtonHandler;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View verificationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.verification_item, parent, false);
        VerificationViewHolder verificationViewHolder = new VerificationViewHolder(verificationView);

        return verificationViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position);
        final VerificationViewHolder verificationViewHolder = (VerificationViewHolder)holder;

        if (textFieldEnabled != null) {
            verificationViewHolder.editText.setEnabled(textFieldEnabled);
        }

        verificationViewHolder.editText.setHint(placeholder);

//        verificationViewHolder.editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println();
//            }
//        });

//        verificationViewHolder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus) {
//                    if (didEndChangingHandler != null) {
//                        didEndChangingHandler.didEndChangingTextView();
//                    }
//                }
//            }
//        });

        verificationViewHolder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enteredText = s.toString();

                if (didEnterTextHandler != null) {
                    didEnterTextHandler.didEnterText(enteredText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println();
            }
        });

        verificationViewHolder.resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificationResendButtonHandler != null) {
                    verificationResendButtonHandler.didSelectResendButton();
                }
            }
        });
    }


    /**
     * ViewHolder class for "verification_item" layout.
     */
    private static class VerificationViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;
        private Button resendButton;

        private VerificationViewHolder(View itemView) {
            super(itemView);

            editText = (EditText) itemView.findViewById(R.id.edit_text);
            resendButton = (Button) itemView.findViewById(R.id.resend_button);
        }
    }

    public interface VerificationResendButtonHandler {
        void didSelectResendButton();
    }
}
