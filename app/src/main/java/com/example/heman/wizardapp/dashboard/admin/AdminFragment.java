package com.example.heman.wizardapp.dashboard.admin;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.Utils;
import com.example.heman.wizardapp.androidutils.service.offline.OfflineManager;
import com.sap.cloud.mobile.odata.offline.OfflineODataException;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAdminFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAdminFragmentInteractionListener mListener;

    Button buttonUpdateSystem;
    ProgressBar progressBar;
    View view;

    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_admin, container, false);
        buttonUpdateSystem = view.findViewById(R.id.buttonUpdateSystem);
        progressBar = view.findViewById(R.id.progressBar);
        buttonUpdateSystem.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAdminFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAdminFragmentInteractionListener) {
            mListener = (OnAdminFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAdminFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonUpdateSystem:
                new UploadStoreAsyncTask().execute();
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
    public interface OnAdminFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAdminFragmentInteraction(Uri uri);
    }
    public class UploadStoreAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OfflineManager.getInstance(getContext()).initializeOfflineStoreUpload(new OfflineManager.IOfflineStoreUploadListener() {
                @Override
                public void offlineStoreUploadSuccess(boolean isSuccess) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
//                        Utils.returnTopViewSnackBar(getContext(),buttonUpdateSystem, Snackbar.LENGTH_INDEFINITE,"Upload Success",false);
                        Utils.toastAMessage("Upload Success",getContext());
                    });

                }

                @Override
                public void offlineStoreUploadFailed(boolean isFailed, OfflineODataException error) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
//                        Utils.returnTopViewSnackBar(getContext(),buttonUpdateSystem, Snackbar.LENGTH_INDEFINITE,"Upload Failed",true);
                        Utils.toastAMessage("Upload Failed",getContext());
                    });
                }
            }).uploadStore();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
