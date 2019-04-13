package com.csming.percent.main.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csming.percent.R;
import com.csming.percent.common.Contacts;
import com.csming.percent.common.LoadingFragment;
import com.csming.percent.common.widget.statuslayout.StatusLayout;
import com.csming.percent.main.adapter.PlanListAdapter;
import com.csming.percent.main.viewmodel.MainViewModel;
import com.csming.percent.record.RecordsActivity;

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
public class PlanFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private RecyclerView mRvPlans;
    private LinearLayoutManager mLinearLayoutManager;
    private StatusLayout mStatusLayout;
    private PlanListAdapter mAdapterPlans;
    private AlertDialog.Builder mDeleteDialogBuilder;
    private MainViewModel mMainViewModel;

    public static PlanFragment getInstance() {
        return new PlanFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plans, container, false);
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

//    @Override
//    public void onBackPressed() {
//        if (mAdapterPlans == null || !mAdapterPlans.clearDeleteState()) {
//            super.onBackPressed();
//        }
//    }

    private void initView(View view) {
        mStatusLayout = view.findViewById(R.id.status_layout);
        mRvPlans = view.findViewById(R.id.rv_plans);

        SimpleItemAnimator animator = (SimpleItemAnimator) mRvPlans.getItemAnimator();
        if (animator != null) {
            animator.setSupportsChangeAnimations(false);
        }

        mAdapterPlans = new PlanListAdapter();

        mStatusLayout.setEmptyMessageView(R.string.plans_empty, null, null);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRvPlans.setLayoutManager(mLinearLayoutManager);
        mRvPlans.setAdapter(mAdapterPlans);

        mAdapterPlans.setOnItemClickListener((view1, position, plan) -> {
            startActivity(RecordsActivity.getIntent(getActivity(), plan.getId()));
            getActivity().overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
        });

        mAdapterPlans.setOnItemDeleteClickListener((v, position, plan) -> {
            showDeleteDialog(plan.getId());
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMainViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel.class);

        mMainViewModel.findPlans().observe(getActivity(), plans -> {
            if (plans.size() > 0) {
                mAdapterPlans.setData(plans);
                mStatusLayout.showNormalView();
            } else {
                mStatusLayout.showEmptyMessageView();
            }
        });

        mMainViewModel.getDeletePlanState().observe(getActivity(), state -> {
            switch (state) {
                case Contacts.STATE_NORMAL: {
                    LoadingFragment.hidden();
                    break;
                }
                case Contacts.STATE_SUCCESS: {
                    LoadingFragment.hidden();
                    break;
                }
            }
        });
    }

    private void showDeleteDialog(final int planId) {
        mMainViewModel.setDeletePlan(planId);
        if (mDeleteDialogBuilder == null) {
            mDeleteDialogBuilder = new AlertDialog.Builder(getActivity());
            mDeleteDialogBuilder.setPositiveButton(R.string.delete_sure, (dialogInterface, i) -> {
                if (mMainViewModel != null) {
                    LoadingFragment.show(getActivity().getSupportFragmentManager());
                    mMainViewModel.deletePlan();
                }
            });
            mDeleteDialogBuilder.setNegativeButton(R.string.delete_cancel, (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            mDeleteDialogBuilder.setMessage(R.string.delete_dialog_message);
        }

        mDeleteDialogBuilder.show();
    }
}
