package com.farmarket.farmarket.Adaptors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.farmarket.farmarket.Api.ApiLocation;
import com.farmarket.farmarket.DataView.ProductCart;
import com.farmarket.farmarket.DataView.ProductEmpty;
import com.farmarket.farmarket.MainActivity;
import com.farmarket.farmarket.Models.GeneralModel;
import com.farmarket.farmarket.R;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;
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
        private final int ProductEmpty = 1, ProductCart = 2;
        public static TextView title, count;
        public static ImageView overflow;
        VideoView thumbnail;
        Activity mActivity;
        private static DecimalFormat df2 = new DecimalFormat(".##");
        //http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4


    public MultiCustomAdapter(Context mContext, List<Object> albumList) {
            this.mContext = mContext;
            this.albumList = albumList;
            albumList1 = new ArrayList<Object>();
            this.albumList1.addAll(MainActivity.albumList1);
        }
    public MultiCustomAdapter(Context mContext, Activity mActivity ,List<Object> albumList) {
            this.mContext = mContext;
            this.albumList = albumList;
            this.mActivity = mActivity;
            albumList1 = new ArrayList<Object>();
            this.albumList1.addAll(MainActivity.albumList1);
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
                default:
                    ProductEmpty vh3 = (ProductEmpty) holder;
                    configureEmptyProduct(vh3, (com.farmarket.farmarket.DataType.ProductEmpty) albumList.get(position),position);

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
            }
        });
        v.getAddToCartTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(intent);
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
            }
        });
        v.getDiscount().setVisibility(View.GONE);
        v.getMeasurement().setText("Per Kg.");
        v.getNameProduct().setText(product.getName());
        v.getPrice().setText("GhC "+product.getPrice_per_kg());
    }




    private void configureCartProduct(final ProductCart v, final com.farmarket.farmarket.DataType.ProductCart product, final int position)
    {
        final Intent intent  = new Intent(mContext,SingleItemActivity.class);
        intent.putExtra("product", product);

        v.getIncreaseCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo increase
                //get current quantity
                double currentQuantity = product.getInCart();
                Realm realm =Realm.getDefaultInstance();
                //increase by 0.2
                currentQuantity += 0.2;
                currentQuantity = Math.round(currentQuantity*100.0)/100.0;

                //get new price

                //effect change in object
                com.farmarket.farmarket.DataType.ProductCart cart = (com.farmarket.farmarket.DataType.ProductCart) MainActivity.albumList.get(position);
                cart.setInCart(currentQuantity);
                //display change and get new price
                v.getQuantityInCart().setText(currentQuantity+"");
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
                //increase by - 0.2
                //effect change in object
                //display change and get new price
                //effect change in cart details

                //get current quantity
                double currentQuantity = product.getInCart();

                //increase by 0.2
                currentQuantity -= 0.2;
                currentQuantity = Math.round(currentQuantity*100.0)/100.0;
                //get new price
                if(currentQuantity > 0.00)
                {
                    com.farmarket.farmarket.DataType.ProductCart cart = (com.farmarket.farmarket.DataType.ProductCart) MainActivity.albumList.get(position);
                    cart.setInCart(currentQuantity);
                    //display change and get new price
                    v.getQuantityInCart().setText(currentQuantity+"");
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
                    Realm realm = Realm.getDefaultInstance();
                    CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()).findFirst();
                    realm.beginTransaction();
                    cartDetailsTable.deleteFromRealm();
                    realm.commitTransaction();
                    MainActivity.albumList.set(position,productEmpty);
                }
                Realm realm = Realm.getDefaultInstance();
                CartDetailsTable cartDetailsTable = realm.where(CartDetailsTable.class).equalTo("produce_id",product.getProduce_id()).equalTo("cart_id",Integer.parseInt(realm.where(CartsTable.class).equalTo("cart_status","Pending").findFirst().getId()+"")).findFirst();
                realm.beginTransaction();
                cartDetailsTable.setWeight(currentQuantity);
                realm.copyToRealmOrUpdate(cartDetailsTable);
                realm.commitTransaction();
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



        v.getDiscount().setVisibility(View.GONE);
        v.getMeasurement().setText("Per Kg.");
        v.getNameProduct().setText(product.getName());
        v.getPrice().setText("GhC "+product.getPrice_per_kg());
        v.getQuantityInCart().setText(product.getInCart()+"");
    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.ProductCart) {
            return ProductCart;
        } else if (albumList.get(position) instanceof com.farmarket.farmarket.DataType.ProductEmpty) {
            return ProductEmpty;
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



    // Filter Class
    public void filter(String charText) {
        this.albumList1.clear();
        this.albumList1.addAll(MainActivity.albumList1);
        charText = charText.toLowerCase(Locale.getDefault());
        MainActivity.albumList.clear();
        albumList.clear();
        if (charText.length() == 0) {
            MainActivity.albumList.addAll(albumList1);
        } else {
            int count =0;
            for (Object wp : albumList1) {
                switch (myViewType(wp))
                {
                    case ProductCart:
                        wp = (com.farmarket.farmarket.DataType.ProductCart)wp;
                        if (((com.farmarket.farmarket.DataType.ProductCart) wp).getDescription().toLowerCase(Locale.getDefault()).contains(charText) || ((com.farmarket.farmarket.DataType.ProductCart) wp).getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                            Boolean idExists = false;

                            if(idExists == false)
                                MainActivity.albumList.add(wp);
                        }

                        break;
                    case ProductEmpty:
                        wp = (com.farmarket.farmarket.DataType.ProductEmpty)wp;
                        if (((com.farmarket.farmarket.DataType.ProductEmpty) wp).getName().toLowerCase(Locale.getDefault()).contains(charText) || ((com.farmarket.farmarket.DataType.ProductEmpty) wp).getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                            Boolean idExists = false;

                            if(idExists == false)
                                MainActivity.albumList.add(wp);
                        }

                        break;
                    default:

                        break;
                }
                try {
                    wp = (GeneralModel)wp;
                    if (((GeneralModel) wp).getDescription().toLowerCase(Locale.getDefault()).contains(charText) || ((GeneralModel) wp).getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        Boolean idExists = false;

                        if(idExists == false)
                            MainActivity.albumList.add(wp);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();

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
        return -1;
    }

    ProgressDialog progressDialog;

}
