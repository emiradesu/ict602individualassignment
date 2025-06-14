package com.example.electricitybillcalculator;

public class Bill {
    int id;
    String month;
    int units;
    int rebate;
    double total;
    double finalCost;

    public Bill(int id, String month, int units, int rebate, double total, double finalCost) {
        this.id = id;
        this.month = month;
        this.units = units;
        this.rebate = rebate;
        this.total = total;
        this.finalCost = finalCost;
    }

    public int getId() { return id; }
    public String getMonth() { return month; }
    public int getUnits() { return units; }
    public int getRebate() { return rebate; }
    public double getTotal() { return total; }
    public double getFinalCost() { return finalCost; }
}
