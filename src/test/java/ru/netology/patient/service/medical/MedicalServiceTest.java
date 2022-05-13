package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalServiceTest {

    @Mock
    private static PatientInfoRepository patientInfoRepository;

    @Mock
    private static SendAlertService alertService;

    private static MedicalService medicalService;

    @BeforeEach
    void setUp() {
        PatientInfo patientInfo = new PatientInfo("123", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80)));
        medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        when(patientInfoRepository.getById(anyString())).thenReturn(patientInfo);
    }

    @Test
    void checkBloodPressureTest() {
        medicalService.checkBloodPressure(anyString(), new BloodPressure(60, 120));
        assertsNotNormalParameters();
    }

    @Test
    void checkNormalBloodPressureTest() {
        medicalService.checkBloodPressure(anyString(), new BloodPressure(120, 80));
        assertsNormalParameters();
    }

    @Test
    void checkTemperatureTest() {
        medicalService.checkTemperature(anyString(), new BigDecimal("30"));
        assertsNotNormalParameters();
    }

    @Test
    void checkNormalTemperatureTest() {
        medicalService.checkTemperature(anyString(), new BigDecimal("36.6"));
        assertsNormalParameters();
    }

    void assertsNotNormalParameters() {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(alertService, only()).send(anyString());
        verify(patientInfoRepository, only()).getById(anyString());
        verify(alertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 123, need help", argumentCaptor.getValue());
    }

    void assertsNormalParameters() {
        verify(patientInfoRepository, only()).getById(anyString());
        verify(alertService, never()).send(anyString());
    }
}