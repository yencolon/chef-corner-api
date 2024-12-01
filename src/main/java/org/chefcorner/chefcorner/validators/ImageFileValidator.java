package org.chefcorner.chefcorner.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.chefcorner.chefcorner.annotations.ValidMultipartImage;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ValidMultipartImage, MultipartFile> {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false; // File must not be null or empty
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size must be less than 5 MB.")
                    .addConstraintViolation();
            return false;
        }

        // Check file type (e.g., only images allowed)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Only image files are allowed.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
