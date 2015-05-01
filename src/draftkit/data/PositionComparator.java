/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draftkit.data;

import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author H2
 */
public class PositionComparator implements Comparator<String> {

    HashMap<String, Integer> values = new HashMap<String, Integer>();

    public PositionComparator() {
        values.put("C", 1);
        values.put("1B", 2);
        values.put("CI", 3);
        values.put("3B", 4);
        values.put("2B", 5);
        values.put("MI", 6);
        values.put("SS", 7);
        values.put("OF", 8);
        values.put("U", 9);
        values.put("P", 10);
    }

    public int compare(String s1, String s2) {
        if (values.get(s1).equals(values.get(s2))) {
            return 0;
        } else if (values.get(s1) > values.get(s2)) {
            return 1;
        } else {
            return -1;
        }
    }

}
