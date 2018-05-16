package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.TagPeopleActivity;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class TagPeopleAdapter extends RecyclerView.Adapter<TagPeopleAdapter.ViewHolder> {
    private List<UserProfilePOJO> items;
    Activity activity;
    Fragment fragment;
    List<UserProfilePOJO> taggedUserProfilePOJOS;

    public TagPeopleAdapter(Activity activity, Fragment fragment, List<UserProfilePOJO> items, List<UserProfilePOJO> taggedUserProfilePOJOS) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.taggedUserProfilePOJOS = taggedUserProfilePOJOS;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_people_tag_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_user_name.setText(items.get(position).getFirstName() + " " + items.get(position).getLastName());
        holder.tv_email.setText(items.get(position).getEmail());

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .into(holder.cv_profile_pic);


        holder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        boolean is_checked=false;
        for(UserProfilePOJO userProfilePOJO:taggedUserProfilePOJOS){
            if(userProfilePOJO.getUserProfileId().equalsIgnoreCase(items.get(position).getUserProfileId())){
                is_checked=true;
            }
        }

        holder.checked_user.setChecked(is_checked);

        holder.checked_user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(activity instanceof TagPeopleActivity){
                        TagPeopleActivity tagPeopleActivity= (TagPeopleActivity) activity;
                        tagPeopleActivity.addUser(items.get(position));
                    }
                }else{
                    if(activity instanceof TagPeopleActivity){
                        TagPeopleActivity tagPeopleActivity= (TagPeopleActivity) activity;
                        tagPeopleActivity.removeUser(items.get(position));
                    }
                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView cv_profile_pic;
        public TextView tv_user_name;
        public TextView tv_email;
        public LinearLayout ll_user;
        public CheckBox checked_user;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            ll_user = itemView.findViewById(R.id.ll_user);
            checked_user = itemView.findViewById(R.id.checked_user);
        }
    }
}
