package com.example.bluetoothlowenergy.enity;

public class BluetoothDevice {

    private String name;
    private String address;
    private int state;

    public BluetoothDevice() {
    }

    public BluetoothDevice(String name, String address, int state) {
        this.name = name;
        this.address = address;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BlueToothDevice{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state +
                '}';
    }
}
