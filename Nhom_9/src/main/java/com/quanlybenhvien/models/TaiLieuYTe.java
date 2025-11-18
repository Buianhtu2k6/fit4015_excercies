package com.quanlybenhvien.models;

import java.io.Serializable;

public abstract class TaiLieuYTe implements DinhDanh, Serializable {
    private static final long serialVersionUID = 1L;
    private final String documentId;
    private final String patientId;

    protected TaiLieuYTe(String documentId, String patientId) {
        this.documentId = documentId;
        this.patientId = patientId;
    }

    @Override
    public String getId() {
        return documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getPatientId() {
        return patientId;
    }
}

