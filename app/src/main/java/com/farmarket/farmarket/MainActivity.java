package com.farmarket.farmarket;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.farmarket.farmarket.Adaptors.MultiCustomAdapter;
import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.Product;
import com.farmarket.farmarket.DataType.ProductCart;
import com.farmarket.farmarket.DataType.ProductEmpty;
import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.OrderDetailTable;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;
import com.google.android.gms.appinvite.AppInviteInvitation;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
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
    TextView name,email;
    ImageView profilePic;
    UserTable userTable;
    public static MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Fud Farma");

        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        userViewSettingTable = realm.where(UserViewSettingTable.class).findFirst();
        userTable = realm.where(UserTable.class).findFirst();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

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
        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.userNameTV);
        email = (TextView) headerView.findViewById(R.id.textView);

        name.setText("");
        email.setText("");
        if(userTable != null)
        {
            name.setText(userTable.getFirstname()+" "+userTable.getLastname());
            email.setText(userTable.getEmail());
        }





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
       // Toast.makeText(getApplicationContext(),realm.where(CartsTable.class).findFirst().getCart_status()+"",Toast.LENGTH_LONG).show();
        loadProducts();


    }
    @Override
    public void onResume() {
        super.onResume();
        loadProducts();
        adapter.notifyDataSetChanged();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        adapter.notifyDataSetChanged();
        mShimmerViewContainer.stopShimmerAnimation();
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

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        //menuItem = menu.findItem( R.id.action_search);
        final SearchView searchView ;
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("Type your search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String text = query;
                adapter.filter(text);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                String text = s;
                adapter.filter(text);
                return false;
            }
        });


        Realm realm = Realm.getDefaultInstance();
        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
        menuItem = menu.findItem(R.id.action_cart);

        if (cartsTable != null && realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll().size()>0)
        {
         menuItem.setIcon(R.drawable.full_cart);


            MenuItemCompat.setActionView(menuItem, R.layout.cart_layout);
            RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(menuItem);

            TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
            tv.setText(realm.where(CartDetailsTable.class).equalTo("cart_id",realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()).findAll().size()+"");

            notifCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this,CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            });
        }
        else
        {
          menuItem.setIcon(R.drawable.empty_cart);
          //menu.findItem(R.id.action_cart).setTooltipText("Sorry your cart is empty");
          //menu.findItem(R.id.action_cart).setTitle("0");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Realm realm = Realm.getDefaultInstance();
            CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
            if (cartsTable != null && realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll().size()>0)
            {

                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_address) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this,SetupAddressActivity.class);
            startActivity(intent);

        } else if (id == R.id.my_order) {
            Intent intent = new Intent(MainActivity.this,MyOrdersActivity.class);
            startActivity(intent);

        } else if (id == R.id.my_settings) {
            Intent intent = new Intent(MainActivity.this,MySettingsActivity.class);
            startActivity(intent);
            finish();

        }  else if (id == R.id.log_out) {
            realm.beginTransaction();
            userTable.deleteFromRealm();
            realm.where(CartsTable.class).findAll().deleteAllFromRealm();
            realm.where(CartDetailsTable.class).findAll().deleteAllFromRealm();
            realm.where(UserViewSettingTable.class).findAll().deleteAllFromRealm();
            realm.commitTransaction();
            Intent intent = new Intent(MainActivity.this,SignInActivity.class);
            startActivity(intent);
            finish();
            return true;

        } else if (id == R.id.contact_us) {
            Intent intent = new Intent(MainActivity.this,ContactUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.about_us) {
            Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.share) {
                Intent intent = new AppInviteInvitation.IntentBuilder("Checkout FudFarma")
                        .setMessage("Hello checkout this awesome app")
                        .setDeepLink(Uri.parse("https://play.google.com/apps/testing/com.farmarket.farmarket"))
                        .setCustomImage(Uri.parse("https://lh3.googleusercontent.com/-E9hmbXjjtn7YVf6cF3-wX23Ru5w0LlwyRDpy1AzUWzhbVWW-6YiXXVH1xZVnMhehRg"))
                        .setCallToActionText("FudFarma")
                        .build();
                startActivityForResult(intent, REQUEST_INVITE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    final int REQUEST_INVITE = 202;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    //Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
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
                if(response.isSuccess() && response.code() == 200)
                {
                    albumList.clear();

                    List<ProduceModel> generalModels = response.body();
                    for(int i = 0; i<generalModels.size();i++)
                    {
                        ProduceModel produceModel = generalModels.get(i);
                            Realm realm = Realm.getDefaultInstance();
                            CartsTable orderDetailTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
                            CartDetailsTable cartDetailsTable = null;
                            if(orderDetailTable != null)
                                cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",produceModel.getProduce_id()).equalTo("cart_id",orderDetailTable.getId()).findFirst();


                            if(cartDetailsTable != null && cartDetailsTable.getWeight() != 0.00)
                            {
                                ProductCart productCart = new ProductCart();
                                productCart.setCreated_at(produceModel.getCreated_at());
                                productCart.setDescription(produceModel.getDescription());
                                productCart.setFile_blob(produceModel.getFile_blob());
                                productCart.setFile_name(produceModel.getFile_name());
                                productCart.setInCart(cartDetailsTable.getWeight());
                                productCart.setName(produceModel.getName());
                                productCart.setPrice_per_kg(produceModel.getPrice_per_kg());
                                productCart.setProduce_id(produceModel.getProduce_id());
                                productCart.setProduce_type(produceModel.getProduce_type());
                                productCart.setUnique_code(produceModel.getUnique_code());
                                productCart.setProduce_type(produceModel.getProduce_type());
                                productCart.setUpdated_at(produceModel.getUpdated_at());
                                productCart.setUuid(produceModel.getUuid());
                                // Toast.makeText(getApplicationContext(),productCart.getName(),Toast.LENGTH_LONG).show();
                                albumList.add(productCart);

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
                                // Toast.makeText(getApplicationContext(),productEmpty.getName(),Toast.LENGTH_LONG).show();
                                albumList.add(productEmpty);

                            }

                    }

                    albumList1.clear();
                    albumList1.addAll(albumList);
                    adapter.notifyDataSetChanged();
                    onItemsLoadComplete();
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                onItemsLoadComplete();

            }
        });

    }

    void recheckMenu()
    {


        Realm realm = Realm.getDefaultInstance();
        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();

        if (cartsTable != null && realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll().size()>0)
        {
            menuItem.setIcon(R.drawable.full_cart);


            MenuItemCompat.setActionView(menuItem, R.layout.cart_layout);
            RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(menuItem);

            TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
            tv.setText(realm.where(CartDetailsTable.class).equalTo("cart_id",realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()).findAll().size()+"");

            notifCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this,CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            });
        }
        else
        {
            menuItem.setIcon(R.drawable.empty_cart);
            //menu.findItem(R.id.action_cart).setTooltipText("Sorry your cart is empty");
            //menu.findItem(R.id.action_cart).setTitle("0");
        }

    }

}
