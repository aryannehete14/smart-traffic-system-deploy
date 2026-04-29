package SmartTrafficSystem.project;

import SmartTrafficSystem.project.model.Violation;
import SmartTrafficSystem.project.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// REMOVED @Component to prevent HeadlessException during Spring startup
public class TrafficUI extends JFrame {

    @Autowired
    private TrafficService trafficService;

    private DefaultTableModel tableModel;
    private JTable dataTable;
    private JLabel lblTotalCount, lblTotalFine;

    public void initUI() {
        setTitle("Enterprise Smart Traffic Management System");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- TOP: DASHBOARD STATISTICS ---
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        statsPanel.setBackground(new Color(230, 235, 240));

        lblTotalCount = new JLabel("Total Violations: 0");
        lblTotalFine = new JLabel("Total Fines: Rs. 0");
        lblTotalCount.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTotalFine.setFont(new Font("SansSerif", Font.BOLD, 14));

        statsPanel.add(lblTotalCount);
        statsPanel.add(lblTotalFine);
        add(statsPanel, BorderLayout.NORTH);

        // --- CENTER: TABS ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Violation Entry", createEntryPanel());
        tabbedPane.addTab("Violation History", createHistoryPanel());
        add(tabbedPane, BorderLayout.CENTER);

        refreshDashboard();
        setVisible(true);
    }

    private JPanel createEntryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField txtVehicleId = new JTextField(15);
        JTextField txtSpeed = new JTextField(15);
        JTextField txtZone = new JTextField(15);
        JCheckBox chkEmergency = new JCheckBox("Emergency Vehicle Priority");

        // Corrected Method Calls
        addComp(panel, new JLabel("Vehicle ID:"), gbc, 0, 0);
        addComp(panel, txtVehicleId, gbc, 1, 0);
        addComp(panel, new JLabel("Speed (km/h):"), gbc, 0, 1);
        addComp(panel, txtSpeed, gbc, 1, 1);
        addComp(panel, new JLabel("Traffic Zone:"), gbc, 0, 2);
        addComp(panel, txtZone, gbc, 1, 2);
        addComp(panel, chkEmergency, gbc, 1, 3);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClear = new JButton("Clear Form");
        JButton btnSubmit = new JButton("Process Violation");
        btnSubmit.setBackground(new Color(40, 167, 69));
        btnSubmit.setForeground(Color.WHITE);

        btnPanel.add(btnClear);
        btnPanel.add(btnSubmit);
        gbc.gridwidth = 2;
        addComp(panel, btnPanel, gbc, 0, 4);

        btnSubmit.addActionListener(e -> {
            try {
                // 1. Capture Data from UI
                String id = txtVehicleId.getText();
                String speedStr = txtSpeed.getText();
                String zone = txtZone.getText();
                boolean isEmergency = chkEmergency.isSelected();

                // Validation for empty fields
                if (id.isEmpty() || speedStr.isEmpty() || zone.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double speed = Double.parseDouble(speedStr);

                // 2. Create Violation Object
                Violation v = new Violation();
                v.setVehicleId(id);
                v.setSpeed(speed);
                v.setZone(zone);
                v.setEmergency(isEmergency);

                // 3. Process via Service (Calculates fine and saves to MySQL)
                trafficService.processAndSave(v);

                // 4. Logic for the Popup Message
                if (isEmergency) {
                    JOptionPane.showMessageDialog(this,
                            "VEHICLE CHECKED\n\n" +
                                    "Vehicle ID: " + id + "\n" +
                                    "Status: NO VIOLATION\n" +
                                    "Reason: Emergency Vehicle Priority.",
                            "Traffic Clearance", JOptionPane.INFORMATION_MESSAGE);
                } else if (speed > 80) {
                    // Re-calculating fine just for the display message
                    int displayFine = (speed > 120) ? 5000 : 2000;

                    String message = String.format(
                            "⚠️ VIOLATION DETECTED! ⚠️\n\n" +
                                    "Vehicle ID: %s\n" +
                                    "Speed recorded: %.2f km/h\n" +
                                    "Zone: %s\n" +
                                    "Fine Amount: Rs. %d\n\n" +
                                    "The record has been saved to the database.",
                            id, speed, zone, displayFine);

                    JOptionPane.showMessageDialog(this, message, "Traffic Violation Alert", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "VEHICLE CHECKED\n\n" +
                                    "Vehicle ID: " + id + "\n" +
                                    "Speed: " + speed + " km/h\n" +
                                    "Status: WITHIN LIMITS",
                            "Traffic Clearance", JOptionPane.INFORMATION_MESSAGE);
                }

                // 5. Update the Dashboard Stats and Table
                refreshDashboard();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Speed must be a valid number!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> {
            txtVehicleId.setText("");
            txtSpeed.setText("");
            txtZone.setText("");
            chkEmergency.setSelected(false);
        });

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "ID", "Vehicle", "Speed", "Zone", "Fine", "Emergency" };
        tableModel = new DefaultTableModel(columns, 0);
        dataTable = new JTable(tableModel);

        JButton btnRefresh = new JButton("Refresh Table");
        btnRefresh.addActionListener(e -> refreshDashboard());

        panel.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        panel.add(btnRefresh, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshDashboard() {
        List<Violation> list = trafficService.getAll();
        long totalFine = list.stream().mapToLong(v -> (long) v.getFine()).sum();
        lblTotalCount.setText("Total Violations: " + list.size());
        lblTotalFine.setText("Total Fines: Rs. " + totalFine);

        tableModel.setRowCount(0);
        for (Violation v : list) {
            Object[] row = { v.getId(), v.getVehicleId(), v.getSpeed(), v.getZone(), v.getFine(), v.isEmergency() };
            tableModel.addRow(row);
        }
    }

    // --- FIX: The Method Signature and Logic ---
    private void addComp(JPanel panel, JComponent comp, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = (comp instanceof JCheckBox || comp instanceof JPanel && x == 0 && y == 4) ? 2 : 1;
        panel.add(comp, gbc);
    }

    // Replace the existing setTrafficService at the bottom of TrafficUI.java
    public void setTrafficService(TrafficService trafficService) {
        this.trafficService = trafficService;
    }
}