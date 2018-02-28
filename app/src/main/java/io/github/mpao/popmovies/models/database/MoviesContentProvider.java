package io.github.mpao.popmovies.models.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import io.github.mpao.popmovies.R;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.CONTENT_URI;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.TABLE_NAME;

/*
 * No injection here.
 * As pointed out at https://stackoverflow.com/questions/23521083/inject-database-in-a-contentprovider-with-dagger/23832168
 * It's not worth it in this simple class
 */
public class MoviesContentProvider extends ContentProvider {

    DbHelper dbHelper;
    Context context;
    // use always the conventional mode: 100,200... for directories and 101,102... for items in that direcotry
    // 42 is not the answer here :(
    public static final int FAVS = 100;
    public static final int FAV_MOVIE = 101;
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AppContract.AUTHORITY, AppContract.PATH_MOVIES, FAVS);
        uriMatcher.addURI(AppContract.AUTHORITY, AppContract.PATH_MOVIES + "/#", FAV_MOVIE);
        return uriMatcher;

    }

    /*
     * Initialize the Content Provider with injected context
     */
    @Override
    public boolean onCreate() {

        this.context = getContext();
        dbHelper = new DbHelper(getContext());
        return true;

    }

    //region CRUD methods
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = URI_MATCHER.match(uri);

        Uri returnUri;
        switch (match){
            case FAVS:
                long id = db.insert(TABLE_NAME, null, values);
                if( id > 0 ){
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                }else {
                    // -1 returned
                    throw new SQLException(context.getString(R.string.network_error));
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI");
        }

        context.getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = URI_MATCHER.match(uri);

        Cursor cursor;
        switch (match){
            case FAVS:
                cursor = db.query(TABLE_NAME, projection, selection,selectionArgs,null,null,sortOrder);
                break;
            case FAV_MOVIE:
                cursor = db.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        "id="+uri.getPathSegments().get(1),
                        sortOrder);
            default:
                throw new UnsupportedOperationException(context.getString(R.string.network_error));
        }

        cursor.setNotificationUri( context.getContentResolver(), uri);
        return cursor;

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: (not required now) Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = URI_MATCHER.match(uri);
        int removed;

        switch (match) {
            case FAV_MOVIE:
                String id = uri.getPathSegments().get(1);
                removed = db.delete(TABLE_NAME, "id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (removed != 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return removed;

    }
    //endregion

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: (not required now) Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
