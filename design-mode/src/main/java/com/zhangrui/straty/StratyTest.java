package com.zhangrui.straty;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class StratyTest {

    public static void main(String[] args) {

        Integer[] arrays = new Integer[]{
                12, 13, 5, 7, 13, 12, 80, 9, 4, 1, 7, 78, 89
        };

        //SortStraty<Integer> sortStraty = new SimpleSelectSortStraty<>();
        //SortStraty<Integer> sortStraty = new InsertSortStraty<>();
        SortStraty<Integer> sortStraty = new BuddleSortStraty<>();
        sortStraty.sort(arrays);
        sortStraty.show(arrays);



    }
}
