package com.example.heman.wizardapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heman.wizardapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDateFilterBottomSheetFragmentCallBack} interface
 * to handle interaction events.
 * Use the {@link DateFilterBottomSheet#} factory method to
 * create an instance of this fragment.
 */
public class DateFilterBottomSheet extends BottomSheetDialogFragment implements IDateFilterView, View.OnClickListener {
    // interface
    private OnDateFilterBottomSheetFragmentCallBack<List<?>> fragmentCallBack;
    // final constants

    // android components
    View view;
    TextView textViewHeader,textViewDate;
    Button buttonFromDate,buttonToDate,buttonGetRecords;
    ProgressBar progressBar;
    ImageButton imageButtonDismiss;
    // variables

    public DateFilterBottomSheet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DateFilterBottomSheet.
     */
    // TODO: Rename and change types and number of parameters
    public static DateFilterBottomSheet getInstance() {
        DateFilterBottomSheet fragment = new DateFilterBottomSheet();
       /* Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_date_filter_bottom_sheet, container, false);
        initializeUI();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateFilterBottomSheetFragmentCallBack) {
            fragmentCallBack = (OnDateFilterBottomSheetFragmentCallBack<List<?>>) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDateFilterBottomSheetFragmentCallBack");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallBack = null;
    }

    @Override
    public void initializeUI() {
        textViewHeader = view.findViewById(R.id.textViewHeader);
        buttonFromDate = view.findViewById(R.id.buttonFromDate);
        buttonToDate = view.findViewById(R.id.buttonToDate);
        buttonGetRecords = view.findViewById(R.id.buttonGetRecords);
        textViewDate = view.findViewById(R.id.textViewDate);
        progressBar = view.findViewById(R.id.progressBar);
        imageButtonDismiss = view.findViewById(R.id.imageButtonDismiss);
        setCancelable(false);

        initializeObjects();
        initializeListeners();

        /*if (getArguments() != null) {
            query =getArguments().getString(Constants.QUERY);
            if (getArguments().getString(Constants.LeadsQuery)!=null) {
                LeadsQuery =getArguments().getString(Constants.LeadsQuery);
            }
        }*/
    }

    @Override
    public void initializeObjects() {
    }

    @Override
    public void initializeListeners() {
        buttonFromDate.setOnClickListener(this);
        buttonToDate.setOnClickListener(this);
        buttonGetRecords.setOnClickListener(this);
        imageButtonDismiss.setOnClickListener(this);
    }

    @Override
    public void showProgress() {
        if (getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void hideProgress() {
        if (getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showMessage(final String message) {
        if (getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void refreshDates() {

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonDismiss:
                dismiss();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDateFilterBottomSheetFragmentCallBack<T> {
        // TODO: Update argument type and name
        void onFragmentInteraction(T object);
    }


    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
}
