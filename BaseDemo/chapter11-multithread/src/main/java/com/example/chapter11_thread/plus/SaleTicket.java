package com.example.chapter11_thread.plus;

import android.util.Log;

public class SaleTicket implements Runnable{

    private int ticket = 20;

    @Override
    public void run() {
        while (true){
            synchronized (this)
            {
                if(ticket > 0)
                {
                    Log.d("test",Thread.currentThread().getName() + "卖出了第" + (20 - ticket + 1) + "张票");
                    ticket--;
                }
                else
                {
                    Log.d("test","火车表已卖完");
                    break;
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
