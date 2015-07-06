//
// DO NOT EDIT THIS FILE.Generated using AndroidAnnotations 3.3.1.
//  You can create a larger work that contains this file and distribute that work under terms of your choice.
//


package com.example.badmintonmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.example.badmintonmanager.R.layout;
import com.example.view.PuImageButton;
import de.greenrobot.dao.Match;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MatchManagerFragment_
    extends com.example.badmintonmanager.MatchManagerFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;
    public final static String MATCH_DATA_ARG = "matchData";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_match_manager, container, false);
        }
        return contentView_;
    }

    @Override
    public void onDestroyView() {
        contentView_ = null;
        super.onDestroyView();
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        injectFragmentArguments_();
        init();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static MatchManagerFragment_.FragmentBuilder_ builder() {
        return new MatchManagerFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        fbEdit = ((FlatButton) hasViews.findViewById(com.example.badmintonmanager.R.id.fb_edit));
        etArea = ((FlatEditText) hasViews.findViewById(com.example.badmintonmanager.R.id.et_area));
        listView = ((ListView) hasViews.findViewById(com.example.badmintonmanager.R.id.list_view));
        etPrice = ((FlatEditText) hasViews.findViewById(com.example.badmintonmanager.R.id.et_price));
        fbDelete = ((FlatButton) hasViews.findViewById(com.example.badmintonmanager.R.id.fb_delect));
        etManager = ((FlatEditText) hasViews.findViewById(com.example.badmintonmanager.R.id.et_manager));
        etTime = ((FlatEditText) hasViews.findViewById(com.example.badmintonmanager.R.id.et_time));
        pbBack = ((PuImageButton) hasViews.findViewById(com.example.badmintonmanager.R.id.pb_back));
        fbSave = ((FlatButton) hasViews.findViewById(com.example.badmintonmanager.R.id.fb_save));
        if (fbEdit!= null) {
            fbEdit.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    MatchManagerFragment_.this.edit(view);
                }

            }
            );
        }
        {
            View view = hasViews.findViewById(com.example.badmintonmanager.R.id.ll_time);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MatchManagerFragment_.this.chooseTime(view);
                    }

                }
                );
            }
        }
        if (pbBack!= null) {
            pbBack.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    MatchManagerFragment_.this.back(view);
                }

            }
            );
        }
        {
            View view = hasViews.findViewById(com.example.badmintonmanager.R.id.fb_complete);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MatchManagerFragment_.this.complete(view);
                    }

                }
                );
            }
        }
        if (fbSave!= null) {
            fbSave.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    MatchManagerFragment_.this.save(view);
                }

            }
            );
        }
        if (fbDelete!= null) {
            fbDelete.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    MatchManagerFragment_.this.delete(view);
                }

            }
            );
        }
        initView();
    }

    private void injectFragmentArguments_() {
        Bundle args_ = getArguments();
        if (args_!= null) {
            if (args_.containsKey(MATCH_DATA_ARG)) {
                matchData = ((Match) args_.getSerializable(MATCH_DATA_ARG));
            }
        }
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<MatchManagerFragment_.FragmentBuilder_, com.example.badmintonmanager.MatchManagerFragment>
    {


        @Override
        public com.example.badmintonmanager.MatchManagerFragment build() {
            MatchManagerFragment_ fragment_ = new MatchManagerFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }

        public MatchManagerFragment_.FragmentBuilder_ matchData(Match matchData) {
            args.putSerializable(MATCH_DATA_ARG, matchData);
            return this;
        }

    }

}