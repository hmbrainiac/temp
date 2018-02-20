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

import com.farmarket.farmarket.DataView.ProductCart;
import com.farmarket.farmarket.DataView.ProductEmpty;
import com.farmarket.farmarket.MainActivity;
import com.farmarket.farmarket.Models.GeneralModel;
import com.farmarket.farmarket.R;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;

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
                        viewHolder = new ProductEmpty(view);

                    }
                    else
                    {
                        view = itemView.inflate(R.layout.products_double_cart_grid, parent, false);
                        viewHolder = new ProductEmpty(view);

                    }
                    break;
                case ProductEmpty:
                    if(userViewSettingTable == null || userViewSettingTable.getViewType().equals("Single"))
                    {
                        view = itemView.inflate(R.layout.products_double_empty_grid, parent, false);
                        viewHolder = new ProductEmpty(view);

                    }
                    else
                    {
                        view = itemView.inflate(R.layout.products_single_empty_grid, parent, false);
                        viewHolder = new ProductEmpty(view);

                    }
                    break;
                default:
                    view = itemView.inflate(R.layout.products_double_empty_grid, parent, false);
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
                    configureCartProduct(vh1,(com.farmarket.farmarket.DataType.ProductCart) albumList.get(position));
                    break;
                case ProductEmpty:
                    ProductEmpty vh2 = (ProductEmpty) holder;
                    configureEmptyProduct(vh2, (com.farmarket.farmarket.DataType.ProductEmpty) albumList.get(position));
                    break;
                default:
                    ProductEmpty vh3 = (ProductEmpty) holder;
                    configureEmptyProduct(vh3, (com.farmarket.farmarket.DataType.ProductEmpty) albumList.get(position));

            }


        }


    private void configureEmptyProduct(ProductEmpty v, final com.farmarket.farmarket.DataType.ProductEmpty product)
    {
        Toast.makeText(mContext,product.getName(),Toast.LENGTH_LONG).show();
        v.getAddToCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo increase
            }
        });
//        v.getAddToCartTV().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
/*
        byte[] decodedString = Base64.decode(product.getFile_blob(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        v.getMainImage().setImageBitmap(decodedByte);
        */
        v.getDiscount().setVisibility(View.GONE);
        v.getMeasurement().setText("Per Kg.");
        v.getNameProduct().setText(product.getName());
        v.getPrice().setText(product.getPrice_per_kg());
    }


    private void configureCartProduct(ProductCart v, final com.farmarket.farmarket.DataType.ProductCart product)
    {
        v.getIncreaseCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo increase
            }
        });
        v.getReduceCart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        byte[] decodedString = Base64.decode(product.getFile_blob(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        v.getMainImage().setImageBitmap(decodedByte);
        v.getDiscount().setVisibility(View.GONE);
        v.getMeasurement().setText("Per Kg.");
        v.getNameProduct().setText(product.getName());
        v.getPrice().setText(product.getPrice_per_kg());
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
