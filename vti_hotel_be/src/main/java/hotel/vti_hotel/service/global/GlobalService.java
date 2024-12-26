package hotel.vti_hotel.service.global;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hotel.vti_hotel.config.jwt.JwtTokenUtil;
import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.modal.request.LoginRequest;
import hotel.vti_hotel.modal.response.AuthResponse;
import hotel.vti_hotel.modal.response.UploadResponse;
import hotel.vti_hotel.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class GlobalService implements IGlobalService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String identifier = userDetails.getUsername();
        Account account = accountRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException("Identifier not found"));
        String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, request.getIdentifier(),account.getImageCard(), userDetails.getAuthorities());
    }

    @Override
    public UploadResponse upload(MultipartFile path, String folder) throws IOException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null.");
        }

        String fullFolderPath = "/vti_hotel/" + folder;

        Map result = cloudinary.uploader().upload(
                path.getBytes(),
                ObjectUtils.asMap("folder", fullFolderPath)
        );

        UploadResponse response = new UploadResponse();
        response.setUrl(result.get("url").toString());
        response.setPublicId(result.get("public_id").toString());
        response.setFormat(result.get("format").toString());

        return response;
    }
}
