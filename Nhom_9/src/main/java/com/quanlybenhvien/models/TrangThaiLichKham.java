package com.quanlybenhvien.models;

public enum TrangThaiLichKham {
    CHUA_KHAM("Chưa khám"),
    DA_KHAM("Đã khám");

    private final String nhan;

    TrangThaiLichKham(String nhan) {
        this.nhan = nhan;
    }

    public String getNhan() {
        return nhan;
    }
}

