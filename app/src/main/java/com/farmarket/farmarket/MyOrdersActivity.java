package com.farmarket.farmarket;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.farmarket.farmarket.Adaptors.MultiCustomAdapter;
import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.Invoice;
import com.farmarket.farmarket.DataType.Order;
import com.farmarket.farmarket.DataType.OrderDetail;
import com.farmarket.farmarket.DataType.Product;
import com.farmarket.farmarket.DataType.ProductCart;
import com.farmarket.farmarket.DataType.ProductEmpty;
import com.farmarket.farmarket.Models.OrderDetailModel;
import com.farmarket.farmarket.Models.OrderModel;
import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.UserTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MyOrdersActivity extends AppCompatActivity {
    Realm realm;
    UserTable userTable;
    private RecyclerView recyclerView;
    private MultiCustomAdapter adapter;
    public static List<Object> albumList,albumList1;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        realm = Realm.getDefaultInstance();
        userTable = realm.where(UserTable.class).findFirst();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        albumList = new ArrayList<>();
        albumList1 = new ArrayList<>();
        adapter = new MultiCustomAdapter(this, MyOrdersActivity.this ,albumList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        albumList1 = new ArrayList<>();
        adapter = new MultiCustomAdapter(this, MyOrdersActivity.this ,albumList);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

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
        loadOrders();

    }

    @Override
    public void onResume() {
        super.onResume();
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


    void loadOrders()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<List<OrderModel>> data = endpoints.listMyOrders(userTable.getServer_id());
        data.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Response<List<OrderModel>> response, Retrofit retrofit) {
                if(response.isSuccess() && response.code() == 200)
                {

                    List<OrderModel> generalModels = response.body();
                    for(int i = 0; i<generalModels.size();i++)
                    {
                        OrderModel orderModel = generalModels.get(i);
                        Order order = new Order();
                        order.setAccountNetwork(orderModel.getAccountNetwork());
                        order.setAccountNumber(orderModel.getAccountNumber());
                        order.setAccountToken(orderModel.getAccountToken());
                        order.setCart_status(orderModel.getCart_status());
                        order.setCreated_at(orderModel.getCreated_at());
                        order.setDelivery_gh_post_code(orderModel.getDelivery_gh_post_code());
                        order.setDelivery_id(orderModel.getDelivery_id());
                        order.setDelivery_lat(orderModel.getDelivery_lat());
                        order.setDelivery_lon(orderModel.getDelivery_lon());
                        order.setDeliveryTown(orderModel.getDeliveryTown());
                        order.setDelivryRegion(orderModel.getDelivryRegion());
                        order.setEmail(orderModel.getEmail());
                        order.setExpected_delivery(orderModel.getExpected_delivery());
                        order.setInvoice_id(orderModel.getInvoice_id());
                        order.setName(orderModel.getName());
                        order.setOrder_id(orderModel.getOrder_id());
                        order.setPhone(orderModel.getPhone());
                        order.setResidentialAddress(orderModel.getResidentialAddress());
                        order.setStatus(orderModel.getStatus());
                        order.setUpdated_at(orderModel.getUpdated_at());
                        order.setUser_id(orderModel.getUser_id());
                        order.setUuid(orderModel.getUuid());
                        Invoice invoice = new Invoice();
                        invoice.setAmount(orderModel.getInvoices().getAmount());
                        invoice.setAmount_after_charge(orderModel.getInvoices().getAmount_after_charge());
                        invoice.setCreated_at(orderModel.getInvoices().getCreated_at());
                        invoice.setInvoice_id(orderModel.getInvoices().getInvoice_id());
                        invoice.setPayment_status(orderModel.getInvoices().getPayment_status());
                        invoice.setReference(orderModel.getInvoices().getReference());
                        invoice.setUnique_code(orderModel.getInvoices().getUnique_code());
                        invoice.setUpdated_at(orderModel.getInvoices().getUpdated_at());
                        invoice.setUuid(orderModel.getInvoices().getUuid());
                        order.setInvoices(invoice);
                        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

                        for (int q = 0; q <orderModel.getDetails().size();q++)
                        {
                            OrderDetailModel orderDetailModel = orderModel.getDetails().get(q);
                            OrderDetail orderDetail = new OrderDetail();
                            orderDetail.setCost_per_kg(orderDetailModel.getCost_per_kg());
                            orderDetail.setCreated_at(orderDetailModel.getCreated_at());
                            orderDetail.setDetail_id(orderDetailModel.getDetail_id());
                            orderDetail.setOrder_id(orderDetailModel.getOrder_id());
                            orderDetail.setPrice_per_kg(orderDetailModel.getPrice_per_kg());
                            orderDetail.setProduce_id(orderDetailModel.getProduce_id());
                            orderDetail.setRemarks(orderDetailModel.getRemarks());
                            orderDetail.setUnique_code(orderDetailModel.getUnique_code());
                            orderDetail.setUpdated_at(orderDetailModel.getUpdated_at());
                            orderDetail.setWeight(orderDetailModel.getWeight());
                            Product product = new Product();
                            product.setCreated_at(orderDetailModel.getProduce().getCreated_at());
                            product.setDescription(orderDetailModel.getProduce().getDescription());
                            product.setFile_blob(orderDetailModel.getProduce().getFile_blob());
                            product.setFile_name(orderDetailModel.getProduce().getFile_name());
                            product.setName(orderDetailModel.getProduce().getName());
                            product.setPrice_per_kg(orderDetailModel.getProduce().getPrice_per_kg());
                            product.setProduce_id(orderDetailModel.getProduce().getProduce_id());
                            product.setProduce_type(orderDetailModel.getProduce().getProduce_type());
                            product.setUnique_code(orderDetailModel.getProduce().getUnique_code());
                            product.setUpdated_at(orderDetailModel.getProduce().getUpdated_at());
                            product.setUuid(orderDetailModel.getProduce().getUuid());
                            orderDetail.setProduce(product);
                            orderDetails.add(orderDetail);
                        }
                        order.setDetails(orderDetails);
                        albumList.add(orderModel);
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
        loadOrders();
        // Load complete
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
