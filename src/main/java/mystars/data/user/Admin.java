package mystars.data.user;

public class Admin extends User {

    private String name;
    private String staffId;
    private char gender;
    private String nationality;
    private String username;

    public Admin(String name, String matricNo, char gender, String nationality, String username) {
        this.name = name;
        this.staffId = staffId;
        this.gender = gender;
        this.nationality = nationality;
        this.username = username;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffId() { return staffId; }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public char getGender() { return gender; }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getNationality() { return nationality; }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /*public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }*/
}
