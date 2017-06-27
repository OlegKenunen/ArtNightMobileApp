package ru.cpsmi.artnightmobileapp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import ru.cpsmi.artnightmobileapp.data.DatabaseHelper;
import ru.cpsmi.artnightmobileapp.data.Event;
import ru.cpsmi.artnightmobileapp.data.Museum;

import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by Oleg on 15.06.2017.
 * oleg.kenunen@gmail.com
 */
class DataController {
    private static DataController instance;
    // Reference of DatabaseHelper class to access its DAOs and other components
    private DatabaseHelper databaseHelper = null;


    // Declaration of DAO to interact with corresponding table
    private Dao<Museum, Integer> museumDao;
    private Dao<Event, Integer> eventDao;

    private DataController() {
    }

    static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }


    void setTestDataToLocalDB(Context context) {
        try {
            // This is how, a reference of DAO object can be done
            museumDao = getHelper(context).getMuseumDao();

            // Query the database. We need all the records so, used queryForAll()
            long coundOfMuseums = museumDao.countOf();
            if (museumDao.countOf() > 0) {
                Log.i("Art", "It is NOT necessary to insert data");
                return;
            } else {
                Log.i("Art", "It is necessary to insert data");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("Art", "Check DB FAIL");
        }


        final List<Museum> listOfMuseums = new ArrayList<Museum>();
        final List<Event> listOfEvents = new ArrayList<Event>();

        final String DIR_SD = "";
        final String FILENAME_SD = "museum_db.txt";

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d("Art", "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);
        Log.d("Art", "Полный путь: " + sdFile);
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";

            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d("Art", str);
                StringTokenizer tokens = new StringTokenizer(str, "\t");

                //Первичное наполнение базы данных
                try {
                    DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
                    listOfMuseums.add(new Museum(
                            "" + tokens.nextToken(),
                            (Date) timeFormatter.parse(tokens.nextToken()),
                            (Date) timeFormatter.parse(tokens.nextToken()),
                            Double.parseDouble(tokens.nextToken()),
                            Double.parseDouble(tokens.nextToken()),
                            "" + tokens.nextToken()));
                    //listOfEvents.add(new Event("12 концертов: скрипка и клавесин, джаз и неофолк", "Концерт", listOfMuseums.get(listOfMuseums.size() - 1)));
                } catch (ParseException e) {
                    Log.d("Art", "ParseException");
                    e.printStackTrace();
                }


            }
        } catch (FileNotFoundException e) {
            Log.d("Art", "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("Art", "IOException");
            e.printStackTrace();
        }


        try {
            // This is how, a reference of DAO object can be done
            final Dao<Museum, Integer> museumDao = getHelper(context).getMuseumDao();
            final Dao<Event, Integer> eventDao = getHelper(context).getEventDao();

            //This is the way to insert data into a database table
            museumDao.create(listOfMuseums);
            eventDao.create(listOfEvents);


            //reset();
            //showDialog();
            Log.i("DB", "Create complete");

        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Create FAIL");
        }

    }

    List<Museum> getListOfMuseums(Context context) {
        List<Museum> museumList;

        try {
            // This is how, a reference of DAO object can be done
            museumDao = getHelper(context).getMuseumDao();
            // Query the database. We need all the records so, used queryForAll()
            museumList = museumDao.queryForAll();
            Log.i("DB", "Select museums complete");
            //int numberOfMuseums = museumList.size();
            Log.i("DB", "Число музеев в базе данных: " + String.valueOf(museumList.size()));
//            Log.i("DB", "Id последнего: " + String.valueOf(museumList.get(numberOfMuseums - 1).getMuseumId()));
//            Log.i("DB", "название последнего: " + museumList.get(numberOfMuseums - 1).getTitle());
            return museumList;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Select museums FAIL");
        }
        return null;
    }

    Museum getMuseumById(Context context, int selectedId) {
        Museum selectedMuseum;

        try {
            // This is how, a reference of DAO object can be done
            museumDao = getHelper(context).getMuseumDao();
            // Query the database. We need all the records so, used queryForAll()
            selectedMuseum = museumDao.queryForId(selectedId);
            Log.i("DB", "Select museum by id complete");
            //int numberOfMuseums = museumList.size();

            return selectedMuseum;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Select museums FAIL");
        }
        return null;
    }

    List<Museum> searchMuseums(Context context, String searchString, String options) {
        if (options.compareTo("WHOLE_TITLE") != 0
                && options.compareTo("TITLE") != 0
                && options.compareTo("PROGRAMME") != 0) {
            Log.i("Art", "На вход получено некорректное значение options: " + options);
            return null;
        }
        List<Museum> museumList;
        try {
            // This is how, a reference of DAO object can be done
            museumDao = getHelper(context).getMuseumDao();
            QueryBuilder<Museum, Integer> queryBuilder = museumDao.queryBuilder();

            if (options.compareTo("WHOLE_TITLE") == 0) {
                Log.i("Art", "Поиск по части названия");
                museumList = museumDao.query(queryBuilder.where().like("title", searchString).prepare());
            } else if (options.compareTo("TITLE") == 0) {
                Log.i("Art", "Поиск по части названия");
                museumList = museumDao.query(queryBuilder.where().like("title", "%" + searchString + "%").prepare());
            } else if (options.compareTo("PROGRAMME") == 0) {
                Log.i("Art", "Поиск по программе");
                museumList = museumDao.query(queryBuilder.where().like("programme", "%" + searchString + "%").prepare());
            } else {
                return null;
            }
            //museumList = museumDao.queryForMatching(searchedMuseum);
            //museumList = museumDao.queryForAll();
            Log.i("DB", "Select museums complete");

            return museumList;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Select museums FAIL");
        }
        return null;
    }


    String[] getMuseumTitles(Context context) {

        List<Museum> museumList;

        try {
            // This is how, a reference of DAO object can be done
            museumDao = getHelper(context).getMuseumDao();

            // Query the database. We need all the records so, used queryForAll()
            museumList = museumDao.queryForAll();

            Log.i("DB", "Select museum titles complete");

            int numberOfMuseums = museumList.size();


            String[] titles = new String[numberOfMuseums];
            for (int i = 0; i < numberOfMuseums; i++) {
                titles[i] = museumList.get(i).getTitle();
            }
            return titles;


        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Select titles FAIL");
            return null;
        }
    }

    void releaseHelper() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }


}
