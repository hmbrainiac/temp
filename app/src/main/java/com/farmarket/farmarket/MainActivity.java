package com.farmarket.farmarket;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.farmarket.farmarket.Adaptors.MultiCustomAdapter;
import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.ProductCart;
import com.farmarket.farmarket.DataType.ProductEmpty;
import com.farmarket.farmarket.Models.GeneralModel;
import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.RealmTables.OrderDetailTable;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private MultiCustomAdapter adapter;
    public static List<Object> albumList,albumList1;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Realm realm;
    UserViewSettingTable userViewSettingTable;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
        userViewSettingTable = realm.where(UserViewSettingTable.class).findFirst();
        //mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        albumList = new ArrayList<>();
        albumList1 = new ArrayList<>();
        adapter = new MultiCustomAdapter(this, MainActivity.this ,albumList);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        albumList1 = new ArrayList<>();
        adapter = new MultiCustomAdapter(this, MainActivity.this ,albumList);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        if(userViewSettingTable != null)
        {
            if(userViewSettingTable.getViewType().equals("Single"))
            {
                mLayoutManager = new GridLayoutManager(this, 1);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

            }
            else if(userViewSettingTable.getViewType().equals("Double"))
            {
                mLayoutManager = new GridLayoutManager(this, 2);
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
        loadProducts();

    }
    @Override
    public void onResume() {
        super.onResume();
       // mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
      //  mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        /*
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint("Type your search here");
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = query;
               // adapter.filter(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                //adapter.filter(text);
                return false;
            }
        });
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
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
        loadProducts();
        // Load complete
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }


    void loadProducts()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<List<ProduceModel>> data = endpoints.listProducts();
        data.enqueue(new Callback<List<ProduceModel>>() {
            @Override
            public void onResponse(Response<List<ProduceModel>> response, Retrofit retrofit) {

                List<ProduceModel> generalModels = response.body();
                for(int i = 0; i<generalModels.size();i++)
                {
                    ProduceModel produceModel = generalModels.get(i);
                    Realm realm = Realm.getDefaultInstance();
                    OrderDetailTable orderDetailTable = realm.where(OrderDetailTable.class).equalTo("produce_id",generalModels.get(i).getProduce_id()).findFirst();
                    if(orderDetailTable != null && orderDetailTable.getWeight() != 0.00)
                    {
                        ProductCart productCart = new ProductCart();
                        productCart.setCreated_at(produceModel.getCreated_at());
                        productCart.setDescription(produceModel.getDescription());
                        productCart.setFile_blob(produceModel.getFile_blob());
                        productCart.setFile_name(produceModel.getFile_name());
                        productCart.setInCart(orderDetailTable.getWeight());
                        productCart.setName(produceModel.getName());
                        productCart.setPrice_per_kg(produceModel.getPrice_per_kg());
                        productCart.setProduce_id(produceModel.getProduce_id());
                        productCart.setProduce_type(produceModel.getProduce_type());
                        productCart.setUnique_code(produceModel.getUnique_code());
                        productCart.setProduce_type(produceModel.getProduce_type());
                        productCart.setUpdated_at(produceModel.getUpdated_at());
                        productCart.setUuid(produceModel.getUuid());
                        albumList.add(productCart);
                       // Toast.makeText(getApplicationContext(),productCart.getName(),Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        ProductEmpty productEmpty = new ProductEmpty();
                        productEmpty.setCreated_at(produceModel.getCreated_at());
                        productEmpty.setDescription(produceModel.getDescription());
                        productEmpty.setFile_blob(produceModel.getFile_blob());
                        productEmpty.setFile_name(produceModel.getFile_name());
                        productEmpty.setName(produceModel.getName());
                        productEmpty.setPrice_per_kg(produceModel.getPrice_per_kg());
                        productEmpty.setProduce_id(produceModel.getProduce_id());
                        productEmpty.setProduce_type(produceModel.getProduce_type());
                        productEmpty.setUnique_code(produceModel.getUnique_code());
                        productEmpty.setProduce_type(produceModel.getProduce_type());
                        productEmpty.setUpdated_at(produceModel.getUpdated_at());
                        productEmpty.setUuid(produceModel.getUuid());
                        //System.out.println(productEmpty.getUuid());
                        albumList.add(productEmpty);
                       // Toast.makeText(getApplicationContext(),productEmpty.getName(),Toast.LENGTH_LONG).show();
                    }
                }
                albumList1.clear();
                albumList1.addAll(albumList);
                adapter.notifyDataSetChanged();
                onItemsLoadComplete();
              //  mShimmerViewContainer.stopShimmerAnimation();
               // mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                onItemsLoadComplete();

            }
        });

    }
}
