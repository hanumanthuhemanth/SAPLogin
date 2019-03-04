package com.example.heman.wizardapp.dashboard.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heman.wizardapp.R;
import com.example.heman.wizardapp.androidutils.AdapterInterface;
import com.example.heman.wizardapp.androidutils.IRootView;
import com.example.heman.wizardapp.androidutils.SimpleRecyclerViewAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHomeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements IRootView,IHomeView, AdapterInterface<HomeBean> {
    //final constants

    //android components
    View view;
    RecyclerView recyclerView;
    SimpleRecyclerViewAdapter<HomeBean> recyclerViewAdapter;
    //sap components

    //variables
    private OnHomeFragmentInteractionListener mListener;
    HomePresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        initializeUI();
        return view;
    }



    @Override
    public void initializeUI() {
        recyclerView = view.findViewById(R.id.recyclerView);
        initializeListeners();
        initializeObjects();
        initializeRecyclerView();
    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void initializeObjects() {
        presenter = new HomePresenter(getContext(),getActivity(),this);
    }

    @Override
    public void initializeRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerViewAdapter = new SimpleRecyclerViewAdapter<>(getContext(),R.layout.recycler_view_home,HomeFragment.this,recyclerView,null);
        recyclerView.setAdapter(recyclerViewAdapter);
        presenter.getAppList();
    }

    @Override
    public void showMessage(int type, String message, boolean validity) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void refreshRecyclerView(List list, int position) {
        if (list!=null) {
            recyclerViewAdapter.refreshAdapter(list);
        }
    }

    @Override
    public void onItemClick(HomeBean object, View view, int position) {
        startActivity(object.getIntent());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position, View view) {
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, HomeBean object) {
        ((HomeViewHolder)viewHolder).textViewName.setText(object.getName());
        ((HomeViewHolder)viewHolder).imageViewIcon.setImageResource(object.getImageResource());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onHomeFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
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
    public interface OnHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onHomeFragmentInteraction(Uri uri);
    }
}
