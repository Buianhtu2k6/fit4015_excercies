package com.quanlybenhvien.repository;

import java.io.*;
import java.util.List;

public class LuuTru {

    // Lưu List<T> ra file (serialization)
    public static <T extends Serializable> void save(String filename, List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
            System.out.println("Lưu file thành công: " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu file: " + e.getMessage());
        }
    }

    // Đọc List<T> từ file
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> load(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File không tồn tại, tạo mới: " + filename);
            return new java.util.ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
}
