package ru.cpsmi.artnightmobileapp;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import ru.cpsmi.artnightmobileapp.data.DatabaseHelper;
import ru.cpsmi.artnightmobileapp.data.Event;
import ru.cpsmi.artnightmobileapp.data.Museum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    private Museum selectedMuseum;

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
                Log.i("DB", "It is NOT necessary to insert data");
                return;
            } else {
                Log.i("DB", "It is necessary to insert data");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("DB", "Check DB FAIL");
        }


        final List<Museum> listOfMuseums = new ArrayList<Museum>();
        final List<Event> listOfEvents = new ArrayList<Event>();

        //Первичное наполнение базы данных
        //Переделать на считывание из файла
        listOfMuseums.add(new Museum("Ботанический сад Петра Великого", 59.970984, 30.32144));

        listOfMuseums.add(new Museum("Планетарий", 59.955366, 30.311068));

        listOfMuseums.add(new Museum("Лофт Проект ЭТАЖИ", 59.921772, 30.35646));

        listOfMuseums.add(new Museum("Музей интерактивной науки «ЛабиринтУм»", 59.965376, 30.315603));

        listOfMuseums.add(new Museum("Ленфильм", 59.958073, 30.317481));

        listOfMuseums.add(new Museum("Академическая Капелла Санкт-Петербурга", 59.939942, 30.320758));
        listOfEvents.add(new Event("12 концертов: скрипка и клавесин, джаз и неофолк", "Концерт", listOfMuseums.get(listOfMuseums.size() - 1)));


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

    List<Museum> searchMuseums(Context context, String searchString) {
        List<Museum> museumList;
        try {
            // This is how, a reference of DAO object can be done
            museumDao = getHelper(context).getMuseumDao();
            // Query the database. We need all the records so, used queryForAll()
            //Museum searchedMuseum = new Museum();
            //searchedMuseum.setTitle(searchString);

            QueryBuilder<Museum, Integer> queryBuilder =
                    museumDao.queryBuilder();
            museumList = museumDao.query(queryBuilder.where().like("title", "%" + searchString + "%").prepare());

            //museumList = museumDao.queryForMatching(searchedMuseum);
            //museumList = museumDao.queryForAll();
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

    public void setSelectedMuseum(Museum selectedMuseum) {
        this.selectedMuseum = selectedMuseum;
    }

    public Museum getSelectedMuseum() {
        return selectedMuseum;
    }
}
