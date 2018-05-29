package cgg.com.threeapp.model.bean;

/**
 * Created by Administrator on 2018-03-14.
 */

public class SQLAddrBean {
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SQLAddrBean(String name, int id) {

        this.name = name;
        this.id = id;
    }
}
