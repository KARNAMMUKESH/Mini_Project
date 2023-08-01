package online_ticket;

public class Ticket {
    private int id;
    private int userId;
    private String issueDescription;
    private String status;
    private String createdAt;
    private String resolvedAt;

    public Ticket(int id, int userId, String issueDescription, String status, String createdAt, String resolvedAt) {
        this.id = id;
        this.userId = userId;
        this.issueDescription = issueDescription;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

   

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getIssueDescription() {
		return issueDescription;
	}



	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}



	public String getResolvedAt() {
		return resolvedAt;
	}



	public void setResolvedAt(String resolvedAt) {
		this.resolvedAt = resolvedAt;
	}



	@Override
    public String toString() {
        return "Ticket ID: " + id +
               ", User ID: " + userId +
               ", Issue Description: " + issueDescription +
               ", Status: " + status +
               ", Created At: " + createdAt +
               ", Resolved At: " + resolvedAt;
    }
}
