package hotel.vti_hotel.controller;

import hotel.vti_hotel.modal.request.AccountRequest;
import hotel.vti_hotel.service.account.IAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotel")
public class AccountController {
    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/findAccounts")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAccounts(){
        try{
            return new ResponseEntity<>(accountService.findAccounts(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAccountById")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> getAccountById(@RequestParam("accountId") int id){
        try{
            return new ResponseEntity<>(accountService.findAccountById(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAccountByEmail")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getAccountByEmail(@RequestParam("email") String email){
        try{
            return new ResponseEntity<>(accountService.findAccountByEmail(email), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createAccountByAdmin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> createAccountByAdmin(@RequestBody AccountRequest request){
        try {
            return new ResponseEntity<>(accountService.createAccount(request), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateAccount")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<?> updateAccount(@RequestParam("accountId") int id, @RequestBody AccountRequest request){
        try{
            return new ResponseEntity<>(accountService.updateAccount(id, request), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountRequest request){
        try{
            return new ResponseEntity<>(accountService.registerAccount(request), HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
