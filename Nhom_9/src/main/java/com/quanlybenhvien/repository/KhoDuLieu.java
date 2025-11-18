package com.quanlybenhvien.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Kho dữ liệu generic quản lý CRUD
public class KhoDuLieu<T> {
    private final List<T> items;

    public KhoDuLieu() {
        this.items = new ArrayList<>();
    }

    // Thêm mới
    public void add(T item) {
        items.add(item);
    }

    // Xóa theo object
    public void remove(T item) {
        items.remove(item);
    }

    // Lấy tất cả
    public List<T> getAll() {
        return items;
    }

    // Tìm kiếm theo condition (dùng lambda)
    public Optional<T> find(java.util.function.Predicate<T> predicate) {
        return items.stream().filter(predicate).findFirst();
    }

    // Xóa tất cả
    public void clear() {
        items.clear();
    }
}
