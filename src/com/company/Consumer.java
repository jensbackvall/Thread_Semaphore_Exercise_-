package com.company;

import static com.company.Main.buyCake;

public class Consumer implements Runnable {

    public Consumer() {
    }

    @Override
    public void run() {
        while (true) {
            eat();
        }
    }

    private void eat() {
        buyCake();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
