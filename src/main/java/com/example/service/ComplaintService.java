package com.example.service;

import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;

import java.util.List;

public interface ComplaintService {

    void addComplaint(Complaint complaint);

    Complaint findById(int id) throws ComplaintNotFoundException;

    List<Complaint> getAllComplaints();

    void updateComplaint(int id, String status) throws Exception;

    void deleteComplaint(int id) throws Exception;

    void updateStatusUsingProcedure(int id, String status);
}
