package model;

public class Member {

    private int memberId;
    private String name;
    private String email;
    private int activeLoans;

    public Member(
            int memberId,
            String name,
            String email,
            int activeLoans
    ) {

        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.activeLoans = activeLoans;
    }

    public int getMemberId() {

        return memberId;
    }

    public String getName() {

        return name;
    }

    public String getEmail() {

        return email;
    }

    public int getActiveLoans() {

        return activeLoans;
    }

    public void setMemberId(int memberId) {

        this.memberId = memberId;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setActiveLoans(int activeLoans) {

        this.activeLoans = activeLoans;
    }

    @Override
    public String toString() {

        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", activeLoans=" + activeLoans +
                '}';
    }
}