package com.quanlybenhvien.models;

public class HoaDon extends TaiLieuYTe {
    private static final long serialVersionUID = 1L;

    private final String maDonThuoc;
    private double tienKham;
    private double tienThuoc;
    private double tongTien;

    public HoaDon(String documentId, String patientId, String prescriptionId, double consultationFee, double medicineCost) {
        super(documentId, patientId);
        this.maDonThuoc = prescriptionId;
        this.tienKham = consultationFee;
        this.tienThuoc = medicineCost;
        this.tongTien = consultationFee + medicineCost;
    }

    public String getMaDonThuoc() {
        return maDonThuoc;
    }

    public double getTienKham() {
        return tienKham;
    }

    public void setTienKham(double tienKham) {
        this.tienKham = tienKham;
        capNhatTongTien();
    }

    public double getTienThuoc() {
        return tienThuoc;
    }

    public void setTienThuoc(double tienThuoc) {
        this.tienThuoc = tienThuoc;
        capNhatTongTien();
    }

    public double getTongTien() {
        return tongTien;
    }

    private void capNhatTongTien() {
        this.tongTien = tienKham + tienThuoc;
    }
}

