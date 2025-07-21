package com.group.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {
    private static Map<String, List> caches = new HashMap<>();
    public static <E> List<E> getData(SelectRepository s, String key) throws SQLException {
        if (caches.containsKey(key)) {
            return caches.get(key);
        }
        else {
            List<E> list =  s.getList();
            caches.put(key, list);
            return list;
        }
    }

    public static<E> void refresh (SelectRepository s, String key) throws SQLException {
        if (caches.containsKey(key)) {
            caches.remove(key);
            List<E> list =  s.getList();
            caches.put(key, list);
        }
    }
}
