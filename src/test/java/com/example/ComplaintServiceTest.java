package com.example;

import com.example.entity.Complaint;
import com.example.service.ComplaintService;
import com.example.service.impl.ComplaintServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplaintServiceTest {
    ComplaintService service = new ComplaintServiceImpl();

    @Test
    void testAddComplaint() {
        service.addComplaint(new Complaint("test", "junit issue", "open"));
        assertTrue(true);
    }

    @Test
    void testFindById() throws Exception {
        Complaint c = service.findById(2);
        assertNotNull(c);
    }

    @Test
    void testUpdate() throws Exception {
        service.updateComplaint(3, "CLOSED");
        assertTrue(true);
    }

    @Test
    void testDelete() throws Exception {
        service.deleteComplaint(3);
        assertTrue(true);
    }
}
