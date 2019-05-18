package com.farmarket.farmarket;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.farmarket.farmarket.Adaptors.MultiCustomAdapter;
import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.Invoice;
import com.farmarket.farmarket.DataType.Order;
import com.farmarket.farmarket.DataType.OrderDetail;
import com.farmarket.farmarket.DataType.Product;
import com.farmarket.farmarket.DataType.Transaction;
import com.farmarket.farmarket.Models.OrderDetailModel;
import com.farmarket.farmarket.Models.OrderModel;
import com.farmarket.farmarket.Models.TransactionModel;
import com.farmarket.farmarket.Models.WalletModel;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MyWalletActivity extends AppCompatActivity {
    TextView balance;


    private RecyclerView recyclerView;
    static public MultiCustomAdapter adapter;
    public static List<Object> albumList,albumList1;
    static SwipeRefreshLayout mSwipeRefreshLayout;
    Realm realm;
    UserViewSettingTable userViewSettingTable;
    private ShimmerFrameLayout mShimmerViewContainer;
    UserTable userTable;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);


        realm = Realm.getDefaultInstance();
        userViewSettingTable = realm.where(UserViewSettingTable.class).findFirst();
        balance = findViewById(R.id.walletBalance);
        balance.setText("Loading");
        userTable = realm.where(UserTable.class).findFirst();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList1 = new ArrayList<>();
        albumList = new ArrayList<>();


        adapter = new MultiCustomAdapter(MyWalletActivity.this, MyWalletActivity.this ,albumList);
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<WalletModel> data = endpoints.getWallet(userTable.getServer_id());
        data.enqueue(new Callback<WalletModel>() {
            @Override
            public void onResponse(Response<WalletModel> response, Retrofit retrofit) {
                if(response.isSuccess() && response.code() == 200)
                {
                    double subBalance = 0.00;
                    albumList.clear();
                    WalletModel walletModel = response.body();
                    balance.setText("GhC "+walletModel.getBalance());
                    List<TransactionModel> transactions = walletModel.getTransactions();
                    for(int i = 0; i< transactions.size(); i++)
                    {
                        TransactionModel transactionModel = transactions.get(i);
                        if(transactionModel.getTransaction_type().equalsIgnoreCase("Deposit"))
                        {
                            subBalance +=transactionModel.getAmount();
                            Transaction transaction = new Transaction();
                            transaction.setUuid(transactionModel.getUpdated_at());
                            transaction.setUnique_code(transactionModel.getReference());
                            transaction.setReference(transactionModel.getReference());
                            transaction.setPayment_status(transactionModel.getTransaction_type());
                            transaction.setInvoice_id(transactionModel.getInvoice_id());
                            transaction.setAmount(transactionModel.getAmount());
                            transaction.setAmount_after_charge(subBalance);
                            albumList.add(transaction);

                        }
                        else if(transactionModel.getTransaction_type().equalsIgnoreCase("Withdrawal"))
                        {
                            subBalance -=transactionModel.getAmount();
                            Transaction transaction = new Transaction();
                            transaction.setUuid(transactionModel.getUpdated_at());
                            transaction.setUnique_code(transactionModel.getReference());
                            transaction.setReference(transactionModel.getReference());
                            transaction.setPayment_status(transactionModel.getTransaction_type());
                            transaction.setInvoice_id(transactionModel.getInvoice_id());
                            transaction.setAmount(transactionModel.getAmount());
                            transaction.setAmount_after_charge(subBalance);
                            albumList.add(transaction);
                        }
                    }
                    // Toast.makeText(getApplicationContext(),""+albumList.size()+" "+generalModels.size(),Toast.LENGTH_LONG).show();
                    albumList1.clear();
                    albumList1.addAll(albumList);
                    adapter.notifyDataSetChanged();
                    onItemsLoadComplete();
                    if(albumList.size() >0)
                    {
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                    else {
                    }

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



}
