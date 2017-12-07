package com.example.admin.instagramgalery.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.admin.instagramgalery.R;
import com.example.admin.instagramgalery.adapter.GridAdapter;
import com.example.admin.instagramgalery.galery.GridAdapterListener;
import com.example.admin.instagramgalery.modules.LoadMoreModule;
import com.example.admin.instagramgalery.modules.LoadMoreModuleDelegate;
import com.example.admin.instagramgalery.utils.Session;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragementgalery extends Fragment implements GridAdapterListener, LoadMoreModuleDelegate {

    private  Context context ;
    public static Fragementgalery newInstance() {
        return new Fragementgalery();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentgalery, container, false);
        ButterKnife.bind(this, v);
        initViews();
        Log.e("surya","hello"+mFiles.size());

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
          if(mFiles.size()==0){
          mGridAdapter.clearItems();
          }else{
          mGridAdapter.setItems(getRangePets());
          }

    }

    @BindView(R.id.mGalleryRecyclerView)
    RecyclerView mGalleryRecyclerView;
    @BindView(R.id.mPreview)
    ImageView mPreview;
    @BindView(R.id.mAppBarContainer)
    AppBarLayout mAppBarContainer;

    private static final String EXTENSION_JPG = ".jpg";
    private static final String EXTENSION_JPEG = ".jpeg";
    private static final String EXTENSION_PNG = ".png";
    private static final int PREVIEW_SIZE = 800;
    private static final int MARGING_GRID = 2;
    private static final int RANGE = 20;

    private Session mSession = Session.getInstance();
    private LoadMoreModule mLoadMoreModule = new LoadMoreModule();
    private GridAdapter mGridAdapter;
    private ArrayList<File> mFiles;
    private boolean isLoading = false;
    private int mOffset;
    private boolean isFirstLoad = true;

    private void initViews() {
        if (isFirstLoad) {
            mGridAdapter = new GridAdapter(getContext());
        }
        mGridAdapter.setListener(this);
        mGalleryRecyclerView.setAdapter(mGridAdapter);
        mGalleryRecyclerView.setHasFixedSize(true);
        mGalleryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mGalleryRecyclerView.addItemDecoration(addItemDecoration());
        mLoadMoreModule.LoadMoreUtils(mGalleryRecyclerView, this, getContext());
        mOffset = 0;
        fetchMedia();

        for(File file:mFiles){
            Log.e("surya","hello"+mFiles.size());
        }
    }

    private RecyclerView.ItemDecoration addItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, RecyclerView.State state) {
                outRect.left = MARGING_GRID;
                outRect.right = MARGING_GRID;
                outRect.bottom = MARGING_GRID;
                if (parent.getChildLayoutPosition(view) >= 0 && parent.getChildLayoutPosition(view) <= 3) {
                    outRect.top = MARGING_GRID;
                }
            }
        };
    }

    private void fetchMedia() {
        mFiles = new ArrayList<>();
        File dirDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        parseDir(dirDownloads);
        File dirDcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        parseDir(dirDcim);
        File dirPictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        parseDir(dirPictures);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            File dirDocuments = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            parseDir(dirDocuments);
        }

        if (mFiles.size() > 0) {
            displayPreview(mFiles.get(0));
            mGridAdapter.setItems(getRangePets());
        }
        isFirstLoad = false;
    }

    private List<File> getRangePets() {
        if (mOffset < mFiles.size()) {
            if ((mOffset + RANGE) < mFiles.size()) {
                return mFiles.subList(mOffset, mOffset + RANGE);
            } else if ((mOffset + RANGE) >= mFiles.size()) {
                return mFiles.subList(mOffset, mFiles.size());
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    private void parseDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            parseFileList(files);
        }
    }

    private void parseFileList(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                if (!file.getName().toLowerCase().startsWith(".")) {
                    parseDir(file);
                }
            } else {
                if (file.getName().toLowerCase().endsWith(EXTENSION_JPG)
                        || file.getName().toLowerCase().endsWith(EXTENSION_JPEG)
                        || file.getName().toLowerCase().endsWith(EXTENSION_PNG)) {
                    mFiles.add(file);

                }
            }
        }
    }

    private void loadNext() {
        if (!isLoading) {
            isLoading = true;
            mOffset += RANGE;
            List<File> files = new ArrayList<>();
            files.addAll(getRangePets());
            if (files.size() > 0) {
                mGridAdapter.addItems(files, mGridAdapter.getItemCount());
            }
            isLoading = false;
        }
    }

    private void displayPreview(File file) {
        Picasso.with(getContext())
                .load(Uri.fromFile(file))
                .noFade()
                .noPlaceholder()
                .resize(PREVIEW_SIZE, PREVIEW_SIZE)
                .centerCrop()
                .into(mPreview);
    }

    @Override
    public void onPause() {
        super.onPause();
        Picasso.with(getContext()).cancelRequest(mPreview);
    }

    @Override
    public void onClickMediaItem(File file) {
        displayPreview(file);
        mSession.setFileToUpload(file);
        mAppBarContainer.setExpanded(true, true);
    }
    @Override
    public void shouldLoadMore() {
        loadNext();
    }

    @Override
    public void onResume() {

        super.onResume();
    }
}
