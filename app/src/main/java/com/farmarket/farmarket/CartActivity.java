package com.farmarket.farmarket;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.farmarket.farmarket.Adaptors.MultiCustomAdapter;
import com.farmarket.farmarket.DataType.CartDetail;
import com.farmarket.farmarket.DataType.CartTotal;
import com.farmarket.farmarket.Misc.GeneralCalculations;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MultiCustomAdapter adapter;
    public static List<Object> albumList,albumList1;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        realm = Realm.getDefaultInstance();

        albumList = new ArrayList<>();
        albumList1 = new ArrayList<>();
        adapter = new MultiCustomAdapter(this, CartActivity.this ,albumList);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        albumList1 = new ArrayList<>();
        adapter = new MultiCustomAdapter(this, CartActivity.this ,albumList);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        setCartDetails();

    }


    void setCartDetails()
    {
        CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();
        if(cartsTable != null)
        {
            double subTotal = 0;
            double total = 0;
            RealmResults<CartDetailsTable> cartDetailsTableRealmResults = realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll();
            if(cartDetailsTableRealmResults != null)
            {
                for (int i= 0; i<cartDetailsTableRealmResults.size();i++
                     ) {

                    CartDetailsTable cartDetailsTable = cartDetailsTableRealmResults.get(i);
                    if(cartDetailsTable.getWeight() >0.00)
                    {
                        CartDetail cartDetail = new CartDetail();
                        cartDetail.setCost_per_kg(cartDetailsTable.getCost_per_kg());
                        cartDetail.setFile_name(cartDetailsTable.getFile_name());
                        cartDetail.setProduct_name(cartDetailsTable.getProduct_name());
                        cartDetail.setPrice_per_kg(cartDetailsTable.getPrice_per_kg());
                        cartDetail.setProduce_id(cartDetailsTable.getProduce_id());
                        cartDetail.setProduct_type(cartDetailsTable.getProduce_type());
                        cartDetail.setWeight(cartDetailsTable.getWeight());
                        cartDetail.setIncremental(cartDetailsTable.getIncremental());
                        cartDetail.setMeasurement(cartDetailsTable.getMeasurement());
                        albumList.add(cartDetail);
                        subTotal += GeneralCalculations.getCost(cartDetailsTable.getPrice_per_kg(),cartDetailsTable.getWeight());

                    }
                }
                CartTotal cartTotal = new CartTotal();
                total = subTotal;
                cartTotal.setSubTotal(subTotal);
                cartTotal.setTotal(total);
                albumList.add(cartTotal);
                albumList1.clear();
                albumList1.addAll(albumList);
                adapter.notifyDataSetChanged();
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

}
