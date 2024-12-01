package org.chefcorner.chefcorner.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileInputStream;
import java.io.IOException;

// TEST CONTROLLER FOR IMAGES
// NOT USED IN PRODUCTION

@Controller
@RequestMapping("/uploads")
public class ResourceController {

    //root path for image files
    private String FILE_PATH_ROOT = "./uploads/";

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = new FileInputStream(FILE_PATH_ROOT + filename).readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

}
