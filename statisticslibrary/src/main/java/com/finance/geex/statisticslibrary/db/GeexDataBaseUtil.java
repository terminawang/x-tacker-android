package com.finance.geex.statisticslibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.finance.geex.statisticslibrary.mananger.GeexDataApi;
import com.finance.geex.statisticslibrary.upload.DataUploadService;
import com.finance.geex.statisticslibrary.util.gson.NullStringToEmptyAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/8/14 14:56.
 * 数据增删改查
 *
 * @author Geex302
 */
public class GeexDataBaseUtil {


    /**
     * 插入单条数据
     *
     * @param type   1:埋点表 2:网络请求表
     * @param object
     */
    public synchronized static void insertSingleData(int type, Object object) {

        SQLiteDatabase mDb = null;
        try {

            mDb = getDb();
            //geexDataBean转化为json串
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();

            ContentValues contentValues = new ContentValues();
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                GeexDataBean geexDataBean = (GeexDataBean) object;
                String geexData = gson.toJson(geexDataBean, GeexDataBean.class);
                if(geexData == null){
                    contentValues.put(GeexDbParams.KEY_DATA, "");
                }else {
                    contentValues.put(GeexDbParams.KEY_DATA, geexData);
                }
                mDb.insert(GeexDbParams.TABLE_EVENTS, null, contentValues);
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                GeexNetworkRequestBean geexNetworkRequestBean = (GeexNetworkRequestBean) object;
                String geexData = gson.toJson(geexNetworkRequestBean, GeexNetworkRequestBean.class);
                if(geexData == null){
                    contentValues.put(GeexDbParams.KEY_DATA, "");
                }else {
                    contentValues.put(GeexDbParams.KEY_DATA, geexData);
                }
                mDb.insert(GeexDbParams.TABLE_NETWORK_REQUEST, null, contentValues);
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                GeexErrDataBean geexErrDataBean = (GeexErrDataBean) object;
                String geexData = gson.toJson(geexErrDataBean, GeexErrDataBean.class);
                if(geexData == null){
                    contentValues.put(GeexDbParams.KEY_DATA, "");
                }else {
                    contentValues.put(GeexDbParams.KEY_DATA, geexData);
                }
                mDb.insert(GeexDbParams.TABLE_APP_CRASH, null, contentValues);
            }

            mDb.close();
        } catch (Exception e) {
            if (mDb != null && mDb.isOpen()) {
                mDb.close();
            }
        }

    }

    /**
     * 插入多条数据(数据不应太多)
     *
     * @param type
     * @param object
     */
    public synchronized static void insertMultiData(int type, Object object) {

        try {

            SQLiteDatabase mDb = getDb();
            ContentValues contentValues = new ContentValues();
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                List<GeexDataBean> geexDataBeans = (List<GeexDataBean>) object;
                for (int i = 0; i < geexDataBeans.size(); i++) {
                    //geexDataBean转化为json串
                    GeexDataBean geexDataBean = geexDataBeans.get(i);
                    String geexData = new Gson().toJson(geexDataBean);
                    contentValues.put(GeexDbParams.KEY_DATA, geexData);
                    mDb.insert(GeexDbParams.TABLE_EVENTS, null, contentValues);
                }
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                List<GeexNetworkRequestBean> geexNetworkRequestBeans = (List<GeexNetworkRequestBean>) object;
                for (int i = 0; i < geexNetworkRequestBeans.size(); i++) {
                    //geexDataBean转化为json串
                    GeexNetworkRequestBean geexNetworkRequestBean = geexNetworkRequestBeans.get(i);
                    String geexData = new Gson().toJson(geexNetworkRequestBean);
                    contentValues.put(GeexDbParams.KEY_DATA, geexData);
                    mDb.insert(GeexDbParams.TABLE_NETWORK_REQUEST, null, contentValues);
                }
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                List<GeexErrDataBean> geexErrDataBeans = (List<GeexErrDataBean>) object;
                for (int i = 0; i < geexErrDataBeans.size(); i++) {
                    //geexDataBean转化为json串
                    GeexErrDataBean geexErrDataBean = geexErrDataBeans.get(i);
                    String geexData = new Gson().toJson(geexErrDataBean);
                    contentValues.put(GeexDbParams.KEY_DATA, geexData);
                    mDb.insert(GeexDbParams.TABLE_APP_CRASH, null, contentValues);
                }
            }


            mDb.close();

        } catch (Exception e) {


        }


    }

    /**
     * 查询所有
     */
    public synchronized static <T> List<T> queryAll(int type) {

        List<T> allData = new ArrayList<>();
        SQLiteDatabase mDb = null;
        Cursor cursor = null;
        try {

            mDb = getDb();

            //获取数据库的游标
            //This interface provides random read-write access to the result set returned
            // by a database query.
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                cursor = mDb.query(GeexDbParams.TABLE_EVENTS, new String[]{"_id, " + GeexDbParams.KEY_DATA}, null,
                        null, null, null, null);
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                cursor = mDb.query(GeexDbParams.TABLE_NETWORK_REQUEST, new String[]{"_id, " + GeexDbParams.KEY_DATA}, null,
                        null, null, null, null);
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                cursor = mDb.query(GeexDbParams.TABLE_APP_CRASH, new String[]{"_id, " + GeexDbParams.KEY_DATA}, null,
                        null, null, null, null);
            }
            //迭代游标
            if (cursor != null) {
                boolean firstCursor = cursor.moveToFirst();
                if (!firstCursor) {
                    return null;
                }
                Gson gson = new Gson();

                for (int i = 0; i < cursor.getCount(); i++) {

                    String data = cursor.getString(cursor.getColumnIndex(GeexDbParams.KEY_DATA));
                    if (type == DataUploadService.TABLE_EVENTS_DATA) {
                        GeexDataBean geexDataBean = gson.fromJson(data, GeexDataBean.class);
                        allData.add((T) geexDataBean);
                    } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                        GeexNetworkRequestBean geexNetworkRequestBean = gson.fromJson(data, GeexNetworkRequestBean.class);
                        allData.add((T) geexNetworkRequestBean);
                    } else {
                        GeexErrDataBean geexErrDataBean = gson.fromJson(data, GeexErrDataBean.class);
                        allData.add((T) geexErrDataBean);
                    }

                    cursor.moveToNext();
                }

                cursor.close();
            }
            mDb.close();

        } catch (Exception e) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (mDb != null && mDb.isOpen()) {
                mDb.close();
            }

        }

        return allData;

    }


    /**
     * 查询数据库条数
     *
     * @return count
     */
    public synchronized static int getCursorCount(int type) {
        int count = 0;
        SQLiteDatabase mDb = null;
        Cursor cursor = null;
        try {
            mDb = getDb();
            // 获取数据库的游标
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                cursor = mDb.rawQuery("select _id," + GeexDbParams.KEY_DATA + " from " + GeexDbParams.TABLE_EVENTS, null);
                count = cursor.getCount();
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                cursor = mDb.rawQuery("select _id," + GeexDbParams.KEY_DATA + " from " + GeexDbParams.TABLE_NETWORK_REQUEST, null);
                count = cursor.getCount();
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                cursor = mDb.rawQuery("select _id," + GeexDbParams.KEY_DATA + " from " + GeexDbParams.TABLE_APP_CRASH, null);
                count = cursor.getCount();
            }
            if (cursor != null) {
                cursor.close();
            }
            mDb.close();
        } catch (Exception e) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (mDb != null && mDb.isOpen()) {
                mDb.close();
            }
        }

        return count;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public synchronized static Object queryById(int type, int id) {

        Cursor cursor = null;
        try {
            SQLiteDatabase mDb = getDb();


            Gson gson = new Gson();

            // select _id,name from gift where _id=?;

            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                cursor = mDb.query(GeexDbParams.TABLE_EVENTS, new String[]{"_id", GeexDbParams.KEY_DATA}, "_id=?",
                        new String[]{String.valueOf(id)}, null, null, null);
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                cursor = mDb.query(GeexDbParams.TABLE_NETWORK_REQUEST, new String[]{"_id", GeexDbParams.KEY_DATA}, "_id=?",
                        new String[]{String.valueOf(id)}, null, null, null);
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                cursor = mDb.query(GeexDbParams.TABLE_APP_CRASH, new String[]{"_id", GeexDbParams.KEY_DATA}, "_id=?",
                        new String[]{String.valueOf(id)}, null, null, null);
            }

            // cursor 不为空并且 存在第一条
            if (cursor != null && cursor.moveToFirst()) {
                if (type == DataUploadService.TABLE_EVENTS_DATA) {
                    String data = cursor.getString(cursor.getColumnIndex(GeexDbParams.KEY_DATA));
                    cursor.close();
                    mDb.close();
                    return gson.fromJson(data, GeexDataBean.class);
                } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                    String data = cursor.getString(cursor.getColumnIndex(GeexDbParams.KEY_DATA));
                    cursor.close();
                    mDb.close();
                    return gson.fromJson(data, GeexNetworkRequestBean.class);
                } else {
                    String data = cursor.getString(cursor.getColumnIndex(GeexDbParams.KEY_DATA));
                    cursor.close();
                    mDb.close();
                    return gson.fromJson(data, GeexErrDataBean.class);
                }

            }

        } catch (Exception e) {

        }
        return null;

    }

    /**
     * 删除所有
     *
     * @return
     */
    public synchronized static int deleteAll(int type) {
        int row = 0;
        try {
            SQLiteDatabase mDb = getDb();
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                row = mDb.delete(GeexDbParams.TABLE_EVENTS, null, null);
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                row = mDb.delete(GeexDbParams.TABLE_NETWORK_REQUEST, null, null);
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                row = mDb.delete(GeexDbParams.TABLE_APP_CRASH, null, null);
            }

            mDb.close();
        } catch (Exception e) {

        }
        return row;
    }

    /**
     * 删除前size条的数据
     *
     * @param type
     * @param size
     * @return
     */
    public synchronized static void deleteBeforeSize(int type, int size) {
        SQLiteDatabase mDb = null;
        try {
            mDb = getDb();
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                String sql = "delete from " + GeexDbParams.TABLE_EVENTS + " where " + "_id" + " in (select " + "_id" + " from "
                        + GeexDbParams.TABLE_EVENTS + " order by " + "_id" + " limit " + size + ")";
                mDb.execSQL(sql);
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                String sql = "delete from " + GeexDbParams.TABLE_NETWORK_REQUEST + " where " + "_id" + " in (select " + "_id" + " from "
                        + GeexDbParams.TABLE_NETWORK_REQUEST + " order by " + "_id" + " limit " + size + ")";
                mDb.execSQL(sql);
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                String sql = "delete from " + GeexDbParams.TABLE_APP_CRASH + " where " + "_id" + " in (select " + "_id" + " from "
                        + GeexDbParams.TABLE_APP_CRASH + " order by " + "_id" + " limit " + size + ")";
                mDb.execSQL(sql);
            }

            mDb.close();
        } catch (Exception e) {
            if (mDb != null && mDb.isOpen()) {
                mDb.close();
            }
        }

    }


    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    public synchronized static int deleteById(int type, int id) {
        int row = 0;
        try {
            SQLiteDatabase mDb = getDb();
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                row = mDb.delete(GeexDbParams.TABLE_EVENTS, "_id=?", new String[]{String.valueOf(id)});
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                row = mDb.delete(GeexDbParams.TABLE_NETWORK_REQUEST, "_id=?", new String[]{String.valueOf(id)});
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                row = mDb.delete(GeexDbParams.TABLE_APP_CRASH, "_id=?", new String[]{String.valueOf(id)});
            }

            mDb.close();
        } catch (Exception e) {

        }

        return row;
    }

    /**
     * 根据id更新数据库
     *
     * @param id
     * @param data
     * @return
     */
    public synchronized static int updateById(int type, int id, String data) {
        int row = 0;
        try {
            SQLiteDatabase mDb = getDb();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GeexDbParams.KEY_DATA, data);
            // 第二参数：whereClause the optional WHERE clause to apply when updating.
            // Passing null will update all rows.
            // 第三参数 ：whereArgs You may include ?s in the where clause, which
            // will be replaced by the values from whereArgs. The values
            // will be bound as Strings.
            if (type == DataUploadService.TABLE_EVENTS_DATA) {
                row = mDb.update(GeexDbParams.TABLE_EVENTS, contentValues, "_id=?", new String[]{String.valueOf(id)});
            } else if (type == DataUploadService.TABLE_NETWORK_REQUEST_DATA) {
                row = mDb.update(GeexDbParams.TABLE_NETWORK_REQUEST, contentValues, "_id=?", new String[]{String.valueOf(id)});
            } else if (type == DataUploadService.TABLE_APP_CRASH_DATA) {
                row = mDb.update(GeexDbParams.TABLE_APP_CRASH, contentValues, "_id=?", new String[]{String.valueOf(id)});
            }

            mDb.close();
        } catch (Exception e) {

        }

        return row;
    }

    private static SQLiteDatabase getDb() {

        GeexDataOpenHelper mDbHelper = new GeexDataOpenHelper(GeexDataApi.getAppContext());


        return mDbHelper.getReadableDatabase();

    }


}
