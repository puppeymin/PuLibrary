//
// DO NOT EDIT THIS FILE.Generated using AndroidAnnotations 3.3.1.
//  You can create a larger work that contains this file and distribute that work under terms of your choice.
//


package com.example.badmintonmanager.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.example.badmintonmanager.R.id;
import com.example.badmintonmanager.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;


/**
 * We use @SuppressWarning here because our java code
 * generator doesn't know that there is no need
 * to import OnXXXListeners from View as we already
 * are in a View.
 * 
 */
@SuppressWarnings("unused")
public final class OpenItemView_
    extends OpenItemView
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public OpenItemView_(Context context) {
        super(context);
        init_();
    }

    public OpenItemView_(Context context, AttributeSet attrs) {
        super(context, attrs);
        init_();
    }

    public static OpenItemView build(Context context) {
        OpenItemView_ instance = new OpenItemView_(context);
        instance.onFinishInflate();
        return instance;
    }

    /**
     * The mAlreadyInflated_ hack is needed because of an Android bug
     * which leads to infinite calls of onFinishInflate()
     * when inflating a layout with a parent and using
     * the <merge /> tag.
     * 
     */
    @Override
    public void onFinishInflate() {
        if (!alreadyInflated_) {
            alreadyInflated_ = true;
            inflate(getContext(), layout.list_item_open, this);
            onViewChangedNotifier_.notifyViewChanged(this);
        }
        super.onFinishInflate();
    }

    private void init_() {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public static OpenItemView build(Context context, AttributeSet attrs) {
        OpenItemView_ instance = new OpenItemView_(context, attrs);
        instance.onFinishInflate();
        return instance;
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        tvMember = ((TextView) hasViews.findViewById(id.tv_member));
        tvArea = ((TextView) hasViews.findViewById(id.tv_area));
        tvManager = ((TextView) hasViews.findViewById(id.tv_manager));
        tvDate = ((TextView) hasViews.findViewById(id.tv_date));
        {
            View view = hasViews.findViewById(id.btn_delect);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        OpenItemView_.this.delect(view);
                    }

                }
                );
            }
        }
    }

}
