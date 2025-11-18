package com.quanlybenhvien.ui;

import com.quanlybenhvien.models.BacSi;
import com.quanlybenhvien.models.BenhNhan;
import com.quanlybenhvien.models.DonThuoc;
import com.quanlybenhvien.models.HoaDon;
import com.quanlybenhvien.models.LichKham;
import com.quanlybenhvien.models.TrangThaiLichKham;
import com.quanlybenhvien.repository.KhoDuLieu;
import com.quanlybenhvien.repository.LuuTru;
import com.quanlybenhvien.utils.CongCuBenhVien;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GiaoDienQuanLyBenhVien extends JFrame {

    private static final String BENH_NHAN_FILE = "benhnhan.dat";
    private static final String BAC_SI_FILE = "bacsi.dat";
    private static final String LICH_KHAM_FILE = "lichkham.dat";
    private static final String DON_THUOC_FILE = "donthuoc.dat";
    private static final String HOA_DON_FILE = "hoadon.dat";

    private final KhoDuLieu<BenhNhan> khoBenhNhan = new KhoDuLieu<>();
    private final KhoDuLieu<BacSi> khoBacSi = new KhoDuLieu<>();
    private final KhoDuLieu<LichKham> khoLichKham = new KhoDuLieu<>();
    private final KhoDuLieu<DonThuoc> khoDonThuoc = new KhoDuLieu<>();
    private final KhoDuLieu<HoaDon> khoHoaDon = new KhoDuLieu<>();

    private DefaultTableModel bangBenhNhanModel;
    private DefaultTableModel bangBacSiModel;
    private DefaultTableModel bangLichKhamModel;
    private DefaultTableModel bangDonThuocModel;
    private DefaultTableModel bangHoaDonModel;

    private JTable bangBenhNhan;
    private JTable bangBacSi;
    private JTable bangLichKham;
    private JTable bangDonThuoc;
    private JTable bangHoaDon;
    private JTextField timKiemBenhNhanField;
    private JTextField timKiemBacSiField;
    private JTextField locNgayField;

    public GiaoDienQuanLyBenhVien() {
        napDuLieu();
        khoiTaoUI();
    }

    private void napDuLieu() {
        taiFile(khoBenhNhan, BENH_NHAN_FILE);
        taiFile(khoBacSi, BAC_SI_FILE);
        taiFile(khoLichKham, LICH_KHAM_FILE);
        taiFile(khoDonThuoc, DON_THUOC_FILE);
        taiFile(khoHoaDon, HOA_DON_FILE);
    }

    private <T extends Serializable> void taiFile(KhoDuLieu<T> kho, String file) {
        List<T> data = LuuTru.load(file);
        data.forEach(kho::add);
    }

    private void khoiTaoUI() {
        setTitle("Phần mềm quản lý bệnh viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Bệnh nhân", taoTabBenhNhan());
        tabs.addTab("Bác sĩ", taoTabBacSi());
        tabs.addTab("Lịch khám", taoTabLichKham());
        tabs.addTab("Đơn thuốc & Hóa đơn", taoTabDonThuocVaHoaDon());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel taoTabBenhNhan() {
        bangBenhNhanModel = taoModel(new String[]{"Mã BN", "Họ tên", "Tuổi", "Giới tính", "Địa chỉ", "Số BHYT"});
        bangBenhNhan = new JTable(bangBenhNhanModel);
        capNhatBangBenhNhan();

        JPanel panel = new JPanel(new BorderLayout());
        timKiemBenhNhanField = new JTextField();
        timKiemBenhNhanField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { capNhatBangBenhNhan(); }
            @Override
            public void removeUpdate(DocumentEvent e) { capNhatBangBenhNhan(); }
            @Override
            public void changedUpdate(DocumentEvent e) { capNhatBangBenhNhan(); }
        });
        JPanel timKiemPanel = new JPanel(new BorderLayout(5, 0));
        timKiemPanel.add(new JLabel("Tìm kiếm:"), BorderLayout.WEST);
        timKiemPanel.add(timKiemBenhNhanField, BorderLayout.CENTER);
        panel.add(timKiemPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bangBenhNhan), BorderLayout.CENTER);

        JPanel thaoTac = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        thaoTac.add(btnThem);
        thaoTac.add(btnSua);
        thaoTac.add(btnXoa);
        panel.add(thaoTac, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> moHopThoaiBenhNhan(null));
        btnSua.addActionListener(e -> {
            BenhNhan bn = layBenhNhanDangChon();
            if (bn != null) {
                moHopThoaiBenhNhan(bn);
            }
        });
        btnXoa.addActionListener(e -> xoaBenhNhan());
        return panel;
    }

    private JPanel taoTabBacSi() {
        bangBacSiModel = taoModel(new String[]{"Mã BS", "Họ tên", "Tuổi", "Giới tính", "Chuyên khoa", "SĐT"});
        bangBacSi = new JTable(bangBacSiModel);
        capNhatBangBacSi();

        JPanel panel = new JPanel(new BorderLayout());
        timKiemBacSiField = new JTextField();
        timKiemBacSiField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { capNhatBangBacSi(); }
            @Override
            public void removeUpdate(DocumentEvent e) { capNhatBangBacSi(); }
            @Override
            public void changedUpdate(DocumentEvent e) { capNhatBangBacSi(); }
        });
        JPanel timKiemPanel = new JPanel(new BorderLayout(5, 0));
        timKiemPanel.add(new JLabel("Tìm kiếm:"), BorderLayout.WEST);
        timKiemPanel.add(timKiemBacSiField, BorderLayout.CENTER);
        panel.add(timKiemPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bangBacSi), BorderLayout.CENTER);

        JPanel thaoTac = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        thaoTac.add(btnThem);
        thaoTac.add(btnSua);
        thaoTac.add(btnXoa);
        panel.add(thaoTac, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> moHopThoaiBacSi(null));
        btnSua.addActionListener(e -> {
            BacSi bs = layBacSiDangChon();
            if (bs != null) {
                moHopThoaiBacSi(bs);
            }
        });
        btnXoa.addActionListener(e -> xoaBacSi());
        return panel;
    }

    private JPanel taoTabLichKham() {
        bangLichKhamModel = taoModel(new String[]{"Mã lịch", "Bệnh nhân", "Bác sĩ", "Ngày khám", "Trạng thái"});
        bangLichKham = new JTable(bangLichKhamModel);
        capNhatBangLichKham();

        JPanel panel = new JPanel(new BorderLayout());
        locNgayField = new JTextField();
        JPanel locPanel = new JPanel(new BorderLayout(5, 0));
        locPanel.add(new JLabel("Lọc theo ngày (dd/MM/yyyy):"), BorderLayout.WEST);
        locPanel.add(locNgayField, BorderLayout.CENTER);
        JPanel nutLoc = new JPanel();
        JButton btnLoc = new JButton("Lọc");
        JButton btnHienTatCa = new JButton("Hiện tất cả");
        nutLoc.add(btnLoc);
        nutLoc.add(btnHienTatCa);
        locPanel.add(nutLoc, BorderLayout.EAST);
        btnLoc.addActionListener(e -> capNhatBangLichKham());
        btnHienTatCa.addActionListener(e -> {
            locNgayField.setText("");
            capNhatBangLichKham();
        });
        panel.add(locPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bangLichKham), BorderLayout.CENTER);

        JPanel thaoTac = new JPanel();
        JButton btnLapLich = new JButton("Lập lịch");
        JButton btnDaKham = new JButton("Đánh dấu đã khám");
        JButton btnXoa = new JButton("Xóa");
        thaoTac.add(btnLapLich);
        thaoTac.add(btnDaKham);
        thaoTac.add(btnXoa);
        panel.add(thaoTac, BorderLayout.SOUTH);

        btnLapLich.addActionListener(e -> taoLichKham());
        btnDaKham.addActionListener(e -> danhDauDaKham());
        btnXoa.addActionListener(e -> xoaLichKham());
        return panel;
    }

    private JPanel taoTabDonThuocVaHoaDon() {
        JPanel container = new JPanel(new GridLayout(2, 1, 0, 10));

        bangDonThuocModel = taoModel(new String[]{"Mã đơn", "Lịch khám", "Bệnh nhân", "Chẩn đoán", "Thuốc", "Tiền thuốc"});
        bangDonThuoc = new JTable(bangDonThuocModel);
        capNhatBangDonThuoc();

        JPanel donThuocPanel = new JPanel(new BorderLayout());
        donThuocPanel.add(new JScrollPane(bangDonThuoc), BorderLayout.CENTER);
        JPanel thaoTacDonThuoc = new JPanel();
        JButton btnTaoDon = new JButton("Tạo đơn thuốc");
        JButton btnXoaDon = new JButton("Xóa");
        thaoTacDonThuoc.add(btnTaoDon);
        thaoTacDonThuoc.add(btnXoaDon);
        donThuocPanel.add(thaoTacDonThuoc, BorderLayout.SOUTH);

        btnTaoDon.addActionListener(e -> taoDonThuoc());
        btnXoaDon.addActionListener(e -> xoaDonThuoc());

        bangHoaDonModel = taoModel(new String[]{"Mã hóa đơn", "Bệnh nhân", "Đơn thuốc", "Tiền khám", "Tiền thuốc", "Tổng cộng"});
        bangHoaDon = new JTable(bangHoaDonModel);
        capNhatBangHoaDon();

        JPanel hoaDonPanel = new JPanel(new BorderLayout());
        hoaDonPanel.add(new JScrollPane(bangHoaDon), BorderLayout.CENTER);
        JPanel thaoTacHoaDon = new JPanel();
        JButton btnLapHD = new JButton("Lập hóa đơn");
        JButton btnXoaHD = new JButton("Xóa");
        thaoTacHoaDon.add(btnLapHD);
        thaoTacHoaDon.add(btnXoaHD);
        hoaDonPanel.add(thaoTacHoaDon, BorderLayout.SOUTH);

        btnLapHD.addActionListener(e -> lapHoaDon());
        btnXoaHD.addActionListener(e -> xoaHoaDon());

        container.add(donThuocPanel);
        container.add(hoaDonPanel);
        return container;
    }

    private DefaultTableModel taoModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    // ----------------- Bệnh nhân -----------------
    private void moHopThoaiBenhNhan(BenhNhan benhNhan) {
        JTextField txtMa = new JTextField(benhNhan == null ? "" : benhNhan.getId());
        txtMa.setEnabled(benhNhan == null);
        JTextField txtTen = new JTextField(benhNhan == null ? "" : benhNhan.getFullName());
        JTextField txtTuoi = new JTextField(benhNhan == null ? "" : String.valueOf(benhNhan.getAge()));
        JTextField txtGioiTinh = new JTextField(benhNhan == null ? "" : benhNhan.getGender());
        JTextField txtDiaChi = new JTextField(benhNhan == null ? "" : benhNhan.getAddress());
        JTextField txtBHYT = new JTextField(benhNhan == null ? "" : benhNhan.getInsuranceNumber());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 5));
        form.add(new JLabel("Mã bệnh nhân:"));
        form.add(txtMa);
        form.add(new JLabel("Họ tên:"));
        form.add(txtTen);
        form.add(new JLabel("Tuổi:"));
        form.add(txtTuoi);
        form.add(new JLabel("Giới tính:"));
        form.add(txtGioiTinh);
        form.add(new JLabel("Địa chỉ:"));
        form.add(txtDiaChi);
        form.add(new JLabel("Số BHYT:"));
        form.add(txtBHYT);

        int option = JOptionPane.showConfirmDialog(this, form,
                benhNhan == null ? "Thêm bệnh nhân" : "Cập nhật bệnh nhân",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = txtMa.getText().trim();
                String name = txtTen.getText().trim();
                int age = Integer.parseInt(txtTuoi.getText().trim());
                String gender = CongCuBenhVien.normalizeGender(txtGioiTinh.getText());
                String address = txtDiaChi.getText().trim();
                String insurance = txtBHYT.getText().trim();

                if (benhNhan == null) {
                    boolean exists = khoBenhNhan.find(p -> p.getId().equalsIgnoreCase(id)).isPresent();
                    if (exists) {
                        CongCuBenhVien.showError(this, "Mã bệnh nhân đã tồn tại");
                        return;
                    }
                    BenhNhan moi = new BenhNhan(id, name, age, gender, address, insurance);
                    khoBenhNhan.add(moi);
                } else {
                    benhNhan.setFullName(name);
                    benhNhan.setAge(age);
                    benhNhan.setGender(gender);
                    benhNhan.setAddress(address);
                    benhNhan.setInsuranceNumber(insurance);
                }
                luuBenhNhan();
                capNhatBangBenhNhan();
            } catch (NumberFormatException ex) {
                CongCuBenhVien.showError(this, "Tuổi không hợp lệ");
            }
        }
    }

    private BenhNhan layBenhNhanDangChon() {
        int row = bangBenhNhan.getSelectedRow();
        if (row >= 0) {
            String id = bangBenhNhanModel.getValueAt(row, 0).toString();
            return khoBenhNhan.find(p -> p.getId().equals(id)).orElse(null);
        }
        CongCuBenhVien.showError(this, "Vui lòng chọn bệnh nhân");
        return null;
    }

    private void xoaBenhNhan() {
        BenhNhan patient = layBenhNhanDangChon();
        if (patient != null) {
            xoaQuanHeBenhNhan(patient.getId());
            khoBenhNhan.remove(patient);
            luuBenhNhan();
            capNhatBangBenhNhan();
            capNhatBangLichKham();
            capNhatBangDonThuoc();
            capNhatBangHoaDon();
            luuLichKham();
        }
    }

    private void xoaQuanHeBenhNhan(String patientId) {
        List<String> appointmentIds = khoLichKham.getAll().stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .map(LichKham::getId)
                .collect(Collectors.toList());
        khoLichKham.getAll().removeIf(a -> appointmentIds.contains(a.getId()));

        List<String> prescriptionIds = khoDonThuoc.getAll().stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .map(DonThuoc::getId)
                .collect(Collectors.toList());
        khoDonThuoc.getAll().removeIf(p -> prescriptionIds.contains(p.getId()));

        khoHoaDon.getAll().removeIf(i -> prescriptionIds.contains(i.getMaDonThuoc()));

        luuLichKham();
        luuDonThuoc();
        luuHoaDon();
    }

    // ----------------- Bác sĩ -----------------
    private void moHopThoaiBacSi(BacSi bacSi) {
        JTextField txtMa = new JTextField(bacSi == null ? "" : bacSi.getId());
        txtMa.setEnabled(bacSi == null);
        JTextField txtTen = new JTextField(bacSi == null ? "" : bacSi.getFullName());
        JTextField txtTuoi = new JTextField(bacSi == null ? "" : String.valueOf(bacSi.getAge()));
        JTextField txtGioiTinh = new JTextField(bacSi == null ? "" : bacSi.getGender());
        JTextField txtDiaChi = new JTextField(bacSi == null ? "" : bacSi.getAddress());
        JTextField txtChuyenKhoa = new JTextField(bacSi == null ? "" : bacSi.getSpecialty());
        JTextField txtSdt = new JTextField(bacSi == null ? "" : bacSi.getPhone());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 5));
        form.add(new JLabel("Mã bác sĩ:"));
        form.add(txtMa);
        form.add(new JLabel("Họ tên:"));
        form.add(txtTen);
        form.add(new JLabel("Tuổi:"));
        form.add(txtTuoi);
        form.add(new JLabel("Giới tính:"));
        form.add(txtGioiTinh);
        form.add(new JLabel("Địa chỉ:"));
        form.add(txtDiaChi);
        form.add(new JLabel("Chuyên khoa:"));
        form.add(txtChuyenKhoa);
        form.add(new JLabel("SĐT:"));
        form.add(txtSdt);

        int option = JOptionPane.showConfirmDialog(this, form,
                bacSi == null ? "Thêm bác sĩ" : "Cập nhật bác sĩ",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = txtMa.getText().trim();
                String name = txtTen.getText().trim();
                int age = Integer.parseInt(txtTuoi.getText().trim());
                String gender = CongCuBenhVien.normalizeGender(txtGioiTinh.getText());
                String address = txtDiaChi.getText().trim();
                String specialty = txtChuyenKhoa.getText().trim();
                String phone = txtSdt.getText().trim();

                if (bacSi == null) {
                    boolean exists = khoBacSi.find(d -> d.getId().equalsIgnoreCase(id)).isPresent();
                    if (exists) {
                        CongCuBenhVien.showError(this, "Mã bác sĩ đã tồn tại");
                        return;
                    }
                    BacSi moi = new BacSi(id, name, age, gender, address, specialty, phone);
                    khoBacSi.add(moi);
                } else {
                    bacSi.setFullName(name);
                    bacSi.setAge(age);
                    bacSi.setGender(gender);
                    bacSi.setAddress(address);
                    bacSi.setSpecialty(specialty);
                    bacSi.setPhone(phone);
                }
                luuBacSi();
                capNhatBangBacSi();
                capNhatBangLichKham();
            } catch (NumberFormatException ex) {
                CongCuBenhVien.showError(this, "Tuổi không hợp lệ");
            }
        }
    }

    private BacSi layBacSiDangChon() {
        int row = bangBacSi.getSelectedRow();
        if (row >= 0) {
            String id = bangBacSiModel.getValueAt(row, 0).toString();
            return khoBacSi.find(d -> d.getId().equals(id)).orElse(null);
        }
        CongCuBenhVien.showError(this, "Vui lòng chọn bác sĩ");
        return null;
    }

    private void xoaBacSi() {
        BacSi doctor = layBacSiDangChon();
        if (doctor != null) {
            xoaQuanHeBacSi(doctor.getId());
            khoBacSi.remove(doctor);
            luuBacSi();
            capNhatBangBacSi();
            capNhatBangLichKham();
            capNhatBangDonThuoc();
            capNhatBangHoaDon();
        }
    }

    private void xoaQuanHeBacSi(String doctorId) {
        List<String> appointmentIds = khoLichKham.getAll().stream()
                .filter(a -> a.getDoctorId().equals(doctorId))
                .map(LichKham::getId)
                .collect(Collectors.toList());
        khoLichKham.getAll().removeIf(a -> appointmentIds.contains(a.getId()));

        List<String> prescriptionIds = khoDonThuoc.getAll().stream()
                .filter(p -> appointmentIds.contains(p.getAppointmentId()))
                .map(DonThuoc::getId)
                .collect(Collectors.toList());
        khoDonThuoc.getAll().removeIf(p -> prescriptionIds.contains(p.getId()));
        khoHoaDon.getAll().removeIf(i -> prescriptionIds.contains(i.getMaDonThuoc()));

        luuLichKham();
        luuDonThuoc();
        luuHoaDon();
    }

    // ----------------- Lịch khám -----------------
    private void taoLichKham() {
        if (khoBenhNhan.getAll().isEmpty() || khoBacSi.getAll().isEmpty()) {
            CongCuBenhVien.showError(this, "Cần có bệnh nhân và bác sĩ trước khi lập lịch");
            return;
        }

        JTextField txtMa = new JTextField();
        JTextField txtBenhNhan = new JTextField();
        JTextField txtBacSi = new JTextField();
        JTextField txtNgay = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 5));
        form.add(new JLabel("Mã lịch khám:"));
        form.add(txtMa);
        form.add(new JLabel("Mã bệnh nhân:"));
        form.add(txtBenhNhan);
        form.add(new JLabel("Mã bác sĩ:"));
        form.add(txtBacSi);
        form.add(new JLabel("Ngày khám (dd/MM/yyyy):"));
        form.add(txtNgay);

        int option = JOptionPane.showConfirmDialog(this, form, "Lập lịch khám",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String id = txtMa.getText().trim();
            String patientId = txtBenhNhan.getText().trim();
            String doctorId = txtBacSi.getText().trim();
            String date = txtNgay.getText().trim();

            if (id.isEmpty() || patientId.isEmpty() || doctorId.isEmpty() || date.isEmpty()) {
                CongCuBenhVien.showError(this, "Vui lòng điền đầy đủ thông tin");
                return;
            }

            if (khoLichKham.find(a -> a.getId().equalsIgnoreCase(id)).isPresent()) {
                CongCuBenhVien.showError(this, "Mã lịch khám đã tồn tại");
                return;
            }
            boolean patientExists = khoBenhNhan.find(p -> p.getId().equalsIgnoreCase(patientId)).isPresent();
            boolean doctorExists = khoBacSi.find(d -> d.getId().equalsIgnoreCase(doctorId)).isPresent();
            if (!patientExists || !doctorExists) {
                CongCuBenhVien.showError(this, "Không tìm thấy bệnh nhân hoặc bác sĩ");
                return;
            }

            LichKham lich = new LichKham(id, patientId, doctorId, date);
            khoLichKham.add(lich);
            luuLichKham();
            capNhatBangLichKham();
        }
    }

    private LichKham layLichKhamDangChon() {
        int row = bangLichKham.getSelectedRow();
        if (row >= 0) {
            String id = bangLichKhamModel.getValueAt(row, 0).toString();
            return khoLichKham.find(a -> a.getId().equals(id)).orElse(null);
        }
        CongCuBenhVien.showError(this, "Vui lòng chọn lịch khám");
        return null;
    }

    private void danhDauDaKham() {
        LichKham appointment = layLichKhamDangChon();
        if (appointment != null) {
            appointment.setStatus(TrangThaiLichKham.DA_KHAM);
            luuLichKham();
            capNhatBangLichKham();
        }
    }

    private void xoaLichKham() {
        LichKham appointment = layLichKhamDangChon();
        if (appointment != null) {
            List<String> prescriptionIds = khoDonThuoc.getAll().stream()
                    .filter(p -> p.getAppointmentId().equals(appointment.getId()))
                    .map(DonThuoc::getId)
                    .collect(Collectors.toList());
            khoDonThuoc.getAll().removeIf(p -> prescriptionIds.contains(p.getId()));
            khoHoaDon.getAll().removeIf(i -> prescriptionIds.contains(i.getMaDonThuoc()));
            khoLichKham.remove(appointment);
            luuLichKham();
            luuDonThuoc();
            luuHoaDon();
            capNhatBangLichKham();
            capNhatBangDonThuoc();
            capNhatBangHoaDon();
        }
    }

    // ----------------- Đơn thuốc -----------------
    private void taoDonThuoc() {
        boolean hasCompletedAppointment = khoLichKham.getAll().stream().anyMatch(LichKham::isCompleted);
        if (!hasCompletedAppointment) {
            CongCuBenhVien.showError(this, "Chưa có lịch khám nào ở trạng thái đã khám");
            return;
        }

        JTextField txtMa = new JTextField();
        JTextField txtLich = new JTextField();
        JTextField txtChanDoan = new JTextField();
        JTextField txtThuoc = new JTextField();
        JTextField txtTienThuoc = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 5));
        form.add(new JLabel("Mã đơn thuốc:"));
        form.add(txtMa);
        form.add(new JLabel("Mã lịch khám:"));
        form.add(txtLich);
        form.add(new JLabel("Chẩn đoán:"));
        form.add(txtChanDoan);
        form.add(new JLabel("Thuốc (Tên:SL;...)"));
        form.add(txtThuoc);
        form.add(new JLabel("Tiền thuốc:"));
        form.add(txtTienThuoc);

        int option = JOptionPane.showConfirmDialog(this, form, "Tạo đơn thuốc",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = txtMa.getText().trim();
                String appointmentId = txtLich.getText().trim();
                String diagnosis = txtChanDoan.getText().trim();
                double medicineCost = Double.parseDouble(txtTienThuoc.getText().trim());
                if (khoDonThuoc.find(p -> p.getId().equalsIgnoreCase(id)).isPresent()) {
                    CongCuBenhVien.showError(this, "Mã đơn thuốc đã tồn tại");
                    return;
                }
                LichKham appointment = khoLichKham.find(a -> a.getId().equalsIgnoreCase(appointmentId))
                        .orElse(null);
                if (appointment == null || !appointment.isCompleted()) {
                    CongCuBenhVien.showError(this, "Chỉ tạo đơn cho lịch khám đã hoàn tất");
                    return;
                }
                boolean existed = khoDonThuoc.getAll().stream()
                        .anyMatch(p -> p.getAppointmentId().equals(appointmentId));
                if (existed) {
                    CongCuBenhVien.showError(this, "Lịch khám này đã có đơn thuốc");
                    return;
                }
                HashMap<String, Integer> medications = CongCuBenhVien.parseMedications(txtThuoc.getText());
                DonThuoc don = new DonThuoc(id, appointment.getPatientId(), appointmentId, diagnosis, medications, medicineCost);
                khoDonThuoc.add(don);
                luuDonThuoc();
                capNhatBangDonThuoc();
            } catch (NumberFormatException ex) {
                CongCuBenhVien.showError(this, "Chi phí thuốc không hợp lệ");
            }
        }
    }

    private DonThuoc layDonThuocDangChon() {
        int row = bangDonThuoc.getSelectedRow();
        if (row >= 0) {
            String id = bangDonThuocModel.getValueAt(row, 0).toString();
            return khoDonThuoc.find(p -> p.getId().equals(id)).orElse(null);
        }
        CongCuBenhVien.showError(this, "Vui lòng chọn đơn thuốc");
        return null;
    }

    private void xoaDonThuoc() {
        DonThuoc prescription = layDonThuocDangChon();
        if (prescription != null) {
            khoHoaDon.getAll().removeIf(i -> i.getMaDonThuoc().equals(prescription.getId()));
            khoDonThuoc.remove(prescription);
            luuDonThuoc();
            luuHoaDon();
            capNhatBangDonThuoc();
            capNhatBangHoaDon();
        }
    }

    // ----------------- Hóa đơn -----------------
    private void lapHoaDon() {
        if (khoDonThuoc.getAll().isEmpty()) {
            CongCuBenhVien.showError(this, "Chưa có đơn thuốc để lập hóa đơn");
            return;
        }

        JTextField txtMa = new JTextField();
        JTextField txtDonThuoc = new JTextField();
        JTextField txtTienKham = new JTextField();
        JTextField txtTienThuoc = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 5));
        form.add(new JLabel("Mã hóa đơn:"));
        form.add(txtMa);
        form.add(new JLabel("Mã đơn thuốc:"));
        form.add(txtDonThuoc);
        form.add(new JLabel("Tiền khám:"));
        form.add(txtTienKham);
        form.add(new JLabel("Tiền thuốc (tùy chọn):"));
        form.add(txtTienThuoc);

        int option = JOptionPane.showConfirmDialog(this, form, "Lập hóa đơn",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = txtMa.getText().trim();
                String prescriptionId = txtDonThuoc.getText().trim();
                double consultationFee = Double.parseDouble(txtTienKham.getText().trim());
                if (khoHoaDon.find(i -> i.getId().equalsIgnoreCase(id)).isPresent()) {
                    CongCuBenhVien.showError(this, "Mã hóa đơn đã tồn tại");
                    return;
                }
                DonThuoc prescription = khoDonThuoc.find(p -> p.getId().equalsIgnoreCase(prescriptionId)).orElse(null);
                if (prescription == null) {
                    CongCuBenhVien.showError(this, "Không tìm thấy đơn thuốc");
                    return;
                }
                double medicineCost = prescription.getMedicineCost();
                if (!txtTienThuoc.getText().trim().isEmpty()) {
                    medicineCost = Double.parseDouble(txtTienThuoc.getText().trim());
                }
                HoaDon invoice = new HoaDon(id, prescription.getPatientId(), prescriptionId, consultationFee, medicineCost);
                khoHoaDon.add(invoice);
                luuHoaDon();
                capNhatBangHoaDon();
            } catch (NumberFormatException ex) {
                CongCuBenhVien.showError(this, "Chi phí không hợp lệ");
            }
        }
    }

    private HoaDon layHoaDonDangChon() {
        int row = bangHoaDon.getSelectedRow();
        if (row >= 0) {
            String id = bangHoaDonModel.getValueAt(row, 0).toString();
            return khoHoaDon.find(i -> i.getId().equals(id)).orElse(null);
        }
        CongCuBenhVien.showError(this, "Vui lòng chọn hóa đơn");
        return null;
    }

    private void xoaHoaDon() {
        HoaDon invoice = layHoaDonDangChon();
        if (invoice != null) {
            khoHoaDon.remove(invoice);
            luuHoaDon();
            capNhatBangHoaDon();
        }
    }

    // ----------------- Cập nhật bảng -----------------
    private void capNhatBangBenhNhan() {
        bangBenhNhanModel.setRowCount(0);
        String keyword = timKiemBenhNhanField != null ? timKiemBenhNhanField.getText().trim().toLowerCase() : "";
        for (BenhNhan p : khoBenhNhan.getAll()) {
            if (!keyword.isEmpty()) {
                String text = (p.getId() + " " + p.getFullName() + " " + p.getAddress()).toLowerCase();
                if (!text.contains(keyword)) {
                    continue;
                }
            }
            bangBenhNhanModel.addRow(new Object[]{p.getId(), p.getFullName(), p.getAge(), p.getGender(), p.getAddress(), p.getInsuranceNumber()});
        }
    }

    private void capNhatBangBacSi() {
        bangBacSiModel.setRowCount(0);
        String keyword = timKiemBacSiField != null ? timKiemBacSiField.getText().trim().toLowerCase() : "";
        for (BacSi d : khoBacSi.getAll()) {
            if (!keyword.isEmpty()) {
                String text = (d.getId() + " " + d.getFullName() + " " + d.getSpecialty()).toLowerCase();
                if (!text.contains(keyword)) {
                    continue;
                }
            }
            bangBacSiModel.addRow(new Object[]{d.getId(), d.getFullName(), d.getAge(), d.getGender(), d.getSpecialty(), d.getPhone()});
        }
    }

    private void capNhatBangLichKham() {
        bangLichKhamModel.setRowCount(0);
        Map<String, BenhNhan> benhNhanMap = CongCuBenhVien.toMap(khoBenhNhan.getAll());
        Map<String, BacSi> bacSiMap = CongCuBenhVien.toMap(khoBacSi.getAll());
        String ngayLoc = locNgayField != null ? locNgayField.getText().trim() : "";
        for (LichKham lich : khoLichKham.getAll()) {
            if (!ngayLoc.isEmpty() && (lich.getAppointmentDate() == null || !lich.getAppointmentDate().contains(ngayLoc))) {
                continue;
            }
            BenhNhan patient = benhNhanMap.get(lich.getPatientId());
            BacSi doctor = bacSiMap.get(lich.getDoctorId());
            bangLichKhamModel.addRow(new Object[]{
                    lich.getId(),
                    patient != null ? patient.getFullName() : lich.getPatientId(),
                    doctor != null ? doctor.getFullName() : lich.getDoctorId(),
                    lich.getAppointmentDate(),
                    lich.getStatus().getNhan()
            });
        }
    }

    private void capNhatBangDonThuoc() {
        bangDonThuocModel.setRowCount(0);
        Map<String, LichKham> lichMap = CongCuBenhVien.toMap(khoLichKham.getAll());
        Map<String, BenhNhan> benhNhanMap = CongCuBenhVien.toMap(khoBenhNhan.getAll());
        for (DonThuoc don : khoDonThuoc.getAll()) {
            LichKham lich = lichMap.get(don.getAppointmentId());
            BenhNhan patient = benhNhanMap.get(don.getPatientId());
            bangDonThuocModel.addRow(new Object[]{
                    don.getId(),
                    lich != null ? lich.getId() : don.getAppointmentId(),
                    patient != null ? patient.getFullName() : don.getPatientId(),
                    don.getDiagnosis(),
                    don.toSummary(),
                    don.getMedicineCost()
            });
        }
    }

    private void capNhatBangHoaDon() {
        bangHoaDonModel.setRowCount(0);
        Map<String, BenhNhan> benhNhanMap = CongCuBenhVien.toMap(khoBenhNhan.getAll());
        for (HoaDon hd : khoHoaDon.getAll()) {
            BenhNhan patient = benhNhanMap.get(hd.getPatientId());
            bangHoaDonModel.addRow(new Object[]{
                    hd.getId(),
                    patient != null ? patient.getFullName() : hd.getPatientId(),
                    hd.getMaDonThuoc(),
                    hd.getTienKham(),
                    hd.getTienThuoc(),
                    hd.getTongTien()
            });
        }
    }

    // ----------------- Lưu file -----------------
    private void luuBenhNhan() {
        LuuTru.save(BENH_NHAN_FILE, khoBenhNhan.getAll());
    }

    private void luuBacSi() {
        LuuTru.save(BAC_SI_FILE, khoBacSi.getAll());
    }

    private void luuLichKham() {
        LuuTru.save(LICH_KHAM_FILE, khoLichKham.getAll());
    }

    private void luuDonThuoc() {
        LuuTru.save(DON_THUOC_FILE, khoDonThuoc.getAll());
    }

    private void luuHoaDon() {
        LuuTru.save(HOA_DON_FILE, khoHoaDon.getAll());
    }
}

