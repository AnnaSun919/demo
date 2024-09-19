package demo.security;
import java.time.Instant;


public class AppToken {

    private String userId;

    private Instant expiresDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Instant getExpiresDate() {
		return expiresDate;
	}

	public void setExpiresDate(Instant expiresDate) {
		this.expiresDate = expiresDate;
	}

}