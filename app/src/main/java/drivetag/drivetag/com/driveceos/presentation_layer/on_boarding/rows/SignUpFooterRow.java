package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-03-30.
 */

public class SignUpFooterRow extends TableRow {

    public SignUpFooterRowHandler handler;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View editTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_up_footer_item, parent, false);
        SignUpFooterViewHolder editTextViewHolder = new SignUpFooterViewHolder(editTextView);

        return editTextViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SignUpFooterViewHolder signUpViewHolder = (SignUpFooterViewHolder)holder;

        signUpViewHolder.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler != null) {
                    handler.didSelectSignUpButton();
                }
            }
        });

        signUpViewHolder.termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler != null) {
                    handler.didSelectTermsButton();
                }
            }
        });
    }

    /**
     * ViewHolder class for "cover_photo_item" layout.
     */
    private static class SignUpFooterViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private Button signUpButton;
        private Button termsButton;

        private SignUpFooterViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            signUpButton = (Button) itemView.findViewById(R.id.sign_up_button);
            termsButton = (Button) itemView.findViewById(R.id.terms_button);
        }
    }

    public interface SignUpFooterRowHandler {
        void didSelectSignUpButton();
        void didSelectTermsButton();
    }
}
