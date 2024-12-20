package hotel.vti_hotel.config.more;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dphnbm561",
                "api_key", "916648135284657",
                "api_secret", "WmMcHCPkqt__ycZGHf2aFJdXIpE",
                "secure", true
        ));
    }
}
