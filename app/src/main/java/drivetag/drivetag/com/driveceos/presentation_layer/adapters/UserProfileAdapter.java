package drivetag.drivetag.com.driveceos.presentation_layer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-03-27.
 */

public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

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
                CoverPhotoViewHolder coverPhotoViewHolder = new CoverPhotoViewHolder(coverPhotoView);
                return coverPhotoViewHolder;

            case 1:
                View userProfileView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile_item, parent, false);
                UserProfileViewHolder userProfileViewHolder = new UserProfileViewHolder(userProfileView);
                return userProfileViewHolder;

            case 2:
                View extendedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.extended_edit_item, parent, false);
                ExtendedEditViewHolder extendedViewHolder = new ExtendedEditViewHolder(extendedView);
                return extendedViewHolder;

            case 3:
                View myPagePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_photo_item, parent, false);
                CoverPhotoViewHolder myPagePhotoViewHolder = new CoverPhotoViewHolder(myPagePhotoView);
                return myPagePhotoViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                CoverPhotoViewHolder coverPhotoViewHolder = (CoverPhotoViewHolder)holder;
                coverPhotoViewHolder.titleTextView.setText("up cover");
                break;

            case 1:
                UserProfileViewHolder userProfileViewHolder = (UserProfileViewHolder)holder;
                userProfileViewHolder.titleTextView.setText("user profile");
                break;
            case 2:
                ExtendedEditViewHolder extendedViewHolder = (ExtendedEditViewHolder)holder;
                extendedViewHolder.titleTextView.setText("extended");
                break;

            case 3:
                CoverPhotoViewHolder myPagePhotoViewHolder = (CoverPhotoViewHolder)holder;
                myPagePhotoViewHolder.titleTextView.setText("bottom cover");
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
    public static class CoverPhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView backgroundImageView;

        public CoverPhotoViewHolder(View itemView) {
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
        private ImageView backgroundImageView;

        public UserProfileViewHolder(View itemView) {
            super(itemView);
//
////            Typeface titleFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/SF_UI_Text_Light.otf");
//
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
//            backgroundImageView = (ImageView) itemView.findViewById(R.id.background_image_view);
            System.out.println();
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
//        private ImageView backgroundImageView;

        public ExtendedEditViewHolder(View itemView) {
            super(itemView);
//
////            Typeface titleFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/SF_UI_Text_Light.otf");
//
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            System.out.println();
//            backgroundImageView = (ImageView) itemView.findViewById(R.id.background_image_view);
//                categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            titleTextView.setTypeface(titleFont);

//            categoryImageView = (ImageView) itemView.findViewById(R.id.category_image_view);
//            checkMarkImageView = (ImageView) itemView.findViewById(R.id.check_mark_image_view);
        }
    }

}