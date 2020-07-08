package com.example.chapter3.homework;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chapter3.homework.recyclerView.FriendListAdapter;
import com.example.chapter3.homework.recyclerView.FriendsDataSet;

public class PlaceholderFragment extends Fragment {

    private static String TAG = "PlaceholderFragment";
    private LottieAnimationView animation;
    private RecyclerView friendsList;
    FriendListAdapter adapter;
    LinearLayoutManager linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_placeholder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件

        friendsList = view.findViewById(R.id.friendList);
        animation = view.findViewById(R.id.animation);

        int ID = getResources().getIdentifier("circle","mipmap","com.example.chapter3.homework");
        Log.d(TAG,"Found ID : " + Integer.toString(ID));
        adapter = new FriendListAdapter(FriendsDataSet.getData(ID));
        linear = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        friendsList.setHasFixedSize(true);
        friendsList.setLayoutManager(linear);
        friendsList.setAdapter(adapter);

        animation.playAnimation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if(view != null) view.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入

                ObjectAnimator animatorLoad = ObjectAnimator.ofFloat(animation,"alpha",1f,0f);
                ObjectAnimator animatorList = ObjectAnimator.ofFloat(friendsList,"alpha",0f,1f);
                animatorLoad.setDuration(500);
                animatorList.setDuration(500);
                friendsList.setAlpha(0f);
                friendsList.setVisibility(View.VISIBLE);

                animatorLoad.start();
                animatorList.start();

                View view = getView();
                if(view != null) view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation.pauseAnimation();
                        animation.setVisibility(View.INVISIBLE);
                    }
                },500);
            }
        }, 5000);
    }

}
