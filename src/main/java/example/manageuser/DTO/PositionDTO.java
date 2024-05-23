package example.manageuser.DTO;

import org.springframework.data.annotation.Id;

import java.util.List;

public class PositionDTO {

    private String positionName;
    private List<Long> userIds;

    public PositionDTO() {
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
