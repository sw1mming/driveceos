package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-03-30.
 */

public class EditTextRow extends TableRow {

    public String enteredText;

    public EditTextRowHandler handler;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View editTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_text_item, parent, false);
        EditTextViewHolder editTextViewHolder = new EditTextViewHolder(editTextView);

        return editTextViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EditTextViewHolder editTextViewHolder = (EditTextViewHolder)holder;

        editTextViewHolder.editText.setHint(placeholder);

        editTextViewHolder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enteredText = s.toString();

                if (handler != null) {
                    handler.didEnterText(enteredText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * ViewHolder class for "cover_photo_item" layout.
     */
    private static class EditTextViewHolder extends RecyclerView.ViewHolder {
        private EditText editText;

        private EditTextViewHolder(View itemView) {
            super(itemView);

            editText = (EditText) itemView.findViewById(R.id.edit_text);
        }
    }

    public interface EditTextRowHandler {
        void didEnterText(String text);
    }
}
