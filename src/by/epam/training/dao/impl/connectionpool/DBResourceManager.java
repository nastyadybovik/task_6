package by.epam.training.dao.impl.connectionpool;

import java.util.ResourceBundle;

/**
 * Created by Настенька on 11/11/2015.
 */
public class DBResourceManager {
    private static final String BUNDLE_NAME = "db";

    private static DBResourceManager instance;

    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    public static DBResourceManager getInstance(){
        if(instance == null){
            instance = new DBResourceManager();
        }
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
