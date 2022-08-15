package dev.brokenstudio.pinklib.function;

/*
    Project: ArcticLib
    File: TriConsumer
    Created by: Jan Z.
    Created at: 3/27/2022
     
    © 2022 BrokenStudio x Jan
 */
public interface TriConsumer<T,U,V> {

    void accept(T t, U u, V v);

}
