package com.example.backend.services;

import com.example.backend.dto.request.DetailProfileCreateRequest;
import com.example.backend.dto.request.DetailProfileUpdateRequest;
import com.example.backend.dto.response.DetailProfileResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.*;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetailProfileService {
    @Autowired
    private DetailProfileRepository detailProfileRepository;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private ReligionRepository religionRepository;
    @Autowired
    private ExaminationRepository examinationRepository;
    @Autowired
    private OfficerRepository officerRepository;
    @Autowired
    private DriverLicenseClassRepository driverLicenseClassRepository;
    @Autowired
    private DriverLicenseDurationRepository driverLicenseDurationRepository;
    @Autowired
    private DetailExaminationsRepository detailExaminationsRepository;
    @Autowired
    private DriverLicenseRepository driverLicenseRepository;

    @Autowired
    private ProfileRepository profileRepository;



    public DetailProfile createDetailProfile(DetailProfileCreateRequest detailProfileCreateRequest) {
             Profile profile = profileRepository.findById(detailProfileCreateRequest.getProfileId())
                    .orElseThrow(() -> new EntityNotFoundException("detailProfileCreateRequest profile not found with ID: " + detailProfileCreateRequest.getProfileId()));


            DetailProfile detailProfile = new DetailProfile();
            detailProfile.setDateExamination(detailProfileCreateRequest.getDateExamination());
            detailProfile.setResultTheoretical(detailProfileCreateRequest.getResultTheoretical());
            detailProfile.setResultPractice(detailProfileCreateRequest.getResultPractice());
            detailProfile.setProfile(profile);
            if (detailProfileCreateRequest.getDriverLicenseId() == 0 || detailProfileCreateRequest.getResultTheoretical() < 50.0 || detailProfileCreateRequest.getResultPractice() < 50.0){
                detailProfile.setDriverLicenseId(detailProfileCreateRequest.getDriverLicenseId());
            }else {
                DriverLicense driverLicense = driverLicenseRepository.findById(detailProfileCreateRequest.getDriverLicenseId())
                        .orElseThrow(() -> new EntityNotFoundException("detailProfileCreateRequest driverLicense not found with ID: " + detailProfileCreateRequest.getDriverLicenseId()));

                detailProfile.setDriverLicenseId(driverLicense);
            }

            return detailProfileRepository.save(detailProfile);

    }

    public Optional<DetailProfile> updateDetailProfile(DetailProfileUpdateRequest detailProfileUpdateRequest) {
        Profile profile = profileRepository.findById(detailProfileUpdateRequest.getProfileId())
                .orElseThrow(() -> new EntityNotFoundException("detailProfileUpdateRequest not found with ID: " + detailProfileUpdateRequest.getProfileId()));

        return detailProfileRepository.findById(detailProfileUpdateRequest.getId()).map(update -> {
            update.setDateExamination(detailProfileUpdateRequest.getDateExamination());
            update.setResultTheoretical(detailProfileUpdateRequest.getResultTheoretical());
            update.setResultPractice(detailProfileUpdateRequest.getResultPractice());
            update.setProfileId(profile);

            if (detailProfileUpdateRequest.getDriverLicenseId() == 0 ||
                    detailProfileUpdateRequest.getResultPractice() < 50.0 || detailProfileUpdateRequest.getResultPractice() > 100.0 ||
                    detailProfileUpdateRequest.getResultTheoretical() < 50.0 || detailProfileUpdateRequest.getResultTheoretical() > 100.0) {
                update.setDriverLicenseId(null);
            } else {
                DriverLicense driverLicense = driverLicenseRepository.findById(detailProfileUpdateRequest.getDriverLicenseId())
                        .orElseThrow(() -> new EntityNotFoundException("detailProfileUpdateRequest not found with ID: " + detailProfileUpdateRequest.getDriverLicenseId()));
                update.setDriverLicenseId(driverLicense);
            }

            return detailProfileRepository.save(update);
        });
    }


    public List<DetailProfileResponse> detailProfileResponseList() {
        return detailProfileRepository.findAll()
                .stream()
                .map(tmp -> {
                    DetailProfileResponse response = new DetailProfileResponse();
                    response.setId(tmp.getId());
                    response.setDateExamination(tmp.getDateExamination());
                    response.setResultTheoretical(tmp.getResultTheoretical());
                    response.setResultPractice(tmp.getResultPractice());
                    Profile profile = tmp.getProfileId();
                    if (profile != null) {
                        response.setId(profile.getId());
                        response.setName(profile.getName());
                        response.setSex(profile.getSex());
                        response.setIdcard(profile.getIdcard());
                        response.setPhone(profile.getPhone());
                        response.setImage(profile.getImage());
                        response.setNote(profile.getNote());
                        Nation nation = tmp.getProfileId().getNationId();
                        if (nation != null) {
                            response.setNationName(nation.getNationName());
                        }
                        Religion religion = tmp.getProfileId().getReligionId();
                        if (religion != null) {
                            response.setReligionName(religion.getReligionName());
                        }
                        response.setProvince(profile.getProvince());
                        response.setDistrict(profile.getDistrict());
                        response.setWards(profile.getWards());
                        Examinations examinations = tmp.getProfileId().getExaminationsId();
                        if (examinations != null) {
                            response.setExaminationsName(examinations.getExaminationsName());
                            response.setDateExamination(examinations.getExaminationsDate());
                        }
                    }
                    DriverLicense driverLicense = tmp.getDriverLicenseId();
                    if (driverLicense != null) {
                        DriverLicenseClass aClass = tmp.getDriverLicenseId().getDriverLicenseClassId();
                        if (aClass != null) {
                            response.setDriverLicenseClass(aClass.getDriverLicenseClassName());
                        }
                        DriverLicenseDuration duration = tmp.getDriverLicenseId().getDriverLicenseDurationId();
                        if (duration != null) {
                            response.setDriverLicenseDuration(duration.getDuration());
                        }
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Optional<DetailProfileResponse> findDetailProfile(int id) {
        var result = detailProfileRepository.findById(id)
                .map(tmp -> {
                    DetailProfileResponse response = new DetailProfileResponse();
                    response.setId(tmp.getId());
                    response.setDateExamination(tmp.getDateExamination());
                    response.setResultTheoretical(tmp.getResultTheoretical());
                    response.setResultPractice(tmp.getResultPractice());
                    Profile profile = tmp.getProfileId();
                    if (profile != null) {
                        response.setId(profile.getId());
                        response.setName(profile.getName());
                        response.setSex(profile.getSex());
                        response.setIdcard(profile.getIdcard());
                        response.setPhone(profile.getPhone());
                        response.setImage(profile.getImage());
                        response.setNote(profile.getNote());
                        Nation nation = tmp.getProfileId().getNationId();
                        if (nation != null) {
                            response.setNationName(nation.getNationName());
                        }
                        Religion religion = tmp.getProfileId().getReligionId();
                        if (religion != null) {
                            response.setReligionName(religion.getReligionName());
                        }
                        response.setProvince(profile.getProvince());
                        response.setDistrict(profile.getDistrict());
                        response.setWards(profile.getWards());
                        Examinations examinations = tmp.getProfileId().getExaminationsId();
                        if (examinations != null) {
                            response.setExaminationsName(examinations.getExaminationsName());
                            response.setDateExamination(examinations.getExaminationsDate());
                        }
                    }
                    DriverLicense driverLicense = tmp.getDriverLicenseId();
                    if (driverLicense != null) {
                        DriverLicenseClass aClass = tmp.getDriverLicenseId().getDriverLicenseClassId();
                        if (aClass != null) {
                            response.setDriverLicenseClass(aClass.getDriverLicenseClassName());
                        }
                        DriverLicenseDuration duration = tmp.getDriverLicenseId().getDriverLicenseDurationId();
                        if (duration != null) {
                            response.setDriverLicenseDuration(duration.getDuration());
                        }
                    }
                    return response;
                });
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteDetailProfileById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = detailProfileRepository.findById(id);
        if(isResult.isPresent()){
            detailProfileRepository.deleteById(id);
            message.setMessage("delete detail profile by id successfully!");
        }else{
            message.setMessage("delete detail profile by id failed!");
        }
        return message;
    }
}
