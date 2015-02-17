package io.github.filipebezerra.ebooksfordevelopers.ui.activity;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v7.widget.*;
import android.view.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.pnikosis.materialishprogress.*;
import io.github.filipebezerra.ebooksfordevelopers.*;
import io.github.filipebezerra.ebooksfordevelopers.adapter.*;
import io.github.filipebezerra.ebooksfordevelopers.app.*;
import io.github.filipebezerra.ebooksfordevelopers.model.*;
import io.github.filipebezerra.ebooksfordevelopers.rest.model.*;
import io.github.filipebezerra.ebooksfordevelopers.widget.*;
import java.util.*;
import org.json.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import io.github.filipebezerra.ebooksfordevelopers.R;
import android.provider.*;
import io.github.filipebezerra.ebooksfordevelopers.provider.*;

public class HomeActivity extends AbstractNavDrawerActivity {

	private static final String TAG = HomeActivity.class.getSimpleName();

	private static final int ID_NAV_MENU_SECTION_EBOOK_CATEGORIES = 10;
	private static final int ID_NAV_MENU_ITEM_TOP_DOWNLOAD_EBOOKS = 11;
	private static final int ID_NAV_MENU_ITEM_LAST_UPLOAD_EBOOKS = 12;
	private static final int ID_NAV_MENU_ITEM_NEW_EBOOKS = 13;
	
	private static final int ID_NAV_MENU_SECTION_USER_STUFFS = 20;
	private static final int ID_NAV_MENU_ITEM_USER_FAVORITE_EBOOKS = 21;
		
	private static final int DEFAULT_NAV_MENU_ITEM_SELECTED = 1;
	private RecyclerView mList;
	private RecyclerAdapter mAdapter;
	private RecyclerView.LayoutManager mLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		selectItem(DEFAULT_NAV_MENU_ITEM_SELECTED);
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.home_activity;
	}

	@Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		final NavDrawerItem [] navItems = new NavDrawerItem [] {
			NavMenuSection.create(ID_NAV_MENU_SECTION_EBOOK_CATEGORIES, getString(R.string.drawer_section_ebook_categories)),
			NavMenuItem.create(ID_NAV_MENU_ITEM_TOP_DOWNLOAD_EBOOKS, getString(R.string.drawer_item_top_download_ebooks), "ic_stars_white_24dp", true, this),
			NavMenuItem.create(ID_NAV_MENU_ITEM_LAST_UPLOAD_EBOOKS, getString(R.string.drawer_item_last_upload_ebooks), "ic_cloud_upload_white_24dp", true, this),
			NavMenuItem.create(ID_NAV_MENU_ITEM_NEW_EBOOKS, getString(R.string.drawer_item_new_ebooks), "ic_new_releases_white_24dp", true, this),
			
			NavMenuSection.create(ID_NAV_MENU_SECTION_USER_STUFFS, getString(R.string.drawer_section_user_stuffs)),
			NavMenuItem.create(ID_NAV_MENU_ITEM_USER_FAVORITE_EBOOKS, getString(R.string.drawer_item_user_favorite_ebooks), "ic_favorite_white_24dp", true, this)
		};
		
        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
        navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer);
        navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
        navDrawerActivityConfiguration.setNavItems(navItems);

        // TODO: Set the Drawer Shadow
        navDrawerActivityConfiguration.setDrawerShadow(0);
        navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_title_opened);
        navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_title_closed);
        navDrawerActivityConfiguration.setBaseAdapter(
			new NavDrawerAdapter(this, R.layout.navdrawer_item, navItems));

        // TODO: Maybe in the future, disabling the menu items
        navDrawerActivityConfiguration.setActionMenuItemsToHideWhenDrawerOpen(null);

        return navDrawerActivityConfiguration;
    }

	@Override
	protected void onNavItemSelected(int id) {
		switch(id) {
			case ID_NAV_MENU_ITEM_TOP_DOWNLOAD_EBOOKS:
				break;
				
			case ID_NAV_MENU_ITEM_LAST_UPLOAD_EBOOKS:
				break;
				
			case ID_NAV_MENU_ITEM_NEW_EBOOKS:
				break;
				
			case ID_NAV_MENU_ITEM_USER_FAVORITE_EBOOKS:
				break;
		}
	}

	@Override
	protected void loadContentView() {
		mList = (RecyclerView) findViewById(R.id.list);
		mList.setHasFixedSize(true);

		mLayout = new LinearLayoutManager(this);
		mList.setLayoutManager(mLayout);

		mAdapter = new RecyclerAdapter(new ArrayList<RecyclerModel>(), R.layout.recycler_item);
		mAdapter.setOnItemClickListener(new OnRecyclerViewItemListener<RecyclerModel>() {
				@Override
				public void onItemClick(View view, RecyclerModel model) {
					Intent intent = new Intent(HomeActivity.this, BookDetailsActivity.class);
					intent.putExtra(BookDetailsActivity.EXTRA_BOOK_ID, model.getId());
					startActivity(intent);
				}
			});
		mList.setAdapter(mAdapter);

		mList.setItemAnimator(new DefaultItemAnimator());

		mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home_menu, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onStop() {
		super.onStop();
		AppSingleton.getInstance().cancelAll(TAG);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			final String query = intent.getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestionsProvider = new SearchRecentSuggestions(this, SuggestionsProvider.AUTHORITY, SuggestionsProvider.DATABASE_MODE_QUERIES);
			suggestionsProvider.saveRecentQuery(query, null);
			
			Uri.Builder builder = Uri.parse(END_POINT).buildUpon();
			builder.appendPath("search");
			builder.appendPath(query);
			
			doSearch(builder.toString(), TAG);
		}
	}

	@Override
	protected void parseResponse(JSONObject response) throws JSONException {
		if (response.has("Books")) {
			BookSearchResponse bookSearch = null;
			
			try {
				mAdapter.removeAll();

				final String error = response.getString("Error");
				final Double time = response.getDouble("Time");
				final Integer page = response.getInt("Page");
				final String total = response.getString("Total");

				final JSONArray books = response.getJSONArray("Books");

				bookSearch = new BookSearchResponse(error, time, page, total);

				for (int index = 0 ; index < books.length(); index++) {
					JSONObject jsonObj = books.getJSONObject(index);

					Long bookId = jsonObj.getLong("ID");
					String bookDescription = jsonObj.getString("Description");
					String bookTitle = jsonObj.getString("Title");
					String bookSubtitle = jsonObj.getString("SubTitle");
					String bookImage = jsonObj.getString("Image");
					String bookIsbn = jsonObj.getString("isbn");

					Book book = new Book(bookId, bookTitle, bookSubtitle, bookDescription, bookImage, bookIsbn);

					bookSearch.getBooks().add(book);

					mAdapter.add(new RecyclerModel(book.getId(), book.getTitle(), book.getImage()));
				}
			} catch (Exception e) {
				showToast(e.getLocalizedMessage());
			} finally {
				if (bookSearch != null) {
					getSupportActionBar().setSubtitle(String.format("%d eBooks found", bookSearch.getBooks().size()));
				}
			}
		}
	}

	private void loadTopDownload() {
		StringRequest request = new StringRequest(Request.Method.GET, BASE_URL, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Document doc = Jsoup.parse(response);
					Elements elements = doc.getElementsByTag("h2");
					
					for (Element element : elements) {
						if (getString(R.string.drawer_item_top_download_ebooks).equalsIgnoreCase(element.text())) {
							
							break;
						}
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					showToast(error.getLocalizedMessage());
				}

			});

		AppSingleton.getInstance().addToRequestQueue(TAG, request);
	}

}
