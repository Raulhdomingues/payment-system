package com.rauldomingues.payment_system.service;

import com.rauldomingues.payment_system.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    private String verificationUrl = "http://localhost:8080/user/verify?code=";

    public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "raulhdomingues@gmail.com";
        String senderName = "Blockit";
        String subject = "Please verify your registration";

//        String content = "Dear [[name]],<br>"
//                + "Please click the link below to verify your registration:<br>"
//                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
//                + "Thank you,<br>"
//                + "Blockit Team.";

        String content = """
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional //EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="x-apple-disable-message-reformatting">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <style type="text/css">
    @media only screen and (min-width: 620px) {
      .u-row { width: 600px !important; }
      .u-row .u-col { vertical-align: top; }
      .u-row .u-col-100 { width: 600px !important; }
    }

    @media only screen and (max-width: 620px) {
      .u-row-container {
        max-width: 100% !important;
        padding-left: 0px !important;
        padding-right: 0px !important;
      }

      .u-row {
        width: 100% !important;
      }

      .u-row .u-col {
        display: block !important;
        width: 100% !important;
      }
    }

    body { margin: 0; padding: 0; }
    table, td, tr { border-collapse: collapse; }
    p { margin: 0; }
  </style>

</head>

<body style="margin:0;padding:0;background-color:#f9f9f9;">
  <table width="100%" style="background-color:#f9f9f9;">
    <tr>
      <td align="center">

        <table width="600" style="background-color:#003399;">
          <tr>
            <td align="center" style="padding:40px;">
              <img src="https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png" width="150" />

              <p style="color:#e5eaf5;font-size:14px;font-weight:bold;margin-top:20px;">
                Muito obrigado por se registrar
              </p>

              <p style="color:#e5eaf5;font-size:28px;font-weight:bold;">
                Verifique o seu e-mail
              </p>
            </td>
          </tr>
        </table>

        <table width="600" style="background-color:#ffffff;">
          <tr>
            <td align="center" style="padding:30px;">
              <p>Olá [[name]],</p>
              <p>Você iniciou a criação da sua conta mas falta um último passo!</p>
              <p>Clique no botão abaixo e confirme o seu registro</p>

              <a href="[[URL]]" style="background:#ff6600;color:#fff;padding:14px 44px;text-decoration:none;border-radius:4px;display:inline-block;margin-top:20px;">
                VERIFIQUE O SEU E-MAIL
              </a>

              <p style="margin-top:20px;">Obrigado</p>
            </td>
          </tr>
        </table>

      </td>
    </tr>
  </table>
</body>

</html>
""";

        content = content.replace("[[name]]", user.getName());
        String verifyURL = verificationUrl + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
