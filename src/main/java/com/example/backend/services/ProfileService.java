package com.example.backend.services;

import com.example.backend.config.FileUploadUtil;
import com.example.backend.dto.request.ProfileCreateRequest;
import com.example.backend.dto.request.ProfileUpdateRequest;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.*;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.ExaminationRepository;
import com.example.backend.repositories.NationRepository;
import com.example.backend.repositories.ProfileRepository;
import com.example.backend.repositories.ReligionRepository;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private ReligionRepository religionRepository;

    @Autowired
    private ExaminationRepository examinationRepository;



    public Profile createProfile(ProfileCreateRequest profileCreateRequest, MultipartFile image) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uploadDir = "D:\\TuongDi\\LVTN\\Code\\backend\\uploads";
        String filePath = Paths.get(uploadDir, fileName).toString();
        FileUploadUtil.saveFile(uploadDir, fileName, image);

        String imageUrl = "http://localhost:8080/image/" + fileName;

        Nation nation = nationRepository.findById(profileCreateRequest.getNationId())
                .orElseThrow(() -> new EntityNotFoundException("profileCreateRequest not found with ID: " + profileCreateRequest.getNationId()));

        Religion religion = religionRepository.findById(profileCreateRequest.getReligionId())
                .orElseThrow(() -> new EntityNotFoundException("profileCreateRequest not found with ID: " + profileCreateRequest.getReligionId()));

        Examinations examinations = examinationRepository.findById(profileCreateRequest.getExaminationsId())
                .orElseThrow(() -> new EntityNotFoundException("profileCreateRequest not found with ID: " + profileCreateRequest.getExaminationsId()));

        System.out.println(nation);
        System.out.println(religion);
        System.out.println(examinations);

        Profile profile = new Profile();
        profile.setName(profileCreateRequest.getName());
        profile.setDateofbirth(profileCreateRequest.getDateofbirth());
        profile.setSex(profileCreateRequest.getSex());
        profile.setIdcard(profileCreateRequest.getIdcard());
        profile.setPhone(profileCreateRequest.getPhone());
        profile.setImage(imageUrl);
        profile.setNote(profileCreateRequest.getNote());
        profile.setNation(nation);
        profile.setReligion(religion);
        profile.setProvince(profileCreateRequest.getProvince());
        profile.setDistrict(profileCreateRequest.getDistrict());
        profile.setWards(profileCreateRequest.getWards());
        profile.setExaminations(examinations);

        return profileRepository.save(profile);
    }


    public Optional<Profile> updateProfile(ProfileUpdateRequest profileUpdateRequest, MultipartFile image, int id) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uploadDir = "D:\\TuongDi\\LVTN\\Code\\backend\\uploads";
        String filePath = Paths.get(uploadDir, fileName).toString();
        FileUploadUtil.saveFile(uploadDir, fileName, image);

        String imageUrl = "http://localhost:8080/image/" + fileName;

        try {
            image.transferTo(new File(imageUrl));
        } catch (IOException e) {
        }
        Nation nation = nationRepository.findById(profileUpdateRequest.getNationId())
                .orElseThrow(() -> new EntityNotFoundException("profileUpdateRequest not found with ID: " + profileUpdateRequest.getNationId()));

        Religion religion = religionRepository.findById(profileUpdateRequest.getReligionId())
                .orElseThrow(() -> new EntityNotFoundException("profileUpdateRequest not found with ID: " + profileUpdateRequest.getReligionId()));

        Examinations examinations = examinationRepository.findById(profileUpdateRequest.getExaminationsId())
                .orElseThrow(() -> new EntityNotFoundException("profileUpdateRequest not found with ID: " + profileUpdateRequest.getExaminationsId()));

        profileUpdateRequest.setNationId(nation.getId());
        profileUpdateRequest.setReligionId(religion.getId());
        profileUpdateRequest.setExaminationsId(examinations.getId());
        profileUpdateRequest.setImage(imageUrl);

        return profileRepository.findById(id).map(profile -> {
            if (profileUpdateRequest.getName() != null)
                profile.setName(profileUpdateRequest.getName());
            if (profileUpdateRequest.getDateofbirth() != null)
                profile.setDateofbirth(profileUpdateRequest.getDateofbirth());
            if (profileUpdateRequest.getSex() != null)
                profile.setSex(profileUpdateRequest.getSex());
            if (profileUpdateRequest.getIdcard() != null)
                profile.setIdcard(profileUpdateRequest.getIdcard());
            if (profileUpdateRequest.getPhone() != null)
                profile.setPhone(profileUpdateRequest.getPhone());
            if (profileUpdateRequest.getImage() != null)
                profile.setImage(profileUpdateRequest.getImage());
            if (profileUpdateRequest.getNote() != null)
                profile.setNote(profileUpdateRequest.getNote());
            if (profileUpdateRequest.getNationId() != 0)
                profile.setNation(nation);
            if (profileUpdateRequest.getReligionId() != 0)
                profile.setReligion(religion);
            if (profileUpdateRequest.getProvince() != null)
                profile.setProvince(profileUpdateRequest.getProvince());
            if (profileUpdateRequest.getDistrict() != null)
                profile.setDistrict(profileUpdateRequest.getDistrict());
            if (profileUpdateRequest.getWards() != null)
                profile.setWards(profileUpdateRequest.getWards());
            if (profileUpdateRequest.getExaminationsId() != 0)
                profile.setExaminations(examinations);
            return profileRepository.save(profile);
        });
    }

    public List<Profile> listProfile() {
        return profileRepository.findAll();
    }

    public Optional<Profile> findProfileById(int id) {
        var result = profileRepository.findById(id);
        if(result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteProfileById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = profileRepository.findById(id);
        try {
            String fileName = isResult.get().getImage().substring(isResult.get().getImage().lastIndexOf('/') + 1);
            Path imagePath = Paths.get("uploads", fileName);
            Files.deleteIfExists(imagePath);
            profileRepository.deleteById(id);
            message.setMessage("delete religion by id successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            message.setMessage("delete religion by id failed!");
        }
        return message;
    }
}
