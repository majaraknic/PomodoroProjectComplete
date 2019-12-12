package eu.execom.pomodoro.web.dto;

public class TeamMemberDto {

    private Long teamId;

    private String email;

    public Long getTeamId() {
        return teamId;
    }

    public TeamMemberDto() {
        
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TeamMemberDto(Long teamId, String email) {
        this.teamId = teamId;
        this.email = email;
    }
}
