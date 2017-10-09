package com.company;

import java.util.concurrent.Semaphore;

public class Main {
    /* initierer et Array "kager" som nybagte kager lagres i , indtil de bliver købt*/
    public static Cake[] kager = new Cake[10];
    /* initierer en Semaphore "hylde" som angiver adgang til vores Array for at placere
    Eller tage en kage*/
    private static Semaphore hylde = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {

        /* Initierer en ny instans af bager med tilhørende thread*/
        Baker bageren = new Baker();
        Thread bagerensTraad = new Thread(bageren);

        /* Initierer 3 nye instanser af kunde med tilhørende threads*/
        Consumer kunden = new Consumer();
        Thread kundensTraad = new Thread(kunden);

        Consumer kunden2 = new Consumer();
        Thread kunden2sTraad = new Thread(kunden2);

        Consumer kunden3 = new Consumer();
        Thread kunden3sTraad = new Thread(kunden3);

        /* starter threads*/
        bagerensTraad.start();
        kundensTraad.start();
        kunden2sTraad.start();
        kunden3sTraad.start();

    }

    public static void buyCake() {

        /* initierer variabel for hyldeplads hvor kage kan tages fra*/
        int take = -1;

        /* prøver at få adgang til hylden*/
        try {
            hylde.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* tjekker hvor der er en hyldeplads med en kage på*/
        for (int j = 0; j < kager.length; j++) {
            if (kager[j] != null) {
                take = j;
                break;
            }
        }

        /* hvis der ikke er nogle kager på hele hylden, afgives adgang til hylden*/
        if (take == -1){
            hylde.release();
            /* afslutter buyCake()*/
            return;
        }

        /* take bliver inkrementeret med 1, for at få hyldepladser fra 1 - 10 i stedet for 0 - 9 */
        System.out.println("Kunde køber kagen på hyldeplads " + (take + 1));
        System.out.println("Kage fra hyldeplads " + (take + 1) + " blev spist");

        /* angiver at hyldepladsen hvorfra kagen kom, nu er tom*/
        kager[take] = null;

        /* gennemgår alle hyldepladser og angiver hvis der er en kage og hvilken type det er*/
        for (int i = 0; i < (kager.length); i++) {
            if (kager[i] != null) {
                System.out.println("hyldeplads nummer " + (i + 1) + " har en " + kager[i].getName());
            } else {
                System.out.println("hyldeplads nummer " + (i + 1) + ": _______ TOM HYLDE _______");
            }
        }
        System.out.println("_______________________________");

        /* afgiver adgang til hylden*/
        hylde.release();
    }

    public static void putCake(Cake cake) {

        /* initierer variabel for hyldeplads hvor kage kan placeres*/
        int put = -1;

        /* prøver at få adgang til hylden*/
        try {
            hylde.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* tjekker hvor der er en tom hyldeplads som kage kan placeres på*/
        for (int j = 0; j < kager.length; j++) {
            if (kager[j] == null) {
                put = j;
                break;
            }
        }

        /* hvis der ikke er en tom hyldeplads, afgives adgang til hylden*/
        if (put == -1){
            hylde.release();
            return;
        }

        /* placerer den nybagte kage i Array "kager"*/
        kager[put] = cake;

        System.out.println("Bager bager en " + cake.getName());
        /* put bliver inkrementeret med 1, for at få hyldepladser fra 1 - 10 i stedet for 0 - 9 */
        System.out.println(cake.getName() + " blev sat på hylde nummer: " + (put + 1));

        /* gennemgår alle hyldepladser og angiver hvis der er en kage og hvilken type det er*/
        for (int i = 0; i < (kager.length); i++) {
            if (kager[i] != null) {
                System.out.println("hyldeplads nummer " + (i + 1) + " har en " + kager[i].getName());
            } else {
                System.out.println("hyldeplads nummer " + (i + 1) + ": _______ TOM HYLDE _______");
            }
        }
        System.out.println("_______________________________");

        /* afgiver adgang til hylden*/
        hylde.release();
    }
}


/*
OLD VERSION THAT DID NOT WORK. INCLUDED TO LET ME REMEMBER, LEARN AND THINK MORE

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

        outerloop:
        for (int k = 0; k < kager.length; k++) {
            if (kager[k] != null) {
                try {
                    hylde.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("BREAKING!!!!!");
                break outerloop;
            } else {
               try {
                   k = 0;
                   System.out.println("Consumer SLEEPS!");
                   Thread.sleep(100);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            }
        }

        int spistKage = take;
        Cake kage = kager[take++];

        System.out.println("Kage fra hyldeplads " + spistKage + " blev spist");

        kager[spistKage] = null;

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

        outerloop:
        for (int j = 0; j < kager.length; j++) {
            if (kager[j] == null) {
                try {
                    hylde.acquire();
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
                System.out.println("BREAKING!!!!!");
                break outerloop;
            } else {
                try {
                    j = 0;
                    System.out.println("Baker SLEEPS!");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
}*/
