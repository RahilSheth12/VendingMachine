package edu.vhhs.rahil.vending_machine.modules;

public class Inventory {
    private long product_id;
    private int available_count;
    private long machine_id;

    public Inventory(long product_id, int available_count, long machine_id) {
        this.product_id = product_id;
        this.available_count = available_count;
        this.machine_id = machine_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public int getAvailable_count() {
        return available_count;
    }

    public void setAvailable_count(int available_count) {
        this.available_count = available_count;
    }

    public long getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(long machine_id) {
        this.machine_id = machine_id;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "product_id=" + product_id +
                ", available_count=" + available_count +
                ", machine_id=" + machine_id +
                '}';
    }
}
