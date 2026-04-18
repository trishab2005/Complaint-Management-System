package com.example.service.impl;

import com.example.config.DBConfig;
import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;
import com.example.service.ComplaintService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComplaintServiceImpl implements ComplaintService {

    @Override
    public void addComplaint(Complaint complaint) {
        try (Connection con = DBConfig.makeCon()) {
            String sql = "insert into complaints(name,description,status) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, complaint.getName());
            ps.setString(2, complaint.getDescription());
            ps.setString(3, complaint.getStatus());
            ps.executeUpdate();
            IO.println("----------complaint added in db----------");
        } catch (SQLException e) {
            IO.println("sql error");
        }
    }

    @Override
    public Complaint findById(int id) throws ComplaintNotFoundException {
        return getAllComplaints()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint with ID " + id + " not found."));
    }

    @Override
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        try (Connection con = DBConfig.makeCon()) {
            ResultSet rs = con.createStatement().executeQuery("select * from complaints");
            while (rs.next()) {
                list.add(new Complaint(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.stream()
                .filter(c -> c.getStatus().equalsIgnoreCase("OPEN"))
                .collect(Collectors.toList());
    }

    @Override
    public void updateComplaint(int id, String status) throws Exception {
        try (Connection con = DBConfig.makeCon()) {
            String sql = "UPDATE complaints SET status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            if (ps.executeUpdate() == 0) {
                throw new ComplaintNotFoundException("no complaint found");
            }
        }
    }

    @Override
    public void deleteComplaint(int id) throws Exception {

        try (Connection con = DBConfig.makeCon()) {
            String sql = "DELETE FROM complaints WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new ComplaintNotFoundException("delete failed");
            }
        }
    }

    @Override
    public void updateStatusUsingProcedure(int id, String status) {
        try (Connection con = DBConfig.makeCon()) {
            CallableStatement cs = con.prepareCall("{call update_status_proc(?,?)}");
            cs.setInt(1, id);
            cs.setString(2, status);
            cs.execute();
            IO.println("----------complaint status updated via procedure----------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
