package com.telemedicine.Notification_Service.service;
import com.telemedicine.Notification_Service.entity.Notification;
import com.telemedicine.Notification_Service.entity.dto.NotificationResponse;
import com.telemedicine.Notification_Service.entity.dto.NotificationResponseDto;
import com.telemedicine.Notification_Service.exception.DoctorNotFoundException;
import com.telemedicine.Notification_Service.exception.PatientNotFoundException;
import com.telemedicine.Notification_Service.exception.ServiceUnavailableException;
import com.telemedicine.Notification_Service.feign.AppointmentServiceClient;
import com.telemedicine.Notification_Service.feign.VideoCallClient;
import com.telemedicine.Notification_Service.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class NotificationService {
    
    private  final NotificationRepository notificationRepository;
//    @Autowired
//    private ModelMapper modelMapper;
    private  final VideoCallClient videoCallClient;
    private final AppointmentServiceClient appointmentServiceClient;

   

  

	public NotificationResponseDto sendNotification(Integer appointmentId) {
    		
    	 String meetingLink = videoCallClient.generateMeetingLink(appointmentId).getBody();
      
            // Call the Video Call Service to get doctor and patient IDs using appointId
//            VideoConsultation videoConsultation = videoCallClient.getVideoConsultation(appointmentId).getBody();
//           
            if (Objects.isNull(meetingLink)) {
                throw new PatientNotFoundException("Patient or doctor not found for Appointment ID " + appointmentId);
            }
            if(appointmentServiceClient.getAppointmentDetails(appointmentId).getStatusCode()== HttpStatus.GATEWAY_TIMEOUT){
                throw new ServiceUnavailableException("service is down");
            }
            NotificationResponse notificationResponse = appointmentServiceClient.getAppointmentDetails(appointmentId).getBody();
            //adding the Response of DoctorId and PatientId
            NotificationResponseDto data = new NotificationResponseDto(meetingLink,appointmentId,"Join meeting on time", notificationResponse.getDoctorId(),notificationResponse.getPatientId());
            // Additional logic for sending notifications
            // ...

//            System.out.println(data);
            
            saveNotification(data);
            String appointmentDetailsResponse = appointmentServiceClient.updateRecord(appointmentId).getBody();
            if(Objects.equals(appointmentDetailsResponse, "")) {
                throw new ServiceUnavailableException("service is down");
            }
            return data;
    }
    
    public Notification convertDtoToNotification(NotificationResponseDto notificationResponseDto) {
    	Notification noti = new Notification();
    	noti.setAppointmentId(notificationResponseDto.getAppointmentId());
    	noti.setDoctorId(notificationResponseDto.getDoctorId());
    	noti.setPatientId(notificationResponseDto.getPatientId());
    	noti.setMessage(notificationResponseDto.getMessage());
    	noti.setMeetingLink(notificationResponseDto.getMeetingLink());
    	return noti;
    }
    
    public NotificationResponseDto convertNotificaionToDto(Notification notification) {
    	NotificationResponseDto notires = new NotificationResponseDto();
    	notires.setAppointmentId(notification.getAppointmentId());
    	notires.setDoctorId(notification.getDoctorId());
    	notires.setPatientId(notification.getPatientId());
    	notires.setMessage(notification.getMessage());
    	notires.setMeetingLink(notification.getMeetingLink());
    	return notires;
    }

    private void saveNotification(NotificationResponseDto notificationResponseDto) {
//    	Notification noti = new Notification();
//    	noti.setAppointmentId(notificationResponseDto.getAppointmentId());
//    	noti.setDoctorId(notificationResponseDto.getDoctorId());
//    	noti.setPatientId(notificationResponseDto.getPatientId());
//    	noti.setMessage(notificationResponseDto.getMessage());
//    	noti.setMeetingLink(notificationResponseDto.getMeetingLink());
    	Notification noti = convertDtoToNotification(notificationResponseDto);
    	System.out.println(noti);
        notificationRepository.save(noti);
    }

    public List<NotificationResponseDto> getNotifications(Integer doctorId) {
       
            List<Notification> notifications = notificationRepository.findByDoctorId(doctorId).orElseThrow(()-> 
            new DoctorNotFoundException("No doctor found with this doctorId"));
            return notifications.stream()
                    .map(notification -> convertNotificaionToDto(notification) )
                    .collect(Collectors.toList());
    	}
    
    public List<NotificationResponseDto> getNotifications(String patientId) {
        
        List<Notification> notifications = notificationRepository.findByPatientId(patientId).orElseThrow(()-> 
        new PatientNotFoundException("No patient found with this patientId"));
        

        return notifications.stream()
                .map(notification -> convertNotificaionToDto(notification))
                .collect(Collectors.toList());
	}
    
    
}
