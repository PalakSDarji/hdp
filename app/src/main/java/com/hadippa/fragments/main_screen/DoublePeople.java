package com.hadippa.fragments.main_screen;

/**
 * Created by Palak on 25-07-2016.
 */
public class DoublePeople {

    People people1;
    People people2;

    public DoublePeople(People people1, People people2) {
        this.people1 = people1;
        this.people2 = people2;
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
