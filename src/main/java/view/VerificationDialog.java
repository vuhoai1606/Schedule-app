package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class VerificationDialog extends JDialog {
    private JTextField codeField;
    private JLabel resendLabel;
    private JLabel countdownLabel;
    private JButton confirmButton;

    private Timer timer;
    private int countdown = 30;
    private Runnable resendAction;
    private Runnable confirmAction;

    public VerificationDialog(JFrame parent, Runnable resendAction, Runnable confirmAction) {
        super(parent, "Xác nhận Email", true);
        this.resendAction = resendAction;
        this.confirmAction = confirmAction;

        initComponents();
        startCountdown();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(350, 200);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 5));

        JLabel label = new JLabel("Nhập mã xác nhận:");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        codeField = new JTextField();
        codeField.setHorizontalAlignment(JTextField.CENTER);

        JPanel resendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resendLabel = new JLabel("Gửi lại mã");
        countdownLabel = new JLabel("(30s)");

        resendLabel.setForeground(Color.GRAY);
        resendLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resendLabel.setEnabled(false);

        resendPanel.add(resendLabel);
        resendPanel.add(countdownLabel);

        panel.add(label);
        panel.add(codeField);
        panel.add(resendPanel);

        add(panel, BorderLayout.CENTER);

        confirmButton = new JButton("Xác nhận");
        add(confirmButton, BorderLayout.SOUTH);

        resendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (resendLabel.isEnabled()) {
                    resendAction.run();
                    resetCountdown();
                }
            }
        });

        confirmButton.addActionListener(e -> confirmAction.run());
    }

    private void startCountdown() {
        timer = new Timer();
        countdown = 30;
        countdownLabel.setText("(" + countdown + "s)");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    countdown--;
                    if (countdown <= 0) {
                        resendLabel.setForeground(Color.BLUE);
                        resendLabel.setEnabled(true);
                        countdownLabel.setText("");
                        timer.cancel();
                    } else {
                        countdownLabel.setText("(" + countdown + "s)");
                    }
                });
            }
        }, 1000, 1000);
    }

    private void resetCountdown() {
        if (timer != null) {
            timer.cancel();
        }
        resendLabel.setForeground(Color.GRAY);
        resendLabel.setEnabled(false);
        startCountdown();
    }

    public String getCode() {
        return codeField.getText();
    }
}
