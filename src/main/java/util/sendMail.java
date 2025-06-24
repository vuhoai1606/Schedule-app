package util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class sendMail {
    public static boolean sendVerificationCode(String toEmail, String code) {
        final String fromEmail = "vuth.24it@vku.udn.vn";
        final String password = "dopl tkvb efip kwuw"; // Mật khẩu ứng dụng (cần phải tạo nếu dùng xác thực hai yếu tố)

        Properties props = new Properties();            // Khởi tạo đối tượng Properties để chứa các thuộc tính kết nối SMTP
        props.put("mail.smtp.host", "smtp.gmail.com");  // SMTP server
        props.put("mail.smtp.port", "587");             // Cổng SMTP cho giao thức TLS (587)
        props.put("mail.smtp.auth", "true");            // Kích hoạt xác thực cho SMTP server
        props.put("mail.smtp.starttls.enable", "true"); // Kích hoạt tính năng STARTTLS (mã hóa dữ liệu khi truyền)

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail, false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject("Xác nhận đăng ký");
            msg.setText("Mã xác nhận của bạn là: " + code);
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}