package com.company;

import java.util.concurrent.ThreadLocalRandom;

import static com.company.Main.putCake;

public class Baker extends Thread {

    public Baker() {

    }

    @Override
    public void run() {
        while (true) {
            bake();
        }
    }

    public void bake() {

        Cake kage = new Cake();

        int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
        if (randomNum == 0) {
            kage.setName("KAJKAGE");
        } else if (randomNum == 1) {
            kage.setName("KARTOFFELKAGE");
        } else if (randomNum == 2) {
            kage.setName("HINDBÆRSNITTE");
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        putCake(kage);

    }
}
