package drivetag.drivetag.com.driveceos.presentation_layer.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-03-27.
 */

public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public UserProfileAdapterHandler handler;

    public Bitmap coverPhotoBitmap;

    public Bitmap myPagePhotoBitmap;

    public Bitmap userProfileBitmap;

    public String suggestionText;

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View coverPhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_photo_item, parent, false);
                return new CoverPhotoViewHolder(coverPhotoView);

            case 1:
                View userProfileView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile_item, parent, false);
                return new UserProfileViewHolder(userProfileView);

            case 2:
                View extendedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.extended_edit_item, parent, false);
                return new ExtendedEditViewHolder(extendedView);

            case 3:
                View myPagePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_photo_item, parent, false);
                return new CoverPhotoViewHolder(myPagePhotoView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                CoverPhotoViewHolder coverPhotoViewHolder = (CoverPhotoViewHolder)holder;
                coverPhotoViewHolder.titleTextView.setText("up cover");

                if (coverPhotoBitmap != null) {
                    coverPhotoViewHolder.titleTextView.setVisibility(TextView.INVISIBLE);
                    coverPhotoViewHolder.backgroundImageView.setImageBitmap(coverPhotoBitmap);
                } else {
                    coverPhotoViewHolder.titleTextView.setVisibility(TextView.VISIBLE);
                    coverPhotoViewHolder.backgroundImageView.setImageBitmap(null);
                }

                coverPhotoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (handler != null) {
                            handler.didSelectCoverPhotoCompletionHandler(getUserProfileAdapter());
                        }
                    }
                });
                break;

            case 1:
                UserProfileViewHolder userProfileViewHolder = (UserProfileViewHolder)holder;
                userProfileViewHolder.titleTextView.setText("user profile");

                if (userProfileBitmap != null) {
                    userProfileViewHolder.userProfileImageView.setImageBitmap(userProfileBitmap);
                } else {
                    userProfileViewHolder.userProfileImageView.setImageBitmap(null);
                }

                userProfileViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (handler != null) {
                            handler.didSelectProfilePhotoCompletionHandler(getUserProfileAdapter());
                        }
                    }
                });

                break;

            case 2:
                ExtendedEditViewHolder extendedViewHolder = (ExtendedEditViewHolder)holder;
                extendedViewHolder.titleTextView.setText("extended");

                if (suggestionText != null) {
                    extendedViewHolder.editText.setText(suggestionText);
                }

                extendedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println();
                        if (handler != null) {
                            handler.didSelectWhatDrivesYouCompletionHandler(getUserProfileAdapter());
                        }
                    }
                });

                break;

            case 3:
                CoverPhotoViewHolder myPagePhotoViewHolder = (CoverPhotoViewHolder)holder;
                myPagePhotoViewHolder.titleTextView.setText("bottom cover");

                if (myPagePhotoBitmap != null) {
                    myPagePhotoViewHolder.titleTextView.setVisibility(TextView.INVISIBLE);
                    myPagePhotoViewHolder.backgroundImageView.setImageBitmap(myPagePhotoBitmap);
                } else {
                    myPagePhotoViewHolder.titleTextView.setVisibility(TextView.VISIBLE);
                    myPagePhotoViewHolder.backgroundImageView.setImageBitmap(null);
                }

                myPagePhotoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println();
                        if (handler != null) {
                            handler.didSelectMyPageCompletionHandler(getUserProfileAdapter());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    /**
     * ViewHolder class for "cover_photo_item" layout.
     */
    private static class CoverPhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView backgroundImageView;

        CoverPhotoViewHolder(View itemView) {
            super(itemView);
//
////            Typeface titleFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/SF_UI_Text_Light.otf");
//
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            backgroundImageView = (ImageView) itemView.findViewById(R.id.background_image_view);
            System.out.println();
//                categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            titleTextView.setTypeface(titleFont);

//            categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            checkMarkImageView = (ImageView) itemView.findViewById(R.id.check_mark_image_view);
        }
    }

    /**
     * ViewHolder class for "cover_photo_item" layout.
     */
    public static class UserProfileViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView userProfileImageView;

        public UserProfileViewHolder(View itemView) {
            super(itemView);
//
////            Typeface titleFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/SF_UI_Text_Light.otf");
//
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            userProfileImageView = (ImageView) itemView.findViewById(R.id.user_image_view);

//                categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            titleTextView.setTypeface(titleFont);

//            categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            checkMarkImageView = (ImageView) itemView.findViewById(R.id.check_mark_image_view);
        }
    }

    /**
     * ExtendedEditViewHolder class for "extended_edit_view_holder" layout.
     */
    public static class ExtendedEditViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private EditText editText;

        public ExtendedEditViewHolder(View itemView) {
            super(itemView);
//
////            Typeface titleFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/SF_UI_Text_Light.otf");
//
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            editText = (EditText) itemView.findViewById(R.id.suggestion_edit_text);
            System.out.println();
//            backgroundImageView = (ImageView) itemView.findViewById(R.id.background_image_view);
//                categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            titleTextView.setTypeface(titleFont);

//            categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            checkMarkImageView = (ImageView) itemView.findViewById(R.id.check_mark_image_view);
        }
    }

    private UserProfileAdapter getUserProfileAdapter() {
        return this;
    }

    public interface UserProfileAdapterHandler {
        void didSelectCoverPhotoCompletionHandler(UserProfileAdapter adapter);
        void didSelectProfilePhotoCompletionHandler(UserProfileAdapter adapter);
        void didSelectWhatDrivesYouCompletionHandler(UserProfileAdapter adapter);
        void didSelectMyPageCompletionHandler(UserProfileAdapter adapter);
    }
}
