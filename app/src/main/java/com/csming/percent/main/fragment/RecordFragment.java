package com.csming.percent.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csming.percent.R;
import com.csming.percent.common.widget.statuslayout.StatusLayout;
import com.csming.percent.main.adapter.RecordListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import dagger.android.support.DaggerFragment;

/**
 * @author Created by csming on 2018/11/21.
 */
public class RecordFragment extends DaggerFragment {

    private StatusLayout mStatusLayout;
    private RecyclerView mRvPlans;
    private LinearLayoutManager mLinearLayoutManager;
    private RecordListAdapter mAdapterRecords;

    @Inject
    ViewModelProvider.Factory factory;

    private MainViewModel mMainViewModel;

    public static RecordFragment getInstance() {
        return new RecordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_records, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView(View view) {
        mStatusLayout = view.findViewById(R.id.status_layout);
        mRvPlans = view.findViewById(R.id.rv_records);

        mStatusLayout.setEmptyMessageView(R.string.today_records_empty, null, null);

        SimpleItemAnimator animator = (SimpleItemAnimator) mRvPlans.getItemAnimator();
        if (animator != null) {
            animator.setSupportsChangeAnimations(false);
        }

        mAdapterRecords = new RecordListAdapter();

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterRecords);

        mAdapterRecords.setOnFinishChangeListener((v, position, record, finish) -> {
            mMainViewModel.updateRecordFinish(record, finish);
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);

        mMainViewModel.findRecordsToday().observe(getActivity(), records -> {
            if (records.size() > 0) {
                mAdapterRecords.setData(records);
                mStatusLayout.showNormalView();
            } else {
                mStatusLayout.showEmptyMessageView();
            }
        });
    }
}
