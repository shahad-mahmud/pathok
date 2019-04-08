package com.exodia.shahad.pathok.recyclerViewAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exodia.shahad.pathok.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String reference;

    private String userName, userEmail, userImage, userId;

    public UserProfileAdapter(Context context, String reference) {
        this.context = context;
        this.reference = reference;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.design_user_profile_header, viewGroup, false);
            return new ProfileHeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(i == 0){
            ProfileHeaderViewHolder headerViewHolder = (ProfileHeaderViewHolder) viewHolder;

            if(reference.equals("self")){ //user's own profile
                getUserDataFromCache();

                //set profile name
                headerViewHolder.userNameTextView.setText(userName);

                //set user image
                Glide.with(context)
                        .asBitmap()
                        .load(userImage)
                        .into(headerViewHolder.profileImage);


                headerViewHolder.badgesRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                headerViewHolder.badgesRecyclerView.setLayoutManager(layoutManager);

                UserProfileBadgesAdapter badgesAdapter = new UserProfileBadgesAdapter(context);
                headerViewHolder.badgesRecyclerView.setAdapter(badgesAdapter);

                //has read
                headerViewHolder.hasReadRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                headerViewHolder.hasReadRecyclerView.setLayoutManager(layoutManager2);

                BookListAdapter bookListAdapter = new BookListAdapter(context);
                headerViewHolder.hasReadRecyclerView.setAdapter(bookListAdapter);

                //is reading
                headerViewHolder.isReadingRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                headerViewHolder.isReadingRecyclerView.setLayoutManager(layoutManager3);

                BookListAdapter bookListAdapter2 = new BookListAdapter(context);
                headerViewHolder.isReadingRecyclerView.setAdapter(bookListAdapter2);

                //has read
                headerViewHolder.willReadRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager4 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                headerViewHolder.willReadRecyclerView.setLayoutManager(layoutManager4);

                BookListAdapter bookListAdapter3 = new BookListAdapter(context);
                headerViewHolder.willReadRecyclerView.setAdapter(bookListAdapter3);

                //profile posts
                HomeFeedAdapter homeFeedAdapter = new HomeFeedAdapter(context);
                headerViewHolder.profilePosts.setHasFixedSize(true);
                LinearLayoutManager layoutManager5 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                headerViewHolder.profilePosts.setLayoutManager(layoutManager5);
                headerViewHolder.profilePosts.setAdapter(homeFeedAdapter);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else if(position == 1){
            return 1;
        }
        return position;
    }

    class ProfileHeaderViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView badgesRecyclerView, hasReadRecyclerView, isReadingRecyclerView,
                willReadRecyclerView, profilePosts;

        private CircleImageView profileImage;

        private TextView userNameTextView, userBioTextView;

        ProfileHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            findElements(itemView);
        }

        void findElements(View view){
            //RecyclerViews
            badgesRecyclerView = (RecyclerView) view.findViewById(R.id.user_profile_badges);
            hasReadRecyclerView = (RecyclerView) view.findViewById(R.id.user_profile_has_read);
            isReadingRecyclerView = (RecyclerView) view.findViewById(R.id.user_profile_is_reading);
            willReadRecyclerView = (RecyclerView) view.findViewById(R.id.user_profile_will_read);
            profilePosts = (RecyclerView) view.findViewById(R.id.user_profile_posts);

            //circle images
            profileImage = (CircleImageView) view.findViewById(R.id.user_profile_user_image);

            //TextViews
            userNameTextView = (TextView) view.findViewById(R.id.user_profile_user_name);
            userBioTextView = (TextView) view.findViewById(R.id.user_profile_user_bio);
        }
    }

    void getUserDataFromCache(){
        SharedPreferences userDetails = context.getSharedPreferences(context.getString(R.string.user_info_file), Context.MODE_PRIVATE);

        userId = userDetails.getString(context.getString(R.string.user_info_file_userID), "");
        userName = userDetails.getString(context.getString(R.string.user_info_file_name), "");
        userEmail = userDetails.getString(context.getString(R.string.user_info_file_email), "");
        userImage = userDetails.getString(context.getString(R.string.user_info_file_profileImage), "");
    }

}
