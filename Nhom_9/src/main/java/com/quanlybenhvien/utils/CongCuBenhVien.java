package com.quanlybenhvien.utils;

import com.quanlybenhvien.models.DinhDanh;

import javax.swing.*;
import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CongCuBenhVien {

    private CongCuBenhVien() {
    }

    // Generic method: convert any Identifiable list to a map keyed by ID
    public static <T extends DinhDanh> Map<String, T> toMap(List<T> items) {
        return items.stream().collect(Collectors.toMap(DinhDanh::getId, Function.identity()));
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thông báo", JOptionPane.ERROR_MESSAGE);
    }

    public static HashMap<String, Integer> parseMedications(String raw) {
        HashMap<String, Integer> results = new HashMap<>();
        if (raw == null || raw.isBlank()) {
            return results;
        }

        String[] entries = raw.split(";");
        for (String entry : entries) {
            String[] pair = entry.trim().split(":");
            if (pair.length == 2) {
                String drug = pair[0].trim();
                try {
                    int qty = Integer.parseInt(pair[1].trim());
                    if (!drug.isEmpty() && qty > 0) {
                        results.put(drug, qty);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return results;
    }

    public static String normalizeGender(String gender) {
        if (gender == null) {
            return "Khác";
        }
        String normalized = gender.trim().toLowerCase(Locale.ROOT);
        if (normalized.startsWith("n")) {
            return "Nam";
        }
        if (normalized.startsWith("m")) {
            return "Nữ";
        }
        return "Khác";
    }
}

