package com.boomer.omer.kollabstr.customviews;

import android.content.Context;
import android.content.res.TypedArray;
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

    private boolean mListImpact = true;
    private int typeVal;


    public SocialMediaView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init(context,null);
    }

    public SocialMediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init(context,attrs);
    }

    public SocialMediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        mMainChild = (RelativeLayout) mInflater.inflate(R.layout.socialmedia_selection_item,this,true);
        mSocialMediaImage  = (ImageView)mMainChild.findViewById(R.id.socialmedia_selection_image);
        mFollowersTextView = (TextView)mMainChild.findViewById(R.id.socialmedia_follower_count);
        mImpactTextView    = (TextView)mMainChild.findViewById(R.id.socialmedia_impact);

        if(attrs!=null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.SocialMediaView,
                    0, 0);

            try {
               mListImpact = a.getBoolean(R.styleable.SocialMediaView_listImpact,true);
               int impact = a.getInteger(R.styleable.SocialMediaView_impact,0);
               typeVal = a.getInteger(R.styleable.SocialMediaView_type,0);

               if(mListImpact){
                   mImpact = impact;
                   mFollowersTextView.setText(Integer.toString(impact));
               }else{
                   mFollowersTextView.setVisibility(GONE);
               }

              doTypeAdjustments(typeVal);



            } finally {
                a.recycle();
            }
        }
    }

    private void doTypeAdjustments(int type){
        switch (type){
            case 0:
                mSocialMediaImage.setImageResource(SocialMediaType.FACEBOOK.getIcon());
                setBackgroundResource(R.color.facebook_icon_blue);
                if(mListImpact){mImpactTextView.setText(SocialMediaType.FACEBOOK.toString() + " " +getResources().getString(R.string.setup_profile_impact)+":");}
                else{mImpactTextView.setText(SocialMediaType.FACEBOOK.toString());}
                break;
            case 1:
                mSocialMediaImage.setImageResource(SocialMediaType.TWITTER.getIcon());
                setBackgroundResource(R.color.twitter_icon_teal);
                if(mListImpact){mImpactTextView.setText(SocialMediaType.TWITTER.toString() + " " +getResources().getString(R.string.setup_profile_impact)+":");}
                else{mImpactTextView.setText(SocialMediaType.TWITTER.toString());}
                break;
            case 2:
                mSocialMediaImage.setImageResource(SocialMediaType.INSTAGRAM.getIcon());
                setBackgroundResource(R.color.instagram_icon_gray);
                if(mListImpact){mImpactTextView.setText(SocialMediaType.INSTAGRAM.toString() + " " +getResources().getString(R.string.setup_profile_impact)+":");}
                else{mImpactTextView.setText(SocialMediaType.INSTAGRAM.toString());}
                break;
            case 3:
                mSocialMediaImage.setImageResource(SocialMediaType.YOUTUBE.getIcon());
                setBackgroundResource(R.color.youtube_icon_red);
                if(mListImpact){mImpactTextView.setText(SocialMediaType.YOUTUBE.toString() + " " +getResources().getString(R.string.setup_profile_impact)+":");}
                else{mImpactTextView.setText(SocialMediaType.YOUTUBE.toString());}
                break;
        }
    }

    public void setSocialMediaType(SocialMediaType socialMediaType){
        mSocialMediaType = socialMediaType;
        if(mListImpact){mImpactTextView.setText(socialMediaType.toString() + " " +getResources().getString(R.string.setup_profile_impact)+":");}
        else{mImpactTextView.setText(socialMediaType.toString());}
        doTypeAdjustments(socialMediaType.getValue());
        invalidate();
    }

    public SocialMediaType getSocialMediaType(){return mSocialMediaType;}

    public void setImpact(int impact){
        mImpact = impact;
        mFollowersTextView.setText(Integer.toString(impact));
        invalidate();
    }

    public int getImpact(){
        if(!mListImpact){return 0;}
        return mImpact;}


}
