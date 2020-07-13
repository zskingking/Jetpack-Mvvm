package com.zs.zs_jetpack.ui.rank;

import java.util.List;

public class RankBean {


    /**
     * curPage : 1
     * datas : [{"coinCount":10420,"level":105,"rank":1,"userId":20382,"username":"g**eii"},{"coinCount":9765,"level":98,"rank":2,"userId":3559,"username":"A**ilEyon"},{"coinCount":8022,"level":81,"rank":3,"userId":29303,"username":"深**士"},{"coinCount":7545,"level":76,"rank":4,"userId":2,"username":"x**oyang"},{"coinCount":7333,"level":74,"rank":5,"userId":27535,"username":"1**08491840"},{"coinCount":6128,"level":62,"rank":6,"userId":28694,"username":"c**ng0218"},{"coinCount":5839,"level":59,"rank":7,"userId":3753,"username":"S**phenCurry"},{"coinCount":5826,"level":59,"rank":8,"userId":9621,"username":"S**24n"},{"coinCount":5724,"level":58,"rank":9,"userId":1260,"username":"于**家的吴蜀黍"},{"coinCount":5677,"level":57,"rank":10,"userId":1534,"username":"j**gbin"},{"coinCount":5606,"level":57,"rank":11,"userId":12467,"username":"c**yie"},{"coinCount":5601,"level":57,"rank":12,"userId":28607,"username":"S**Brother"},{"coinCount":5535,"level":56,"rank":13,"userId":863,"username":"m**qitian"},{"coinCount":5520,"level":56,"rank":14,"userId":7891,"username":"h**zkp"},{"coinCount":5502,"level":56,"rank":15,"userId":14829,"username":"l**changwen"},{"coinCount":5497,"level":55,"rank":16,"userId":7809,"username":"1**23822235"},{"coinCount":5481,"level":55,"rank":17,"userId":7710,"username":"i**Cola7"},{"coinCount":5481,"level":55,"rank":18,"userId":27,"username":"y**ochoo"},{"coinCount":5443,"level":55,"rank":19,"userId":25793,"username":"F**_2014"},{"coinCount":5406,"level":55,"rank":20,"userId":12351,"username":"w**igeny"},{"coinCount":5401,"level":55,"rank":21,"userId":29185,"username":"轻**宇"},{"coinCount":5388,"level":54,"rank":22,"userId":833,"username":"w**lwaywang6"},{"coinCount":5381,"level":54,"rank":23,"userId":2068,"username":"i**Cola"},{"coinCount":5301,"level":54,"rank":24,"userId":12331,"username":"R**kieJay"},{"coinCount":5301,"level":54,"rank":25,"userId":26707,"username":"p**xc.com"},{"coinCount":5268,"level":53,"rank":26,"userId":25419,"username":"蔡**打篮球"},{"coinCount":5236,"level":53,"rank":27,"userId":6095,"username":"W**derfulMtf"},{"coinCount":5205,"level":53,"rank":28,"userId":29076,"username":"f**ham"},{"coinCount":5175,"level":52,"rank":29,"userId":2160,"username":"R**iner"},{"coinCount":5161,"level":52,"rank":30,"userId":4662,"username":"1**71599512"}]
     * offset : 0
     * over : false
     * pageCount : 862
     * size : 30
     * total : 25847
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * coinCount : 10420
         * level : 105
         * rank : 1
         * userId : 20382
         * username : g**eii
         */

        private int coinCount;
        private int level;
        private int rank;
        private int userId;
        private String username;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
