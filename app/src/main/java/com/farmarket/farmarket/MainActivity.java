package com.farmarket.farmarket;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import com.farmarket.farmarket.DataType.Category;
import com.farmarket.farmarket.DataType.Product;
import com.farmarket.farmarket.DataType.ProductCart;
import com.farmarket.farmarket.DataType.ProductEmpty;
import com.farmarket.farmarket.Fragments.AllProductsFragment;
import com.farmarket.farmarket.Fragments.BakeryFragment;
import com.farmarket.farmarket.Fragments.BeveragesFragment;
import com.farmarket.farmarket.Fragments.DiaryEggFragment;
import com.farmarket.farmarket.Fragments.FishMeatFragment;
import com.farmarket.farmarket.Fragments.FruitsVeggiesFragment;
import com.farmarket.farmarket.Fragments.ProductsFragment;
import com.farmarket.farmarket.Models.ProduceModel;
import com.farmarket.farmarket.RealmTables.CartDetailsTable;
import com.farmarket.farmarket.RealmTables.CartsTable;
import com.farmarket.farmarket.RealmTables.OrderDetailTable;
import com.farmarket.farmarket.RealmTables.UserTable;
import com.farmarket.farmarket.RealmTables.UserViewSettingTable;
import com.google.android.gms.appinvite.AppInviteInvitation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    public static ArrayList<ProductsFragment> fragments;
    public static List<Object> albumList,albumList1;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Realm realm;
    TextView name,email;
    UserTable userTable;
    public static MenuItem menuItem;
    public static ArrayList<Category> categories;
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Fud Farma");

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        fragments = new ArrayList<>();
        try
        {
            categories = (ArrayList<Category>) intent.getExtras().get("categories");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
        realm = Realm.getDefaultInstance();
        userTable = realm.where(UserTable.class).findFirst();
        if(userTable != null)
        {
            name.setText(userTable.getFirstname()+" "+userTable.getLastname());
            email.setText(userTable.getEmail());
        }



        viewPager = (ViewPager) findViewById(R.id.viewpager);
       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    public static ViewPagerAdapter adapter;

    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllProductsFragment(), "All");
        adapter.addFragment(new FruitsVeggiesFragment(), "Fruits & Veggies");
        adapter.addFragment(new BakeryFragment(), "Bakery");
        adapter.addFragment(new DiaryEggFragment(), "Diary & Eggs");
        adapter.addFragment(new FishMeatFragment(), "Fish & Meat");
        adapter.addFragment(new BeveragesFragment(), "Beverages");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {

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
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        //menuItem = menu.findItem( R.id.action_search);
        myActionMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                switch (adapter.getItem(viewPager.getCurrentItem()).getId())
                {
                    case 0:
                        intent.putExtra("albumlist", (Serializable) AllProductsFragment.albumList);
                        break;
                    case 1:
                        intent.putExtra("albumlist", (Serializable) FruitsVeggiesFragment.albumList);
                        break;

                    case 2:
                        intent.putExtra("albumlist", (Serializable) BakeryFragment.albumList);

                        break;
                    case 3:
                        intent.putExtra("albumlist", (Serializable) DiaryEggFragment.albumList);
                        break;
                    case 4:
                        intent.putExtra("albumlist", (Serializable) FishMeatFragment.albumList);
                        break;
                    case 5:
                        intent.putExtra("albumlist", (Serializable) BeveragesFragment.albumList);
                        break;
                    default:
                        intent.putExtra("albumlist", (Serializable) AllProductsFragment.albumList);
                        break;
                }


                startActivity(intent);

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
                    startActivity(intent);

                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,CartActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
          menuItem.setIcon(R.drawable.empty_cart);
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
