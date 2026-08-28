package com.springboot.scm.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // ============================================================
    // UPLOAD FILE
    // ============================================================

    @Override
    public String uploadFile(MultipartFile file, String filename) {

        try {

            // ----------------------------------------------------
            // Validate file
            // ----------------------------------------------------

            if (file == null || file.isEmpty()) {
                return null;
            }

            // ----------------------------------------------------
            // Validate filename
            // ----------------------------------------------------

            if (filename == null || filename.isBlank()) {
                return null;
            }

            // ----------------------------------------------------
            // Get original extension
            // ----------------------------------------------------

            String originalFilename = file.getOriginalFilename();

            String extension = "";

            if (originalFilename != null
                    && originalFilename.contains(".")) {

                extension = originalFilename
                        .substring(originalFilename.lastIndexOf(".") + 1)
                        .toLowerCase();
            }

            // ----------------------------------------------------
            // Remove extension from public ID
            //
            // IMPORTANT:
            // Image/video public_id should NOT contain extension.
            // Raw public_id SHOULD contain extension.
            // ----------------------------------------------------

            String publicId = filename;

            if (publicId.contains(".")) {

                publicId = publicId.substring(
                        0,
                        publicId.lastIndexOf(".")
                );
            }

            // ----------------------------------------------------
            // Detect resource type
            // ----------------------------------------------------

            String resourceType = "image";

            if (isVideo(extension)) {

                resourceType = "video";

            } else if ("pdf".equalsIgnoreCase(extension)) {

                /*
                 * We are intentionally storing PDF as RAW.
                 *
                 * For raw resources Cloudinary requires the
                 * extension to be part of the public_id.
                 */

                resourceType = "raw";

                publicId = publicId + ".pdf";
            }

            // ----------------------------------------------------
            // Upload options
            // ----------------------------------------------------

            Map<String, Object> options;

            if ("raw".equals(resourceType)) {

                options = ObjectUtils.asMap(
                        "public_id", publicId,
                        "resource_type", "raw",
                        "overwrite", true
                );

            } else {

                options = ObjectUtils.asMap(
                        "public_id", publicId,
                        "resource_type", resourceType,
                        "overwrite", true
                );
            }

            // ----------------------------------------------------
            // Upload
            // ----------------------------------------------------

            Map<String, Object> uploadResult =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            options
                    );

            // ----------------------------------------------------
            // Get secure URL directly from Cloudinary
            // ----------------------------------------------------

            Object secureUrl = uploadResult.get("secure_url");

            if (secureUrl == null) {

                System.out.println(
                        "Cloudinary upload succeeded but secure_url is null"
                );

                return null;
            }

            String fileUrl = secureUrl.toString();

            System.out.println("------------------------------------");
            System.out.println("Cloudinary Upload Successful");
            System.out.println("Public ID     : " + uploadResult.get("public_id"));
            System.out.println("Resource Type : " + uploadResult.get("resource_type"));
            System.out.println("Format        : " + uploadResult.get("format"));
            System.out.println("Secure URL    : " + fileUrl);
            System.out.println("------------------------------------");

            return fileUrl;

        } catch (IOException e) {

            e.printStackTrace();

            return null;
        }
    }

    // ============================================================
    // CHECK VIDEO EXTENSION
    // ============================================================

    private boolean isVideo(String extension) {

        return "mp4".equalsIgnoreCase(extension)
                || "avi".equalsIgnoreCase(extension)
                || "mov".equalsIgnoreCase(extension)
                || "mkv".equalsIgnoreCase(extension)
                || "webm".equalsIgnoreCase(extension)
                || "flv".equalsIgnoreCase(extension)
                || "wmv".equalsIgnoreCase(extension);
    }

    // ============================================================
    // GET URL FROM PUBLIC ID + RESOURCE TYPE
    // ============================================================

    
    public String getUrlFromPublicId(
            String publicId,
            String resourceType) {

        if (publicId == null || publicId.isBlank()) {
            return null;
        }

        if (resourceType == null || resourceType.isBlank()) {
            resourceType = "image";
        }

        // --------------------------------------------------------
        // IMAGE
        // --------------------------------------------------------

        if ("image".equalsIgnoreCase(resourceType)) {

            return cloudinary.url()
                    .secure(true)
                    .resourceType("image")
                    .transformation(
                            new Transformation<>()
                                    .width(AppConstants.CONTACT_IMAGE_WIDTH)
                                    .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                                    .crop(AppConstants.CONTACT_IMAGE_CROP)
                    )
                    .generate(publicId);
        }

        // --------------------------------------------------------
        // RAW / PDF
        // --------------------------------------------------------

        if ("raw".equalsIgnoreCase(resourceType)) {

            /*
             * Raw public ID must contain the extension.
             */

            if (!publicId.toLowerCase().endsWith(".pdf")) {

                publicId = publicId + ".pdf";
            }

            return cloudinary.url()
                    .secure(true)
                    .resourceType("raw")
                    .generate(publicId);
        }

        // --------------------------------------------------------
        // VIDEO
        // --------------------------------------------------------

        if ("video".equalsIgnoreCase(resourceType)) {

            return cloudinary.url()
                    .secure(true)
                    .resourceType("video")
                    .generate(publicId);
        }

        throw new IllegalArgumentException(
                "Unsupported Cloudinary resource type: "
                        + resourceType
        );
    }

    // ============================================================
    // GET URL FROM PUBLIC ID
    // DEFAULT = IMAGE
    // ============================================================

    @Override
    public String getUrlFromPublicId(String publicId) {

        if (publicId == null || publicId.isBlank()) {
            return null;
        }

        /*
         * This method cannot reliably determine whether a public ID
         * belongs to image, video, or raw just from the public ID.
         *
         * Therefore image is the default.
         */

        return cloudinary.url()
                .secure(true)
                .resourceType("image")
                .generate(publicId);
    }

    // ============================================================
    // DELETE CLOUDINARY FILE
    // ============================================================

    @Override
    public void deleteCloudinaryFile(
            String cloudinaryId,
            String mediaType) throws IOException {

        // --------------------------------------------------------
        // Validate public ID
        // --------------------------------------------------------

        if (cloudinaryId == null || cloudinaryId.isBlank()) {
            return;
        }

        // --------------------------------------------------------
        // Validate media type
        // --------------------------------------------------------

        if (mediaType == null || mediaType.isBlank()) {

            throw new IllegalArgumentException(
                    "Media type cannot be null or empty"
            );
        }

        // --------------------------------------------------------
        // Determine Cloudinary resource type
        // --------------------------------------------------------

        String resourceType;

        if ("VIDEO".equalsIgnoreCase(mediaType)) {

            resourceType = "video";

        } else if ("PDF".equalsIgnoreCase(mediaType)) {

            resourceType = "raw";

        } else if ("IMAGE".equalsIgnoreCase(mediaType)) {

            resourceType = "image";

        } else {

            throw new IllegalArgumentException(
                    "Unsupported media type: " + mediaType
                            + ". Allowed values: IMAGE, VIDEO, PDF"
            );
        }

        // --------------------------------------------------------
        // For raw PDF:
        // public ID must contain .pdf
        // --------------------------------------------------------

        if ("raw".equals(resourceType)
                && !cloudinaryId.toLowerCase().endsWith(".pdf")) {

            cloudinaryId = cloudinaryId + ".pdf";
        }

        // --------------------------------------------------------
        // Delete options
        // --------------------------------------------------------

        Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", resourceType,
                "invalidate", true
        );

        // --------------------------------------------------------
        // Delete from Cloudinary
        // --------------------------------------------------------

        Map<String, Object> result =
                cloudinary.uploader().destroy(
                        cloudinaryId,
                        options
                );

        System.out.println("------------------------------------");
        System.out.println("Cloudinary Delete");
        System.out.println("Public ID     : " + cloudinaryId);
        System.out.println("Resource Type : " + resourceType);
        System.out.println("Result        : " + result);
        System.out.println("------------------------------------");
    }
}