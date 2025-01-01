package hotel.vti_hotel.service.global;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hotel.vti_hotel.config.jwt.JwtTokenUtil;
import hotel.vti_hotel.modal.entity.Account;
import hotel.vti_hotel.modal.entity.ImageUpload;
import hotel.vti_hotel.modal.request.LoginRequest;
import hotel.vti_hotel.modal.request.UploadRequest;
import hotel.vti_hotel.modal.response.AuthResponse;
import hotel.vti_hotel.modal.response.UploadResponse;
import hotel.vti_hotel.repository.AccountRepository;
import hotel.vti_hotel.repository.ImageUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;

import static hotel.vti_hotel.support.Calculate.calculateFileHash;

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

    @Autowired
    private ImageUploadRepository imageUploadRepository;

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
        return new AuthResponse(String.valueOf(account.getId()), token, request.getIdentifier(), account.getImageCard(), userDetails.getAuthorities());
    }

    @Override
    public UploadResponse upload(UploadRequest request) throws IOException, NoSuchAlgorithmException {
        if (request.getFile() == null || request.getFile().isEmpty()) {
            throw new IllegalArgumentException("File is empty or null.");
        }

        try {
            // Tính hash của file để kiểm tra xem đã tồn tại hay chưa
            String fileHash = calculateFileHash(request.getFile());

            // Tìm ảnh trong DB theo hash
            ImageUpload existingImage = imageUploadRepository.findByHash(fileHash);

            if (Objects.nonNull(existingImage)) {
                // Nếu ảnh đã tồn tại, trả về thông tin từ cơ sở dữ liệu
                ImageUpload image = existingImage;
                UploadResponse response = new UploadResponse();
                response.setUrl(image.getUrl());
                response.setPublicId(image.getPublicId());
                response.setFormat(image.getFormat());
                return response;
            }

            // Nếu ảnh chưa tồn tại, upload lên Cloudinary
            String fullFolderPath = "/vti_hotel/" + request.getFolder();

            Map result = cloudinary.uploader().upload(
                    request.getFile().getBytes(),
                    ObjectUtils.asMap("folder", fullFolderPath)
            );

            // Lưu thông tin ảnh vào cơ sở dữ liệu
            ImageUpload newImage = new ImageUpload();
            newImage.setHash(fileHash);
            newImage.setUrl(result.get("url").toString());
            newImage.setPublicId(result.get("public_id").toString());
            newImage.setFormat(result.get("format").toString());
            imageUploadRepository.save(newImage);

            // Trả về thông tin ảnh đã upload
            UploadResponse response = new UploadResponse();
            response.setUrl(newImage.getUrl());
            response.setPublicId(newImage.getPublicId());
            response.setFormat(newImage.getFormat());

            return response;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}