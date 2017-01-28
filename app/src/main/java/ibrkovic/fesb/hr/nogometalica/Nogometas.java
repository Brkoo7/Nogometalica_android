package ibrkovic.fesb.hr.nogometalica;

/**
 * Created by Ivan on 25.1.2017..
 */
public class Nogometas {
    private	int	id;
    private	String	name;
    private String url;

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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "gaga";
    }

}

