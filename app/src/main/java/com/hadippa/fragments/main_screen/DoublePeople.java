package com.hadippa.fragments.main_screen;

import com.hadippa.model.DataModel;

/**
 * Created by Palak on 25-07-2016.
 */
public class DoublePeople {

    People people1;
    People people2;

    DataModel.PeopleGoingBean.UserBeanX beanX;
    DataModel.PeopleGoingBean.UserBeanX beanX1;

    public DoublePeople(People people1, People people2) {
        this.people1 = people1;
        this.people2 = people2;
    }

    public DoublePeople(DataModel.PeopleGoingBean.UserBeanX beanX, DataModel.PeopleGoingBean.UserBeanX beanX1) {
        this.beanX = beanX;
        this.beanX1 = beanX1;
    }


    public DataModel.PeopleGoingBean.UserBeanX getBeanX() {
        return beanX;
    }

    public void setBeanX(DataModel.PeopleGoingBean.UserBeanX beanX) {
        this.beanX = beanX;
    }

    public DataModel.PeopleGoingBean.UserBeanX getBeanX1() {
        return beanX1;
    }

    public void setBeanX1(DataModel.PeopleGoingBean.UserBeanX beanX1) {
        this.beanX1 = beanX1;
    }

    public People getPeople1() {
        return people1;
    }

    public void setPeople1(People people1) {
        this.people1 = people1;
    }

    public People getPeople2() {
        return people2;
    }

    public void setPeople2(People people2) {
        this.people2 = people2;
    }
}
