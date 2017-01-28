package ibrkovic.fesb.hr.nogometalica;

import java.util.ArrayList;

/**
 * Created by adagelic on 09/01/17.
 */
public class DataStorage {

    public static ArrayList<Nogometas> allFootballersList = new ArrayList<Nogometas>();
    public static ArrayList<User> allUserList = new ArrayList<User>();

    public static Nogometas getFootballerById(Integer id) {
        for (int i = 0; i < allFootballersList.size(); i++) {
            if (allFootballersList.get(i).getId() == id) {
                return allFootballersList.get(i);
            }
        }

        return null;
    }

    public static User getUserById(Integer id) {
        for (int i = 0; i < allFootballersList.size(); i++) {
            if (allUserList.get(i).getId() == id) {
                return allUserList.get(i);
            }
        }

        return null;
    }

}
