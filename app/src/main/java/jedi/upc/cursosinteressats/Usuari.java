package jedi.upc.cursosinteressats;

/**
 * Created by albert on 23/03/15.
 */
public class Usuari {
    String name;
    String surnames;
    String email;
    String course_id;
    long data;

    public Usuari(String _name, String _surnames, String _email, String _course_id, long _data) {
        name = _name;
        surnames = _surnames;
        email = _email;
        course_id = _course_id;
        data = _data;
    }
}
