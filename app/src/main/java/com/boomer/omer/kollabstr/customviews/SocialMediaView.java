package com.boomer.omer.kollabstr.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boomer.omer.kollabstr.R;
import com.boomer.omer.kollabstr.core.SocialMediaType;


/**
 * Created by Omer on 7/19/2016.
 */
public class SocialMediaView extends RelativeLayout{

    private RelativeLayout mMainChild;
    private LayoutInflater mInflater;

    private SocialMediaType mSocialMediaType;
    private int mImpact;

    private ImageView mSocialMediaImage;
    private TextView mFollowersTextView;
    private TextView mImpactTextView;


    public SocialMediaView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public SocialMediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public SocialMediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init(){
        mMainChild = (RelativeLayout) mInflater.inflate(R.layout.socialmedia_selection_item,this,true);
        mSocialMediaImage  = (ImageView)mMainChild.findViewById(R.id.socialmedia_selection_image);
        mFollowersTextView = (TextView)mMainChild.findViewById(R.id.socialmedia_follower_count);
        mImpactTextView    = (TextView)mMainChild.findViewById(R.id.socialmedia_impact);
    }

    public void setSocialMediaType(SocialMediaType socialMediaType){
        mSocialMediaType = socialMediaType;
        mImpactTextView.setText(socialMediaType.toString() + " " +getResources().getString(R.string.setup_profile_impact)+":");
        mSocialMediaImage.setImageResource(socialMediaType.getIcon());
    }

    public SocialMediaType getSocialMediaType(){return mSocialMediaType;}

    public void setImpact(int impact){
        mImpact = impact;
        mFollowersTextView.setText(Integer.toString(impact));
    }

    public int getmImpact(){return mImpact;}


    @Override
    public void setOnClickListener(OnClickListener l) {
        mMainChild.setOnClickListener(l);
    }

    @Override
    public boolean hasOnClickListeners() {
        return mMainChild.hasOnClickListeners();
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        mMainChild.setOnLongClickListener(l);
    }

    @Override
    public void setOnDragListener(OnDragListener l) {
        mMainChild.setOnDragListener(l);
    }
}
