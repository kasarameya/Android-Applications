package com.example.a968660.flickrgallery;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoGalleryFragment extends Fragment {


    private static String TAG = "PhotoFragment";
    @BindView(R.id.rv_photo_gallery_frag)
    RecyclerView mRvPhotoGalleryFrag;
    List<GalleryBean> mBeanList = new ArrayList<>();

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "New Broadcast received", Toast.LENGTH_SHORT).show();
            setResultCode(Activity.RESULT_CANCELED);
        }
    };

    public PhotoGalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(FlickrService.LOCAL_BROADCAST);
        getActivity().registerReceiver(mReceiver, intentFilter, FlickrService.PERM_PRIVATE, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        /*Intent i = FlickrService.newIntent(getActivity());
        getActivity().startService(i);*/
        updatePhotoList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_photo_gallery,menu);

        MenuItem menuItem = menu.findItem(R.id.sv_search_box);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: "+query);
                searchedPreferences.storeInSharedPref(getActivity(),query);
                updatePhotoList();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: "+ newText);
                return true;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchedPreferences.getPrefSearchQuery(getActivity());
                searchView.setQuery(query,false);
            }
        });
        MenuItem startPolling = menu.findItem(R.id.menu_start_polling);
        if (FlickrService.hasAlarmStarted(getActivity()))
            startPolling.setTitle("STOP POLLING");
        else
            startPolling.setTitle("START POLLING");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_clear_search :
                searchedPreferences.storeInSharedPref(getActivity(),null);
                updatePhotoList();
                return true;
            case R.id.menu_start_polling:
                FlickrService.setServicedAlarm(getActivity(), !FlickrService.hasAlarmStarted(getActivity()));
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        ButterKnife.bind(this, view);
        mRvPhotoGalleryFrag.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mRvPhotoGalleryFrag.setAdapter(new PhotoAdapter(mBeanList));
        }
    }

    private void updatePhotoList() {
        String query = searchedPreferences.getPrefSearchQuery(getActivity());
        new getRemoteItems(query).execute();
    }

    private class getRemoteItems extends AsyncTask<Void, Integer, List<GalleryBean>> {
        ProgressDialog mProgressDialog;

        private String mQuery;

        public getRemoteItems(String query) {
            mQuery = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Loading");
            mProgressDialog.show();
        }

        @Override
        protected List<GalleryBean> doInBackground(Void... params) {
            DataDownloader dataDownloader = new DataDownloader();
                      /* String downloadedData = dataDownloader.getUrlString("https://www.bignerdranch.com");
            Log.d("kk", downloadedData);*/
            //return dataDownloader.getItemsFromFlickr();
            if (mQuery == null){
                return dataDownloader.fetchPhotos();
            }else
                return dataDownloader.searchPhotos(mQuery);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<GalleryBean> galleryBeen) {
            super.onPostExecute(galleryBeen);
            mProgressDialog.hide();
            mBeanList = galleryBeen;
            setupAdapter();
        }
    }

    ////////////////////////////////////RecyclerView Code//////////////////////////////////////////
    private class PhotoHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        public void bindPhoto(GalleryBean galleryBean) {
            mTextView.setText(galleryBean.toString());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        List<GalleryBean> mBeanList;

        public PhotoAdapter(List<GalleryBean> beanList) {
            mBeanList = beanList;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(getActivity());
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryBean galleryBean = mBeanList.get(position);
            holder.bindPhoto(galleryBean);
        }

        @Override
        public int getItemCount() {
            return mBeanList.size();
        }
    }

}
