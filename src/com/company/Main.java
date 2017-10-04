package com.company;

import java.util.concurrent.Semaphore;

public class Main {
    public static Cake[] kager = new Cake[10];
    private static Semaphore hylde = new Semaphore(1);
    public static int take, put = 0;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < kager.length; i++) {
            kager[i] = null;
        }

        Baker bageren = new Baker();
        Thread bagerensTraad = new Thread(bageren);

        Consumer kunden = new Consumer();
        Thread kundensTraad = new Thread(kunden);

        bagerensTraad.start();
        kundensTraad.start();

    }

    public static Cake buyCake() {

        try {
            hylde.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int spistKage = take;
        Cake kage = kager[take++];

        System.out.println("Kage fra hyldeplads " + spistKage + " blev spist");

        for (int i = 0; i < (kager.length); i++) {
            if (kager[i] != null) {
                System.out.println("hyldeplads nummer " + i + " har en " + kager[i].getName());
            } else {
                System.out.println("hyldeplads nummer " + i + " har IKKE en kage");
            }
        }

        System.out.println("_______________________________");

        take = take % kager.length;

        hylde.release();
        return kage;
    }

    public static void putCake(Cake cake) {
        try {
            hylde.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        kager[put++] = cake;

        System.out.println(cake.getName() + " blev sat på hylde nummer: " + (put - 1));

        for (int i = 0; i < (kager.length); i++) {
            if (kager[i] != null) {
                System.out.println("hyldeplads nummer " + i + " har en " + kager[i].getName());
            } else {
                System.out.println("hyldeplads nummer " + i + " har IKKE en kage");
            }
        }

        System.out.println("_______________________________");

        put = put % kager.length; // cyklisk indexering: foo = foo % bar.length();

        hylde.release();
    }
}


