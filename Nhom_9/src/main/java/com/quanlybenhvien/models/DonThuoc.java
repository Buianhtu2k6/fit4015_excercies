package com.quanlybenhvien.models;

import java.util.HashMap;
import java.util.Map;

public class DonThuoc extends TaiLieuYTe {
    private static final long serialVersionUID = 1L;

    private final String appointmentId;
    private String diagnosis;
    private final HashMap<String, Integer> medications;
    private double medicineCost;

    public DonThuoc(String documentId, String patientId, String appointmentId, String diagnosis,
                    HashMap<String, Integer> medications, double medicineCost) {
        super(documentId, patientId);
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.medicineCost = medicineCost;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Map<String, Integer> getMedications() {
        return medications;
    }

    public double getMedicineCost() {
        return medicineCost;
    }

    public void setMedicineCost(double medicineCost) {
        this.medicineCost = medicineCost;
    }

    public String toSummary() {
        StringBuilder builder = new StringBuilder();
        medications.forEach((name, qty) -> builder.append(name).append("(").append(qty).append("); "));
        return builder.length() == 0 ? "Chưa có thuốc" : builder.toString();
    }
}

