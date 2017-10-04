package com.company;

import static com.company.Main.buyCake;

public class Consumer extends Thread {

    public Consumer() {
    }

    @Override
    public void run() {
        while (true) {
            eat();
        }
    }

    private void eat() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buyCake();
    }

}
