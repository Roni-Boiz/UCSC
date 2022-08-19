/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.*;

/**
 *
 * @author Ronila
 */
public class NewsEntityComparator implements Comparator<NewsEntity2> {

    @Override
    public int compare(NewsEntity2 o1, NewsEntity2 o2) {
        return o1.getDate().compareTo(o2.getDate());
    }

}
