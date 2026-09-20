package com.sega.engine.lib;

public interface Iterator {
    boolean hasNext();

    Object next();

    void remove();
}
