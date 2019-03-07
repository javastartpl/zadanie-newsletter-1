class Names {
    private String yearOfBirth;
    private String gender;
    private String firstNameChild;
    private String count;
    private String rank;

    Names(String yearOfBirth, String gender, String firstNameChild, String count, String rank) {
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.firstNameChild = firstNameChild;
        this.count = count;
        this.rank = rank;
    }

    String getFirstNameChild() {
        return firstNameChild;
    }

    String getGender() {
        return gender;
    }
}
