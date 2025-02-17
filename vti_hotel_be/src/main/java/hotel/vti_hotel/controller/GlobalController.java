package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.LoginRequest;
import hotel.vti_hotel.modal.request.UploadRequest;
import hotel.vti_hotel.service.global.IGlobalService;
import hotel.vti_hotel.service.mailSender.IMailSender;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class GlobalController {
    private final IGlobalService globalService;
    private final IMailSender mailSender;
    public GlobalController(IGlobalService globalService, IMailSender mailSender) {
        this.globalService = globalService;
        this.mailSender = mailSender;
    }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
            try{
                return ResponseEntity.ok(globalService.login(loginRequest));
            }catch(Exception e){
                return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @PostMapping(value = "/upImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> upToCloudinary(
            UploadRequest request){
        try{
            return ResponseEntity.ok(globalService.upload(request));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: "+e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/sendMail")
    public ResponseEntity<?> sendMailSupported(
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String body
    ){
        try{
            return ResponseEntity.ok(mailSender.mailUserComplain(email, subject, body));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't send mail: "+e.getMessage());
        }
    }
}
