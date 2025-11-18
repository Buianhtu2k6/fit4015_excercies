package com.quanlybenhvien;

import com.quanlybenhvien.ui.GiaoDienQuanLyBenhVien;

import javax.swing.SwingUtilities;

public class UngDung {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GiaoDienQuanLyBenhVien cuaSoChinh = new GiaoDienQuanLyBenhVien();
            cuaSoChinh.setVisible(true);
        });
    }
}
