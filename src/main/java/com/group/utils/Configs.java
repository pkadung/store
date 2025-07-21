package com.group.utils;

import com.group.facade.Facade;
import com.group.repository.Cache;

import java.time.format.DateTimeFormatter;

public class Configs {
    public final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public final static Facade f = new Facade();
}
