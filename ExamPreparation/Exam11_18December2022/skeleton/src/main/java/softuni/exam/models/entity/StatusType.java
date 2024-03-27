package softuni.exam.models.entity;

public enum StatusType {
    UNEMPLOYED, EMPLOYED, FREELANCER;
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
