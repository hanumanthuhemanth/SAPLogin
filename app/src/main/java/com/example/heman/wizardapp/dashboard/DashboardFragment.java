package com.example.heman.wizardapp.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.heman.wizardapp.Customer;
import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.AdapterInterface;
import com.example.heman.wizardapp.androidutils.SimpleRecyclerViewAdapter;
import com.example.heman.wizardapp.androidutils.Utils;
import com.example.heman.wizardapp.androidutils.service.offline.OfflineManager;
import com.sap.cloud.mobile.odata.offline.OfflineODataException;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements IDashboardView, View.OnClickListener, AdapterInterface<Object>, OfflineManager.IOfflineStoreListener {
    //final constants

    //android components
    Button buttonStartAttendance;
    View view;
    OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    SimpleRecyclerViewAdapter<?> recyclerViewAdapter;
    //sap components

    //variables
    DashboardPresenter presenter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_dashboard, container, false);
        initializeUI();
        return view;
    }
    @Override
    public void initializeUI() {
        buttonStartAttendance = view.findViewById(R.id.buttonStartAttendance);
        recyclerView = view.findViewById(R.id.recyclerView);
        initializeListeners();
        initializeObjects();
        initializeRecyclerView();
    }

    @Override
    public void initializeListeners() {
        buttonStartAttendance.setOnClickListener(this);
    }

    @Override
    public void initializeObjects() {
        presenter = new DashboardPresenter(getContext(), (AppCompatActivity) getActivity(),this);
        presenter.loadAsyncTask();
    }

    @Override
    public void initializeRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new SimpleRecyclerViewAdapter<>(getContext(),R.layout.recycler_view_customers,this,recyclerView,null);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void showMessage(String message, int type) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void refreshRecyclerView(List list, int position) {
        recyclerViewAdapter.refreshAdapter(list);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
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
            case R.id.buttonStartAttendance:

                break;
        }
    }

    @Override
    public void onItemClick(Object object, View view, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position, View view) {
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, Object object) {
        if (object instanceof Customer){
            Customer customer = (Customer) object;
            ((CustomerViewHolder)viewHolder).textViewName.setText(String.format("%s%s", customer.getFirstName(), customer.getCustomerID()));
            ((CustomerViewHolder)viewHolder).textViewID.setText(customer.getCustomerID());
        }

    }

    @Override
    public void offlineStoreOpened(boolean isSuccess) {
        Log.d("offline store","open success");
        Utils.toastAMessage("offline store opened",getContext());
    }

    @Override
    public void offlineStoreFailed(boolean isFailed, OfflineODataException error) {
        Log.d("offline store","open failed");
        Utils.toastAMessage("offline store failed "+error.toString(),getContext());
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Object object);
    }
}
