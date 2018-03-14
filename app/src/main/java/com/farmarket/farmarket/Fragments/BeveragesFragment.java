package com.farmarket.farmarket.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.farmarket.farmarket.Adaptors.MultiCustomAdapter;
import com.farmarket.farmarket.Api.ApiEndpoints;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataType.Category;
import com.farmarket.farmarket.DataType.ProductCart;
import com.farmarket.farmarket.DataType.ProductEmpty;
import com.farmarket.farmarket.MainActivity;
import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.R;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeveragesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeveragesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeveragesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView;
    static public MultiCustomAdapter adapter;
    public static List<Object> albumList,albumList1;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    static SwipeRefreshLayout mSwipeRefreshLayout;
    Realm realm;
    UserViewSettingTable userViewSettingTable;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView name,email;
    ImageView profilePic;
    UserTable userTable;
    public static MenuItem menuItem;
    static ArrayList<Category> categories;
    public static int         currentPosition = 6;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProductsFragment.OnFragmentInteractionListener mListener;

    public BeveragesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeveragesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeveragesFragment newInstance(String param1, String param2) {
        BeveragesFragment fragment = new BeveragesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        realm = Realm.getDefaultInstance();
        userViewSettingTable = realm.where(UserViewSettingTable.class).findFirst();
        userTable = realm.where(UserTable.class).findFirst();
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        albumList = new ArrayList<>();
        albumList1 = new ArrayList<>();
        adapter = new MultiCustomAdapter(getContext(), getActivity() ,albumList,5);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        if(userViewSettingTable != null)
        {
            if(userViewSettingTable.getViewType().equals("Single"))
            {
                mLayoutManager = new GridLayoutManager(getContext(), 1);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

            }
            else if(userViewSettingTable.getViewType().equals("Double"))
            {
                mLayoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

            }

        }
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);

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
        return view;
    }
    void decideLoad()
    {
        mShimmerViewContainer.startShimmerAnimation();
        currentPosition = 6;
        loadCategoryProducts();

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

    @Override
    public void onDestroy() {
        albumList.clear();
        super.onDestroy();
    }

    static void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        decideLoad();
    }



    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        currentPosition = 6;
        mSwipeRefreshLayout.setRefreshing(false);
        decideLoad();

    }

    @Override
    public void onDestroyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        super.onDestroyView();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible)
        {
            if(mShimmerViewContainer != null)
                decideLoad();
        }
    }


    public  void loadCategoryProducts()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLocation.getApiLocation1())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiEndpoints endpoints = retrofit.create(ApiEndpoints.class);
        Call<List<ProduceModel>> data = endpoints.listCategoryProducts(currentPosition);
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
                    //Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
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
