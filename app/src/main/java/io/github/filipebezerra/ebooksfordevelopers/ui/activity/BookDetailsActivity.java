package io.github.filipebezerra.ebooksfordevelopers.ui.activity;
import io.github.filipebezerra.ebooksfordevelopers.*;
import io.github.filipebezerra.ebooksfordevelopers.rest.model.BookDetailsResponse;

import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class BookDetailsActivity extends BaseActivity {

    private static final String TAG = BookDetailsActivity.class.getSimpleName();

    public static final String EXTRA_BOOK_ID = "BOOK_ID";

    private ImageView mBookImageView;
    private TextView mBookTitleView;
    private TextView mBookSubtitleView;
    private TextView mBookAuthorView;
    private TextView mBookPublisherView;
    private TextView mBookYearView;
    private TextView mBookIsbnView;
    private TextView mBookPagesView;
    private TextView mBookDescriptionView;

    private BookDetailsResponse mBookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra(EXTRA_BOOK_ID)) {
                Uri.Builder builder = Uri.parse(END_POINT).buildUpon();
                builder.appendPath("book");
                builder.appendPath(String.valueOf(intent.getLongExtra(EXTRA_BOOK_ID, -1)));

                doSearch(builder.toString(), TAG);
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.book_details_activity;
    }

    @Override
    protected void loadContentView() {
		/*mBookImageView = (ImageView) findViewById(R.id.book_detail_image);
		mBookTitleView = (TextView) findViewById(R.id.book_detail_title);
		mBookSubtitleView = (TextView) findViewById(R.id.book_detail_subtitle);
		mBookAuthorView = (TextView) findViewById(R.id.book_detail_author);
		mBookPublisherView = (TextView) findViewById(R.id.book_detail_publisher);
		mBookYearView = (TextView) findViewById(R.id.book_detail_year);
		mBookIsbnView = (TextView) findViewById(R.id.book_detail_isbn);
		mBookDescriptionView = (TextView) findViewById(R.id.book_detail_description);

		mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
		*/
    }

    @Override
    protected void parseResponse(JSONObject response) throws JSONException {
        if (response.has("Error") && (! response.getString("Error").equals("0"))) {
            showToast(response.getString("Error"));
            finish();
        } else if (response.has("ID")) {
            try {
                final Long id = response.getLong("ID");
                final String title = response.getString("Title");
                final String subtitle = response.getString("SubTitle");
                final String description = response.getString("Description");
                final String author = response.getString("Author");
                final String isbn = response.getString("ISBN");
                final String pages = response.getString("Page");
                final String year = response.getString("Year");
                final String publisher = response.getString("Publisher");
                final String image = response.getString("Image");
                final String download = response.getString("Download");

                mBookDetail = new BookDetailsResponse(
                        id, title, subtitle, description,
                        author, isbn, pages, year, publisher,
                        image, download
                );
            } catch (Exception e) {
                showToast(e.getLocalizedMessage());
            } finally {
                if (mBookDetail != null) {
                    mBookTitleView.setText(mBookDetail.getTitle());
                    mBookSubtitleView.setText(mBookDetail.getSubtitle());
                    //mBookAuthorView.setText(mBookDetail.getAuthor());
                    //mBookPublisherView.setText(mBookDetail.getPublisher());
                    //mBookYearView.setText(mBookDetail.getYear());
                    //mBookIsbnView.setText(mBookDetail.getIsbn());
                    //mBookPagesView.setText(mBookDetail.getPages());
                    //mBookDescriptionView.setText(mBookDetail.getDescription());

                    //Picasso.with(mBookImageView.getContext()).load(mBookDetail.getImage()).into(mBookImageView);

                    getSupportActionBar().setTitle(mBookDetail.getTitle());
                }
            }
        }
    }

}
