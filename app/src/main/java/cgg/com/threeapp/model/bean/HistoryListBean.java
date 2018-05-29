package cgg.com.threeapp.model.bean;

import java.util.List;

/**
 * author: Wanderer
 * date:   2018/3/28 15:29
 * email:  none
 */

public class HistoryListBean {

    private List<HistoryBean> list;

    public List<HistoryBean> getList() {
        return list;
    }

    public void setList(List<HistoryBean> list) {
        this.list = list;
    }

    public static class HistoryBean {

        private int id;
        private String name;

        public HistoryBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
