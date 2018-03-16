package com.farmarket.farmarket;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.farmarket.farmarket.Adaptors.MultiCustomAdapter;
import com.farmarket.farmarket.DataType.Category;
import com.farmarket.farmarket.Models.GeneralModel;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    static public MultiCustomAdapter adapter;
    public static List<Object> albumList,albumList1;
    static SwipeRefreshLayout mSwipeRefreshLayout;
    Realm realm;
    UserViewSettingTable userViewSettingTable;
    private ShimmerFrameLayout mShimmerViewContainer;
    UserTable userTable;
    Intent intent;
    MenuItem myActionMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        albumList = (List<Object>) intent.getExtras().get("albumlist");

        Toast.makeText(getApplicationContext(),""+albumList.size(),Toast.LENGTH_LONG).show();
       // myActionMenuItem.expandActionView();

        realm = Realm.getDefaultInstance();
        userViewSettingTable = realm.where(UserViewSettingTable.class).findFirst();
        userTable = realm.where(UserTable.class).findFirst();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList1 = new ArrayList<>();
        albumList1.addAll(albumList);

        adapter = new MultiCustomAdapter(SearchActivity.this, SearchActivity.this ,albumList);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        if(userViewSettingTable != null)
        {
            if(userViewSettingTable.getViewType().equals("Single"))
            {
                mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

            }
            else if(userViewSettingTable.getViewType().equals("Double"))
            {
                mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

            }

        }
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        prepareAlbums();

        try {
            //  Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(),realm.where(CartsTable.class).findFirst().getCart_status()+"",Toast.LENGTH_LONG).show();
        decideLoad();
    }




    void decideLoad()
    {
        // System.out.println("Deciding "+ MainActivity.categories.get(MainActivity.viewPager.getCurrentItem()).getName());
        mShimmerViewContainer.startShimmerAnimation();
        loadProducts();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void prepareAlbums() {

        adapter.notifyDataSetChanged();
    }

    void refreshItems() {
        // Load items
        // ...
        decideLoad();
        // Load complete
    }

    static void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private  void loadProducts()
    {

        //albumList1.clear();
        albumList.addAll(albumList1);

        onItemsLoadComplete();
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);

    }


    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.search, menu);
         myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView ;
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.requestFocus();
        myActionMenuItem.expandActionView();
        searchView.setQueryHint("Type your search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = query;
                adapter.filter(text);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                String text = s;
                adapter.filter(text);
                return true;
            }
        });

        return true;
    }

}
