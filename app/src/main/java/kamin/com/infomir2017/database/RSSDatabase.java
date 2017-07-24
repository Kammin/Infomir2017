package kamin.com.infomir2017.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kamin.com.infomir2017.Model.RSS;


public class RSSDatabase extends SQLiteOpenHelper {
    private static final String LOG = "logDB";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rssDB";
    private static final String TABLE_RSS = "tableRSS";

    private static final String KEY_ID = "id";
    private static final String KEY_RSS_LINK = "rss_link";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LINK = "link";
    private static final String KEY_DESCRIPTION = "description";

    public RSSDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RSS_TABLE = "CREATE TABLE " + TABLE_RSS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_LINK
                + " TEXT," + KEY_RSS_LINK + " TEXT," + KEY_DESCRIPTION
                + " TEXT" + ")";
        db.execSQL(CREATE_RSS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RSS);
        // Create tables again
        onCreate(db);
    }


    public void addrss(RSS rss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, rss.title);
        values.put(KEY_LINK, rss.link);
        values.put(KEY_RSS_LINK, rss.RSSLink);
        values.put(KEY_DESCRIPTION, rss.description);

        if (!isRSSExists(db, rss.RSSLink)) {
            long count = db.insert(TABLE_RSS, null, values);
                Log.d(LOG,"insert RSS to "+ count);
            db.close();
        } else {
            updateRSS(rss);
            db.close();
        }
    }


    public List<RSS> getAllRSS() {
        List<RSS> rssList = new ArrayList<RSS>();
        String selectQuery = "SELECT  * FROM " + TABLE_RSS
                + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(LOG,"fethed  ---------");
        if (cursor.moveToFirst()) {
            do {
                RSS rss = new RSS();
                rss.id = Integer.parseInt(cursor.getString(0));
                rss.title = cursor.getString(1);
                rss.link = cursor.getString(2);
                rss.RSSLink = cursor.getString(3);
                rss.description = cursor.getString(4);
                rssList.add(rss);
                Log.d(LOG,"fethed rss.id"+ rss.id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(LOG,"fethed "+ rssList.size()+ " items");
        return rssList;
    }


    public int updateRSS(RSS rss) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, rss.title);
        values.put(KEY_LINK, rss.link);
        values.put(KEY_RSS_LINK, rss.RSSLink);
        values.put(KEY_DESCRIPTION, rss.description);

        int update = db.update(TABLE_RSS, values, KEY_RSS_LINK + " = ?",
                new String[] { String.valueOf(rss.RSSLink)});
        if(update==1)
            Log.d(LOG,"update one RSS");
        db.close();
        return update;

    }


    public RSS getRss(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RSS, new String[] { KEY_ID, KEY_TITLE,
                        KEY_LINK, KEY_RSS_LINK, KEY_DESCRIPTION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        RSS rss = new RSS(cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));

        rss.id = Integer.parseInt(cursor.getString(0));
        rss.title = cursor.getString(1);
        rss.link = cursor.getString(2);
        rss.RSSLink = cursor.getString(3);
        rss.description = cursor.getString(4);
        cursor.close();
        db.close();
        return rss;
    }


    public void deleteRss(RSS rss) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(LOG,"DBdelete rss.id"+ rss.id);
        db.delete(TABLE_RSS, KEY_ID + " = ?",
                new String[] { String.valueOf(rss.id)});

        db.close();
    }


    public boolean isRSSExists(SQLiteDatabase db, String rss_link) {

        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_RSS
                + " WHERE rss_link = '" + rss_link + "'", new String[] {});
        boolean exists = (cursor.getCount() > 0);
        return exists;
    }

}