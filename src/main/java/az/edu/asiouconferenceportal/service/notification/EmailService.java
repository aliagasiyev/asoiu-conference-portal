package az.edu.asiouconferenceportal.service.notification;

public interface EmailService {
	void sendEmail(String to, String subject, String text);
}


