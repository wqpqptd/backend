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
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        String uploadDir = "D:\\github\\backend\\uploads";
        String filePath = Paths.get(uploadDir, fileName).toString();
        FileUploadUtil.saveFile(uploadDir, fileName, image);

        try {
            image.transferTo(new File(filePath));
        } catch (IOException e) {
        }
        profileCreateRequest.setImage(filePath);
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
        profile.setImage(profileCreateRequest.getImage());
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
        String uploadDir = "D:\\intellij\\backend\\uploads";
        String filePath = Paths.get(uploadDir, fileName).toString();
        FileUploadUtil.saveFile(uploadDir, fileName, image);

        try {
            image.transferTo(new File(filePath));
        } catch (IOException e) {
        }
        profileUpdateRequest.setImage(filePath);
        Nation nation = nationRepository.findById(profileUpdateRequest.getNationId())
                .orElseThrow(() -> new EntityNotFoundException("profileUpdateRequest not found with ID: " + profileUpdateRequest.getNationId()));

        Religion religion = religionRepository.findById(profileUpdateRequest.getReligionId())
                .orElseThrow(() -> new EntityNotFoundException("profileUpdateRequest not found with ID: " + profileUpdateRequest.getReligionId()));

        Examinations examinations = examinationRepository.findById(profileUpdateRequest.getExaminationsId())
                .orElseThrow(() -> new EntityNotFoundException("profileUpdateRequest not found with ID: " + profileUpdateRequest.getExaminationsId()));


        var result = profileRepository.findById(id).map(profile -> {
            profile.setName(profileUpdateRequest.getName());
            profile.setDateofbirth(profileUpdateRequest.getDateofbirth());
            profile.setSex(profileUpdateRequest.getSex());
            profile.setIdcard(profileUpdateRequest.getIdcard());
            profile.setPhone(profileUpdateRequest.getPhone());
            profile.setImage(profileUpdateRequest.getImage());
            profile.setNote(profileUpdateRequest.getNote());
            profile.setNation(nation);
            profile.setReligion(religion);
            profile.setProvince(profileUpdateRequest.getProvince());
            profile.setDistrict(profileUpdateRequest.getDistrict());
            profile.setWards(profileUpdateRequest.getWards());
            profile.setExaminations(examinations);
            return profileRepository.save(profile);
        });
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_UPDATE);
        }
        return result;
    }

    public List<Profile> listProfile() {
        var result = profileRepository.findAll();
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_GET_ALL_LIST);
        }
        return result;
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
        if(isResult.isPresent()){
            profileRepository.deleteById(id);
            message.setMessage("delete profile by id successfully!");
        }else{
            message.setMessage("delete profile by id failed!");
        }
        return message;
    }
}
