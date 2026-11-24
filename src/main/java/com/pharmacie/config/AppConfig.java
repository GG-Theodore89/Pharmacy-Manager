package com.pharmacie.config;

public class AppConfig {

    private static int joursAlertePeremption = 30;

    static public int getjoursAlertePeremption() {
    return joursAlertePeremption;
}

    public static void setjoursAlertePeremption(int jour ) {
        if(jour<=0){
             throw new IllegalArgumentException(
        "jour must be strictly positive"
    );
        }
    joursAlertePeremption = jour;
}

}