package lk.ijse.spring.controller;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.CustomerDTO;
import lk.ijse.spring.service.CarService;
import lk.ijse.spring.service.CustomerService;
import lk.ijse.spring.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/v1/car")
@CrossOrigin
public class CarController {

    @Autowired
    CarService carService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveCar(@RequestPart("file") MultipartFile[] files, @RequestPart("car") CarDTO carDTO) {
        System.out.println("working01");
            String pic01 = null; String pic02 = null; String pic03 = null; String pic04 = null;
        for (MultipartFile myFile: files) {
            try {
                String projectPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getParentFile().getAbsolutePath();
                File uploadsDir = new File(projectPath + "/uploads");
                uploadsDir.mkdir();
                myFile.transferTo(new File(uploadsDir.getAbsolutePath() + "/" + myFile.getOriginalFilename()));

                if( pic01==null){
                    pic01=uploadsDir.getAbsolutePath() + "/" + myFile.getOriginalFilename();
                }else if (pic02==null){
                    pic02=uploadsDir.getAbsolutePath() + "/" + myFile.getOriginalFilename();
                }else if (pic03==null){
                    pic03=uploadsDir.getAbsolutePath() + "/" + myFile.getOriginalFilename();
                }else {
                    pic04=uploadsDir.getAbsolutePath() + "/" + myFile.getOriginalFilename();
                }
                System.out.println("working02");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                return new ResponseUtil(500, " Registration failed", null);
            }
        }

//        carDTO.getCarImgDetailDTO().setFront("front.png");
//        carDTO.getCarImgDetailDTO().setBack("Back.png");
//        carDTO.getCarImgDetailDTO().setSide_01("side01.png");
//        carDTO.getCarImgDetailDTO().setSide_02("side02.png");
        //car eke pic save wen nehe id eka genaret wen nehe
        System.out.println("working03");
        carService.saveCar(carDTO);

        return new ResponseUtil(200,"Registration Success",null);
    }
}
