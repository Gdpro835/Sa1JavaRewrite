package com.sega.mobile.framework.device;

import java.util.Enumeration;
import java.util.Vector;

public class MFVector<E> {
    private Vector<E> mVector = new Vector<>();

    public void addElement(E obj) {
        this.mVector.addElement(obj);
    }

    public void insertElementAt(E obj, int i) {
        this.mVector.insertElementAt(obj, i);
    }

    public int size() {
        return this.mVector.size();
    }

    public void copyInto(E[] obj) {
        this.mVector.copyInto(obj);
    }

    public E elementAt(int i) {
        return this.mVector.elementAt(i);
    }

    public void removeElementAt(int i) {
        this.mVector.removeElementAt(i);
    }

    public void removeAllElements() {
        this.mVector.removeAllElements();
    }

    public Enumeration<E> elements() {
        return this.mVector.elements();
    }

    public E get(int i) {
        return this.mVector.get(i);
    }
}
