package com.pfl.lib_common.base;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * Fragment 第一次可见状态应该在哪里通知用户 在 onResume 以后？
 */
public abstract class LazyLoadBaseFragment extends BaseFragment {

    private boolean mIsFirstVisible = true;
    private boolean isViewCreated = false;
    private boolean currentVisibleState = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 对于默认 tab 和 间隔 checked tab 需要等到 isViewCreated = true 后才可以通过此通知用户可见
        // 这种情况下第一次可见不是在这里通知 因为 isViewCreated = false 成立,等从别的界面回到这里后会使用 onFragmentResume 通知可见
        // 对于非默认 tab mIsFirstVisible = true 会一直保持到选择则这个 tab 的时候，因为在 onActivityCreated 会返回 false
        if (isViewCreated) {
            if (isVisibleToUser && !currentVisibleState) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isViewCreated = true;
        // !isHidden() 默认为 true  在调用 hide show 的时候可以使用
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(getClass().getSimpleName(), getClass().getSimpleName() + "  onHiddenChanged dispatchChildVisibleState  hidden " + hidden);

        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstVisible) {
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 当前 Fragment 包含子 Fragment 的时候 dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见
        // 子 fragment 走到这里的时候自身又会调用一遍 ？
        if (currentVisibleState && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }


    /**
     * 统一处理 显示隐藏
     *
     * @param visible
     */
    private void dispatchUserVisibleHint(boolean visible) {
        //当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment getUserVisibleHint = true
        //但当父 fragment 不可见所以 currentVisibleState = false 直接 return 掉
        // 这里限制则可以限制多层嵌套的时候子 Fragment 的分发
        if (visible && isParentInvisible()) return;

        //此处是对子 Fragment 不可见的限制，因为 子 Fragment 先于父 Fragment回调本方法 currentVisibleState 置位 false
        // 当父 dispatchChildVisibleState 的时候第二次回调本方法 visible = false 所以此处 visible 将直接返回
        if (currentVisibleState == visible) {
            return;
        }

        currentVisibleState = visible;

        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentResume();
            dispatchChildVisibleState(true);
        } else {
            dispatchChildVisibleState(false);
            onFragmentPause();
        }
    }

    /**
     * 用于分发可见时间的时候父获取 fragment 是否隐藏
     *
     * @return true fragment 不可见， false 父 fragment 可见
     */
    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof LazyLoadBaseFragment) {
            LazyLoadBaseFragment fragment = (LazyLoadBaseFragment) parentFragment;
            return !fragment.isSupportVisible();
        } else {
            return false;
        }
    }

    private boolean isSupportVisible() {
        return currentVisibleState;
    }

    /**
     * 当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment 的唯一或者嵌套 VP 的第一 fragment 时 getUserVisibleHint = true
     * 但是由于父 Fragment 还进入可见状态所以自身也是不可见的， 这个方法可以存在是因为庆幸的是 父 fragment 的生命周期回调总是先于子 Fragment
     * 所以在父 fragment 设置完成当前不可见状态后，需要通知子 Fragment 我不可见，你也不可见，
     * <p>
     * 因为 dispatchUserVisibleHint 中判断了 isParentInvisible 所以当 子 fragment 走到了 onActivityCreated 的时候直接 return 掉了
     * <p>
     * 当真正的外部 Fragment 可见的时候，走 setVisibleHint (VP 中)或者 onActivityCreated (hide show) 的时候
     * 从对应的生命周期入口调用 dispatchChildVisibleState 通知子 Fragment 可见状态
     *
     * @param visible
     */
    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment child : fragments) {
                if (child instanceof LazyLoadBaseFragment && !child.isHidden() && child.getUserVisibleHint()) {
                    ((LazyLoadBaseFragment) child).dispatchUserVisibleHint(visible);
                }
            }
        }
    }

    public void onFragmentFirstVisible() {
        Log.e(getClass().getSimpleName(), getClass().getSimpleName() + "  对用户第一次可见");

    }

    public void onFragmentResume() {
        Log.e(getClass().getSimpleName(), getClass().getSimpleName() + "  对用户可见");
    }

    public void onFragmentPause() {
        Log.e(getClass().getSimpleName(), getClass().getSimpleName() + "  对用户不可见");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        mIsFirstVisible = true;
    }


}