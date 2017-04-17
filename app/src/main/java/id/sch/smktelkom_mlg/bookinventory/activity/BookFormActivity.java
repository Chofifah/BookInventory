package id.sch.smktelkom_mlg.bookinventory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sch.smktelkom_mlg.bookinventory.R;
import id.sch.smktelkom_mlg.bookinventory.model.Book;

public class BookFormActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.editAuthor)
    EditText editAuthor;
    @BindView(R.id.editGenre)
    EditText editGenre;
    @BindView(R.id.editIsbn)
    EditText editIsbn;
    @BindView(R.id.editYear)
    EditText editYear;
    @BindView(R.id.editSynopsis)
    EditText editSynopsis;
    @BindView(R.id.btnSave)
    Button btnSave;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        //utk view detail
        if (bundle != null) {
            book = (Book) bundle.getSerializable("bookEdit");
            editIsbn.setText(book.getISBN());
            editYear.setText(book.getPublished_year() + "");
            editAuthor.setText(book.getBook_author());
            editTitle.setText(book.getBook_title());
            editGenre.setText(book.getBook_genre());
            editSynopsis.setText(book.getBook_synopsis());
            btnSave.setEnabled(false);
            getSupportActionBar().setTitle(book.getBook_title());
        } else {
            book = new Book();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    book.setISBN(editIsbn.getText().toString());
                    book.setBook_title(editTitle.getText().toString());
                    book.setBook_author(editAuthor.getText().toString());
                    book.setPublished_year(Integer.parseInt(editYear.getText().toString()));
                    book.setBook_genre(editGenre.getText().toString());
                    book.setBook_synopsis(editSynopsis.getText().toString().equals("") ? "-" : editSynopsis.getText().toString());

                    Intent i = new Intent();
                    i.putExtra("book", book);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {
        boolean valid = true;

        String isbn = editIsbn.getText().toString();
        String booktitle = editTitle.getText().toString();
        String bookauthor = editAuthor.getText().toString();
        String publishedYear = editYear.getText().toString();

        if (isbn.isEmpty()) {
            editIsbn.setError("Enter ISBN");
            valid = false;
        } else {
            editIsbn.setError(null);
        }

        if (booktitle.isEmpty()) {
            editTitle.setError("Enter Book Title");
            valid = false;
        } else {
            editTitle.setError(null);
        }

        if (bookauthor.isEmpty()) {
            editAuthor.setError("Enter Book Author");
            valid = false;
        } else {
            editAuthor.setError(null);
        }

        if (publishedYear.isEmpty() || publishedYear.length() < 4) {
            editYear.setError("Publish Year empty or must in yyyy format");
            valid = false;
        } else {
            editYear.setError(null);
        }
        return valid;
    }
}