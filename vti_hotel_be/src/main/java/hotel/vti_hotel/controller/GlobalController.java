package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.LoginRequest;
import hotel.vti_hotel.modal.request.UploadRequest;
import hotel.vti_hotel.service.global.IGlobalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class GlobalController {
    private final IGlobalService globalService;
    public GlobalController(IGlobalService globalService) {
        this.globalService = globalService;
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
        }
    }
}
