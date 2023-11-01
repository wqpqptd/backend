package com.example.backend.services;

import com.example.backend.config.FileUploadUtil;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Profile;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.ProfileRepository;
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
    private final String root = "D:\\intellij\\backend\\uploads";


    public Profile createProfile(Profile profile, MultipartFile image) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uploadDir = root;
        String filePath = Paths.get(uploadDir, fileName).toString();
        FileUploadUtil.saveFile(uploadDir, fileName, image);

        try {
            image.transferTo(new File(filePath));
        } catch (IOException e) {
        }

        profile.setImage(filePath);

        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile profile, MultipartFile image, int id) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uploadDir = root;
        String filePath = Paths.get(uploadDir, fileName).toString();
        FileUploadUtil.saveFile(uploadDir, fileName, image);
        profile.setImage(filePath);
        try {
            image.transferTo(new File(filePath));
        } catch (IOException e) {
        }

        var result = profileRepository.findById(id);
        if(result.isPresent()){
            var tmp = result.get();
            tmp.setId(id);
            tmp.setName(profile.getName());
            tmp.setSex(profile.getSex());
            tmp.setIdcard(profile.getIdcard());
            tmp.setDateofbirth(profile.getDateofbirth());
            tmp.setPhone(profile.getPhone());
            tmp.setImage(profile.getImage());
            tmp.setNote(profile.getNote());
            tmp.setNationId(profile.getNationId());
            tmp.setReligionId(profile.getReligionId());
            tmp.setProvince(profile.getProvince());
            tmp.setDistrict(profile.getDistrict());
            tmp.setWards(profile.getWards());
            tmp.setExaminationsId(profile.getExaminationsId());
            return profileRepository.save(tmp);
        }
        return null;
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
