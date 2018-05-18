package com.ritvi.kaajneeti.fragment.analyze;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.SuggestionAnalyzeAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.analyze.SuggestionPOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 31-03-2018.
 */

public class SuggestionListFragment extends Fragment {

    @BindView(R.id.rv_complaints)
    RecyclerView rv_complaints;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_suggestion_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        callAPI();

//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.i(TagUtils.getTag(), "keyCode: " + keyCode);
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                    Log.i(TagUtils.getTag(), "onKey Back listener is working!!!");
//                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    return true;
//                }
//                return false;
//            }
//        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));

        new WebServiceBaseResponseList<SuggestionPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<SuggestionPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO responseListPOJO) {
                suggestionPOJOS.clear();
                try {
                    if (responseListPOJO.isSuccess()) {
                        suggestionPOJOS.addAll(responseListPOJO.getResultList());
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                suggestionAnalyzeAdapter.notifyDataSetChanged();
            }
        }, SuggestionPOJO.class, "call_complaint_list_api", true).execute(WebServicesUrls.SUGGESTION_LIST);
    }


    List<SuggestionPOJO> suggestionPOJOS = new ArrayList<>();
    SuggestionAnalyzeAdapter suggestionAnalyzeAdapter;

    public void attachAdapter() {

        suggestionAnalyzeAdapter = new SuggestionAnalyzeAdapter(getActivity(), this, suggestionPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(suggestionAnalyzeAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }


}
