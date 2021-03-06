package com.farmarket.farmarket.Adaptors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.CartActivity;
import com.farmarket.farmarket.DataView.CartDetail;
import com.farmarket.farmarket.DataView.CartTotal;
import com.farmarket.farmarket.DataView.Order;
import com.farmarket.farmarket.DataView.OrderDetail;
import com.farmarket.farmarket.DataView.ProductCart;
import com.farmarket.farmarket.DataView.ProductEmpty;
import com.farmarket.farmarket.DataView.Transaction;
import com.farmarket.farmarket.Fragments.AllProductsFragment;
import com.farmarket.farmarket.Fragments.BakeryFragment;
import com.farmarket.farmarket.Fragments.BeveragesFragment;
import com.farmarket.farmarket.Fragments.DiaryEggFragment;
import com.farmarket.farmarket.Fragments.FishMeatFragment;
import com.farmarket.farmarket.Fragments.FruitsVeggiesFragment;
import com.farmarket.farmarket.Fragments.ProductsFragment;
import com.farmarket.farmarket.MainActivity;
import com.farmarket.farmarket.Misc.GeneralCalculations;
import com.farmarket.farmarket.Models.GeneralModel;
import com.farmarket.farmarket.Models.OrderDetailModel;
import com.farmarket.farmarket.Models.OrderModel;
import com.farmarket.farmarket.Models.TransactionModel;
import com.farmarket.farmarket.MyAddressActivity;
import com.farmarket.farmarket.MyOrdersActivity;
import com.farmarket.farmarket.MySingleOrderActivity;
import com.farmarket.farmarket.PayForOrderActivity;
import com.farmarket.farmarket.R;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;
import com.farmarket.farmarket.ReviewActivity;
import com.farmarket.farmarket.SearchActivity;
import com.farmarket.farmarket.SingleItemActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

/**
 * Created by isaac on 2/18/18.
 */

public class MultiCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<Object> albumList,albumList1;
        private final int ProductEmpty = 1, ProductCart = 2, CartDetail = 3,CartTotal= 4,Order = 5,OrderDetail=6,Transaction=7;
        public static TextView title, count;
        public static ImageView overflow;
        VideoView thumbnail;
        Activity mActivity;
        private static DecimalFormat df2 = new DecimalFormat(".##");
        private int myPosition;

        //http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4


    public MultiCustomAdapter(Context mContext, List<Object> albumList) {
            this.mContext = mContext;
            this.albumList = albumList;
            albumList1 = new ArrayList<Object>();
            this.albumList1.addAll(ProductsFragment.albumList1);
        }

    public MultiCustomAdapter(Context mContext, Activity mActivity ,List<Object> albumList, int position) {
            this.mContext = mContext;
            this.albumList = albumList;
            this.mActivity = mActivity;
            this.myPosition = position;
            albumList1 = new ArrayList<Object>();
            if(position == 0)
            {
                this.albumList1.addAll(AllProductsFragment.albumList1);
            }
            else if(position == 1)
            {
                this.albumList1.addAll(FruitsVeggiesFragment.albumList1);

            }
            else if(position == 2)
            {
                this.albumList1.addAll(BakeryFragment.albumList1);

            }
            else if(position == 3)
            {
                this.albumList1.addAll(DiaryEggFragment.albumList1);
            }
            else if(position == 4)
            {
                this.albumList1.addAll(FishMeatFragment.albumList1);

            }
            else if (position == 5)
            {
                this.albumList1.addAll(BeveragesFragment.albumList1);
            }
        }

    public MultiCustomAdapter(Context mContext, Activity mActivity ,List<Object> albumList,List<Object> albumList1) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.mActivity = mActivity;
        this.albumList1 = new ArrayList<Object>();
        this.albumList1.addAll(albumList1);
    }


    public MultiCustomAdapter(Context mContext, Activity mActivity ,List<Object> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.mActivity = mActivity;
        this.albumList1 = new ArrayList<Object>();
        this.albumList1.addAll(albumList);
    }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater itemView = LayoutInflater.from(parent.getContext());
            View view  = null;
            RecyclerView.ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            Realm realm = Realm.getDefaultInstance();
            UserViewSettingTable userViewSettingTable =  realm.where(UserViewSettingTable.class).findFirst();

            switch (viewType)
            {
                case ProductCart:
                    if(userViewSettingTable == null || userViewSettingTable.getViewType().equals("Single"))
                    {
                        view = itemView.inflate(R.layout.products_single_cart_grid, parent, false);
                        viewHolder = new ProductCart(view);

                    }
                    else
                    {
                        view = itemView.inflate(R.layout.products_double_cart_grid, parent, false);
                        viewHolder = new ProductCart(view);

                    }
                    break;
                case ProductEmpty:
                    if(userViewSettingTable == null || userViewSettingTable.getViewType().equals("Single"))
                    {
                        view = itemView.inflate(R.layout.products_single_empty_grid, parent, false);
                        viewHolder = new ProductEmpty(view);

                    }
                    else
                    {
                        view = itemView.inflate(R.layout.products_double_empty_grid, parent, false);
                        viewHolder = new ProductEmpty(view);

                    }
                    break;

                case CartDetail:
                    view = itemView.inflate(R.layout.single_row_cart, parent, false);
                    viewHolder = new CartDetail(view);
                    break;

                case CartTotal:
                    view = itemView.inflate(R.layout.cart_bottom_row, parent, false);
                    viewHolder = new CartTotal(view);
                    break;

                case Order:
                    view = itemView.inflate(R.layout.single_order_row, parent, false);
                    viewHolder = new Order(view);
                    break;

                case OrderDetail:
                    view = itemView.inflate(R.layout.single_order_detail_row, parent, false);
                    viewHolder = new OrderDetail(view);
                    break;
                case Transaction:

                    view = itemView.inflate(R.layout.wallet_refund_row, parent, false);
                    viewHolder = new Transaction(view);
                    break;

                default:
                    view = itemView.inflate(R.layout.products_single_empty_grid, parent, false);
                    viewHolder = new ProductEmpty(view);


            }


            return    viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            switch (holder.getItemViewType())
            {
                case ProductCart:
                    com.farmarket.farmarket.DataView.ProductCart vh1 = (ProductCart) holder;
                    configureCartProduct(vh1,(com.farmarket.farmarket.DataType.ProductCart) albumList.get(position),position);
                    break;
                case ProductEmpty:
                    ProductEmpty vh2 = (ProductEmpty) holder;
                    configureEmptyProduct(vh2, (com.farmarket.farmarket.DataType.ProductEmpty) albumList.get(position),position);
                    break;
                case CartDetail:
                    CartDetail vh3 = (CartDetail) holder;
                    configureCartDetail(vh3, (com.farmarket.farmarket.DataType.CartDetail) albumList.get(position),position);
                    break;
                case CartTotal:
                    CartTotal vh4 = (CartTotal) holder;
                    configureCartTotal(vh4, (com.farmarket.farmarket.DataType.CartTotal) albumList.get(position),position);
                    break;

                case Order:
                    Order vh5 = (Order) holder;
                    configureOrder(vh5, (com.farmarket.farmarket.DataType.Order) albumList.get(position),position);
                    break;
                case OrderDetail:
                    com.farmarket.farmarket.DataView.OrderDetail vh6 = (com.farmarket.farmarket.DataView.OrderDetail) holder;
                    configureOrderDetail(vh6, (com.farmarket.farmarket.DataType.OrderDetail) albumList.get(position),position);
                    break;
                case Transaction:
                    com.farmarket.farmarket.DataView.Transaction vh7 = (com.farmarket.farmarket.DataView.Transaction) holder;
                    configureTransaction(vh7, (com.farmarket.farmarket.DataType.Transaction) albumList.get(position),position);
                    break;
                default:
                    ProductEmpty vh10 = (ProductEmpty) holder;
                    configureEmptyProduct(vh10, (com.farmarket.farmarket.DataType.ProductEmpty) albumList.get(position),position);

            }


        }

    private void configureOrder(final Order v, final com.farmarket.farmarket.DataType.Order order, final int position)
    {
       // Toast.makeText(mContext,order.getUnique_code(),Toast.LENGTH_LONG).show();
       // System.out.println("Order displaying one "+order.getOrder_id());
        v.getViewDetails().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPollPopup(v,order);
            }
        });
        if(!order.getInvoices().getPayment_status().equalsIgnoreCase("Pending"))
        {
            v.getCompletePayment().setText("Rate Delivery");
        }
        v.getCompletePayment().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order.getInvoices().getPayment_status().equalsIgnoreCase("Pending"))
                {
                    Intent intent = new Intent(mActivity,PayForOrderActivity.class);
                    intent.putExtra("order", order);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    mActivity.startActivity(intent);
                    mActivity.finish();
                    return;
                }
                else
                {
                    Intent intent = new Intent(mActivity,ReviewActivity.class);
                    intent.putExtra("order",order.getOrder_id());
                    intent.putExtra("uuid",order.getUuid());
                    intent.putExtra("expected",order.getExpected_delivery());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    mActivity.startActivity(intent);
                    mActivity.finish();
                    return;
                }
            }
        });
        v.getOrderCode().setText("Order Code "+order.getUnique_code());
        v.getOrderCreationDate().setText(order.getCreated());
        if(order.getStatus().equalsIgnoreCase("Pending"))
        {
            v.getOrderStatus().setText(order.getStatus()+" Delivery");
        }
        else
        {
            v.getOrderStatus().setText(order.getStatus());
        }
        double sumTotal = 0.00;
        for(int i = 0; i<order.getDetails().size(); i++)
        {
            double weight = order.getDetails().get(i).getWeight();
            double total = GeneralCalculations.getCost(order.getDetails().get(i).getCost_per_kg(),weight);
            sumTotal +=total;
        }
        v.getSumWeightOrder().setText("Invoice Code "+order.getInvoices().getUnique_code());
        v.getOrderTotal().setText("GhC "+sumTotal);
        v.getPaymentStatus().setText("Payment "+order.getInvoices().getPayment_status());
    }


    private PopupWindow popWindow;


    // call this method when required to show popup
    public void onShowPollPopup(View v, final com.farmarket.farmarket.DataType.Order order){
        RecyclerView recyclerView;
        MultiCustomAdapter adapter;
        List<Object> albumList;


        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.activity_cart, null,false);
        // find the ListView in the popup layout



        recyclerView = (RecyclerView)inflatedView.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new MultiCustomAdapter(mContext, mActivity ,albumList);
        RecyclerView.LayoutManager mLayoutManager;

        mLayoutManager = new GridLayoutManager(mContext, 1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // get device size
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);



        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 400, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.pop_bg));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);
        popWindow.setAnimationStyle(R.style.PopupAnimation);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });


        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,100);


        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        albumList.addAll(order.getDetails());
        adapter.notifyDataSetChanged();

    }


    private void configureOrderDetail(final com.farmarket.farmarket.DataView.OrderDetail v, final com.farmarket.farmarket.DataType.OrderDetail orderDetail, final int position)
    {

        if(orderDetail.getProduce().getProduce_type().equals("Organic"))
        {
            v.getProductType().setText(orderDetail.getProduce().getProduce_type());
        }
        else
        {
            v.getProductType().setVisibility(View.GONE);
        }
        Glide.with(mContext).load(ApiLocation.getImageLocation()+orderDetail.getProduce().getFile_name())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(v.getMainImage());

        v.getProductPrice().setText("GhC "+GeneralCalculations.getCost(orderDetail.getPrice_per_kg(),orderDetail.getWeight())+"");
        v.getProductWeight().setText(orderDetail.getWeight()+" "+orderDetail.getMeasureemt());
        v.getProductName().setText(orderDetail.getProduce().getName());

    }

    private void configureTransaction(final com.farmarket.farmarket.DataView.Transaction  v, final com.farmarket.farmarket.DataType.Transaction transaction, final int position)
    {
        v.getAmountAdded().setText("GhC "+transaction.getAmount());
        v.getClosingBalance().setText("GhC "+transaction.getAmount_after_charge());
        if(transaction.getPayment_status().equalsIgnoreCase("Deposit"))
        {
            v.getTypeName().setText("Refund");
            v.getRightName().setText("Added");
        }
        else
        {
            v.getTypeName().setText("Purchase");
            v.getRightName().setText("Spent");
        }
    }

    private void configureEmptyProduct(ProductEmpty v, final com.farmarket.farmarket.DataType.ProductEmpty product, int position)
    {
        final Intent intent  = new Intent(mContext,SingleItemActivity.class);
        intent.putExtra("product", product);
        v.getAddToCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
                //mActivity.finish();
            }
        });
        v.getAddToCartTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
                //mActivity.finish();

            }
        });
        /*
        v.getDividerView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
            }
        });
        */
        v.getMeasurement().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
                //mActivity.finish();
            }
        });
        v.getNameProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
               //mActivity.finish();

            }
        });
        v.getPrice().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
                //mActivity.finish();
            }
        });

        /*
        byte[] decodedString = Base64.decode(product.getFile_blob(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        v.getMainImage().setImageBitmap(decodedByte);
        */
        Glide.with(mContext).load(ApiLocation.getImageLocation()+product.getFile_name())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(v.getMainImage());
        v.getMainImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
                //mActivity.finish();

            }
        });

        if(product.getProduce_type().equals("Organic"))
        {
            v.getDiscount().setText(product.getProduce_type());
        }
        else
        {
            v.getDiscount().setVisibility(View.GONE);
        }

        v.getMeasurement().setText("Per "+product.getMeasurement_full());
        v.getNameProduct().setText(product.getName());
        v.getPrice().setText("GhC "+product.getPrice_per_kg());
    }


    private void configureCartDetail(final CartDetail v, final com.farmarket.farmarket.DataType.CartDetail product, final int position)
    {
        final Intent intent  = new Intent(mContext,MySingleOrderActivity.class);
        intent.putExtra("product", product);

        v.getPlusImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo increase
                //get current quantity
                double currentQuantity = product.getWeight();
                Realm realm =Realm.getDefaultInstance();
                currentQuantity += product.getIncremental();
                currentQuantity = Math.round(currentQuantity*100.0)/100.0;

                //get new price

                //effect change in object
                double currentPrice = GeneralCalculations.getCost(product.getPrice_per_kg(),currentQuantity);
                //display change and get new price
                v.getProductPrice().setText(currentPrice+"");

                product.setWeight(currentQuantity);
                CartActivity.albumList.set(position,product);
                //effect change in cart details

                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                realm.beginTransaction();
                cartDetailsTable.setWeight(currentQuantity);
                realm.copyToRealmOrUpdate(cartDetailsTable);
                realm.commitTransaction();
                notifyDataSetChanged();
                setCartTotal();
            }
        });
        v.getRemoveFromCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartActivity.albumList.remove(position);
                notifyDataSetChanged();
                setCartTotal();
                Realm realm = Realm.getDefaultInstance();
                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                if(cartDetailsTable != null)
                {
                    realm.beginTransaction();
                    cartDetailsTable.setWeight(0);
                    realm.copyToRealmOrUpdate(cartDetailsTable);
                    cartDetailsTable.deleteFromRealm();
                    realm.commitTransaction();

                }


            }
        });
        v.getMinusImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get current quantity
                double currentQuantity = product.getWeight();

                currentQuantity -= product.getIncremental();
                if(currentQuantity < 0.00)
                    currentQuantity = product.getIncremental();

                currentQuantity = Math.round(currentQuantity*100.0)/100.0;
                double currentPrice = GeneralCalculations.getCost(product.getPrice_per_kg(),currentQuantity);

                //get new price
                if(currentQuantity > 0.00)
                {
                    //display change and get new price
                    //effect change in cart details
                    product.setWeight(currentQuantity);
                    CartActivity.albumList.set(position,product);


                }
                else {
                    Realm realm = Realm.getDefaultInstance();
                    CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                    if(cartDetailsTable != null)
                    {
                        realm.beginTransaction();
                        cartDetailsTable.setWeight(0);
                        realm.copyToRealmOrUpdate(cartDetailsTable);
                        realm.commitTransaction();

                    }

                    CartActivity.albumList.remove(position);
                }
                Realm realm = Realm.getDefaultInstance();
                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                if(cartDetailsTable != null)
                {
                    realm.beginTransaction();
                    cartDetailsTable.setWeight(currentQuantity);
                    realm.copyToRealmOrUpdate(cartDetailsTable);
                    realm.commitTransaction();

                }
                //effect change in object
                notifyDataSetChanged();
                setCartTotal();

            }
        });
        Glide.with(mContext).load(ApiLocation.getImageLocation()+product.getFile_name())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(v.getMainImage());
        v.getMainImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
            }
        });

        if(product.getProduct_type().equals("Organic"))
        {
            v.getProductType().setText(product.getProduct_type());
        }
        else
        {
            v.getProductType().setVisibility(View.GONE);
        }

        v.getProductPrice().setText("GhC "+GeneralCalculations.getCost(product.getPrice_per_kg(),product.getWeight())+"");
        v.getCurrentWeight().setText(product.getWeight()+" "+product.getMeasurement());
        v.getCurrentWeight().setEnabled(false);
        v.getProductWeight().setText(product.getWeight()+" "+product.getMeasurement());
        v.getProductName().setText(product.getProduct_name());
    }

    void setCartTotal()
    {
        double subTotal,total;
        com.farmarket.farmarket.DataType.CartTotal   cartTotal = (com.farmarket.farmarket.DataType.CartTotal) CartActivity.albumList.get(CartActivity.albumList.size()-1);
        if(cartTotal != null)
        {
            subTotal = 0;
            total = 0;
            for(int i = 0; i< CartActivity.albumList.size(); i++)
            {
                if(CartActivity.albumList.get(i) instanceof com.farmarket.farmarket.DataType.CartDetail)
                {

                    subTotal += GeneralCalculations.getCost(((com.farmarket.farmarket.DataType.CartDetail) CartActivity.albumList.get(i)).getPrice_per_kg(),((com.farmarket.farmarket.DataType.CartDetail) CartActivity.albumList.get(i)).getWeight());
                }

            }
            total = subTotal;
            cartTotal.setTotal(total);
            cartTotal.setSubTotal(subTotal);
            notifyDataSetChanged();
        }

    }

    private void configureCartTotal(final CartTotal v, final com.farmarket.farmarket.DataType.CartTotal product, final int position)
    {
        final Intent intent  = new Intent(mContext,MyAddressActivity.class);

        v.getSubTotal().setText(product.getSubTotal()+"");
        v.getTotal().setText(product.getTotal()+"");

        v.getToCheckOutBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(intent);
                //mActivity.finish();
                //return;
            }
        });
    }

    private void configureCartProduct(final ProductCart v, final com.farmarket.farmarket.DataType.ProductCart product, final int position)
    {
        final Intent intent  = new Intent(mContext,MySingleOrderActivity.class);
        intent.putExtra("product", product);

        v.getIncreaseCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo increase
                //get current quantity
                double currentQuantity = product.getInCart();
                Realm realm =Realm.getDefaultInstance();
                currentQuantity += product.getIncremental();
                currentQuantity = Math.round(currentQuantity*100.0)/100.0;

                //get new price

                //effect change in object


                com.farmarket.farmarket.DataType.ProductCart cart;
                switch (myPosition)
                {
                    case 0:
                        cart = (com.farmarket.farmarket.DataType.ProductCart) AllProductsFragment.albumList.get(position);
                        break;
                    case 1:
                        cart = (com.farmarket.farmarket.DataType.ProductCart) FruitsVeggiesFragment.albumList.get(position);
                    break;

                    case 2:
                        cart = (com.farmarket.farmarket.DataType.ProductCart) BakeryFragment.albumList.get(position);

                        break;
                    case 3:
                        cart = (com.farmarket.farmarket.DataType.ProductCart) DiaryEggFragment.albumList.get(position);
                        break;
                    case 4:
                        cart = (com.farmarket.farmarket.DataType.ProductCart) FishMeatFragment.albumList.get(position);

                        break;
                    case 5:
                        cart = (com.farmarket.farmarket.DataType.ProductCart) BeveragesFragment.albumList.get(position);
                        break;
                    default:
                        cart = (com.farmarket.farmarket.DataType.ProductCart) SearchActivity.albumList.get(position);
                        break;
                }
                cart.setInCart(currentQuantity);
                //display change and get new price
                v.getQuantityInCart().setText(currentQuantity+" "+product.getMeasurement());
                //effect change in cart details

                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                realm.beginTransaction();
                cartDetailsTable.setWeight(currentQuantity);
                realm.copyToRealmOrUpdate(cartDetailsTable);
                realm.commitTransaction();
                notifyDataSetChanged();
            }
        });
        v.getReduceCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo decrease
                //get current quantity
                //effect change in object
                //display change and get new price
                //effect change in cart details

                //get current quantity
                double currentQuantity = product.getInCart();

                currentQuantity -= product.getIncremental();
                if(currentQuantity <= 0.00)
                    currentQuantity = product.getIncremental();
                currentQuantity = Math.round(currentQuantity*100.0)/100.0;
                //get new price
                if(currentQuantity > 0.00)
                {

                    com.farmarket.farmarket.DataType.ProductCart cart;
                    switch (myPosition)
                    {
                        case 0:
                            cart = (com.farmarket.farmarket.DataType.ProductCart) AllProductsFragment.albumList.get(position);
                            break;
                        case 1:
                            cart = (com.farmarket.farmarket.DataType.ProductCart) FruitsVeggiesFragment.albumList.get(position);
                            break;

                        case 2:
                            cart = (com.farmarket.farmarket.DataType.ProductCart) BakeryFragment.albumList.get(position);

                            break;
                        case 3:
                            cart = (com.farmarket.farmarket.DataType.ProductCart) DiaryEggFragment.albumList.get(position);
                            break;
                        case 4:
                            cart = (com.farmarket.farmarket.DataType.ProductCart) FishMeatFragment.albumList.get(position);

                            break;
                        case 5:
                            cart = (com.farmarket.farmarket.DataType.ProductCart) BeveragesFragment.albumList.get(position);
                            break;
                        default:
                            cart = (com.farmarket.farmarket.DataType.ProductCart) SearchActivity.albumList.get(position);
                            break;
                    }

                    cart.setInCart(currentQuantity);
                    //display change and get new price
                    v.getQuantityInCart().setText(currentQuantity+" "+product.getMeasurement());
                    //effect change in cart details
                }
                else {
                    com.farmarket.farmarket.DataType.ProductEmpty productEmpty = new com.farmarket.farmarket.DataType.ProductEmpty();
                    productEmpty.setCreated_at(product.getCreated_at());
                    productEmpty.setDescription(product.getDescription());
                    productEmpty.setFile_blob(product.getFile_blob());
                    productEmpty.setFile_name(product.getFile_name());
                    productEmpty.setName(product.getName());
                    productEmpty.setPrice_per_kg(product.getPrice_per_kg());
                    productEmpty.setProduce_id(product.getProduce_id());
                    productEmpty.setProduce_type(product.getProduce_type());
                    productEmpty.setUnique_code(product.getUnique_code());
                    productEmpty.setProduce_type(product.getProduce_type());
                    productEmpty.setUpdated_at(product.getUpdated_at());
                    productEmpty.setUuid(product.getUuid());
                    productEmpty.setMeasurement(product.getMeasurement());
                    product.setIncremental(product.getIncremental());
                    /*
                    Realm realm = Realm.getDefaultInstance();
                    CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()).findFirst();
                    realm.beginTransaction();
                    cartDetailsTable.setWeight(currentQuantity);
                    realm.copyToRealmOrUpdate(cartDetailsTable);
                    realm.commitTransaction();
                    */

                    switch (myPosition)
                    {
                        case 0:
                             AllProductsFragment.albumList.set(position,productEmpty);
                            break;
                        case 1:
                            FruitsVeggiesFragment.albumList.set(position,productEmpty);
                            break;

                        case 2:
                             BakeryFragment.albumList.set(position,productEmpty);

                            break;
                        case 3:
                            DiaryEggFragment.albumList.set(position,productEmpty);
                            break;
                        case 4:
                             FishMeatFragment.albumList.set(position,productEmpty);

                            break;
                        case 5:
                            BeveragesFragment.albumList.set(position,productEmpty);
                            break;
                        default:
                            SearchActivity.albumList.set(position,productEmpty);
                            break;
                    }

                }
                Realm realm = Realm.getDefaultInstance();
                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                if(cartDetailsTable != null)
                {
                    realm.beginTransaction();
                    cartDetailsTable.setWeight(currentQuantity);
                    realm.copyToRealmOrUpdate(cartDetailsTable);
                    realm.commitTransaction();

                }
                //effect change in object
                notifyDataSetChanged();

            }
        });
        Glide.with(mContext).load(ApiLocation.getImageLocation()+product.getFile_name())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(v.getMainImage());
        v.getMainImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
            }
        });

        v.getMeasurement().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
            }
        });
        v.getNameProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);

            }
        });
        v.getPrice().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
            }
        });

        if(product.getProduce_type().equals("Organic"))
        {
            v.getDiscount().setText(product.getProduce_type());
        }
        else
        {
            v.getDiscount().setVisibility(View.GONE);
        }
        v.getMeasurement().setText("GhC "+GeneralCalculations.getCost(product.getInCart(),Double.parseDouble(product.getPrice_per_kg())));
        v.getNameProduct().setText(product.getName());
        v.getPrice().setText("GhC "+product.getPrice_per_kg()+ "/"+product.getMeasurement_full());
        v.getQuantityInCart().setText(product.getInCart()+" "+product.getMeasurement_full());
    }


    @Override
    public int getItemCount() {
       // Toast.makeText(mContext, ""+albumList.size(), Toast.LENGTH_SHORT).show();
        return albumList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.ProductCart) {

            Realm realm = Realm.getDefaultInstance();
            CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();

            if (cartsTable != null && realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll().size()>0)
            {
               MainActivity. menuItem.setIcon(R.drawable.full_cart);


                MenuItemCompat.setActionView(MainActivity.menuItem, R.layout.cart_layout);
                RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(MainActivity.menuItem);

                TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
                tv.setText(realm.where(CartDetailsTable.class).equalTo("cart_id",realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()).findAll().size()+"");

                notifCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mActivity,CartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        mContext.startActivity(intent);

                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity,CartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        mContext.startActivity(intent);
                    }
                });
            }
            else
            {
                MainActivity.menuItem.setIcon(R.drawable.empty_cart);
                //menu.findItem(R.id.action_cart).setTooltipText("Sorry your cart is empty");
                //menu.findItem(R.id.action_cart).setTitle("0");
            }
            return ProductCart;
        } else if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.ProductEmpty) {
            ((Activity)this.mContext).invalidateOptionsMenu();

            Realm realm = Realm.getDefaultInstance();
            CartsTable cartsTable = realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst();

            if (cartsTable != null && realm.where(CartDetailsTable.class).equalTo("cart_id",cartsTable.getId()).findAll().size()>0)
            {
                MainActivity. menuItem.setIcon(R.drawable.full_cart);


                MenuItemCompat.setActionView(MainActivity.menuItem, R.layout.cart_layout);
                RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(MainActivity.menuItem);

                TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
                tv.setText(realm.where(CartDetailsTable.class).equalTo("cart_id",realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()).findAll().size()+"");

                notifCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mActivity,CartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        mContext.startActivity(intent);

                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity,CartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        mContext.startActivity(intent);
                    }
                });
            }
            else
            {
                MainActivity.menuItem.setIcon(R.drawable.empty_cart);
                //menu.findItem(R.id.action_cart).setTooltipText("Sorry your cart is empty");
                //menu.findItem(R.id.action_cart).setTitle("0");
            }

            return ProductEmpty;
        }if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.CartDetail) {
            return CartDetail;
        }
        if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.CartTotal) {
            return CartTotal;
        }
        if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.Order) {
            return Order;
        }
        if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.OrderDetail) {
            return OrderDetail;
        }
        if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.Transaction) {
            return Transaction;
        }

        return -1;
    }


    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    private void storeAll()
    {
        switch (myPosition)
        {
            case 0:
                this.albumList1.addAll(AllProductsFragment.albumList1);
                break;
            case 1:
                this.albumList1.addAll(FruitsVeggiesFragment.albumList1);
                break;

            case 2:
                this.albumList1.addAll(BakeryFragment.albumList1);

                break;
            case 3:
                this.albumList1.addAll(DiaryEggFragment.albumList1);
                break;
            case 4:
                this.albumList1.addAll(FishMeatFragment.albumList1);
                break;
            case 5:
                this.albumList1.addAll(BeveragesFragment.albumList1);
                break;
            default:
                this.albumList1.addAll(SearchActivity.albumList1);
                break;
        }
    }

    void clearAll()
    {
        switch (myPosition)
        {
            case 0:
                AllProductsFragment.albumList.clear();
                break;
            case 1:
                FruitsVeggiesFragment.albumList.clear();
                break;

            case 2:
                BakeryFragment.albumList.clear();

                break;
            case 3:
                DiaryEggFragment.albumList.clear();
                break;
            case 4:
                FishMeatFragment.albumList.clear();

                break;
            case 5:
                BeveragesFragment.albumList.clear();
                break;
            default:
                SearchActivity.albumList.clear();
                break;
        }

    }

    void addAll()
    {
        switch (myPosition)
        {
            case 0:
                AllProductsFragment.albumList.addAll(albumList1);
                break;
            case 1:
                FruitsVeggiesFragment.albumList.addAll(this.albumList1);
                break;

            case 2:
                BakeryFragment.albumList.addAll(this.albumList1);

                break;
            case 3:
                DiaryEggFragment.albumList.addAll(this.albumList1);
                break;
            case 4:
                FishMeatFragment.albumList.addAll(this.albumList1);

                break;
            case 5:
                BeveragesFragment.albumList.addAll(this.albumList1);
                break;
            default:
                SearchActivity.albumList.addAll(this.albumList1);
                break;
        }


    }
    void addSingle(Object wp)
    {
        switch (myPosition)
        {
            case 0:
                AllProductsFragment.albumList.add(wp);
                break;
            case 1:
                FruitsVeggiesFragment.albumList.add(wp);
                break;

            case 2:
                BakeryFragment.albumList.add(wp);

                break;
            case 3:
                DiaryEggFragment.albumList.add(wp);
                break;
            case 4:
                FishMeatFragment.albumList.add(wp);

                break;
            case 5:
                BeveragesFragment.albumList.add(wp);
                break;
            default:
                SearchActivity.albumList.add(wp);
                System.out.println("Default single");
                break;
        }
    }


    // Filter Class
    public void myFilter(String charText) {
        this.albumList1.clear();
        this.albumList1.addAll(SearchActivity.albumList1);
        charText = charText.toLowerCase(Locale.getDefault());
        SearchActivity.albumList.clear();
        albumList.clear();
        if (charText.length() == 0) {
            SearchActivity.albumList.addAll(this.albumList1);
        } else {
            for (Object wp : this.albumList1) {
                switch (myViewType(wp))
                {
                    case ProductCart:
                        Toast.makeText(mContext,this.albumList1.size()+" 1-- "+this.albumList.size(),Toast.LENGTH_LONG).show();
                        wp = (com.farmarket.farmarket.DataType.ProductCart)wp;
                        if (((com.farmarket.farmarket.DataType.ProductCart) wp).getDescription().toLowerCase().contains(charText) || ((com.farmarket.farmarket.DataType.ProductCart) wp).getName().toLowerCase().contains(charText)) {
                            SearchActivity.albumList.add(wp);
                        }
                        break;
                    case ProductEmpty:
                        wp = (com.farmarket.farmarket.DataType.ProductEmpty)wp;
                        if (((com.farmarket.farmarket.DataType.ProductEmpty) wp).getName().toLowerCase().contains(charText) || ((com.farmarket.farmarket.DataType.ProductEmpty) wp).getDescription().toLowerCase().contains(charText)) {
                            SearchActivity.albumList.add(wp);
                        }
                        break;
                    default:

                        break;
                }
            }
        }
//        imageModelArrayList.addAll(DirectoryActivity.imageModelArrayList);
        notifyDataSetChanged();
    }


    // Filter Class
    public void filter(String charText) {
        this.albumList1.clear();
        storeAll();
        //Toast.makeText(mContext,this.albumList1.size()+" -- "+this.albumList.size(),Toast.LENGTH_LONG).show();

        charText = charText.toLowerCase();
        clearAll();
        albumList.clear();
        if (charText.length() == 0) {
            addAll();

        } else {
            int count =0;
            for (Object wp : albumList1) {
                switch (myViewType(wp))
                {
                    case ProductCart:
                        Toast.makeText(mContext,this.albumList1.size()+" 1-- "+this.albumList.size(),Toast.LENGTH_LONG).show();
                        wp = (com.farmarket.farmarket.DataType.ProductCart)wp;
                        if (((com.farmarket.farmarket.DataType.ProductCart) wp).getDescription().toLowerCase().contains(charText) || ((com.farmarket.farmarket.DataType.ProductCart) wp).getName().toLowerCase().contains(charText)) {
                             addSingle(wp);
                        }
                        break;
                    case ProductEmpty:
                        Toast.makeText(mContext,this.albumList1.size()+" 2-- "+this.albumList.size(),Toast.LENGTH_LONG).show();

                        wp = (com.farmarket.farmarket.DataType.ProductEmpty)wp;
                        System.out.println(charText+" "+((com.farmarket.farmarket.DataType.ProductEmpty) wp).getName() +" "+((com.farmarket.farmarket.DataType.ProductEmpty) wp).getDescription());

                        if (((com.farmarket.farmarket.DataType.ProductEmpty) wp).getName().toLowerCase().contains(charText) || ((com.farmarket.farmarket.DataType.ProductEmpty) wp).getDescription().toLowerCase().contains(charText)) {
                            System.out.println("Added "+charText+" "+((com.farmarket.farmarket.DataType.ProductEmpty) wp).getName() +" "+((com.farmarket.farmarket.DataType.ProductEmpty) wp).getDescription());
                             addSingle(wp);
                        }

                        break;
                    default:

                        break;
                }
                count++;
            }
        }
//        imageModelArrayList.addAll(DirectoryActivity.imageModelArrayList);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position)
    {
        return  position;
        //return imageModelArrayList.get(position).getUser_id();
    }

    private int myViewType(Object o)
    {
        if (o instanceof com.farmarket.farmarket.DataType.ProductEmpty) {
            return ProductEmpty;
        } else if (o instanceof com.farmarket.farmarket.DataType.ProductCart) {
            return ProductCart;
        }
        else if (o instanceof com.farmarket.farmarket.DataType.CartDetail) {
            return CartDetail;
        }
        else if (o instanceof com.farmarket.farmarket.DataType.CartTotal) {
            return CartTotal;
        }
        else if (o instanceof com.farmarket.farmarket.DataType.Order) {
            return Order;
        }
        else if (o instanceof com.farmarket.farmarket.DataType.OrderDetail) {
            return OrderDetail;
        }
        else if (o instanceof com.farmarket.farmarket.DataType.Transaction) {
            return Transaction;
        }

        return -1;
    }

    ProgressDialog progressDialog;
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
        Resources r = mContext.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
