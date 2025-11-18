package com.quanlybenhvien.models;

import java.io.Serializable;

public class LichKham implements Serializable, DinhDanh {
    private static final long serialVersionUID = 1L;

    private final String appointmentId;
    private final String patientId;
    private final String doctorId;
    private String appointmentDate;
    private TrangThaiLichKham status;

    public LichKham(String appointmentId, String patientId, String doctorId, String appointmentDate) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = TrangThaiLichKham.CHUA_KHAM;
    }

    @Override
    public String getId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public TrangThaiLichKham getStatus() {
        return status;
    }

    public void setStatus(TrangThaiLichKham status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return status == TrangThaiLichKham.DA_KHAM;
    }
}
