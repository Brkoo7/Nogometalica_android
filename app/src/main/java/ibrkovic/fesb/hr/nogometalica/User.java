package ibrkovic.fesb.hr.nogometalica;

/**
 * Created by Ivan on 27.1.2017..
 */
public class User {

    private	int	id;
    private	String	name;

    private String rezultat;
    public int getId() {
        return id;
    }

    public void	setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void	setId(int id) {
        this.id	= id;
    }
    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }


    @Override
    public String toString() {
        return "gaga";
    }

}
