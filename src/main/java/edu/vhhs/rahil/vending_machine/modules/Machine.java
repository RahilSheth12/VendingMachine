package edu.vhhs.rahil.vending_machine.modules;

public class Machine {
    private long machine_id;
    private long location_id;

    public Machine(long machine_id, long location_id) {
        this.machine_id = machine_id;
        this.location_id = location_id;
    }

    public long getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(long machine_id) {
        this.machine_id = machine_id;
    }

    public long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machine_id=" + machine_id +
                ", location_id=" + location_id +
                '}';
    }
}
