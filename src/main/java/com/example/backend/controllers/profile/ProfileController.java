package com.example.backend.controllers.profile;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Profile;
import com.example.backend.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService service;

    @PostMapping()
    public ResponseEntity<Profile> createProfile(@RequestParam("name") String name,
                                        @RequestParam("dateofbirth")LocalDate dateofbirth,
                                        @RequestParam("sex") String sex,
                                        @RequestParam("idcard") String idcard,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("image") MultipartFile image,
                                        @RequestParam("note") String note,
                                        @RequestParam("nation_id") String nation_id,
                                        @RequestParam("religion_id") String religion_id,
                                        @RequestParam("province") String province,
                                        @RequestParam("district") String district,
                                        @RequestParam("wards") String wards,
                                        @RequestParam("examinations_id") String examinations_id ) {

            Profile profile = new Profile();
            profile.setName(name);
            profile.setDateofbirth(dateofbirth);
            profile.setSex(sex);
            profile.setIdcard(idcard);
            profile.setPhone(phone);
            profile.setNote(note);
            profile.setNationId(Integer.parseInt(nation_id));
            profile.setReligionId(Integer.parseInt(religion_id));
            profile.setProvince(province);
            profile.setDistrict(district);
            profile.setWards(wards);
            profile.setExaminationsId(Integer.parseInt(examinations_id));
            return new ResponseEntity<>(service.createProfile(profile, image), HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable(value = "id") int id,
                                 @RequestParam("name") String name,
                                 @RequestParam("dateofbirth")LocalDate dateofbirth,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("idcard") String idcard,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("image") MultipartFile image,
                                 @RequestParam("note") String note,
                                 @RequestParam("nation_id") String nation_id,
                                 @RequestParam("religion_id") String religion_id,
                                 @RequestParam("province") String province,
                                 @RequestParam("district") String district,
                                 @RequestParam("wards") String wards,
                                 @RequestParam("examinations_id") String examinations_id ) {


        Profile profile = new Profile();
        profile.setName(name);
        profile.setDateofbirth(dateofbirth);
        profile.setSex(sex);
        profile.setIdcard(idcard);
        profile.setPhone(phone);
        profile.setNote(note);
        profile.setNationId(Integer.parseInt(nation_id));
        profile.setReligionId(Integer.parseInt(religion_id));
        profile.setProvince(province);
        profile.setDistrict(district);
        profile.setWards(wards);
        profile.setExaminationsId(Integer.parseInt(examinations_id));
        return ResponseEntity.ok(service.updateProfile(profile, image, id));
    }

    @GetMapping()
    public ResponseEntity<List<Profile>> getAllProfile() {
        return ResponseEntity.ok(service.listProfile());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Profile>> getProfileById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(service.findProfileById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteProfileById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(service.deleteProfileById(id));
    }

}
